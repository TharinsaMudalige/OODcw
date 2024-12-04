package com.example.oodcw.Controllers;

import com.example.oodcw.DatabaseHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class AdminLogInController extends BaseController{
    @FXML
    private TextField adminUsernameText;
    @FXML
    private PasswordField adminPwdText;

    private DatabaseHandler databaseHandler;

    public AdminLogInController() {
        this.databaseHandler = new DatabaseHandler();
    }

    public void OnLogInButtonClick(ActionEvent actionEvent) throws Exception{
        String enteredUsername = adminUsernameText.getText().trim();
        String enteredPassword = adminPwdText.getText().trim();

        // Validate input
        if (enteredUsername.isEmpty() || enteredPassword.isEmpty()) {
            showAlertMessage(AlertType.ERROR, "Error", "Username or Password cannot be empty!");
            return;
        }

        // Check credentials using DatabaseHandler
        boolean isAdminValid = databaseHandler.validateAdminCredentials(enteredUsername, enteredPassword);

        if (isAdminValid) {
            // Redirect to Admin Menu
            GoToAdminMenu(actionEvent);
        } else {
            showAlertMessage(AlertType.ERROR, "Login Failed", "Invalid Username or Password!");
        }
    }

    public void OnBackButtonClick(ActionEvent actionEvent) throws Exception{
        GoToMainMenu(actionEvent);
    }
}
