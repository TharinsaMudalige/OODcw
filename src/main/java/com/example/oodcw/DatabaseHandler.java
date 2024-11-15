package com.example.oodcw;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DatabaseHandler {

    private Connection connection;

    public DatabaseHandler() {
        this.connection = DatabaseConnection.getInstance().getConnection();
        initializeDatabase();

    }

    private void initializeDatabase(){
        createUsersTableIfNotExists();
    }


    // Method to create the users table if it doesn't exist
    private void createUsersTableIfNotExists() {
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
        } catch (SQLException e) {
            System.err.println("Error creating users table" + e.getMessage());
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

    //Method to not allow usernames that already exists
    public boolean isUsernameExists(String username) {
        String selectQuery = "SELECT COUNT(*) FROM users WHERE username = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)){
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1) > 0; //returns true if username exists

        } catch (SQLException e) {
            System.err.println("Error checking if username exists: " + e.getMessage());
            return false;
        }
    }

    //Method to reset the password
    public boolean updatePassword(String username, String newPassword) {
        String query = "UPDATE users SET password = ? WHERE username = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, newPassword);
            preparedStatement.setString(2, username);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error updating password: " + e.getMessage());
        }
        return false;
    }
}
