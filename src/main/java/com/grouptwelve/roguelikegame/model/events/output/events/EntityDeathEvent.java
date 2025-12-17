package com.grouptwelve.roguelikegame.model.events.output.events;

import com.grouptwelve.roguelikegame.model.entities.Obstacle;
import com.grouptwelve.roguelikegame.model.entities.ObstacleType;

/**
 * Event data for entity death.
 */
public class EntityDeathEvent {
    private final Obstacle obstacle;
    private final double x;
    private final double y;

    /**
     * Creates a new entity death event.
     *
     * @param obstacle the obstacle that died
     */
    public EntityDeathEvent(Obstacle obstacle, double x, double y) {
        this.obstacle = obstacle;
        this.x = x;
        this.y = y;

    }

    public Obstacle getObstacle() {
        return obstacle;
    }

    public double getX(){return x;}

    public  double getY(){return y;}
}
