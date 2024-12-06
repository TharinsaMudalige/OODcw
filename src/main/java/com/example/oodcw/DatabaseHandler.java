package com.example.oodcw;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler {

    private Connection connection;

    public DatabaseHandler() {
        this.connection = DatabaseConnection.getInstance().getConnection();
        initializeDatabase();

    }

    private void initializeDatabase(){
        createUsersTableIfNotExists();
        createArticlesTableIfNotExists();
        createArticleInteractionsTableIfNotExists();
        createAdminTableIfNotExists();

        //Inserts the admin if admin does not exist in the database
        Admin defaultAdmin = new Admin("admin_tharinsa","tharinsam@admin");
        if (!isAdminExists(defaultAdmin.getAdminUserName())) {
            boolean success = insertAdmin(defaultAdmin);
            if (success) {
                System.out.println("Default admin inserted successfully.");
            } else {
                System.out.println("Failed to insert the default admin.");
            }
        }
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
            System.out.println("Users table created/checked successfully");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error creating users table" + e.getMessage());
        }
    }

    //Method to create the articles table if it doesn't exist
    private void createArticlesTableIfNotExists() {
        String createArticleTable = """
                CREATE TABLE IF NOT EXISTS articles (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    article_id VARCHAR(255) UNIQUE,
                    title VARCHAR(255),
                    content TEXT,
                    category VARCHAR(50),
                    source VARCHAR(255)                   
                )
                """;
        try(Statement statement = connection.createStatement()){
            statement.executeUpdate(createArticleTable);
            System.out.println(" Article table created/checked successfully");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error creating article table" + e.getMessage());
        }
    }

    //Method to create the article_interactions table if it doesn't exist
    private void createArticleInteractionsTableIfNotExists() {
        String createQuery = """
                CREATE TABLE IF NOT EXISTS article_interactions (   
                article_id INT,
                user_id INT,                
                liked BOOLEAN DEFAULT FALSE,
                view_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                PRIMARY KEY (article_id, user_id),
                FOREIGN KEY (article_id) REFERENCES articles(id) ON DELETE CASCADE,
                FOREIGN KEY (user_id) REFERENCES users(userId) ON DELETE CASCADE
                )
                """;

        try (Statement statement = connection.createStatement()){
            statement.executeUpdate(createQuery);
            System.out.println("Article interaction table created/checked successfully");

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error creating article interaction table" + e.getMessage());
        }
    }

    //Method to create the admin table if it doesn't exist
    private void createAdminTableIfNotExists() {
        String createAdminTableQuery = """
                CREATE TABLE IF NOT EXISTS admin (
                    admin_id INT AUTO_INCREMENT PRIMARY KEY,
                    admin_username VARCHAR(100) NOT NULL UNIQUE,
                    admin_password VARCHAR(100) NOT NULL
                )
                """;

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(createAdminTableQuery);
            System.out.println("Admin table created/checked successfully.");
        } catch (SQLException e) {
            System.err.println("Error creating Admin table: " + e.getMessage());
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
            return true;  //Returns true if the user is added successfully
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error adding user to the database: " + e.getMessage());
            return false;
        }
    }

    //Method to check whether a username is already there in the database
    public boolean isUsernameExists(String username) {
        String selectQuery = "SELECT COUNT(*) FROM users WHERE username = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)){
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();  //Executes query and retrieves the result
            resultSet.next();  //Moves to the first row in the results set
            return resultSet.getInt(1) > 0; //returns true if username exists

        } catch (SQLException e) {
            e.printStackTrace();
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
            e.printStackTrace();
            System.err.println("Error updating password: " + e.getMessage());
        }
        return false;
    }

    //Method to delete a user
    public boolean deleteUser(String username) {
        String deleteQuery = "DELETE FROM users WHERE username = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)){
            preparedStatement.setString(1, username);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error deleting user: " + e.getMessage());
            return false;
        }
    }

    //Method to check if the password entered by the user is correct
    public boolean isPasswordCorrect(String username, String password) {
        String pwdCorrectQuery = "SELECT password FROM users WHERE username = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(pwdCorrectQuery)){
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                String storedPwd = resultSet.getString("password");
                return storedPwd.equals(password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error checking if password exists: " + e.getMessage());
        }
        return false;
    }

    //Method to get the user details by the username
    public User getUserByUsername(String username) {
        String detailsQuery = "SELECT firstName, lastName, username FROM users WHERE username = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(detailsQuery)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return new User(
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getString("username"),
                        null
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Method to add article to the database
    public boolean addArticle(Article article) {
        String insertArticleQuery = """
                INSERT INTO articles (article_id, title, content, category, source)
                VALUES (?, ?, ?, ?, ?)
                """;

        try(PreparedStatement preparedStatement = connection.prepareStatement(insertArticleQuery)){
            preparedStatement.setString(1, article.getArticle_id());
            preparedStatement.setString(2, article.getTitle());
            preparedStatement.setString(3, article.getContent());
            preparedStatement.setString(4, article.getCategory());
            preparedStatement.setString(5, article.getSource());

            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error adding article to the database: " + e.getMessage());
            return false;
        }
    }

    //Method to check whether an article exists in the articles table
    public boolean isArticleExists(String articleId) {
        String query = "SELECT COUNT(*) FROM articles WHERE article_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, articleId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0; // Returns true if the article exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error checking if article exists: " + e.getMessage());
        }
        return false;
    }

    public List<Article> getArticles(int countPerCategory) {
        List<Article> articles = new ArrayList<>();
        String getQuery = """
                SELECT article_id, title, content, category, source
                FROM articles
                WHERE category = ?
                ORDER BY RAND()
                LIMIT ?
                """;
        try(PreparedStatement preparedStatement = connection.prepareStatement(getQuery)){
            for(String category : List.of("Technology", "Sports", "Health", "Crime", "Politics", "Business")){
                preparedStatement.setString(1, category);
                preparedStatement.setInt(2, countPerCategory);
                ResultSet resultSet = preparedStatement.executeQuery();

                while(resultSet.next()){
                    String articleID = resultSet.getString("article_id");
                    String title = resultSet.getString("title");
                    String content = resultSet.getString("content");
                    String source = resultSet.getString("source");

                    articles.add(new Article(articleID, title, content, category, source));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error getting articles from the database: " + e.getMessage());
        }
        return articles;
    }

    //Method to retrieve articles by the category
    public List<Article> getArticlesByCategory(String category, int limit) {
        List<Article> articles = new ArrayList<>();
        String query = """
                SELECT article_id, title, content, category, source
                FROM articles
                WHERE category = ?
                ORDER BY RAND()
                LIMIT ?
                """;

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, category);
            preparedStatement.setInt(2, limit);

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                String articleID = resultSet.getString("article_id");
                String title = resultSet.getString("title");
                String content = resultSet.getString("content");
                String selectCategory = resultSet.getString("category");
                String source = resultSet.getString("source");

                articles.add(new Article(articleID, title, content, selectCategory, source));
            }
        } catch (SQLException e){
            e.printStackTrace();
            System.err.println("Error getting articles from the database: " + e.getMessage());
        }
        return articles;
    }

    //Method to get the userId by the username
    public int getUserIDByUsername(String username){
        String query = "SELECT userId FROM users WHERE username = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt("userId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error getting the user id: " + e.getMessage());
        }
        return -1; //Return -1 if user does not exist
    }

    //Method to get article ID by the url
    public int getArticleIDByUrl(String url){
        String query = "SELECT id FROM articles WHERE article_id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, url);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error getting the article id: " + e.getMessage());
        }
        return -1; //Return -1 if article does not exist
    }

    //Method to add and update user interactions
    public boolean addOrUpdateInteractions(int userId, int articleId, boolean liked){
        String query = "SELECT liked FROM article_interactions WHERE user_id = ? AND article_id = ?";
        try(PreparedStatement checkStatement = connection.prepareStatement(query)){
            checkStatement.setInt(1, userId);
            checkStatement.setInt(2, articleId);
            ResultSet resultSet = checkStatement.executeQuery();

            if(resultSet.next()){
                //Update the liked column if the interaction already exists
                String updateQuery = "UPDATE article_interactions SET liked = ? WHERE user_id = ? AND article_id = ?";
                try(PreparedStatement updateStatement = connection.prepareStatement(updateQuery)){
                    updateStatement.setBoolean(1, liked);
                    updateStatement.setInt(2, userId);
                    updateStatement.setInt(3, articleId);
                    updateStatement.executeUpdate();
                    System.out.println("Updated for userID: " + userId + " and articleID: " + articleId);
                    return true;
                }
            } else {
                //Insert a new interaction if it doesn't exist already
                String insertQuery = "INSERT INTO article_interactions (user_id, article_id, liked) VALUES (?, ?, ?)";
                try(PreparedStatement insertStatement = connection.prepareStatement(insertQuery)){
                    insertStatement.setInt(1, userId);
                    insertStatement.setInt(2, articleId);
                    insertStatement.setBoolean(3, liked);
                    insertStatement.executeUpdate();
                    System.out.println("Inserted for userID: " + userId + " and articleID: " + articleId);
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error inserting or updating interactions in the database: " + e.getMessage());
        }
        return false;
    }

    //Method to retrieve article details by the ID
    public Article getArticleByID(String articleID) {
        String query = "SELECT * FROM articles WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, articleID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Article(
                        resultSet.getString("article_id"),
                        resultSet.getString("title"),
                        resultSet.getString("content"),
                        resultSet.getString("category"),
                        resultSet.getString("source")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User loadUserWithInteractions(String username) {
        User user = null;
        String userQuery = "SELECT * FROM users WHERE username = ?";
        String interactionsQuery = "SELECT * FROM article_interactions WHERE user_id = ?";

        try (PreparedStatement userStatement = connection.prepareStatement(userQuery)) {
            //Get the user details
            userStatement.setString(1, username);
            ResultSet userResult = userStatement.executeQuery();

            if (userResult.next()) {
                int userId = userResult.getInt("userId");
                String firstName = userResult.getString("firstName");
                String lastName = userResult.getString("lastName");
                String password = userResult.getString("password");

                user = new User(userId, firstName, lastName, username, password);

                //Get the user's interactions
                try (PreparedStatement interactionStatement = connection.prepareStatement(interactionsQuery)) {
                    interactionStatement.setInt(1, userId);
                    ResultSet interactionResult = interactionStatement.executeQuery();

                    while (interactionResult.next()) {
                        String articleID = interactionResult.getString("article_id");
                        boolean liked = interactionResult.getBoolean("liked");
                        LocalDateTime timeViewed = interactionResult.getTimestamp("view_time").toLocalDateTime();

                        ArticleInteractions interaction = new ArticleInteractions(userId, articleID, liked, timeViewed);
                        user.addArticleInteractions(interaction);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    //Method to insert an admin
    public boolean insertAdmin(Admin admin) {
        String insertQuery = """
            INSERT INTO admin (admin_username, admin_password)
            VALUES (?, ?)
            """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, admin.getAdminUserName());
            preparedStatement.setString(2, admin.getAdminPassword());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error inserting admin: " + e.getMessage());
            return false;
        }
    }

    public boolean validateAdminCredentials(String username, String password) {
        String query = "SELECT COUNT(*) FROM admin WHERE admin_username = ? AND admin_password = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0; // Returns true if a matching admin record exists
            }
        } catch (SQLException e) {
            System.err.println("Error validating admin credentials: " + e.getMessage());
        }

        return false; // Return false if an error occurs or no matching record is found
    }

    public boolean isAdminExists(String adminUserName) {
        String query = "SELECT COUNT(*) FROM admin WHERE admin_username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, adminUserName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error checking admin existence: " + e.getMessage());
        }
        return false;
    }

    //Retrieves all the users in the users table
    public List<User> getAllUsers() {
        String query = "SELECT userId, firstName, lastName, username FROM users";
        List<User> users = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int userId = resultSet.getInt("userId");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String username = resultSet.getString("username");

                users.add(new User(userId, firstName, lastName, username, null));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching users: " + e.getMessage());
        }

        return users;
    }

    //Method to remove a user by the ID
    public boolean deleteUserById(int userId) {
        String deleteQuery = "DELETE FROM users WHERE userId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setInt(1, userId);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting user: " + e.getMessage());
        }

        return false;
    }

    //Retrieves all the articles in the articles table
    public List<Article> getAllArticles() {
        List<Article> articles = new ArrayList<>();
        String query = "SELECT * FROM articles";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String articleId = resultSet.getString("article_id");
                String title = resultSet.getString("title");
                String content = resultSet.getString("content");
                String category = resultSet.getString("category");
                String source = resultSet.getString("source");

                Article article = new Article(id, articleId, title, content, category, source);
                articles.add(article);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error fetching articles from the database.");
        }

        return articles;
    }


}
