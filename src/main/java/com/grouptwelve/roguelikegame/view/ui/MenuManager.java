package com.grouptwelve.roguelikegame.view.ui;

import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Manages menu visibility and blur effects.
 * Handles pause menu, death menu, and upgrade/level menu.
 */
public class MenuManager {
    private final VBox pauseMenu;
    private final VBox deathMenu;
    private final VBox upgradeMenu;
    private final AnchorPane gameLayer;
    private final GaussianBlur blur;

    private static final double BLUR_RADIUS = 10;

    public MenuManager(VBox pauseMenu, VBox deathMenu, VBox upgradeMenu, AnchorPane gameLayer, GaussianBlur blur) {
        this.pauseMenu = pauseMenu;
        this.deathMenu = deathMenu;
        this.upgradeMenu = upgradeMenu;
        this.gameLayer = gameLayer;
        this.blur = blur;
    }

    /**
     * Changes visibility of pause menu layer.
     *
     * @param show If true, pause menu is visible and blur is applied
     */
    public void showPauseMenu(boolean show) {
        pauseMenu.setVisible(show);
        pauseMenu.setDisable(!show);
        gameLayer.setDisable(show);
        blur.setRadius(show ? BLUR_RADIUS : 0);
    }

    /**
     * Changes visibility of upgrade/level menu layer.
     *
     * @param show If true, upgrade menu is visible and blur is applied
     */
    public void showLevelMenu(boolean show) {
        upgradeMenu.setVisible(show);
        blur.setRadius(show ? BLUR_RADIUS : 0);
    }

    /**
     * Shows the death menu with blur effect.
     */
    public void showDeathMenu() {
        deathMenu.setVisible(true);
        blur.setRadius(BLUR_RADIUS);
    }
}
