package com.mycompany.studentmanagementsystem.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Simple DB utility. Reads connection params from system properties or environment variables.
 */
public class DBUtil {

    private static final String DEFAULT_URL = "jdbc:mysql://127.0.0.1:3306/student_management_system?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String DEFAULT_USER = "root";
    private static final String DEFAULT_PASSWORD = "admin"; // change this to your local MySQL root password

    static {
        try {
            // Explicitly load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC Driver not found. Add mysql-connector-java to dependencies.", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        String url = System.getProperty("db.url", System.getenv("DB_URL"));
        String user = System.getProperty("db.user", System.getenv("DB_USER"));
        String pass = System.getProperty("db.password", System.getenv("DB_PASSWORD"));

        if (url == null || url.isEmpty()) url = DEFAULT_URL;
        if (user == null) user = DEFAULT_USER;
        if (pass == null) pass = DEFAULT_PASSWORD;

        return DriverManager.getConnection(url, user, pass);
    }

}
