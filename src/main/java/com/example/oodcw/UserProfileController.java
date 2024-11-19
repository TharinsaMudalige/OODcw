package com.example.oodcw;

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

public class UserProfileController extends BaseController {
    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label usernameLabel;

    private String currentUsername;

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

    @FXML
    private void onLogOutButtonClick(ActionEvent actionEvent) throws Exception {
        GoToLoginPage(actionEvent);
    }

    @FXML
    private void OnDeleteAccountButtonClick(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("article-view.fxml"));
            Parent articleViewWindow = loader.load();

            ArticleViewController controller = loader.getController();
            controller.setUsername(currentUsername);

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
