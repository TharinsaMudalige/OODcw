package com.example.oodcw;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ArticleViewController extends BaseController{
    @FXML
    private ChoiceBox<String> optionChoiceBox;
    @FXML
    private Label usernameDisplayText;
    @FXML
    private VBox articlesContainer;

    private DatabaseHandler databaseHandler;
    private String currentUsername;

    public ArticleViewController() {
        databaseHandler = new DatabaseHandler();
    }

    public void initialize() {
        optionChoiceBox.getItems().addAll("Log Out", "Delete Account");
        optionChoiceBox.setOnAction(this::onOptionSelected);

    }

    public void setUsername(String username){
        usernameDisplayText.setText(username);
    }

    public void onMoreArticlesButtonClick(ActionEvent actionEvent) throws Exception {
    }

    @FXML
    private void onOptionSelected(ActionEvent actionEvent) {
        String option = optionChoiceBox.getValue();
        if(option.equals("Log Out")){
            logOut(actionEvent);

        } else if(option.equals("Delete Account")){
            deleteAccount(actionEvent);
        }
    }

    private void logOut(ActionEvent actionEvent) {
        Alert logOutConfirmation = new Alert(AlertType.CONFIRMATION);
        logOutConfirmation.setTitle("Confirm Log Out");
        logOutConfirmation.setHeaderText(null);
        logOutConfirmation.setContentText("Are you sure you want to log out?");

        logOutConfirmation.showAndWait().ifPresent(response -> {
            if(response.getButtonData().isDefaultButton()){ // If "OK" is clicked
                try{
                    GoToLoginPage(actionEvent);
                } catch(Exception e){
                    e.printStackTrace();
                    showAlertMessage(AlertType.CONFIRMATION,"Error!", "Failed to Log out. Please try again!");
                }
            }
        });
    }

    private void deleteAccount(ActionEvent actionEvent) {
        Alert deleteConfirmation = new Alert(AlertType.CONFIRMATION);
        deleteConfirmation.setTitle("Confirm Delete Account");
        deleteConfirmation.setHeaderText(null);
        deleteConfirmation.setContentText("Are you sure you want to delete the account? This cannot be undone!");

        deleteConfirmation.showAndWait().ifPresent(response -> {
            if (response.getButtonData().isDefaultButton()) {
                if (databaseHandler.deleteUser(currentUsername)) {
                    showAlertMessage(AlertType.INFORMATION, "Account Deleted!", "Your account has been deleted successfully.");
                    logOut(actionEvent); // Redirect to log in screen
                } else {
                    showAlertMessage(AlertType.ERROR, "Error", "Failed to delete account. Please try again.");
                }
            }
        });


    }

}
