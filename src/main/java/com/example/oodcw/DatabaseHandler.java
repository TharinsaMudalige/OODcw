package com.example.oodcw;

import java.sql.*;

public class DatabaseHandler {
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "smartReadDB";
    private static final String USER = "root";
    private static final String PASSWORD = "oodcw123";
    private Connection connection;

    public DatabaseHandler() {
        try {
            // Step 1: Connect to MySQL server and create the database if it doesn't exist
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            createDatabaseIfNotExists();

            // Step 2: Connect to the specific database
            connection = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
            createTableIfNotExists();
            System.out.println("Connection established");

        } catch (SQLException e) {
            System.err.println("Connection failed: " + e.getMessage());
        }
    }

    // Method to create the database if it doesn't exist
    private void createDatabaseIfNotExists() throws SQLException {
        String createDBQuery = "CREATE DATABASE IF NOT EXISTS " + DB_NAME;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(createDBQuery);
            System.out.println("Database created/checked successfully");
        }
    }

    // Method to create the users table if it doesn't exist
    private void createTableIfNotExists() throws SQLException {
        String createTableQuery = """
                CREATE TABLE IF NOT EXISTS users (
                    userId INT AUTO_INCREMENT PRIMARY KEY,
                    firstName VARCHAR(50),
                    lastName VARCHAR(50),
                    username VARCHAR(50) UNIQUE,
                    password VARCHAR(100)
                )
                """;

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(createTableQuery);
            System.out.println("Table created/checked successfully");
        }
    }

    // Method to add a new user to the database
    public boolean addUser(User user) {
        String insertUserQuery = "INSERT INTO users (firstName, lastName, username, password) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertUserQuery)) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getUserName());
            preparedStatement.setString(4, user.getUserPassword());

            preparedStatement.executeUpdate();
            System.out.println("User added successfully: " + user.getUserName());
            return true;
        } catch (SQLException e) {
            System.err.println("Error adding user to the database: " + e.getMessage());
            return false;
        }
    }

    // Close MySQL connection
    public void closeDBConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("MySQL connection closed.");
            } catch (SQLException e) {
                System.err.println("Error closing MySQL connection: " + e.getMessage());
            }
        }
    }
}
