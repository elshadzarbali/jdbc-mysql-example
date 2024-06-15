package org.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

    // for creating database if not exist, we can add "?createDatabaseIfNotExist=true" to end of url
    private static final String URL = "jdbc:mysql://localhost:3306/jdbc_example";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "rootPass";

    // Using static block to load the MySQL JDBC driver
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // Wrapping this checked exception into RuntimeException to simplify exception, because,
            // failing to load the driver is a critical for the execution of application.
            throw new RuntimeException("MySQL JDBC Driver could not be loaded.", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public static void handleSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }

}
