package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import util.CurrentUser;
import util.DatabaseConnection;
import util.DatabaseQuery;
import util.TimeConverter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZonedDateTime;

public class CustomerDAO {
    public static boolean createCustomer(Customer customer) {
        Connection connection = DatabaseConnection.getConnection();
        String sqlInsertStatement = "INSERT INTO customers(Customer_Name, Address, Postal_Code, Phone, Created_By, Last_Updated_By, Division_ID) VALUES(?,?,?,?,?,?,?)";

        DatabaseQuery.createPreparedStatement(connection, sqlInsertStatement);

        try {
            PreparedStatement ps = DatabaseQuery.getPreparedStatement();

            ps.setString(1, customer.getCustomerName());
            ps.setString(2, customer.getCustomerAddress());
            ps.setString(3, customer.getPostalCode());
            ps.setString(4, customer.getPhoneNumber());
            ps.setString(5, CurrentUser.getUserName());
            ps.setString(6, CurrentUser.getUserName());
            ps.setInt(7, customer.getDivisionID());

            if (ps.executeUpdate() != 0) {
                return true;
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return false;
    }

    public static Customer retrieveCustomer(int customerID) {
        Connection connection = DatabaseConnection.getConnection();
        String sqlSelectStatement = "SELECT * FROM customers LEFT JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID LEFT JOIN countries ON first_level_divisions.COUNTRY_ID = countries.Country_ID WHERE Customer_ID = ?";

        DatabaseQuery.createPreparedStatement(connection, sqlSelectStatement);

        try {
            PreparedStatement ps = DatabaseQuery.getPreparedStatement();
            ps.setInt(1, customerID);
            ResultSet rs = ps.executeQuery();
            //TODO: Come back and fix this for all DAO classes

            while (rs.next()) {
                int id = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String customerAddress = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phoneNumber = rs.getString("Phone");
                LocalDateTime createDate = TimeConverter.databaseToLocal(rs.getString("Create_Date"));
                String createdBy = rs.getString("Created_By");
                LocalDateTime lastUpdate = TimeConverter.databaseToLocal(rs.getString("Last_Update"));
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int divisionID = rs.getInt("Division_ID");
                String associatedCountryName = rs.getString("Country");
                String associatedDivisionName = rs.getString("Division");

                Customer newCustomer = new Customer(id, customerName, customerAddress, postalCode, phoneNumber, createDate,
                        createdBy, lastUpdate, lastUpdatedBy, divisionID, associatedCountryName, associatedDivisionName);
                return newCustomer;
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    public static ObservableList<Customer> retrieveAllCustomers() {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        Connection connection = DatabaseConnection.getConnection();
        String sqlSelectAllStatement = "SELECT * FROM customers LEFT JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID LEFT JOIN countries ON first_level_divisions.COUNTRY_ID = countries.Country_ID";

        DatabaseQuery.createPreparedStatement(connection, sqlSelectAllStatement);

        try {
            PreparedStatement ps = DatabaseQuery.getPreparedStatement();
            ResultSet rs = ps.executeQuery();
            //TODO: Also fix this for all DAO classes

            while (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerID(rs.getInt("Customer_ID"));
                customer.setCustomerName(rs.getString("Customer_Name"));
                customer.setCustomerAddress(rs.getString("Address"));
                customer.setPostalCode(rs.getString("Postal_Code"));
                customer.setPhoneNumber(rs.getString("Phone"));
                customer.setCreateDate(TimeConverter.databaseToLocal(rs.getString("Create_Date")));
                customer.setCreatedBy(rs.getString("Created_By"));
                customer.setLastUpdate(TimeConverter.databaseToLocal(rs.getString("Last_Update")));
                customer.setLastUpdatedBy(rs.getString("Last_Updated_By"));
                customer.setDivisionID(rs.getInt("Division_ID"));
                customer.setAssociatedCountryName(rs.getString("Country"));
                customer.setAssociatedDivisionName(rs.getString("Division"));

                allCustomers.add(customer);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return allCustomers;
    }

    public static boolean updateCustomer(int customerID, String newCustomerName, String newAddress, String newPostalCode,
                                         String newPhoneNumber, int newDivisionID) {
        Connection connection = DatabaseConnection.getConnection();
        String sqlUpdateStatement = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";

        DatabaseQuery.createPreparedStatement(connection, sqlUpdateStatement);

        try {
            PreparedStatement ps = DatabaseQuery.getPreparedStatement();

            ps.setString(1, newCustomerName);
            ps.setString(2, newAddress);
            ps.setString(3, newPostalCode);
            ps.setString(4, newPhoneNumber);
            ps.setString(5, TimeConverter.localToDatabase(LocalDateTime.now()));
            ps.setString(6, CurrentUser.getUserName());
            ps.setInt(7, newDivisionID);
            ps.setInt(8, customerID);

            if (ps.executeUpdate() != 0) { return true; }
            //if (ps.getUpdateCount() > 0) { return true; }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return false;
    }

    public static boolean deleteCustomer(int customerID) {
        Connection connection = DatabaseConnection.getConnection();
        String sqlDeleteStatement = "DELETE FROM customers WHERE Customer_ID = ?";

        DatabaseQuery.createPreparedStatement(connection, sqlDeleteStatement);

        try {
            PreparedStatement ps = DatabaseQuery.getPreparedStatement();

            ps.setInt(1, customerID);

            return ps.executeUpdate() != 0;

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return false;
    }

    public static ObservableList<String> retrieveCustomerCountByCountry() {
        ObservableList<String> customerCountList = FXCollections.observableArrayList();
        Connection connection = DatabaseConnection.getConnection();
        String sqlSelectStatement = "SELECT Country, COUNT(Customer_Name) AS Count FROM customers LEFT JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID LEFT JOIN countries ON first_level_divisions.COUNTRY_ID = countries.Country_ID GROUP BY Country";

        DatabaseQuery.createPreparedStatement(connection, sqlSelectStatement);

        try {
            PreparedStatement ps = DatabaseQuery.getPreparedStatement();
            ResultSet rs = ps.executeQuery();
            //TODO: Also fix this for all DAO classes

            while (rs.next()) {
                String country = rs.getString("Country");
                int customerCount = rs.getInt("Count");

                String resultString = country + ": " + customerCount + " customer(s)";

                customerCountList.add(resultString);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return customerCountList;
    }
}
