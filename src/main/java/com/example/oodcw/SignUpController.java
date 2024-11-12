package com.example.oodcw;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignUpController {

    public TextField usernameText;
    public PasswordField passwordText;
    public PasswordField confirmPasswordText;
    public Button createAccountButton;
    public Button loginButton2;
    public TextField lastNameText;
    public TextField firstNameText;

    public void OnCreateAccountButtonClick(ActionEvent actionEvent) throws Exception{

    }

    public void OnLoginButtonClick(ActionEvent actionEvent) throws Exception{
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
