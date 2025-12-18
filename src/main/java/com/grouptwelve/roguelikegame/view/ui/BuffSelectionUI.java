package com.grouptwelve.roguelikegame.view.ui;

import com.grouptwelve.roguelikegame.model.upgrades.UpgradeInterface;
import javafx.scene.control.Button;

/**
 * Manages the buff selection UI.
 * Handles button text, selection highlighting, and clearing visuals.
 */
public class BuffSelectionUI {
    private final Button fireBuffBox;
    private final Button speedBuffBox;
    private final Button healthBuffBox;

    private static final String TRANSPARENT_BORDER = "-fx-border-color: transparent;";
    private static final String FIRE_SELECTED = "-fx-border-color: orange; -fx-border-width: 3;";
    private static final String SPEED_SELECTED = "-fx-border-color: cyan; -fx-border-width: 3;";
    private static final String HEALTH_SELECTED = "-fx-border-color: lime; -fx-border-width: 3;";

    public BuffSelectionUI(Button fireBuffBox, Button speedBuffBox, Button healthBuffBox) {
        this.fireBuffBox = fireBuffBox;
        this.speedBuffBox = speedBuffBox;
        this.healthBuffBox = healthBuffBox;
    }

    /**
     * Sets the text on buff buttons based on available Buffs.
     *
     * @param Buffs Array of 3 Buffs to display
     */
    public void setBuffOptions(UpgradeInterface[] Buffs) {
        fireBuffBox.setText(Buffs[0].getName());
        speedBuffBox.setText(Buffs[1].getName());
        healthBuffBox.setText(Buffs[2].getName());
    }

    /**
     * Highlights the selected buff button based on the index.
     *
     * @param selectedBuff index of selected Buff (0, 1, or 2)
     */
    public void updateSelectedLabel(int selectedBuff) {
        clearBuffVisuals();

        switch (selectedBuff) {
            case 0 -> fireBuffBox.setStyle(FIRE_SELECTED);
            case 1 -> speedBuffBox.setStyle(SPEED_SELECTED);
            case 2 -> healthBuffBox.setStyle(HEALTH_SELECTED);
        }
    }

    /**
     * Clears buff selection highlights.
     */
    public void clearBuffVisuals() {
        fireBuffBox.setStyle(TRANSPARENT_BORDER);
        speedBuffBox.setStyle(TRANSPARENT_BORDER);
        healthBuffBox.setStyle(TRANSPARENT_BORDER);
    }
}
