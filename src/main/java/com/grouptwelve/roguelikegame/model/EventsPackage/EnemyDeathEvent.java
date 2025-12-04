package com.grouptwelve.roguelikegame.model.EventsPackage;

public class EnemyDeathEvent {
    public final double x;
    public final double y;
    public final int xp;

    public EnemyDeathEvent(double x, double y, int xp) {
        this.x = x;
        this.y = y;
        this.xp = xp;
    }
}
