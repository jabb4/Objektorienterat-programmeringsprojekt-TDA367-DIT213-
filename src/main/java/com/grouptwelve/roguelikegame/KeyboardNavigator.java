package com.grouptwelve.roguelikegame;

import javafx.scene.control.Button;

public class KeyboardNavigator {
    private final Button[] buttons;
    private int selectedIndex = 0;

    public KeyboardNavigator(Button... buttons) {
        this.buttons = buttons;
    }

    public void next() {
        selectedIndex = (selectedIndex + 1) % buttons.length;
    }

    public void previous() {
        selectedIndex = (selectedIndex - 1 + buttons.length) % buttons.length;
    }

    public void activate() {
        buttons[selectedIndex].fire();
    }

    public void focusCurrent() {
        buttons[selectedIndex].requestFocus();
    }
}
