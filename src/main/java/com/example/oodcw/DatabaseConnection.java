package com.example.oodcw;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "smartReadDB";
    private static final String USER = "root";
    private static final String PASSWORD = "oodcw123";

    private static DatabaseConnection instance;
    private Connection connection;

    private DatabaseConnection() {
        try {
            // Connect to MySQL server and create database if it doesn't exist
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            createDatabaseIfNotExists();

            // Connect to the specific database
            connection = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
            System.out.println("Connected to database: " + DB_NAME);
        } catch (SQLException e) {
            System.err.println("Error initializing database connection: " + e.getMessage());
        }
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    private void createDatabaseIfNotExists() throws SQLException {
        String createDBQuery = "CREATE DATABASE IF NOT EXISTS " + DB_NAME;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(createDBQuery);
            System.out.println("Database checked/created successfully.");
        }
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                System.err.println("Error closing the connection: " + e.getMessage());
            }
        }
    }
}
