package com.grouptwelve.roguelikegame.view.ui;

import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;

/**
 * Manages the HUD elements.
 * Handles health bar, level/XP bar, and timer display.
 */
public class HudManager {
    private final Rectangle hpFill;
    private final Label hpLabel;
    private final Rectangle levelFill;
    private final Label levelLabel;
    private final Label xpLabel;
    private final Label timerLabel;

    private static final double BAR_WIDTH = 200;

    public HudManager(Rectangle hpFill, Label hpLabel, Rectangle levelFill, Label levelLabel, Label xpLabel, Label timerLabel) {
        this.hpFill = hpFill;
        this.hpLabel = hpLabel;
        this.levelFill = levelFill;
        this.levelLabel = levelLabel;
        this.xpLabel = xpLabel;
        this.timerLabel = timerLabel;
    }

    /**
     * Updates the health bar display.
     *
     * @param currentHp Entity's current hp
     * @param maxHp Entity's max hp
     */
    public void updateHealthBar(double currentHp, double maxHp) {
        double percentage = currentHp / maxHp;
        hpFill.setWidth(BAR_WIDTH * percentage);

        int roundedHp = (int) Math.round(currentHp);
        int roundedMaxHp = (int) Math.round(maxHp);
        hpLabel.setText(roundedHp + " / " + roundedMaxHp);
    }

    /**
     * Updates the level/XP bar display.
     *
     * @param xp Player's current xp amount
     * @param xpToNext The amount of xp to next level
     * @param level Player's level amount
     */
    public void updateLevelBar(int xp, int xpToNext, int level) {
        double percentage = (double) xp / xpToNext;
        levelFill.setWidth(BAR_WIDTH * percentage);
        levelLabel.setText("LVL: " + level);
        xpLabel.setText(xp + "/" + xpToNext);
    }

    /**
     * Updates the game timer display.
     *
     * @param gameTime Game time in seconds
     */
    public void updateGameTimeLabel(double gameTime) {
        int minutes = (int) (gameTime / 60);
        int seconds = (int) (gameTime % 60);
        timerLabel.setText(String.format("%d:%02d", minutes, seconds));
    }
}
