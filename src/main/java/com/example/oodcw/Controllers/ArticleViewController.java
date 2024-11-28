package com.example.oodcw.Controllers;

import com.example.oodcw.Article;
import com.example.oodcw.DatabaseHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class ArticleViewController extends BaseController {

    @FXML
    private ImageView userProfileImage;
    @FXML
    private ComboBox<String> categoryComboBox;
    @FXML
    private Label usernameDisplayText;
    @FXML
    private VBox articlesContainer;

    private DatabaseHandler databaseHandler;

    private String currentUsername;


    public ArticleViewController() {
        databaseHandler = new DatabaseHandler();
    }

    public void initialize() {
        categoryComboBox.getItems().addAll("Technology", "Sports", "Health", "Crime", "Politics", "Business");
    }

    public void setUsername(String username){
        this.currentUsername = username;
        usernameDisplayText.setText(username);

    }

    public void displayArticles(List<Article> articles){
        articlesContainer.getChildren().clear(); //Clear any existing articles

        for(Article article : articles){
            VBox articleBox = new VBox(5);
            articleBox.setStyle("-fx-padding: 15; -fx-background-color: #f0f0f0; -fx-border-color: #ccc; -fx-border-width: 1;");
            articleBox.setPrefWidth(Region.USE_COMPUTED_SIZE);
            articleBox.setMinWidth(Region.USE_PREF_SIZE);
            articleBox.setMaxWidth(Double.MAX_VALUE);

            Label titleLabel = new Label("Title: " + article.getTitle());
            titleLabel.setStyle("-fx-font-weight: bold;");

            Label contentLabel = new Label("Content: " + article.getContent());
            contentLabel.setStyle("-fx-font-size: 12px;"); // Optional: Style the text
            contentLabel.setPrefWidth(Region.USE_COMPUTED_SIZE); // Allow content to grow horizontally
            contentLabel.setMaxWidth(Double.MAX_VALUE);
            Label categoryLabel = new Label("Category: " + article.getCategory());
            Label sourceLabel = new Label("Source: " + article.getSource());
            Label linkLabel = new Label("Link: " + article.getArticle_id());

            linkLabel.setStyle("-fx-text-fill: blue; -fx-underline: true;");
            linkLabel.setOnMouseClicked(event -> articleLinkClick(article.getArticle_id()));

            articleBox.getChildren().addAll(titleLabel, contentLabel, categoryLabel, sourceLabel, linkLabel);
            articlesContainer.getChildren().add(articleBox);
        }

    }

    private void articleLinkClick(String articleID){
        try{
            java.awt.Desktop.getDesktop().browse(new java.net.URI(articleID));

        } catch (Exception e){
            e.printStackTrace();
        }
    }


    @FXML
    private void onMoreArticlesButtonClick(ActionEvent actionEvent) throws Exception {
    }

    @FXML
    private void onCategorySelected() {
    }

    @FXML
    private void onProfileImageClick(MouseEvent mouseEvent) throws Exception {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/oodcw/user-profile.fxml"));
            Parent myProfileView = loader.load();

            UserProfileController userProfileController = loader.getController();
            userProfileController.setUserDetails(currentUsername);

            Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            stage.setTitle("My Profile");
            stage.setScene(new Scene(myProfileView));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
