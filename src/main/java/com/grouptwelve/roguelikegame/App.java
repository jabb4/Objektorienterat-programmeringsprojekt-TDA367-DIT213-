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
        Font.loadFont(App.class.getResourceAsStream("fonts/PressStart2P-Regular.ttf"), 12);

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("menu-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setScene(scene);
        stage.show(); 
    }

    public static void main(String[] args) {
        launch();
    }
}
