package com.grouptwelve.roguelikegame.view.effects;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

/**
 * Handles floating damage number effects.
 * Numbers rise and fade out.
 */
public class DamageNumberEffect {
    private final AnchorPane effectsLayer;

    public DamageNumberEffect(AnchorPane effectsLayer) {
        this.effectsLayer = effectsLayer;
    }

    /**
     * Displays a floating damage number that rises and fades out.
     * Critical hits are displayed larger and in a different color.
     *
     * @param x X position of the damage
     * @param y Y position of the damage
     * @param damage Amount of damage to display
     * @param isCritical Whether this was a critical hit
     */
    public void show(double x, double y, double damage, boolean isCritical) {
        Label dmgLabel = new Label(String.format("%.0f", damage) + (isCritical ? "!" : ""));

        if (isCritical) {
            dmgLabel.setStyle("-fx-text-fill: gold; -fx-font-size: 20px; -fx-font-family: 'Press Start 2P';");
        } else {
            dmgLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-font-family: 'Press Start 2P';");
        }

        dmgLabel.setLayoutX(x - 10);
        dmgLabel.setLayoutY(y - 30);
        effectsLayer.getChildren().add(dmgLabel);

        TranslateTransition floatUp = new TranslateTransition(Duration.millis(400), dmgLabel);
        floatUp.setByY(-25);

        FadeTransition fadeOut = new FadeTransition(Duration.millis(400), dmgLabel);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(e -> effectsLayer.getChildren().remove(dmgLabel));

        floatUp.play();
        fadeOut.play();
    }
}
