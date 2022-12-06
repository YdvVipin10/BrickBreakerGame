package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    static Stage globalstage;
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("startpage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 400);
        globalstage = stage;
        stage.setTitle("Break Breaker Game");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void startbuttonclick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("gamescreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 400);
        globalstage.setTitle("Game Screen");
        globalstage.setScene(scene);
    }
    public static void main(String[] args) {
        launch();
    }
}