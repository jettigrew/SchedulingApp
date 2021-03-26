package util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseQuery {

    private static Statement statement;

    //creating statement object
    public static void createStatementObj(Connection connection) {
        try {
            statement = connection.createStatement();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public static Statement getStatementObj() {
        return statement;
    }

}
