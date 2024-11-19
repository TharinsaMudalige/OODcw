package com.example.oodcw;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ArticleViewController extends BaseController{

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
        categoryComboBox.getItems().addAll("Technology", "Sports", "Health", "Entertainment", "Travel", "Business");
    }

    public void setUsername(String username){
        this.currentUsername = username;
        usernameDisplayText.setText(username);

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("user-profile.fxml"));
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
