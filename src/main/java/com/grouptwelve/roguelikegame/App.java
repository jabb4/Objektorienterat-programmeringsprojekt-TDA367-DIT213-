package com.grouptwelve.roguelikegame;

import java.io.IOException;

import com.grouptwelve.roguelikegame.controller.SceneManager;

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
        Font.loadFont(getClass().getResource("fonts/PressStart2P-Regular.ttf").toExternalForm(), 12);
        SceneManager sceneManager = new SceneManager(stage);
        sceneManager.showMenu();
    }

    public static void main(String[] args) {
        launch();
    }
}