package com.grouptwelve.roguelikegame.view.effects;

import javafx.animation.PauseTransition;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/**
 * Handles attack visual effects.
 * Displays a temporary circle indicating the attack range.
 */
public class AttackVisualEffect {
    private final AnchorPane effectsLayer;

    public AttackVisualEffect(AnchorPane effectsLayer) {
        this.effectsLayer = effectsLayer;
    }

    /**
     * Shows a circle at the specified position.
     *
     * @param x X position of the attack
     * @param y Y position of the attack
     * @param range Range/radius of the attack
     * @param isPlayer Whether the attacker is the player
     */
    public void show(double x, double y, double range, boolean isPlayer) {
        Circle attackCircle = new Circle(x, y, range);
        attackCircle.setFill(isPlayer ? Color.BLUE : Color.VIOLET);
        attackCircle.setManaged(false);
        effectsLayer.getChildren().add(attackCircle);

        PauseTransition pause = new PauseTransition(Duration.seconds(0.1));
        pause.setOnFinished(_ -> effectsLayer.getChildren().remove(attackCircle));
        pause.play();
    }
}
