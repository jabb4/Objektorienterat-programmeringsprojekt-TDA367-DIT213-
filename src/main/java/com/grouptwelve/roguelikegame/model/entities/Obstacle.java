package com.grouptwelve.roguelikegame.model.entities;

/**
 * interface that only contains information of a general object
 */
public interface Obstacle {
    int getSize();

    // ==================== Getters ====================
    double getDirX();

    double getDirY();

    double getX();
    double getY();
    ObstacleType getObstacleType();

}
