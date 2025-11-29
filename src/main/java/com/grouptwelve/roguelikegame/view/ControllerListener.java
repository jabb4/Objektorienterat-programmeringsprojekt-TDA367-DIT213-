package com.grouptwelve.roguelikegame.view;

public interface ControllerListener {
    void showAttack(double x, double y, double size, double duration);
    void playerDied(double x, double y);
    void onEnemyHit(double x, double y, double damage);
}
