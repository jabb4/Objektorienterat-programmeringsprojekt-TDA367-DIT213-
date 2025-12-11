package com.grouptwelve.roguelikegame.controller;

import java.io.IOException;

import com.grouptwelve.roguelikegame.model.Game;
import com.grouptwelve.roguelikegame.model.events.output.EventPublisher;
import com.grouptwelve.roguelikegame.view.GameView;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MenuController {
    @FXML private Label actionLabel;
    @FXML private VBox root;
    
    public void initialize(){

    }

    @FXML
    protected void onStartGamePressed() throws IOException {
        Stage stage = (Stage) root.getScene().getWindow();

        // Create view and input handler
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/grouptwelve/roguelikegame/game-view.fxml"));
        Parent root = loader.load();  // FXML creates the GameView instance
        GameView gameView = loader.getController();
        InputHandler inputHandler = new InputHandler();
    
        // Get the event manager (acts as event bus between model and controller)
        EventPublisher eventManager = new EventPublisher();
        
        // Create game with event publisher
        Game game = new Game(eventManager);
        game.reset(); // To make sure that previous session doesnt effect new one

        Scene gameScene = new Scene(root, 1280, 720);
        gameScene.getStylesheets().add(getClass().getResource("/com/grouptwelve/roguelikegame/global.css").toExternalForm());

        inputHandler.setupInputHandling(gameScene);
        
        GameController gameController = new GameController(game, gameView, inputHandler, eventManager);
        gameView.setGameController(gameController);
        gameController.start();
        
        stage.setTitle("Roguelike Game");
        stage.setScene(gameScene);
        stage.show();
    }

    @FXML
    protected void onOptionsPressed() throws IOException {
        actionLabel.setText("Opening options...");

        Stage stage = (Stage) root.getScene().getWindow();

        FXMLLoader optionLoader = new FXMLLoader(getClass().getResource("/com/grouptwelve/roguelikegame/option-view.fxml"));
        Scene optionScene = new Scene(optionLoader.load(), 800, 600);

        optionScene.getStylesheets().add(getClass().getResource("/com/grouptwelve/roguelikegame/global.css").toExternalForm());

        
        stage.setScene(optionScene);
        stage.show();
    }

    @FXML
    protected void onExitPressed() {
        System.exit(0);
    }
}