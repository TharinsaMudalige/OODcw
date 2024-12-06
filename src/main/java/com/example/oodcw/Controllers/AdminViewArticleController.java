package com.example.oodcw.Controllers;

import com.example.oodcw.Admin;
import com.example.oodcw.Article;
import com.example.oodcw.ArticleManager;
import com.example.oodcw.DatabaseHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class AdminViewArticleController extends BaseController {
    @FXML
    private TableView<Article> articleTable;

    @FXML
    private TableColumn<Article, Integer> idColumn;
    @FXML
    private TableColumn<Article, String> articleIdColumn;
    @FXML
    private TableColumn<Article, String> titleColumn;
    @FXML
    private TableColumn<Article, String> categoryColumn;
    @FXML
    private TableColumn<Article, String> contentColumn;
    @FXML
    private TableColumn<Article, String> sourceColumn;

    private DatabaseHandler databaseHandler;
    private ArticleManager articleManager;
    private Admin admin;

    public AdminViewArticleController() {
        databaseHandler = new DatabaseHandler();
        this.articleManager = new ArticleManager();
        admin = new Admin(1, "admin_tharinsa", "tharinsam@admin");
    }

    @FXML
    public void initialize() {
        // Set up the table columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        articleIdColumn.setCellValueFactory(new PropertyValueFactory<>("article_id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        contentColumn.setCellValueFactory(new PropertyValueFactory<>("content"));
        sourceColumn.setCellValueFactory(new PropertyValueFactory<>("source"));

        //Load articles from the database and display them in the TableView
        loadArticles();
    }

    private void loadArticles() {
        List<Article> articles = databaseHandler.getAllArticles(); //Load all the articles into the viewArticles list
        admin.addViewArticles(articles);
        ObservableList<Article> observableArticles = FXCollections.observableArrayList(admin.getViewArticles());
        articleTable.setItems(observableArticles);
    }

    public void OnBackButtonClick(ActionEvent actionEvent) throws Exception{
        GoToAdminMenu(actionEvent);
    }

    public void OnAddArticlesButtonClick() {
        try {
            //Fetch and store articles from API
            articleManager.fetchAndStoreArticles("Technology"); // Example category, you can expand to more categories
            articleManager.fetchAndStoreArticles("Health");
            articleManager.fetchAndStoreArticles("Sports");
            articleManager.fetchAndStoreArticles("Business");
            articleManager.fetchAndStoreArticles("Crime");
            articleManager.fetchAndStoreArticles("Politics");

            admin.getViewArticles().clear();
            //Update the tables
            loadArticles();


            showAlertMessage(AlertType.INFORMATION, "Success", "Articles added successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            showAlertMessage(AlertType.ERROR, "Error", "Failed to add articles.");
        }
    }

}
