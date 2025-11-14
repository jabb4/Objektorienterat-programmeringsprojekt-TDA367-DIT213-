package com.grouptwelve.roguelikegame;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import java.util.HashMap;
import java.util.Map;


public class InputManager {
    private static final InputManager instance = new InputManager();

    public static InputManager get() {
        return instance;
    }

    private final Map<KeyCode, Runnable> keyActions = new HashMap<>();

    private InputManager() {}

    public void attach(Scene scene) {
        scene.setOnKeyPressed(event -> {
            Runnable action = keyActions.get(event.getCode());
            if (action != null) action.run();
        });
    }

    public void bind(KeyCode key, Runnable action) {
        keyActions.put(key, action);
    }

    public void unbind(KeyCode key) {
        keyActions.remove(key);
    }

    public void clear() {
        keyActions.clear();
    }
}
