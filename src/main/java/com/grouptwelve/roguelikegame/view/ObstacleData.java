package com.grouptwelve.roguelikegame.view;

import javafx.scene.paint.Color;

/**
 * stores information about an obstacle(only used for enemies currently)
 * this is necessary because obstacle don't store these values themselves
 * these values are changed by events like onEntityHit
 */
class ObstacleData {
    private Color color;
    private double hp;
    private double maxHp;

    ObstacleData(Color color, double hp, double maxHp) {
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