package com.grouptwelve.roguelikegame.controller;

import java.io.IOException;
import java.util.List;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class OptionsController implements InputEventListener {
    @FXML private Label actionLabel;
    @FXML private VBox root;

    private MenuNavigator menuNavigator;


    public void initialize(){
        // Extract available buttons from menu-view
        List<Button> menuButtons = root.getChildren().stream()
            .filter(node -> node instanceof Button)
            .map(node -> (Button) node)
            .toList();

        menuNavigator = new MenuNavigator(menuButtons);

        // Make sure scene exists and attach InputHandler
        Platform.runLater(() -> {
            Scene scene = root.getScene();
            if (scene != null) {
                InputHandler inputHandler = new InputHandler();
                inputHandler.setListener(this);  // MenuController will receive commands
                inputHandler.setupInputHandling(scene);

                root.requestFocus(); // Ensure scene receives key events
            }
        });
    }

    // ================= InputEventListener =================
    @Override
    public void onCommandPressed(Command command) {
        switch (command) {
            case MOVE_UP -> menuNavigator.moveUp();
            case MOVE_DOWN -> menuNavigator.moveDown();
            case SELECT -> menuNavigator.select();
        }
    }

    @Override
    public void onCommandReleased(Command command) {
        // Nothing needed for menu navigation
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
        Scene menuScene = new Scene(menuLoader.load(), 1280, 720);
        
        // Attach global CSS
        menuScene.getStylesheets().add(getClass().getResource("/com/grouptwelve/roguelikegame/global.css").toExternalForm());

        stage.setScene(menuScene);
        stage.show();
    }
}
