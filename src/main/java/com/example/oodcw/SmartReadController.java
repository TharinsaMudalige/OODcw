package com.example.oodcw;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Node;

public class SmartReadController {

    @FXML
    private Button signUpButton;
    @FXML
    private Button loginButton;


    public void OnLoginButtonClick(ActionEvent actionEvent) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent LoginWindow = loader.load();

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Log In");
        Scene scene = new Scene(LoginWindow,940,720);
        stage.setScene(scene);

        stage.show();
    }

    public void OnSignUpButtonClick(ActionEvent actionEvent) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sign-up.fxml"));
        Parent SignUpWindow = loader.load();

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Log In");
        Scene scene = new Scene(SignUpWindow,940,720);
        stage.setScene(scene);

        stage.show();
    }
}