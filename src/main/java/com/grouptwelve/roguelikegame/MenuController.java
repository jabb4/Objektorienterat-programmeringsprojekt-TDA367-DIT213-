package com.grouptwelve.roguelikegame;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;

public class MenuController {
    @FXML private Label actionLabel;
    @FXML private VBox root;
    @FXML private Button startButton;
    @FXML private Button optionsButton;
    @FXML private Button exitButton;
    
    private KeyboardNavigator navigator;

    public void initialize(){
        navigator = new KeyboardNavigator(startButton, optionsButton, exitButton);

        // navigator.focusCurrent();
        // InputManager.get().clear(); // remove old bindnings
        
        InputManager.get().bind(KeyCode.UP, navigator::previous);
        InputManager.get().bind(KeyCode.DOWN, navigator::next);
        InputManager.get().bind(KeyCode.ENTER, navigator::activate);
    }

    @FXML
    protected void onStartGamePressed() throws IOException {
        actionLabel.setText("Starting game...");

        Stage stage = (Stage) actionLabel.getScene().getWindow();

        FXMLLoader gameLoader = new FXMLLoader(getClass().getResource("game-view.fxml"));
        Scene gameScene = new Scene(gameLoader.load(), 800, 600);
        
        stage.setScene(gameScene);
        stage.show();
    }

    @FXML
    protected void onOptionsPressed() throws IOException {
        actionLabel.setText("Opening options...");

        Stage stage = (Stage) actionLabel.getScene().getWindow();

        FXMLLoader optionLoader = new FXMLLoader(getClass().getResource("option-view.fxml"));
        Scene optionScene = new Scene(optionLoader.load(), 800, 600);
        
        stage.setScene(optionScene);
        stage.show();
    }

    @FXML
    protected void onExitPressed() {
        System.exit(0);
    }
}