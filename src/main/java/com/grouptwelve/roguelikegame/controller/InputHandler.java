package com.grouptwelve.roguelikegame.controller;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Handles keyboard input and translates it to abstract Commands.
 *
 * Uses Observer pattern to notify listeners of command events.
 */
public class InputHandler {
    private final Set<KeyCode> pressedKeys;
    private final Map<KeyCode, Command> keyBindings;
    private InputEventListener listener;

    public InputHandler() {
        this.pressedKeys = new HashSet<>();
        this.keyBindings = new HashMap<>();

        // Movement keysi
        keyBindings.put(KeyCode.W, Command.MOVE_UP);
        keyBindings.put(KeyCode.UP, Command.MOVE_UP);
        keyBindings.put(KeyCode.S, Command.MOVE_DOWN);
        keyBindings.put(KeyCode.DOWN, Command.MOVE_DOWN);
        keyBindings.put(KeyCode.A, Command.MOVE_LEFT);
        keyBindings.put(KeyCode.LEFT, Command.MOVE_LEFT);
        keyBindings.put(KeyCode.D, Command.MOVE_RIGHT);
        keyBindings.put(KeyCode.RIGHT, Command.MOVE_RIGHT);

        // Action keys
        keyBindings.put(KeyCode.K, Command.ATTACK);
        // keyBindings.put(KeyCode.E, Command.ABILITY_1);

        // System keys
        // keyBindings.put(KeyCode.ESCAPE, Command.PAUSE);
    }
    public void setListener(InputEventListener listener)
    {
        this.listener = listener;
    }

    /**
     * Sets up keyboard event listeners on the given scene.
     * Should be called once during initialization.
     *
     * @param scene The JavaFX scene to attach listeners to
     */
    public void setupInputHandling(Scene scene) {
        scene.setOnKeyPressed(event -> {
            KeyCode key = event.getCode();

            // Only process if this is a new key press (not key repeat)
            if (!pressedKeys.contains(key)) {
                pressedKeys.add(key);

                // Translate key to command and notify listener
                Command command = keyBindings.get(key);
                if (command != null && listener != null) {
                    listener.onCommandPressed(command);
                }
            }
        });

        scene.setOnKeyReleased(event -> {
            KeyCode key = event.getCode();
            pressedKeys.remove(key);

            // Translate key to command and notify listener
            Command command = keyBindings.get(key);
            if (command != null && listener != null) {
                listener.onCommandReleased(command);
            }
        });
    }

    /**
     * Gets the set of currently active commands.
     *
     * @return Set of active commands
     */
    public Set<Command> getActiveCommands() {
        Set<Command> activeCommands = new HashSet<>();
        for (KeyCode key : pressedKeys) {
            Command command = keyBindings.get(key);
            if (command != null) {
                activeCommands.add(command);
            }
        }
        return activeCommands;
    }
}
