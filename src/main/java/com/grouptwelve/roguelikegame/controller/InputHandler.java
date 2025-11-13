package com.grouptwelve.roguelikegame.controller;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import java.util.HashSet;
import java.util.Set;

/**
 * Handles keyboard input for player movement.
 * Tracks currently pressed keys and provides movement direction queries.
 */
public class InputHandler {
    private final Set<KeyCode> pressedKeys;
    
    public InputHandler() {
        this.pressedKeys = new HashSet<>();
    }
    
    /**
     * Sets up keyboard event listeners on the given scene.
     * Should be called once during initialization.
     * 
     * @param scene The JavaFX scene to attach listeners to
     */
    public void setupInputHandling(Scene scene) {
        scene.setOnKeyPressed(event -> {
            pressedKeys.add(event.getCode());
        });
        
        scene.setOnKeyReleased(event -> {
            pressedKeys.remove(event.getCode());
        });
    }
    
    /**
     * Checks if a specific key is currently pressed.
     * 
     * @param key The KeyCode to check
     * @return true if the key is pressed, false otherwise
     */
    public boolean isKeyPressed(KeyCode key) {
        return pressedKeys.contains(key);
    }
    
    /**
     * Checks if any "move up" key is pressed (W or UP arrow).
     */
    public boolean isMoveUp() {
        return isKeyPressed(KeyCode.W) || isKeyPressed(KeyCode.UP);
    }
    
    /**
     * Checks if any "move down" key is pressed (S or DOWN arrow).
     */
    public boolean isMoveDown() {
        return isKeyPressed(KeyCode.S) || isKeyPressed(KeyCode.DOWN);
    }
    
    /**
     * Checks if any "move left" key is pressed (A or LEFT arrow).
     */
    public boolean isMoveLeft() {
        return isKeyPressed(KeyCode.A) || isKeyPressed(KeyCode.LEFT);
    }
    
    /**
     * Checks if any "move right" key is pressed (D or RIGHT arrow).
     */
    public boolean isMoveRight() {
        return isKeyPressed(KeyCode.D) || isKeyPressed(KeyCode.RIGHT);
    }
    
    /**
     * Calculates the movement direction as discrete values (-1, 0, or 1).
     *
     * @return An array [dx, dy] where each value is -1, 0, or 1
     */
    public int[] getMovementDirection() {
        int dx = 0;
        int dy = 0;
        
        if (isMoveLeft()) dx -= 1;
        if (isMoveRight()) dx += 1;
        if (isMoveUp()) dy += 1;
        if (isMoveDown()) dy -= 1;
        
        return new int[]{dx, dy};
    }
}
