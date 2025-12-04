package com.grouptwelve.roguelikegame;

import com.grouptwelve.roguelikegame.controller.GameController;
import com.grouptwelve.roguelikegame.controller.InputHandler;
import com.grouptwelve.roguelikegame.model.events.EventPublisher;
import com.grouptwelve.roguelikegame.model.Game;
import com.grouptwelve.roguelikegame.view.GameView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main application - bootstraps the game with proper dependency injection.
 */
public class App extends Application {
    @Override
    public void start(Stage stage) {
        // Get the event manager (acts as event bus between model and controller)
        EventPublisher eventManager = EventPublisher.getInstance();
        
        // Create game with event publisher
        Game game = new Game(eventManager);
        
        // Create view and input handler
        GameView gameView = new GameView();
        InputHandler inputHandler = new InputHandler();

        Scene scene = new Scene(gameView.getRoot(), 800, 600);
        inputHandler.setupInputHandling(scene);
        
        // Create controller (subscribes to event manager)
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
