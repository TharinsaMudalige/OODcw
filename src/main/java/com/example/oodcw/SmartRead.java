package com.example.oodcw;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SmartRead extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SmartRead.class.getResource("smartRead-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 940, 720);
        stage.setTitle("Personalised News Recommendation System!");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        // Close database connection when the application exits
        DatabaseConnection.getInstance().closeConnection();
        System.out.println("Application exited and database connection closed.");

        // Shut down the executor service when the application exits
        ServiceManager.shutdownServices();
        System.out.println("Executor service shut down.");
    }

    public static void main(String[] args) {
        launch();
    }
}