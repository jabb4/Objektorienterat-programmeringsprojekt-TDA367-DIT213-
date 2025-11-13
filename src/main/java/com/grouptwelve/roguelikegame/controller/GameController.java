package com.grouptwelve.roguelikegame.controller;

import com.grouptwelve.roguelikegame.model.Game;
import com.grouptwelve.roguelikegame.view.GameView;
import javafx.animation.AnimationTimer;

import java.util.ArrayList;
import java.util.List;

/**
 * Coordinates the game loop, connecting user input to model updates and view rendering.
 */
public class GameController {
    private final Game game;
    private final GameView gameView;
    private final InputHandler inputHandler;
    private AnimationTimer gameLoop;
    private long lastUpdate;
    
    public GameController(Game game, GameView gameView, InputHandler inputHandler) {
        this.game = game;
        this.gameView = gameView;
        this.inputHandler = inputHandler;
        this.lastUpdate = 0;
    }
    
    /**
     * Starts the game loop.
     * Should be used for resuming game states
     */
    public void start() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (lastUpdate == 0) {
                    lastUpdate = now;
                    return;
                }
                
                // Calculate delta time in seconds
                double deltaTime = (now - lastUpdate) / 1_000_000_000.0;
                lastUpdate = now;
                
                update(deltaTime);
                render();
            }
        };
        gameLoop.start();
    }
    
    /**
     * Updates the game state based on input and elapsed time.
     */
    private void update(double deltaTime) {
        // Handle input
        int[] dir = inputHandler.getMovementDirection();
        game.movePlayer(dir[0], dir[1], deltaTime);
        
        // Update game logic
        game.update(deltaTime);
        
        // Update UI displays
        gameView.updateDirectionLabel(dir[0], dir[1]);
        gameView.updateGameTimeLabel(game.getGameTime());
        updateStatusDisplay();
    }
    
    /**
     * Renders the current game state.
     */
    private void render() {
        gameView.render(game);
    }
    
    /**
     * Updates the status label based on active keys.
     */
    private void updateStatusDisplay() {
        List<String> activeKeys = new ArrayList<>();
        if (inputHandler.isMoveUp()) activeKeys.add("UP");
        if (inputHandler.isMoveDown()) activeKeys.add("DOWN");
        if (inputHandler.isMoveLeft()) activeKeys.add("LEFT");
        if (inputHandler.isMoveRight()) activeKeys.add("RIGHT");
        
        if (activeKeys.isEmpty()) {
            gameView.updateStatusLabel("No keys pressed");
        } else {
            gameView.updateStatusLabel("Active: " + String.join(", ", activeKeys));
        }
    }
    
    /**
     * Stops the game loop.
     * Should be used for pausing game states
     */
    public void stop() {
        if (gameLoop != null) {
            gameLoop.stop();
        }
    }
}
