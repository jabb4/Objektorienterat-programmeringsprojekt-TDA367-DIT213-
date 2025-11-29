package com.grouptwelve.roguelikegame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class GameApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Load the font
        Font.loadFont(getClass().getResource("fonts/PressStart2P-Regular.ttf").toExternalForm(), 12);

        FXMLLoader fxmlLoader = new FXMLLoader(GameApplication.class.getResource("menu-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        scene.getStylesheets().add(getClass().getResource("global.css").toExternalForm());
        stage.setScene(scene);
        stage.show(); 
    }

    public static void main(String[] args) {
        launch();
    }
}