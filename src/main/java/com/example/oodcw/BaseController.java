package com.example.oodcw;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class BaseController {

    protected void GoToMainMenu(ActionEvent actionEvent) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("smartRead-view.fxml"));
        Parent mainMenu = loader.load();

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Personalised News Recommendation System");
        Scene scene = new Scene(mainMenu, 940, 720);
        stage.setScene(scene);

        stage.show();
    }

    protected void GoToLoginPage(ActionEvent actionEvent) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent LoginWindow = loader.load();

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Log In");
        Scene scene = new Scene(LoginWindow,940,720);
        stage.setScene(scene);

        stage.show();
    }

    protected void showAlertMessage(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null); // No header for simplicity
        alert.setContentText(message);
        alert.showAndWait();
    }
}
