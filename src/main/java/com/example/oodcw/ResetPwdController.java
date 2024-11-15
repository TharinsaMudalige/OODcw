package com.example.oodcw;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ResetPwdController {
    @FXML
    private TextField usernameText2;
    @FXML
    private PasswordField newPasswordText;
    @FXML
    private PasswordField confirmPasswordText2;
    @FXML
    private Button changePasswordButton;

    private DatabaseHandler databaseHandler;

    public ResetPwdController(){
        databaseHandler = new DatabaseHandler();
    }

    public void OnChangePasswordButtonClick(ActionEvent actionEvent) {
        String username = usernameText2.getText();
        String newPassword = newPasswordText.getText();
        String confirmPassword = confirmPasswordText2.getText();

        if(username.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()){
            showAlertMessage(AlertType.ERROR, "Error!", "All fields are required!");
            return;
        }

        if(!newPassword.equals(confirmPassword)){
            showAlertMessage(AlertType.ERROR, "Error!", "Passwords do not match!");
            return;
        }

        if(!databaseHandler.isUsernameExists(username)){
            showAlertMessage(AlertType.ERROR, "Error!", "Username does not exist!");
            return;
        }

        if(databaseHandler.updatePassword(username, newPassword)){
            showAlertMessage(AlertType.INFORMATION, "Success!", "Password updated successfully!");

        } else {
            showAlertMessage(AlertType.ERROR, "Error!", "Password reset failed!");
        }
    }

    public void OnBackToMainMenuButtonClick(ActionEvent actionEvent) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("smartRead-view.fxml"));
        Parent BackToMenu = loader.load();

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Personalised News Recommendation System");
        Scene scene = new Scene(BackToMenu,940,720);
        stage.setScene(scene);

        stage.show();
    }

    public void showAlertMessage(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.showAndWait();
    }
}
