package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import util.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;

//TODO: Fix all of this, add the associated contact name

public class AppointmentDAO {
    public static boolean createAppointment(Appointment appointment) {
        Connection connection = DatabaseConnection.getConnection();
        String sqlInsertStatement = "INSERT INTO appointments(Title, Description, Location, Type, Start, End, Created_By, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        //Create_Date, Created_By, Last_Updated_By,
        DatabaseQuery.createPreparedStatement(connection, sqlInsertStatement);
                //TODO: Add time converter here and in other places?

        try {
            PreparedStatement ps = DatabaseQuery.getPreparedStatement();

            ps.setString(1, appointment.getAppointmentTitle());
            ps.setString(2, appointment.getAppointmentDescription());
            ps.setString(3, appointment.getAppointmentLocation());
            ps.setString(4, appointment.getAppointmentType());
            ps.setString(5, appointment.getAppointmentStart());
            ps.setString(6, appointment.getAppointmentEnd());
            ps.setString(7, CurrentUser.getUserName());
            ps.setString(8, CurrentUser.getUserName());
            ps.setInt(9, appointment.getCustomerID());
            ps.setInt(10, appointment.getUserID());
            ps.setInt(11, appointment.getContactID());

            if (ps.executeUpdate() != 0) {
                return true;
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return false;
    }

    public static Appointment retrieveAppointment(int appointmentID) {
        Connection connection = DatabaseConnection.getConnection();
        //TODO: IS this right??
        String sqlSelectStatement = "SELECT * FROM appointments JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID WHERE Appointment_ID = ?";

        DatabaseQuery.createPreparedStatement(connection, sqlSelectStatement);

        try {
            PreparedStatement ps = DatabaseQuery.getPreparedStatement();
            ps.setInt(1, appointmentID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Appointment_ID");
                String appointmentTitle = rs.getString("Title");
                String appointmentDescription = rs.getString("Description");
                String appointmentLocation = rs.getString("Location");
                String appointmentType = rs.getString("Type");
                String appointmentStart = TimeConverter.databaseToLocalString(rs.getString("Start"));
                String appointmentEnd = TimeConverter.databaseToLocalString(rs.getString("End"));
                LocalDateTime createDate = TimeConverter.databaseToLocal(rs.getString("Create_Date"));
                String createdBy = rs.getString("Created_By");
                LocalDateTime lastUpdate = TimeConverter.databaseToLocal(rs.getString("Last_Update"));
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                String associatedContactName = rs.getString("Contact_Name");

                Appointment newAppointment = new Appointment(id, appointmentTitle, appointmentDescription, appointmentLocation, appointmentType,
                        appointmentStart, appointmentEnd, createDate, createdBy, lastUpdate, lastUpdatedBy, customerID, userID, contactID, associatedContactName);
                return newAppointment;
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    public static ObservableList<Appointment> retrieveAllAppointments(String dateFilter) {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        Connection connection = DatabaseConnection.getConnection();
        String sqlSelectAllStatement;
        switch (dateFilter) {
            case "weekly":
                sqlSelectAllStatement = "SELECT * FROM appointments LEFT JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID WHERE YEARWEEK(Start) = YEARWEEK(CURDATE())";
                break;
            case "monthly":
                sqlSelectAllStatement = "SELECT * FROM appointments LEFT JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID WHERE YEAR(Start) = YEAR(CURDATE()) AND MONTH(Start) = MONTH(CURDATE())";
                break;
            case "all":
                sqlSelectAllStatement = "SELECT * FROM appointments LEFT JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + dateFilter);
        }

        DatabaseQuery.createPreparedStatement(connection, sqlSelectAllStatement);

        try {
            PreparedStatement ps = DatabaseQuery.getPreparedStatement();
            ResultSet rs = ps.executeQuery();
            //TODO: Also fix this for all DAO classes

            while (rs.next()) {
                Appointment appointment = new Appointment();
                appointment.setAppointmentID(rs.getInt("Appointment_ID"));
                appointment.setAppointmentTitle(rs.getString("Title"));
                appointment.setAppointmentDescription(rs.getString("Description"));
                appointment.setAppointmentLocation(rs.getString("Location"));
                appointment.setAppointmentType(rs.getString("Type"));
                appointment.setAppointmentStart(TimeFormatter.getDisplayTime((TimeConverter.databaseToLocalString((rs.getString("Start"))))));
                appointment.setAppointmentEnd(TimeFormatter.getDisplayTime((TimeConverter.databaseToLocalString((rs.getString("End"))))));
                appointment.setCreateDate(TimeConverter.databaseToLocal((rs.getString("Create_Date"))));
                appointment.setCreatedBy(rs.getString("Created_By"));
                appointment.setLastUpdate(TimeConverter.databaseToLocal((rs.getString("Last_Update"))));
                appointment.setLastUpdatedBy(rs.getString("Last_Updated_By"));
                appointment.setCustomerID(rs.getInt("Customer_ID"));
                appointment.setUserID(rs.getInt("User_ID"));
                appointment.setContactID(rs.getInt("Contact_ID"));
                appointment.setAssociatedContactName(rs.getString("Contact_Name"));

                allAppointments.add(appointment);
            }
        } catch (SQLException | ParseException throwable) {
            throwable.printStackTrace();
        }
        return allAppointments;
    }

    public static ObservableList<Appointment> retrieveAllAppointmentsByContact(int contactID) {
        ObservableList<Appointment> allAppointmentsByContact = FXCollections.observableArrayList();
        Connection connection = DatabaseConnection.getConnection();
        String sqlSelectAllStatement = "SELECT * FROM appointments LEFT JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID WHERE appointments.Contact_ID = ?";

        DatabaseQuery.createPreparedStatement(connection, sqlSelectAllStatement);

        try {
            PreparedStatement ps = DatabaseQuery.getPreparedStatement();
            ps.setInt(1, contactID);
            ResultSet rs = ps.executeQuery();
            //TODO: Also fix this for all DAO classes

            while (rs.next()) {
                Appointment appointment = new Appointment();
                appointment.setAppointmentID(rs.getInt("Appointment_ID"));
                appointment.setAppointmentTitle(rs.getString("Title"));
                appointment.setAppointmentDescription(rs.getString("Description"));
                appointment.setAppointmentLocation(rs.getString("Location"));
                appointment.setAppointmentType(rs.getString("Type"));
                appointment.setAppointmentStart(TimeFormatter.getDisplayTime((TimeConverter.databaseToLocalString((rs.getString("Start"))))));
                appointment.setAppointmentEnd(TimeFormatter.getDisplayTime((TimeConverter.databaseToLocalString((rs.getString("End"))))));
                appointment.setCreateDate(TimeConverter.databaseToLocal((rs.getString("Create_Date"))));
                appointment.setCreatedBy(rs.getString("Created_By"));
                appointment.setLastUpdate(TimeConverter.databaseToLocal((rs.getString("Last_Update"))));
                appointment.setLastUpdatedBy(rs.getString("Last_Updated_By"));
                appointment.setCustomerID(rs.getInt("Customer_ID"));
                appointment.setUserID(rs.getInt("User_ID"));
                appointment.setContactID(rs.getInt("Contact_ID"));
                appointment.setAssociatedContactName(rs.getString("Contact_Name"));

                allAppointmentsByContact.add(appointment);
            }
        } catch (SQLException | ParseException throwable) {
            throwable.printStackTrace();
        }
        return allAppointmentsByContact;
    }

    public static ObservableList<Appointment> retrieveAppointmentDeletionInfo(int customerID) {
        ObservableList<Appointment> allAppointmentsByCustomer = FXCollections.observableArrayList();
        Connection connection = DatabaseConnection.getConnection();
        String sqlSelectAllStatement = "SELECT * FROM appointments WHERE Customer_ID = ?";

        DatabaseQuery.createPreparedStatement(connection, sqlSelectAllStatement);

        try {
            PreparedStatement ps = DatabaseQuery.getPreparedStatement();
            ps.setInt(1, customerID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Appointment appointment = new Appointment();
                appointment.setAppointmentID(rs.getInt("Appointment_ID"));
                appointment.setAppointmentTitle(rs.getString("Title"));
                appointment.setAppointmentType(rs.getString("Type"));

                allAppointmentsByCustomer.add(appointment);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return allAppointmentsByCustomer;
    }

    public static Appointment retrieveUsersNextAppointment(int userID) {
        Connection connection = DatabaseConnection.getConnection();
        //TODO: IS this right??
        String sqlSelectStatement = "SELECT * FROM appointments WHERE User_ID = ? AND Start > NOW() ORDER BY Start ASC LIMIT 1";

        DatabaseQuery.createPreparedStatement(connection, sqlSelectStatement);

        try {
            PreparedStatement ps = DatabaseQuery.getPreparedStatement();
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Appointment_ID");
                String appointmentTitle = rs.getString("Title");
                String appointmentDescription = rs.getString("Description");
                String appointmentLocation = rs.getString("Location");
                String appointmentType = rs.getString("Type");
                String appointmentStart = TimeConverter.databaseToLocalString(rs.getString("Start"));
                String appointmentEnd = TimeConverter.databaseToLocalString(rs.getString("End"));
                LocalDateTime createDate = TimeConverter.databaseToLocal(rs.getString("Create_Date"));
                String createdBy = rs.getString("Created_By");
                LocalDateTime lastUpdate = TimeConverter.databaseToLocal(rs.getString("Last_Update"));
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int customerID = rs.getInt("Customer_ID");
                int contactID = rs.getInt("Contact_ID");

                Appointment newAppointment = new Appointment(id, appointmentTitle, appointmentDescription, appointmentLocation, appointmentType,
                        appointmentStart, appointmentEnd, createDate, createdBy, lastUpdate, lastUpdatedBy, customerID, userID, contactID);
                return newAppointment;
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    //TODO: Also fix this for all DAO classes
    public static boolean updateAppointment(int appointmentID, String newAppointmentTitle, String newAppointmentDescription,
                                            String newAppointmentLocation, String newAppointmentType, String newAppointmentStart,
                                            String newAppointmentEnd, int newCustomerID, int newUserID, int newContactID) {
        Connection connection = DatabaseConnection.getConnection();
        String sqlUpdateStatement = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";

        DatabaseQuery.createPreparedStatement(connection, sqlUpdateStatement);

        try {
            PreparedStatement ps = DatabaseQuery.getPreparedStatement();

            ps.setString(1, newAppointmentTitle);
            ps.setString(2, newAppointmentDescription);
            ps.setString(3, newAppointmentLocation);
            ps.setString(4, newAppointmentType);
            ps.setString(5, newAppointmentStart);
            ps.setString(6, newAppointmentEnd);
            ps.setString(7, TimeConverter.localToDatabase(LocalDateTime.now()));
            ps.setString(8, CurrentUser.getUserName());
            ps.setInt(9, newCustomerID);
            ps.setInt(10, newUserID);
            ps.setInt(11, newContactID);
            ps.setInt(12, appointmentID);

            //TODO: Change all to be like this
            if (ps.executeUpdate() != 0) {
                return true;
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return false;
    }

    public static boolean deleteAppointment(int appointmentID) {
        Connection connection = DatabaseConnection.getConnection();
        String sqlDeleteStatement = "DELETE FROM appointments WHERE Appointment_ID = ?";

        DatabaseQuery.createPreparedStatement(connection, sqlDeleteStatement);

        try {
            PreparedStatement ps = DatabaseQuery.getPreparedStatement();

            ps.setInt(1, appointmentID);

            //TODO: Does this need to be changed to the conditional ps.getUpdateCount() > 0 ??
            return ps.executeUpdate() != 0;

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return false;
    }

    public static ObservableList<String> retrieveTypeCountByMonth() {
        ObservableList<String> typeCountList = FXCollections.observableArrayList();
        Connection connection = DatabaseConnection.getConnection();
        String sqlSelectStatement = "SELECT Type, Month(Start) AS Month, Year(Start) AS Year, COUNT(Type) AS Count FROM appointments GROUP BY Year, Month, Type";

        DatabaseQuery.createPreparedStatement(connection, sqlSelectStatement);

        try {
            PreparedStatement ps = DatabaseQuery.getPreparedStatement();
            ResultSet rs = ps.executeQuery();
            //TODO: Also fix this for all DAO classes

            while (rs.next()) {
                int month = rs.getInt("Month");
                int year = rs.getInt("Year");
                String type = rs.getString("Type");
                int typeCount = rs.getInt("Count");

                String resultString = year + " " + Month.of(month).name() + ": " + typeCount + " " + type + " meeting(s)";

                typeCountList.add(resultString);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return typeCountList;
    }

    public static boolean isOverlapping(int customerID, int appointmentID, String startDate, String endDate) {
        Connection connection = DatabaseConnection.getConnection();
        //TODO: Definitely check this against recommended times
        String sqlSelectStatement = "SELECT * FROM appointments WHERE Customer_ID = ? AND ? < End AND ? > Start AND NOT Appointment_ID = ?";

        DatabaseQuery.createPreparedStatement(connection, sqlSelectStatement);

        try {
            PreparedStatement ps = DatabaseQuery.getPreparedStatement();
            ps.setInt(1, customerID);
            ps.setString(2, startDate);
            ps.setString(3, endDate);
            ps.setInt(4, appointmentID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return true;
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return false;
    }
}
