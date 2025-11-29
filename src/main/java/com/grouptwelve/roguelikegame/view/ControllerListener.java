package com.grouptwelve.roguelikegame.view;

public interface ControllerListener {
    void drawAttack(double x, double y, double size);
    void playerDied(double x, double y);
    void onEnemyHit(double x, double y, double damage);
}
