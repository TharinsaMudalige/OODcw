package com.example.oodcw.Controllers;

import com.example.oodcw.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.util.ArrayList;
import java.util.List;

public class ArticleViewController extends BaseController {

    @FXML
    private ComboBox<String> categoryComboBox;
    @FXML
    private Label usernameDisplayText;
    @FXML
    private VBox articlesContainer;

    private DatabaseHandler databaseHandler;
    private String currentUsername;
    private List<Article> displayedArticles = new ArrayList<>();


    public ArticleViewController() {
        databaseHandler = new DatabaseHandler();

    }

    public void initialize() {
        categoryComboBox.getItems().addAll("Technology", "Sports", "Health", "Crime", "Politics", "Business");
    }

    public void setUsername(User user) {
        this.currentUsername = user.getUserName(); //Store the username in currentUsername
        usernameDisplayText.setText(user.getUserName()); //Update the UI label
    }

    public void displayArticles(List<Article> articles) {
        articlesContainer.getChildren().clear(); //Clear any existing articles
        displayedArticles.clear();

        for (Article article : articles) {
            VBox articleBox = new VBox(5);
            articleBox.setStyle("-fx-padding: 15; -fx-background-color: #f0f0f0; -fx-border-color: #ccc; -fx-border-width: 1;");
            articleBox.setPrefWidth(Region.USE_COMPUTED_SIZE);
            articleBox.setMaxWidth(Double.MAX_VALUE);

            Label titleLabel = new Label("Title: " + article.getTitle());
            titleLabel.setStyle("-fx-font-weight: bold;");

            Label contentLabel = new Label("Content: " + article.getContent());
            contentLabel.setMaxWidth(Double.MAX_VALUE);

            Label categoryLabel = new Label("Category: " + article.getCategory());
            Label sourceLabel = new Label("Source: " + article.getSource());
            Label linkLabel = new Label("Link: " + article.getArticle_id());

            linkLabel.setStyle("-fx-text-fill: blue; -fx-underline: true;");
            linkLabel.setOnMouseClicked(event -> {
                OnArticleLinkClick(article.getArticle_id());
                linkLabel.setStyle("-fx-text-fill: purple; -fx-underline: true;");
            });

            Button likeButton = new Button("Like");

            likeButton.setStyle("-fx-text-fill: white; " +
                    "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #003366, #66ccff); " +
                    "-fx-border-color: transparent; " +
                    "-fx-font-weight: bold; " +
                    "-fx-cursor: hand;");

            likeButton.setOnMouseEntered(e ->
                    likeButton.setStyle("-fx-text-fill: white; " +
                            "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #0066cc, #66ccff); " +
                            "-fx-border-color: transparent; " +
                            "-fx-font-weight: bold; " +
                            "-fx-cursor: hand;" +
                            "-fx-scale-x: 1.1; -fx-scale-y: 1.1;"));

            likeButton.setOnMouseExited(e ->
                    likeButton.setStyle("-fx-text-fill: white; " +
                            "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #003366, #66ccff); " +
                            "-fx-border-color: transparent; " +
                            "-fx-font-weight: bold; " +
                            "-fx-cursor: hand;" +
                            "-fx-scale-x: 1.0; -fx-scale-y: 1.0;"));

            likeButton.setOnAction(event -> onLikeButtonClick(article, likeButton));

            articleBox.getChildren().addAll(titleLabel, contentLabel, categoryLabel, sourceLabel, linkLabel, likeButton);
            articlesContainer.getChildren().add(articleBox);
        }

        displayedArticles.addAll(articles);
    }

    public void OnArticleLinkClick(String articleID) {
        ServiceManager.getExecutorService().submit(() -> {
            int userId = databaseHandler.getUserIDByUsername(currentUsername);
            int articleId = databaseHandler.getArticleIDByUrl(articleID);

            if (userId != -1 && articleId != -1) {
                databaseHandler.addOrUpdateInteractions(userId, articleId, false);
                try {
                    java.awt.Desktop.getDesktop().browse(new java.net.URI(articleID));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public void onMoreArticlesButtonClick() {

        categoryComboBox.getSelectionModel().clearSelection();
        ServiceManager.getExecutorService().submit(() -> {
            try {
                if (displayedArticles.isEmpty()) {
                    // Fallback: Fetch articles from all categories
                    List<Article> fallbackArticles = new ArrayList<>();
                    List<String> allCategories = List.of("Technology", "Sports", "Health", "Crime", "Politics", "Business");
                    for (String category : allCategories) {
                        fallbackArticles.addAll(databaseHandler.getArticlesByCategory(category, 7));
                    }
                    javafx.application.Platform.runLater(() -> displayArticles(fallbackArticles));
                } else {
                    // Generate recommendations
                    User currentUser = databaseHandler.loadUserWithInteractions(currentUsername);
                    RecommendationEngine recommendationEngine = new RecommendationEngine();
                    List<Article> recommendations = recommendationEngine.recommendArticles(currentUser);
                    javafx.application.Platform.runLater(() -> displayArticles(recommendations));
                }
            } catch (Exception e) {
                e.printStackTrace();
                javafx.application.Platform.runLater(() ->
                        showAlertMessage(AlertType.ERROR, "Error", "Unable to fetch articles.")
                );
            }
        });
    }

    public void onCategorySelected() {
        String selectedCategory = categoryComboBox.getValue();
        if (selectedCategory != null) {
            ServiceManager.getExecutorService().submit(() -> {
                List<Article> articles = databaseHandler.getArticlesByCategory(selectedCategory, 20);
                javafx.application.Platform.runLater(() -> displayArticles(articles));
            });
        }
    }

    public void onLikeButtonClick(Article article, Button likeButton) {
        likeButton.setDisable(true);
        likeButton.setText("Liked");

        ServiceManager.getExecutorService().submit(() -> {
            int userId = databaseHandler.getUserIDByUsername(currentUsername);
            int articleId = databaseHandler.getArticleIDByUrl(article.getArticle_id());

            if (userId != -1 && articleId != -1) {
                if (databaseHandler.addOrUpdateInteractions(userId, articleId, true)) {
                    System.out.println("Successfully added liked interaction");
                } else {
                    System.out.println("Failed to add liked interaction");
                }
            }
        });
    }

    public List<Article> getDisplayedArticles(){
        return new ArrayList<>(displayedArticles);
    }

    public void setDisplayedArticles(List<Article> articles){
        displayArticles(articles);
    }

    public void onProfileImageClick(MouseEvent mouseEvent) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/oodcw/user-profile.fxml"));
        Parent myProfileView = loader.load();

        UserProfileController userProfileController = loader.getController();
        userProfileController.setUserDetails(currentUsername);
        userProfileController.setPreviousArticles(getDisplayedArticles());

        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setTitle("My Profile");
        stage.setScene(new Scene(myProfileView));
        stage.show();
    }
}
