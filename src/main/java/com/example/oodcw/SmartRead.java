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

    public static void main(String[] args) {
        launch();
    }
}