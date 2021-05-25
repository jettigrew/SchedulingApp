package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;
import util.DatabaseConnection;
import util.DatabaseQuery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactDAO {
    public static boolean createContact(Contact contact) {
        Connection connection = DatabaseConnection.getConnection();
        String sqlInsertStatement = "INSERT INTO contacts(Contact_Name, Email) VALUES(?,?)";

        DatabaseQuery.createPreparedStatement(connection, sqlInsertStatement);

        try {
            PreparedStatement ps = DatabaseQuery.getPreparedStatement();

            ps.setString(1, contact.getContactName());
            ps.setString(2, contact.getEmailAddress());

            //return ps.executeUpdate() != 0;
            ps.execute();

            //TODO: Fix this
            if(ps.getUpdateCount() > 0) { System.out.println("Successfully added.");}

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return false;
    }

    public static Contact retrieveContact(int contactID) {
        Connection connection = DatabaseConnection.getConnection();
        String sqlSelectStatement = "SELECT * FROM contacts WHERE Contact_ID = ?";

        DatabaseQuery.createPreparedStatement(connection, sqlSelectStatement);

        try {
            PreparedStatement ps = DatabaseQuery.getPreparedStatement();
            ps.setInt(1, contactID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String emailAddress = rs.getString("Email");

                Contact newContact = new Contact(id, contactName, emailAddress);
                return newContact;
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    public static ObservableList<Contact> retrieveAllContacts() {
        ObservableList<Contact> allContacts = FXCollections.observableArrayList();
        Connection connection = DatabaseConnection.getConnection();
        String sqlSelectAllStatement = "SELECT * FROM contacts";

        DatabaseQuery.createPreparedStatement(connection, sqlSelectAllStatement);

        try {
            PreparedStatement ps = DatabaseQuery.getPreparedStatement();
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Contact contact = new Contact();
                contact.setContactID(rs.getInt("Contact_ID"));
                contact.setContactName(rs.getString("Contact_Name"));
                contact.setEmailAddress(rs.getString("Email"));

                allContacts.add(contact);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return allContacts;
    }

    public static boolean updateContact(int contactID, String newContactName, String newEmailAddress) {
        Connection connection = DatabaseConnection.getConnection();
        String sqlUpdateStatement = "UPDATE contacts SET Contact_Name = ?, Email = ? WHERE Contact_ID = ?";

        DatabaseQuery.createPreparedStatement(connection, sqlUpdateStatement);

        try {
            PreparedStatement ps = DatabaseQuery.getPreparedStatement();

            ps.setString(1, newContactName);
            ps.setString(2, newEmailAddress);
            ps.setInt(3, contactID);

            return ps.executeUpdate() != 0;

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return false;
    }

    public static boolean deleteContact(int contactID) {
        Connection connection = DatabaseConnection.getConnection();
        String sqlDeleteStatement = "DELETE FROM contacts WHERE Contact_ID = ?";

        DatabaseQuery.createPreparedStatement(connection, sqlDeleteStatement);

        try {
            PreparedStatement ps = DatabaseQuery.getPreparedStatement();

            ps.setInt(1, contactID);

            return ps.executeUpdate() != 0;

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return false;
    }
}
