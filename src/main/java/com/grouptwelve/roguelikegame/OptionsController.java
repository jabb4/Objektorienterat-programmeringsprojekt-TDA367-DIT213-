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

public class OptionsController {
    @FXML private Label actionLabel;
    @FXML private VBox root;
    @FXML private Button screenResolution;
    @FXML private Button audio;
    @FXML private Button controls;
    @FXML private Button back;

    private KeyboardNavigator navigator;


    public void initialize(){
        navigator = new KeyboardNavigator(screenResolution, audio, controls, back);

        // navigator.focusCurrent();
        // InputManager.get().clear(); // remove old bindnings
        
        InputManager.get().bind(KeyCode.UP, navigator::previous);
        InputManager.get().bind(KeyCode.DOWN, navigator::next);
        InputManager.get().bind(KeyCode.ENTER, navigator::activate);
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

        Stage stage = (Stage) actionLabel.getScene().getWindow();

        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("menu-view.fxml"));
        Scene menuScene = new Scene(menuLoader.load(), 800, 600);
        
        stage.setScene(menuScene);
        stage.show();
    }
}
