package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import util.DatabaseConnection;
import util.DatabaseQuery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AppointmentDAO {
    public static boolean createAppointment(Appointment appointment) {
        Connection connection = DatabaseConnection.getConnection();
        String sqlInsertStatement = "INSERT INTO appointments(Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

        DatabaseQuery.createPreparedStatement(connection, sqlInsertStatement);

        try {
            PreparedStatement ps = DatabaseQuery.getPreparedStatement();

            ps.setString(1, appointment.getAppointmentTitle());
            ps.setString(2, appointment.getAppointmentDescription());
            ps.setString(3, appointment.getAppointmentLocation());
            ps.setString(4, appointment.getAppointmentType());
            ps.setString(5, appointment.getAppointmentStart());
            ps.setString(6, appointment.getAppointmentEnd());
            ps.setString(7, appointment.getCreateDate());
            ps.setString(8, appointment.getCreatedBy());
            ps.setString(9, appointment.getLastUpdatedBy());
            ps.setInt(10, appointment.getCustomerID());
            ps.setInt(11, appointment.getUserID());
            ps.setInt(12, appointment.getContactID());

            return ps.executeUpdate() != 0;

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return false;
    }

    public static Appointment retrieveAppointment(int appointmentID) {
        Connection connection = DatabaseConnection.getConnection();
        String sqlSelectStatement = "SELECT * FROM appointments WHERE Appointment_ID = ?";

        DatabaseQuery.createPreparedStatement(connection, sqlSelectStatement);

        try {
            PreparedStatement ps = DatabaseQuery.getPreparedStatement();
            ps.setInt(1, appointmentID);
            ResultSet rs = ps.executeQuery();
            //TODO: Come back and fix this for all DAO classes

            while (rs.next()) {
                int id = rs.getInt("Appointment_ID");
                String appointmentTitle = rs.getString("Title");
                String appointmentDescription = rs.getString("Description");
                String appointmentLocation = rs.getString("Location");
                String appointmentType = rs.getString("Type");
                String appointmentStart = rs.getString("Start");
                String appointmentEnd = rs.getString("End");
                String createDate = rs.getString("Create_Date");
                String createdBy = rs.getString("Created_By");
                String lastUpdate = rs.getString("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
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

    public static ObservableList<Appointment> retrieveAllAppointments() {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        Connection connection = DatabaseConnection.getConnection();
        String sqlSelectAllStatement = "SELECT * FROM appointments";

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
                appointment.setAppointmentStart(rs.getString("Start"));
                appointment.setAppointmentEnd(rs.getString("End"));
                appointment.setCreateDate(rs.getString("Create_Date"));
                appointment.setCreatedBy(rs.getString("Created_By"));
                appointment.setLastUpdate(rs.getString("Last_Update"));
                appointment.setLastUpdatedBy(rs.getString("Last_Updated_By"));
                appointment.setCustomerID(rs.getInt("Customer_ID"));
                appointment.setUserID(rs.getInt("User_ID"));
                appointment.setContactID(rs.getInt("Contact_ID"));

                allAppointments.add(appointment);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return allAppointments;
    }

    //TODO: Also fix this for all DAO classes
    public static boolean updateAppointment(int appointmentID, String newAppointmentTitle, String newAppointmentDescription,
                                            String newAppointmentLocation, String newAppointmentType, String newAppointmentStart,
                                            String newAppointmentEnd, int newCustomerID, int newUserID, int newContactID) {
        Connection connection = DatabaseConnection.getConnection();
        String sqlUpdateStatement = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Customer_ID = ?";

        DatabaseQuery.createPreparedStatement(connection, sqlUpdateStatement);

        try {
            PreparedStatement ps = DatabaseQuery.getPreparedStatement();

            ps.setString(1, newAppointmentTitle);
            ps.setString(2, newAppointmentDescription);
            ps.setString(3, newAppointmentLocation);
            ps.setString(4, newAppointmentType);
            ps.setString(5, newAppointmentStart);
            ps.setString(6, newAppointmentEnd);
            ps.setInt(7, newCustomerID);
            ps.setInt(8, newUserID);
            ps.setInt(9, newContactID);
            ps.setInt(10, appointmentID);

            return ps.executeUpdate() != 0;

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

            return ps.executeUpdate() != 0;

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return false;
    }
}
