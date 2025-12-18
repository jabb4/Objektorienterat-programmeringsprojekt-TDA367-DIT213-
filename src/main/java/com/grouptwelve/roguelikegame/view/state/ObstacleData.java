package com.grouptwelve.roguelikegame.view.state;

import javafx.scene.paint.Color;

/**
 * Stores information about an obstacle (only used for enemies currently).
 * This is necessary because obstacles don't store these values themselves.
 * These values are changed by events like onEntityHit.
 */
public class ObstacleData {
    private Color color;
    private double hp;
    private double maxHp;

    public ObstacleData(Color color, double hp, double maxHp) {
        this.color = color;
        this.hp = hp;
        this.maxHp = maxHp;
    }

    public Color getColor() {
        return color;
    }

    public double getHp() {
        return hp;
    }

    public double getMaxHp() {
        return maxHp;
    }
}
