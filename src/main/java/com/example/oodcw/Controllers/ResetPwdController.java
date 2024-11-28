package com.example.oodcw.Controllers;

import com.example.oodcw.DatabaseHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class ResetPwdController extends BaseController {
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
        GoToMainMenu(actionEvent);
    }

}
