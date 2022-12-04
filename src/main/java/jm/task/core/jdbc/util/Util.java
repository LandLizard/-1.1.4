package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    //MySQL settings
    private static final String USER = "root";
    private static final String PASS = "7953";
    private static final String URL = "jdbc:mysql://localhost:3306/my_db";
    private static final String DIALECT = "org.hibernate.dialect.MySQLDialect";
    private static Util instance;
    private static Connection connection;

    private Util() {

    }

    public static Connection connectToDB() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASS); //Connect to DB using Connector JDBC
        } catch (SQLException e) {
            System.out.println("Failed connection to DB");
            e.printStackTrace();
        }
        return connection;
    }

    public static Util getInstance() {
        if (instance == null) {
            instance = new Util();
        }
        return instance;
    }

    public static SessionFactory getHibernateSF() {
        Configuration configuration = new Configuration();

        configuration.addAnnotatedClass(User.class);
        /*
        Setting connection to DB without xml file
         */
        configuration.setProperty(Environment.URL, URL);
        configuration.setProperty(Environment.USER, USER);
        configuration.setProperty(Environment.PASS, PASS);
        configuration.setProperty(Environment.DIALECT, DIALECT);
        configuration.setProperty(Environment.SHOW_SQL, "true");
        configuration.setProperty(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();
        System.out.println("Successfully connected to DB");
        return configuration.buildSessionFactory(serviceRegistry);
    }
}
