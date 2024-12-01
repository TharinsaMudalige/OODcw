package com.example.oodcw.Controllers;

import com.example.oodcw.Article;
import com.example.oodcw.DatabaseHandler;
import com.example.oodcw.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class UserProfileController extends BaseController {
    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label usernameLabel;

    private String currentUsername;
    private List<Article> previousArticles = new ArrayList<>();

    public void setUserDetails(String username){
        this.currentUsername = username;

        DatabaseHandler databaseHandler = new DatabaseHandler();
        User user = databaseHandler.getUserByUsername(currentUsername);

        if(user != null){
            firstNameLabel.setText(user.getFirstName());
            lastNameLabel.setText(user.getLastName());
            usernameLabel.setText(user.getUserName());
        }
    }

    public void setPreviousArticles(List<Article> articles){
        this.previousArticles = articles;
    }

    @FXML
    private void onLogOutButtonClick(ActionEvent actionEvent) throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Log Out");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to log out? You will have to log back in.");

        alert.showAndWait().ifPresent(response -> {
            if(response.getButtonData().isDefaultButton()){
                try {
                    showAlertMessage(AlertType.INFORMATION, "Logged Out!", "You have been logged out successfully.");
                    GoToLoginPage(actionEvent);
                } catch (Exception e) {
                    showAlertMessage(AlertType.ERROR, "Error", "Something went wrong while logging out.");
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @FXML
    private void OnDeleteAccountButtonClick(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete Account");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete this account?");

        alert.showAndWait().ifPresent(response -> {
            if(response.getButtonData().isDefaultButton()){
                DatabaseHandler databaseHandler = new DatabaseHandler();

                if(databaseHandler.deleteUser(currentUsername)){
                    showAlertMessage(AlertType.INFORMATION, "Account Deleted!", "Your account has been deleted successfully");
                    try {
                        redirectToMenu(actionEvent);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                }else {
                    showAlertMessage(AlertType.ERROR, "Error!", "Failed to delete account! Please try again");
                }

            }
        });

    }

    @FXML
    private void backToFeedButtonClick(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/oodcw/article-view.fxml"));
            Parent articleViewWindow = loader.load();

            ArticleViewController controller = loader.getController();
            controller.setUsername(currentUsername);
            controller.displayArticles(previousArticles);

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Explore Articles");
            stage.setScene(new Scene(articleViewWindow));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void redirectToMenu(ActionEvent actionEvent) throws Exception {
        GoToMainMenu(actionEvent);
    }
}
