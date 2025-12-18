package com.grouptwelve.roguelikegame.view.effects;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * Handles player death visual effects.
 * Includes shockwave and red flash effects.
 */
public class DeathEffect {
    private final AnchorPane effectsLayer;

    public DeathEffect(AnchorPane effectsLayer) {
        this.effectsLayer = effectsLayer;
    }

    /**
     * Plays the player death visual effects with brief freeze frame and then shockwave and red flash.
     *
     * @param x X position of the player
     * @param y Y position of the player
     */
    public void play(double x, double y) {
        PauseTransition freeze = new PauseTransition(Duration.millis(100));
        freeze.setOnFinished(e -> {
            spawnRipple(x, y);
            showRedFlash();
        });
        freeze.play();
    }

    /**
     * Creates an expanding shockwave effect from the death location.
     *
     * @param x X position of the ripple center
     * @param y Y position of the ripple center
     */
    private void spawnRipple(double x, double y) {
        int rippleCount = 3;

        for (int i = 0; i < rippleCount; i++) {
            Circle ripple = new Circle(x, y, 10);
            ripple.setFill(Color.TRANSPARENT);
            ripple.setStroke(Color.WHITE);
            ripple.setStrokeWidth(3);
            effectsLayer.getChildren().add(ripple);

            PauseTransition delay = new PauseTransition(Duration.millis(i * 150));
            delay.setOnFinished(e -> {
                ScaleTransition expand = new ScaleTransition(Duration.millis(500), ripple);
                expand.setFromX(1);
                expand.setFromY(1);
                expand.setToX(15);
                expand.setToY(15);

                FadeTransition fade = new FadeTransition(Duration.millis(500), ripple);
                fade.setFromValue(1.0);
                fade.setToValue(0.0);
                fade.setOnFinished(ev -> effectsLayer.getChildren().remove(ripple));

                expand.play();
                fade.play();
            });
            delay.play();
        }
    }

    /**
     * Shows a brief red flash overlay on the screen.
     */
    private void showRedFlash() {
        Scene scene = effectsLayer.getScene();
        Rectangle flash = new Rectangle(0, 0, scene.getWidth(), scene.getHeight());
        flash.setFill(Color.RED);
        flash.setOpacity(0.4);
        effectsLayer.getChildren().add(flash);

        FadeTransition fadeFlash = new FadeTransition(Duration.millis(300), flash);
        fadeFlash.setFromValue(0.4);
        fadeFlash.setToValue(0.0);
        fadeFlash.setOnFinished(e -> effectsLayer.getChildren().remove(flash));
        fadeFlash.play();
    }
}
