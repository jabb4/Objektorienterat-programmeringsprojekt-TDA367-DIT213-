package com.grouptwelve.roguelikegame.controller;

import java.io.IOException;
import java.util.List;
import javafx.scene.control.Button;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

public class MenuController implements InputEventListener {
    @FXML private AnchorPane root;
    
    private MenuNavigator menuNavigator;
    private SceneManager sceneManager;


    public void initialize(){
        // Extract available buttons from menu-view
        List<Button> menuButtons = root.lookupAll(".menu-button").stream()
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

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
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


    // ================= FXML Buttons =================

    @FXML
    protected void onStartGamePressed() throws IOException {
        sceneManager.startGame();
    }

    @FXML
    protected void onExitPressed() {
        System.exit(0);
    }
}