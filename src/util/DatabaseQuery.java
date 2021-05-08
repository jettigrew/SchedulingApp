package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseQuery {

    private static PreparedStatement preparedStatement;

    //creating statement object
    public static void createPreparedStatement(Connection connection, String sqlStatement) {
        try {
            preparedStatement = connection.prepareStatement(sqlStatement);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public static PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }

}
