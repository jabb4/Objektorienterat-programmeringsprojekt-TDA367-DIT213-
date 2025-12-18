package com.grouptwelve.roguelikegame.view.rendering;

import com.grouptwelve.roguelikegame.model.entities.Obstacle;
import com.grouptwelve.roguelikegame.view.state.ObstacleData;
import com.grouptwelve.roguelikegame.view.state.ViewState;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.List;

/**
 * Handles rendering of game entities.
 * Draws entity circles and HP bars.
 */
public class EntityRenderer {
    private final AnchorPane gameObjectsLayer;
    private final GraphicsContext gc;
    private final ViewState viewState;

    private static final double HP_BAR_WIDTH = 40;
    private static final double HP_BAR_HEIGHT = 5;
    private static final double HP_BAR_OFFSET = 10;

    private static final Color PLAYER_COLOR = Color.LIGHTBLUE;
    private static final Color ENEMY_DEFAULT_COLOR = Color.RED;
    private static final Color HP_BAR_BACKGROUND_COLOR = Color.GRAY;
    private static final Color HP_BAR_FILL_COLOR = Color.RED;

    public EntityRenderer(AnchorPane gameObjectsLayer, GraphicsContext gc, ViewState viewState) {
        this.gameObjectsLayer = gameObjectsLayer;
        this.gc = gc;
        this.viewState = viewState;
    }

    /**
     * Clears the canvas and game objects layer for the next frame.
     *
     * @param canvasWidth  Width of the canvas
     * @param canvasHeight Height of the canvas
     */
    public void clear(double canvasWidth, double canvasHeight) {
        gc.clearRect(0, 0, canvasWidth, canvasHeight);
        gameObjectsLayer.getChildren().clear();
    }

    /**
     * Renders the player as a circle.
     *
     * @param player Obstacle data for the player
     */
    public void renderPlayer(Obstacle player) {
        Circle playerCircle = new Circle(player.getX(), player.getY(), player.getSize());
        playerCircle.setFill(PLAYER_COLOR);
        playerCircle.setManaged(false);
        gameObjectsLayer.getChildren().add(playerCircle);
    }

    /**
     * Renders all enemies.
     *
     * @param enemies List of Obstacle data for enemies
     */
    public void renderEnemies(List<Obstacle> enemies) {
        for (Obstacle enemy : enemies) {
            renderEnemy(enemy);
        }
    }

    /**
     * Renders a single enemy.
     *
     * @param enemy Obstacle data for the enemy
     */
    private void renderEnemy(Obstacle enemy) {
        Circle enemyCircle = new Circle(enemy.getX(), enemy.getY(), enemy.getSize());
        ObstacleData data = viewState.getObstacleData(enemy);

        gc.setFill(HP_BAR_BACKGROUND_COLOR);
        gc.fillRect(enemy.getX() - HP_BAR_WIDTH / 2, enemy.getY() - enemy.getSize() - HP_BAR_OFFSET, HP_BAR_WIDTH, HP_BAR_HEIGHT);

        double hpPercentage;

        if (data != null) {
            // Data has been updated by some event, use stored values
            enemyCircle.setFill(data.getColor());
            hpPercentage = data.getHp() / data.getMaxHp();
        } else {
            // No value has been changed since spawning, use defaults
            enemyCircle.setFill(ENEMY_DEFAULT_COLOR);
            hpPercentage = 1.0;
        }

        enemyCircle.setManaged(false);
        gameObjectsLayer.getChildren().add(enemyCircle);

        gc.setFill(HP_BAR_FILL_COLOR);
        gc.fillRect(enemy.getX() - HP_BAR_WIDTH / 2, enemy.getY() - enemy.getSize() - HP_BAR_OFFSET, HP_BAR_WIDTH * hpPercentage, HP_BAR_HEIGHT);
    }
}
