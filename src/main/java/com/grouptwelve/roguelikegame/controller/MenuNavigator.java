package com.grouptwelve.roguelikegame.controller;

import java.util.List;
import javafx.scene.control.Button;


/**
* Enables UI button navigation in menu layers (options, mainmenu etc.) using WASD,
* 
*
*/
public class MenuNavigator {
    private final List<Button> buttons;
    private int selectedIndex = 0;

    public MenuNavigator(List<Button> buttons) {
        this.buttons = buttons;
        
        // Disable FXML default focus traversal
        buttons.forEach(b -> b.setFocusTraversable(false));
        
        updateSelection();
    }
    
    /** Move selection up */
    public void moveUp(){
        if (buttons.isEmpty()) return;
        selectedIndex--;
        if (selectedIndex < 0) selectedIndex = buttons.size() - 1; // Wraps around
        updateSelection();
    }
    /** Move selection down */
    public void moveDown() {
        if (buttons.isEmpty()) return;
        selectedIndex++;
        if (selectedIndex >= buttons.size()) selectedIndex = 0; // Wraps around
        updateSelection();
    }

    /** Trigger the selected button */
    public void select() {
        if (buttons.isEmpty()) return;
        buttons.get(selectedIndex).fire();
    }

    /** Visually update selection highlight */
    public void updateSelection() {
        for (int i = 0; i < buttons.size(); i++) {
            Button b = buttons.get(i);
            if (i == selectedIndex) {
                // Highlight selected button
                b.setStyle("-fx-border-color: yellow; -fx-border-width: 3;");
            } else {
                // Reset
                b.setStyle("-fx-border-color: transparent;");
            }
        }
    }

    public void reset() {
        selectedIndex = 0;
        updateSelection();
    }
}