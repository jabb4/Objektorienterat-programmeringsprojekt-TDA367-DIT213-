package com.grouptwelve.roguelikegame.view.effects;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.Random;

/**
 * Handles particle effects for the game view.
 */
public class ParticleSystem {
    private final AnchorPane effectsLayer;
    private final Random rand = new Random();

    public ParticleSystem(AnchorPane effectsLayer) {
        this.effectsLayer = effectsLayer;
    }

    /**
     * Spawns particle effects at the hit location.
     * Particles burst outward and fade.
     *
     * @param x X position of the hit
     * @param y Y position of the hit
     */
    public void spawnHitParticles(double x, double y) {
        int particleCount = 8;

        for (int i = 0; i < particleCount; i++) {
            Circle particle = new Circle(x, y, 3, Color.WHITE);
            particle.setManaged(false);
            effectsLayer.getChildren().add(particle);

            // Random direction and distance
            double angle = rand.nextDouble() * 2 * Math.PI;
            double distance = 30 + rand.nextDouble() * 20;

            // Move outward
            TranslateTransition move = new TranslateTransition(Duration.millis(300), particle);
            move.setByX(Math.cos(angle) * distance);
            move.setByY(Math.sin(angle) * distance);

            // Fade out
            FadeTransition fade = new FadeTransition(Duration.millis(300), particle);
            fade.setFromValue(1.0);
            fade.setToValue(0.0);
            fade.setOnFinished(e -> effectsLayer.getChildren().remove(particle));

            move.play();
            fade.play();
        }
    }
}
