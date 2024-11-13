package com.example.oodcw;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    @FXML

    public void OnCreateAccountButtonClick(ActionEvent actionEvent) throws Exception{

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
}
