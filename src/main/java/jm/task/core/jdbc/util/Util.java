package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    private static final String USER = "root";
    private static final String PASS = "7953";
    private static final String URL = "jdbc:mysql://localhost:3306/my_db";

    public static Connection connectToDB() {
        Connection connection;

        try {
            connection = DriverManager.getConnection(URL, USER, PASS);
            connection.setAutoCommit(false);
            System.out.println("Successfully connected to DB");
        } catch (SQLException e) {
            System.out.println("Failed connection to DB");
            throw new RuntimeException(e);
        }
        return connection;
    }
}
