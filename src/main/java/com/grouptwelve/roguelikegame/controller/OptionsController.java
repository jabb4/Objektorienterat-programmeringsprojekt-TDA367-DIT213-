package com.grouptwelve.roguelikegame.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class OptionsController {
    @FXML private Label actionLabel;
    @FXML private VBox root;


    public void initialize(){
    }


    @FXML
    protected void onSRPressed() throws IOException {
        actionLabel.setText("Screen resolution pressed...");
    }

    @FXML
    protected void onAudioPressed() throws IOException {
        actionLabel.setText("Audio pressed...");
    }

     @FXML
    protected void onControlsPressed() throws IOException {
        actionLabel.setText("Controls pressed...");
    }

    @FXML
    protected void onBackPressed() throws IOException  {
        actionLabel.setText("Back pressed...");

        Stage stage = (Stage) root.getScene().getWindow();

        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("/com/grouptwelve/roguelikegame/menu-view.fxml"));
        Scene menuScene = new Scene(menuLoader.load(), 800, 600);
        
        // Attach global CSS
        menuScene.getStylesheets().add(getClass().getResource("/com/grouptwelve/roguelikegame/global.css").toExternalForm());

        stage.setScene(menuScene);
        stage.show();
    }
}
