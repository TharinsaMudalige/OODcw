package com.example.oodcw.Controllers;

import com.example.oodcw.DatabaseHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController extends BaseController {
    @FXML
    private TextField logUsernameText;
    @FXML
    private Button logInButton3;
    @FXML
    private Button createAccountButton2;
    @FXML
    private PasswordField logPasswordText;

    private DatabaseHandler databaseHandler;

    public LoginController() {
        databaseHandler = new DatabaseHandler();
    }


    public void OnLoginButton3Click(ActionEvent actionEvent){
        String username = logUsernameText.getText();
        String password = logPasswordText.getText();

        if(username.isEmpty() || password.isEmpty()){
            showAlertMessage(Alert.AlertType.ERROR, "Error!", "Username or Password is empty");
            return;
        }

        if(!databaseHandler.isUsernameExists(username)){
            showAlertMessage(Alert.AlertType.ERROR, "Error!", "Username is invalid");
            return;
        }

        if(!databaseHandler.isPasswordCorrect(username, password)){
            showAlertMessage(Alert.AlertType.ERROR, "Error!", "Password is incorrect");
            return;
        }

        navigateToArticleDisplay(actionEvent,username);
    }

    public void OnCreateAccountButtonClick2(ActionEvent actionEvent) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/oodcw/sign-up.fxml"));
        Parent SignUpWindow = loader.load();

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Sign Up");
        Scene scene = new Scene(SignUpWindow,940,720);
        stage.setScene(scene);

        stage.show();
    }

    public void navigateToArticleDisplay(ActionEvent actionEvent, String username){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/oodcw/article-view.fxml"));
            Parent ArticleViewWindow = loader.load();

            ArticleViewController articleViewController = loader.getController();
            articleViewController.setUsername(username);

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Explore Articles");
            Scene scene = new Scene(ArticleViewWindow,948,720);
            stage.setScene(scene);

            stage.show();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void OnBackToMainMenuButtonClick(ActionEvent actionEvent) throws Exception {
        GoToMainMenu(actionEvent);
    }

    public void OnResetPasswordButtonClick(ActionEvent actionEvent) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/oodcw/reset-pwd.fxml"));
        Parent ResetPwd = loader.load();

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Reset Password");
        Scene scene = new Scene(ResetPwd,940,720);
        stage.setScene(scene);

        stage.show();

    }
}
