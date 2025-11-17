package com.grouptwelve.roguelikegame;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
        actionLabel.setText("Starting game...");

        Stage stage = (Stage) root.getScene().getWindow();

        FXMLLoader gameLoader = new FXMLLoader(getClass().getResource("game-view.fxml"));
        Scene gameScene = new Scene(gameLoader.load(), 800, 600);
        
        stage.setScene(gameScene);
        stage.show();
    }

    @FXML
    protected void onOptionsPressed() throws IOException {
        actionLabel.setText("Opening options...");

        Stage stage = (Stage) root.getScene().getWindow();

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