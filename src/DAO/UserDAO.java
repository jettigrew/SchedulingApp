package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;
import util.DatabaseConnection;
import util.DatabaseQuery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    public static boolean createUser(User user) {
        Connection connection = DatabaseConnection.getConnection();
        String sqlInsertStatement = "INSERT INTO users(User_Name, Password, Create_Date, Created_By, Last_Updated_By) VALUES(?,?,?,?,?)";

        DatabaseQuery.createPreparedStatement(connection, sqlInsertStatement);

        try {
            PreparedStatement ps = DatabaseQuery.getPreparedStatement();

            ps.setString(1, user.getUserName());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getCreateDate());
            ps.setString(4, user.getCreatedBy());
            ps.setString(5, user.getLastUpdatedBy());

            return ps.executeUpdate() != 0;

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return false;
    }

    public static User retrieveUser(int userID) {
        Connection connection = DatabaseConnection.getConnection();
        String sqlSelectStatement = "SELECT * FROM users WHERE User_ID = ?";

        DatabaseQuery.createPreparedStatement(connection, sqlSelectStatement);

        try {
            PreparedStatement ps = DatabaseQuery.getPreparedStatement();
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String password = rs.getString("Password");
                String createDate = rs.getString("Create_Date");
                String createdBy = rs.getString("Created_By");
                String lastUpdate = rs.getString("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");

                User newUser = new User(id, userName, password, createDate,createdBy, lastUpdate, lastUpdatedBy);
                return newUser;
            }

        } catch (SQLException throwable) {
                throwable.printStackTrace();
        }
        return null;
    }

    public static ObservableList<User> retrieveAllUsers() {
        ObservableList<User> allUsers = FXCollections.observableArrayList();
        Connection connection = DatabaseConnection.getConnection();
        String sqlSelectAllStatement = "SELECT * FROM users";

        DatabaseQuery.createPreparedStatement(connection, sqlSelectAllStatement);

        try {
            PreparedStatement ps = DatabaseQuery.getPreparedStatement();
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setUserID(rs.getInt("User_ID"));
                user.setUserName(rs.getString("User_Name"));
                user.setPassword(rs.getString("Password"));
                user.setCreateDate(rs.getString("Create_Date"));
                user.setCreatedBy(rs.getString("Created_By"));
                user.setLastUpdate(rs.getString("Last_Update"));
                user.setLastUpdatedBy(rs.getString("Last_Updated_By"));

                allUsers.add(user);
            }
        } catch (SQLException throwable) {
                throwable.printStackTrace();
        }
        return allUsers;
    }

    public static boolean updateUser(int userID, String newUserName, String newPassword) {
        Connection connection = DatabaseConnection.getConnection();
        String sqlUpdateStatement = "UPDATE users SET User_Name = ?, Password = ? WHERE User_ID = ?";

        DatabaseQuery.createPreparedStatement(connection, sqlUpdateStatement);

        try {
            PreparedStatement ps = DatabaseQuery.getPreparedStatement();

            ps.setString(1, newUserName);
            ps.setString(2, newPassword);
            ps.setInt(3,userID);

            return ps.executeUpdate() != 0;

        } catch (SQLException throwable) {
                throwable.printStackTrace();
        }
        return false;
    }

    public static boolean deleteUser(int userID) {
        Connection connection = DatabaseConnection.getConnection();
        String sqlDeleteStatement = "DELETE FROM users WHERE User_ID = ?";

        DatabaseQuery.createPreparedStatement(connection, sqlDeleteStatement);

        try {
            PreparedStatement ps = DatabaseQuery.getPreparedStatement();

            ps.setInt(1, userID);

            return ps.executeUpdate() != 0;

        } catch (SQLException throwable) {
                throwable.printStackTrace();
        }
        return false;
    }
}
