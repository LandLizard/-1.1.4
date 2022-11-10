package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    //MySQL settings
    private static final String USER = "root";
    private static final String PASS = "7953";
    private static final String URL = "jdbc:mysql://localhost:3306/my_db";

    public static Connection connectToDB() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(URL, USER, PASS); //Connect to DB using Connector JDBC
        } catch (SQLException e) {
            System.out.println("Failed connection to DB");
            e.printStackTrace();
        }
        return connection;
    }
}
