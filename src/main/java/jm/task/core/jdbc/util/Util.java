package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    private static final String USER = "root";
    private static final String PASS = "7953";
    private static final String URL = "jdbc:mysql://localhost:3306/my_db";

    public static void connectToDB() {

        try (Connection connection = DriverManager.getConnection(URL, USER, PASS)) {
            System.out.println("Successfully connect to DB");
        } catch (SQLException e) {
            throw new RuntimeException("Failed connection to DB");
        }
    }
}
