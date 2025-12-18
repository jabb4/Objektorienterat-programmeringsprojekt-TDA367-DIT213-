package com.grouptwelve.roguelikegame.view.state;

import com.grouptwelve.roguelikegame.model.entities.Obstacle;
import javafx.scene.paint.Color;

import java.util.HashMap;

/**
 * Holds shared visual state for the view layer.
 * Tracks obstacle visual data between events and render frames.
 */
public class ViewState {
    private final HashMap<Obstacle, ObstacleData> obstacleData = new HashMap<>();

    public ObstacleData getObstacleData(Obstacle obstacle) {
        return obstacleData.get(obstacle);
    }

    public void setObstacleData(Obstacle obstacle, Color color, double hp, double maxHp) {
        obstacleData.put(obstacle, new ObstacleData(color, hp, maxHp));
    }
}
