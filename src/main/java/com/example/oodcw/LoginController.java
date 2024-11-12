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

public class LoginController {
    public TextField logUsernameButton;
    public Button logInButton3;
    public Button createAccountButton2;
    public PasswordField logPasswordText;


    public void OnLoginButton3Click(ActionEvent actionEvent) throws Exception {
    }

    public void OnCreateAccountButtonClick2(ActionEvent actionEvent) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sign-up.fxml"));
        Parent SignUpWindow = loader.load();

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Sign Up");
        Scene scene = new Scene(SignUpWindow,940,720);
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
