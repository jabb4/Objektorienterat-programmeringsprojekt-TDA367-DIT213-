package com.grouptwelve.roguelikegame.view;

/**
 * Interface for listening to game events that require visual feedback.
 * Implemented by the controller to bridge model events to the view.
 */
public interface ControllerListener {
    void drawAttack(double x, double y, double size);
    void playerDied(double x, double y);
    void onEnemyHit(double x, double y, double damage);
    void onEnemyCritHit(double x, double y, double damage);
}
