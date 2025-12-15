package com.grouptwelve.roguelikegame;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Main application - bootstraps the game with proper dependency injection.
 */
public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Load the font
        Font.loadFont(getClass().getResource("fonts/PressStart2P-Regular.ttf").toExternalForm(), 12);

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("menu-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        scene.getStylesheets().add(getClass().getResource("global.css").toExternalForm());
        stage.setScene(scene);
        stage.show(); 
    }

    public static void main(String[] args) {
        launch();
    }
}
