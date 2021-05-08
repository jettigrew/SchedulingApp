package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FirstLevelDivision;
import util.DatabaseConnection;
import util.DatabaseQuery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FirstLevelDivisionDAO {
    public static boolean createFirstLevelDivision(FirstLevelDivision firstLevelDivision) {
        Connection connection = DatabaseConnection.getConnection();
        String sqlInsertStatement = "INSERT INTO first_level_divisions(Division, Create_Date, Created_By, Last_Updated_By) VALUES(?,?,?,?)";

        DatabaseQuery.createPreparedStatement(connection, sqlInsertStatement);

        try {
            PreparedStatement ps = DatabaseQuery.getPreparedStatement();

            ps.setString(1, firstLevelDivision.getDivisionName());
            ps.setString(2, firstLevelDivision.getCreateDate());
            ps.setString(3, firstLevelDivision.getCreatedBy());
            ps.setString(4, firstLevelDivision.getLastUpdatedBy());

            return ps.executeUpdate() != 0;

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return false;
    }

    public static FirstLevelDivision retrieveFirstLevelDivision(int divisionID) {
        Connection connection = DatabaseConnection.getConnection();
        String sqlSelectStatement = "SELECT * FROM first_level_divisions WHERE Division_ID = ?";

        DatabaseQuery.createPreparedStatement(connection, sqlSelectStatement);

        try {
            PreparedStatement ps = DatabaseQuery.getPreparedStatement();
            ps.setInt(1, divisionID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                String createDate = rs.getString("Create_Date");
                String createdBy = rs.getString("Created_By");
                String lastUpdate = rs.getString("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int countryID = rs.getInt("COUNTRY_ID");

                FirstLevelDivision newFirstLevelDivision = new FirstLevelDivision(id, divisionName, createDate,createdBy,
                        lastUpdate, lastUpdatedBy, countryID);
                return newFirstLevelDivision;
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    public static ObservableList<FirstLevelDivision> retrieveAllFirstLevelDivisions() {
        ObservableList<FirstLevelDivision> allFirstLevelDivisions = FXCollections.observableArrayList();
        Connection connection = DatabaseConnection.getConnection();
        String sqlSelectAllStatement = "SELECT * FROM first_level_divisions";

        DatabaseQuery.createPreparedStatement(connection, sqlSelectAllStatement);

        try {
            PreparedStatement ps = DatabaseQuery.getPreparedStatement();
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                FirstLevelDivision firstLevelDivision = new FirstLevelDivision();
                firstLevelDivision.setDivisionID(rs.getInt("Division_ID"));
                firstLevelDivision.setDivisionName(rs.getString("Division"));
                firstLevelDivision.setCreateDate(rs.getString("Create_Date"));
                firstLevelDivision.setCreatedBy(rs.getString("Created_By"));
                firstLevelDivision.setLastUpdate(rs.getString("Last_Update"));
                firstLevelDivision.setLastUpdatedBy(rs.getString("Last_Updated_By"));
                firstLevelDivision.setCountryID(rs.getInt("COUNTRY_ID"));

                allFirstLevelDivisions.add(firstLevelDivision);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return allFirstLevelDivisions;
    }

    public static boolean updateFirstLevelDivision(int divisionID, String newDivisionName, int newCountryID) {
        Connection connection = DatabaseConnection.getConnection();
        String sqlUpdateStatement = "UPDATE first_level_divisions SET Division = ?, COUNTRY_ID = ? WHERE Division_ID = ?";

        DatabaseQuery.createPreparedStatement(connection, sqlUpdateStatement);

        try {
            PreparedStatement ps = DatabaseQuery.getPreparedStatement();

            ps.setString(1, newDivisionName);
            ps.setInt(2, newCountryID);
            ps.setInt(3, divisionID);

            return ps.executeUpdate() != 0;

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return false;
    }

    public static boolean deleteFirstLevelDivision(int divisionID) {
        Connection connection = DatabaseConnection.getConnection();
        String sqlDeleteStatement = "DELETE FROM first_level_divisions WHERE Division_ID = ?";

        DatabaseQuery.createPreparedStatement(connection, sqlDeleteStatement);

        try {
            PreparedStatement ps = DatabaseQuery.getPreparedStatement();

            ps.setInt(1, divisionID);

            return ps.executeUpdate() != 0;

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return false;
    }
}
