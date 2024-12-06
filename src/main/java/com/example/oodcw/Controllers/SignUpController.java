package com.example.oodcw.Controllers;


import com.example.oodcw.DatabaseHandler;
import com.example.oodcw.ServiceManager;
import com.example.oodcw.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.concurrent.ExecutorService;

public class SignUpController extends BaseController {

    @FXML
    private TextField usernameText;
    @FXML
    private PasswordField passwordText;
    @FXML
    private PasswordField confirmPasswordText;
    @FXML
    private TextField lastnameText;
    @FXML
    private TextField firstnameText;

    private DatabaseHandler databaseHandler;

    public SignUpController() {
        databaseHandler = new DatabaseHandler(); //Initializing the database handler
    }

    public void OnCreateAccountButtonClick(ActionEvent actionEvent) throws Exception{
        String firstName = firstnameText.getText();
        String lastName = lastnameText.getText();
        String username = usernameText.getText();
        String password = passwordText.getText();
        String confirmPassword = confirmPasswordText.getText();

        //Check if fields are empty
        if(firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()){
            showAlertMessage(AlertType.ERROR, "Error!","Please fill all fields!");
            return;
        }

        //Check whether confirm password not equals password
        if(!password.equals(confirmPassword)){
            showAlertMessage(AlertType.ERROR, "Error!","Passwords don't match!");
            return;
        }

        ExecutorService executorService = ServiceManager.getExecutorService();
        executorService.submit(() -> {
            try {
                //Check if the username exists
                if (databaseHandler.isUsernameExists(username)) {
                    javafx.application.Platform.runLater(() ->
                            showAlertMessage(AlertType.ERROR, "Error!", "Username already exists! Please enter a different username."));
                    return;
                }

                //Create and add the new user
                User user = new User(firstName, lastName, username, password);
                if (databaseHandler.addUser(user)) {
                    javafx.application.Platform.runLater(() -> {
                        showAlertMessage(AlertType.INFORMATION, "Success!", "Registered successfully!");
                        try {
                            GoToLoginPage(actionEvent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                } else {
                    javafx.application.Platform.runLater(() ->
                            showAlertMessage(AlertType.ERROR, "Error!", "Registration failed!"));
                }
            } catch (Exception e) {
                e.printStackTrace();
                javafx.application.Platform.runLater(() ->
                        showAlertMessage(AlertType.ERROR, "Error!", "An unexpected error occurred!"));
            }
        });

    }

    public void OnLoginButton2Click(ActionEvent actionEvent) throws Exception{
        GoToLoginPage(actionEvent);
    }

    public void OnBackToMainMenuButtonClick(ActionEvent actionEvent) throws Exception {
        GoToMainMenu(actionEvent);
    }
}
