/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
 package za.ac.cput.server.DBConnectionPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Phiwa
 */


    

public class DBConnection {

    // You can externalize these to a properties file later
    private static final String USER = "CourseEnrolmentSystem";
    private static final String PASSWORD = "@Khizo14251";
    private static final String URL = "jdbc:derby://localhost:1527/CourseEnrolmentSystem";

    private static Connection connection = null;

    private DBConnection() {
        // no instantiation
    }

    public static synchronized Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                // optional: explicit driver load (helps in some environments)
                Class.forName("org.apache.derby.jdbc.ClientDriver");
            } catch (ClassNotFoundException e) {
                // driver not found - print warning but let DriverManager try
                System.err.println("Derby client driver not found on classpath: " + e.getMessage());
            }

            System.out.println("DEBUG: Connecting to DB URL: " + URL + " USER: " + USER);
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("DEBUG: DB connection established");
        }
        return connection;
    }

    public static synchronized void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("DEBUG: DB connection closed");
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connection = null;
            }
        }
    }
}

    

