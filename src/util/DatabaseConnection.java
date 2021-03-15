package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    //JDBC URL components
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String ip = "//wgudb.ucertify.com/";
    private static final String database = "WJ06Rap";

    //full URL
    private static final String fullURL = protocol + vendor + ip + database;

    //referencing MySQL driver and connection interface
    private static final String mysqlDriver = "java.sql.Connection";
    private static Connection connection = null;

    //database username and password
    private static final String username = "U06Rap";
    private static final String password = "53688845033";

    public static Connection connect() {
        try {
            Class.forName(mysqlDriver);
            connection = DriverManager.getConnection(fullURL, username, password);
        } catch(ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void disconnect() {
        try {
            connection.close();
        } catch(SQLException e) {
            e.getMessage();
        }
    }

}
