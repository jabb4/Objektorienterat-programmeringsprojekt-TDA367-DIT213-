package com.grouptwelve.roguelikegame;

import com.grouptwelve.roguelikegame.controller.GameController;
import com.grouptwelve.roguelikegame.controller.InputHandler;
import com.grouptwelve.roguelikegame.model.Game;
import com.grouptwelve.roguelikegame.view.GameView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main application
 */
public class App extends Application {
    @Override
    public void start(Stage stage) {

        Game game = Game.getInstance();
        GameView gameView = new GameView();
        InputHandler inputHandler = new InputHandler();

        Scene scene = new Scene(gameView.getRoot(), 800, 600);
        inputHandler.setupInputHandling(scene);
        
        GameController gameController = new GameController(game, gameView, inputHandler);

        gameController.start();
        
        stage.setTitle("Roguelike Game");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
