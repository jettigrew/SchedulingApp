package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import util.CurrentUser;
import util.DatabaseConnection;
import util.DatabaseQuery;
import util.TimeConverter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class CountryDAO {
    public static boolean createCountry(Country country) {
        Connection connection = DatabaseConnection.getConnection();
        String sqlInsertStatement = "INSERT INTO countries(Country, Create_Date, Created_By, Last_Updated_By) VALUES(?,?,?,?)";

        DatabaseQuery.createPreparedStatement(connection, sqlInsertStatement);

        try {
            PreparedStatement ps = DatabaseQuery.getPreparedStatement();

            ps.setString(1, country.getCountryName());
            ps.setObject(2, country.getCreateDate());
            ps.setString(3, country.getCreatedBy());
            ps.setString(4, country.getLastUpdatedBy());

            return ps.executeUpdate() != 0;

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return false;
    }

    public static Country retrieveCountry(int countryID) {
        Connection connection = DatabaseConnection.getConnection();
        String sqlSelectStatement = "SELECT * FROM countries WHERE Country_ID = ?";

        DatabaseQuery.createPreparedStatement(connection, sqlSelectStatement);

        try {
            PreparedStatement ps = DatabaseQuery.getPreparedStatement();
            ps.setInt(1, countryID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                LocalDateTime createDate = TimeConverter.databaseToLocal(rs.getString("Create_Date"));
                String createdBy = rs.getString("Created_By");
                LocalDateTime lastUpdate = TimeConverter.databaseToLocal(rs.getString("Last_Update"));
                String lastUpdatedBy = rs.getString("Last_Updated_By");

                Country newCountry = new Country(id, countryName, createDate,createdBy, lastUpdate, lastUpdatedBy);
                return newCountry;
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    public static ObservableList<Country> retrieveAllCountries() {
        ObservableList<Country> allCountries = FXCollections.observableArrayList();
        Connection connection = DatabaseConnection.getConnection();
        String sqlSelectAllStatement = "SELECT * FROM countries";

        DatabaseQuery.createPreparedStatement(connection, sqlSelectAllStatement);

        try {
            PreparedStatement ps = DatabaseQuery.getPreparedStatement();
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Country country = new Country();
                country.setCountryID(rs.getInt("Country_ID"));
                country.setCountryName(rs.getString("Country"));
                country.setCreateDate(TimeConverter.databaseToLocal((rs.getString("Create_Date"))));
                country.setCreatedBy(rs.getString("Created_By"));
                country.setLastUpdate(TimeConverter.databaseToLocal((rs.getString("Last_Update"))));
                country.setLastUpdatedBy(rs.getString("Last_Updated_By"));

                allCountries.add(country);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return allCountries;
    }

    public static boolean updateCountry(int countryID, String newCountryName) {
        Connection connection = DatabaseConnection.getConnection();
        String sqlUpdateStatement = "UPDATE countries SET Country = ?, Last_Updated_By = ? WHERE Country_ID = ?";

        DatabaseQuery.createPreparedStatement(connection, sqlUpdateStatement);

        try {
            PreparedStatement ps = DatabaseQuery.getPreparedStatement();

            ps.setString(1, newCountryName);
            ps.setString(2, CurrentUser.getUserName());
            ps.setInt(3, countryID);

            return ps.executeUpdate() != 0;

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return false;
    }

    public static boolean deleteCountry(int countryID) {
        Connection connection = DatabaseConnection.getConnection();
        String sqlDeleteStatement = "DELETE FROM countries WHERE Country_ID = ?";

        DatabaseQuery.createPreparedStatement(connection, sqlDeleteStatement);

        try {
            PreparedStatement ps = DatabaseQuery.getPreparedStatement();

            ps.setInt(1, countryID);

            return ps.executeUpdate() != 0;

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return false;
    }
}
