package com.example.oodcw.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Node;

public class SmartReadController extends BaseController {

    @FXML
    public Button signUpButton;
    @FXML
    public Button loginButton;


    public void OnLoginButtonClick(ActionEvent actionEvent) throws Exception{
        GoToLoginPage(actionEvent);
    }

    public void OnSignUpButtonClick(ActionEvent actionEvent) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/oodcw/sign-up.fxml"));
        Parent SignUpWindow = loader.load();

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Log In");
        Scene scene = new Scene(SignUpWindow,940,720);
        stage.setScene(scene);

        stage.show();
    }

    public void OnAdminButtonClick(ActionEvent actionEvent) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/oodcw/adminLogin.fxml"));
        Parent AdminLogInWindow = loader.load();

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Admin Login");
        Scene scene = new Scene(AdminLogInWindow,940,720);
        stage.setScene(scene);

        stage.show();

    }
}