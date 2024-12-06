package com.example.oodcw.Controllers;

import com.example.oodcw.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import java.util.List;
import java.util.concurrent.*;

public class LoginController extends BaseController implements ArticleViewNavigator{
    @FXML
    private TextField logUsernameText;
    @FXML
    private PasswordField logPasswordText;

    private final DatabaseHandler databaseHandler;

    public LoginController() {
        databaseHandler = new DatabaseHandler();

    }

    public void OnLoginButton3Click(ActionEvent actionEvent) {
        String username = logUsernameText.getText().trim();
        String password = logPasswordText.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            showAlertMessage(AlertType.ERROR, "Error!", "Username or Password is empty");
            return;
        }

        if (!databaseHandler.isUsernameExists(username)) {
            showAlertMessage(AlertType.ERROR, "Error!", "Username is invalid");
            return;
        }

        if (!databaseHandler.isPasswordCorrect(username, password)) {
            showAlertMessage(AlertType.ERROR, "Error!", "Password is incorrect");
            return;
        }

        ExecutorService executorService = ServiceManager.getExecutorService();
        executorService.submit(() -> {
            try {
                User user = databaseHandler.loadUserWithInteractions(username);
                javafx.application.Platform.runLater(() -> navigateToArticleView(actionEvent, user));
            } catch (Exception e) {
                e.printStackTrace();
                javafx.application.Platform.runLater(() ->
                        showAlertMessage(AlertType.ERROR, "Error!", "Failed to log in. Please try again."));
            }
        });
    }

    public void OnCreateAccountButtonClick2(ActionEvent actionEvent) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/oodcw/sign-up.fxml"));
        Parent SignUpWindow = loader.load();

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Sign Up");
        Scene scene = new Scene(SignUpWindow, 940, 720);
        stage.setScene(scene);

        stage.show();
    }

    @Override
    public void navigateToArticleView(ActionEvent actionEvent, User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/oodcw/article-view.fxml"));
            Parent articleViewWindow = loader.load();

            ArticleViewController controller = loader.getController();
            controller.setUsername(user);

            // Submit the recommendation task to the executor service
            Future<List<Article>> futureRecommendations = ServiceManager.getExecutorService().submit(() -> {
                RecommendationEngine recommendationEngine = new RecommendationEngine();
                return recommendationEngine.recommendArticles(user);
            });

            ServiceManager.getExecutorService().submit(() -> {
                try {
                    List<Article> recommendedArticles = futureRecommendations.get(); // Blocking call
                    javafx.application.Platform.runLater(() -> controller.setDisplayedArticles(recommendedArticles));
                } catch (Exception e) {
                    e.printStackTrace();
                    javafx.application.Platform.runLater(() ->
                            showAlertMessage(AlertType.ERROR, "Error!", "Failed to load recommendations."));
                }
            });

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Your Feed");
            Scene scene = new Scene(articleViewWindow, 948, 720);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error navigating from login to article view");
        }
    }

    public void OnBackToMainMenuButtonClick(ActionEvent actionEvent) throws Exception {
        GoToMainMenu(actionEvent);
    }

    public void OnResetPasswordButtonClick(ActionEvent actionEvent) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/oodcw/reset-pwd.fxml"));
        Parent ResetPwd = loader.load();

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Reset Password");
        Scene scene = new Scene(ResetPwd, 940, 720);
        stage.setScene(scene);

        stage.show();
    }


}
