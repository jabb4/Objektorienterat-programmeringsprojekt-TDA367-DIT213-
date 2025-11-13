package com.grouptwelve.roguelikegame;

import com.grouptwelve.roguelikegame.controller.InputHandler;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        InputHandler inputHandler = new InputHandler();
        
        // UI Components
        Label directionLabel = new Label("Direction: [0, 0]");

        Label statusLabel = new Label("No keys pressed");

        VBox root = new VBox(20, directionLabel, statusLabel);

        Scene scene = new Scene(root, 800, 600);
        inputHandler.setupInputHandling(scene);
        
        // Game loop to update display
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                int[] dir = inputHandler.getMovementDirection();
                directionLabel.setText(String.format("Direction: [%d, %d]", dir[0], dir[1]));
                
                // Show which keys are active
                List<String> activeKeys = new ArrayList<>();
                if (inputHandler.isMoveUp()) activeKeys.add("UP");
                if (inputHandler.isMoveDown()) activeKeys.add("DOWN");
                if (inputHandler.isMoveLeft()) activeKeys.add("LEFT");
                if (inputHandler.isMoveRight()) activeKeys.add("RIGHT");
                
                if (activeKeys.isEmpty()) {
                    statusLabel.setText("No keys pressed");
                } else {
                    statusLabel.setText("Active: " + String.join(", ", activeKeys));
                }
            }
        };
        timer.start();
        
        stage.setTitle("Input Handler Test");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}