package com.example.oodcw.Controllers;

import com.example.oodcw.Admin;
import com.example.oodcw.DatabaseHandler;
import com.example.oodcw.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class AdminViewUsersController extends BaseController {
    @FXML
    private TableView<User> usersTable;
    @FXML
    private TextField deleteUserIDText;

    private DatabaseHandler databaseHandler;
    private Admin admin;

    public AdminViewUsersController() {
        this.databaseHandler = new DatabaseHandler();
        this.admin = new Admin(1, "admin_tharinsa", "tharinsam@admin");
    }

    @FXML
    public void initialize() {
        //Configuring the table columns
        TableColumn<User, Integer> userIdColumn = new TableColumn<>("UserID");
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));

        TableColumn<User, String> firstNameColumn = new TableColumn<>("First Name");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<User, String> lastNameColumn = new TableColumn<>("Last Name");
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn<User, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));

        //Adding columns to the table
        usersTable.getColumns().setAll(userIdColumn, firstNameColumn, lastNameColumn, usernameColumn);

        //Load all the users from the database
        loadUsers();
    }

    private void loadUsers() {
        List<User> users = databaseHandler.getAllUsers();
        admin.addViewUsers(users);  //Adds users to the list
        ObservableList<User> usersObservableList = FXCollections.observableArrayList(admin.getViewUsers());
        usersTable.setItems(usersObservableList);
    }

    public void OnBackButtonClick(ActionEvent actionEvent) throws Exception {
        GoToAdminMenu(actionEvent);
    }

    public void OnDeleteUserButtonClick() {
        String userIdText = deleteUserIDText.getText();

        if (userIdText.isEmpty()) {
            showAlertMessage(AlertType.ERROR, "Error!", "Please enter a valid user ID.");
            return;
        }

        try {
            int userId = Integer.parseInt(userIdText);
            boolean isDeleted = databaseHandler.deleteUserById(userId);

            if (isDeleted) {
                showAlertMessage(AlertType.INFORMATION, "Success!", "User deleted successfully.");
                admin.getViewUsers().clear();
                loadUsers(); // Refresh the table
                deleteUserIDText.clear();

            } else {
                showAlertMessage(AlertType.ERROR, "Error!", "Failed to delete user. Please try again.");
            }
        } catch (NumberFormatException e) {
            showAlertMessage(AlertType.ERROR, "Error!", "User ID must be a number.");
        }
    }

}
