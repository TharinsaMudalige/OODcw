package com.example.oodcw.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AdminMenuController extends BaseController{
    public void OnViewArticlesButtonClick(ActionEvent actionEvent) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/oodcw/adminViewArticles.fxml"));
        Parent adminViewWindow = loader.load();

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Admin view articles");
        Scene scene = new Scene(adminViewWindow,940,720);
        stage.setScene(scene);

        stage.show();
    }

    public void OnViewUsersButtonClick(ActionEvent actionEvent) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/oodcw/adminViewUsers.fxml"));
        Parent adminViewUsersWindow = loader.load();

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Admin view users");
        Scene scene = new Scene(adminViewUsersWindow,940,720);
        stage.setScene(scene);

        stage.show();
    }


    public void OnLogOutButtonClick(ActionEvent actionEvent) throws Exception {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirm Log Out");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to log out? You will have to log back in.");

        alert.showAndWait().ifPresent(response -> {
            if(response.getButtonData().isDefaultButton()){
                try {
                    showAlertMessage(Alert.AlertType.INFORMATION, "Logged Out!", "You have been logged out successfully.");
                    GoToLoginPage(actionEvent);
                } catch (Exception e) {
                    showAlertMessage(Alert.AlertType.ERROR, "Error", "Something went wrong while logging out.");
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
