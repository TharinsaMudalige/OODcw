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

public class SignUpController {

    @FXML
    private TextField usernameText;
    @FXML
    private PasswordField passwordText;
    @FXML
    private PasswordField confirmPasswordText;
    @FXML
    private Button createAccountButton;
    @FXML
    private Button loginButton2;
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

        //Check if the username exists already
        if(databaseHandler.isUsernameExists(username)){
            showAlertMessage(AlertType.ERROR, "Error!","Username already exists!");
            return;
        }

        User user = new User(firstName, lastName, username, password);

        if(databaseHandler.addUser(user)){
            showAlertMessage(AlertType.INFORMATION, "Success!","User successfully added!");
        } else {
            showAlertMessage(AlertType.ERROR, "Error!","Registration failed!");
        }

    }

    public void OnLoginButton2Click(ActionEvent actionEvent) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent LoginWindow = loader.load();

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Log In");
        Scene scene = new Scene(LoginWindow,940,720);
        stage.setScene(scene);

        stage.show();
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

    public void showAlertMessage(AlertType alertType, String title, String message){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
