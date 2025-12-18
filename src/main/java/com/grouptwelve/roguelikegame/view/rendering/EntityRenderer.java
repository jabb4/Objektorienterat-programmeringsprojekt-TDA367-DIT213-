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
    private static final Color DIRECTION_ARROW_COLOR = Color.YELLOW;

    private static final double ARROW_DISTANCE = 20;
    private static final double ARROW_LENGTH = 10;
    private static final double ARROW_HEAD_LENGTH = 8;
    private static final double ARROW_HEAD_ANGLE = Math.PI / 6;

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

        renderDirectionArrow(player);
    }

    /**
     * Renders an arrow showing the direction the obstacle is looking.
     *
     * @param obstacle The obstacle to render the direction arrow for
     */
    private void renderDirectionArrow(Obstacle obstacle) {
        double dirX = obstacle.getDirX();
        double dirY = obstacle.getDirY();

        // Normalize direction vector
        double magnitude = Math.sqrt(dirX * dirX + dirY * dirY);
        if (magnitude == 0) {
            return; // No direction to show
        }
        dirX /= magnitude;
        dirY /= magnitude;

        // Calculate arrow start position (at distance from obstacle)
        double startX = obstacle.getX() + dirX * ARROW_DISTANCE;
        double startY = obstacle.getY() + dirY * ARROW_DISTANCE;

        // Calculate arrow end position
        double endX = startX + dirX * ARROW_LENGTH;
        double endY = startY + dirY * ARROW_LENGTH;

        // Draw main arrow line
        gc.setStroke(DIRECTION_ARROW_COLOR);

        // Calculate arrowhead lines
        double angle = Math.atan2(dirY, dirX);
        double arrowHead1X = endX - ARROW_HEAD_LENGTH * Math.cos(angle - ARROW_HEAD_ANGLE);
        double arrowHead1Y = endY - ARROW_HEAD_LENGTH * Math.sin(angle - ARROW_HEAD_ANGLE);
        double arrowHead2X = endX - ARROW_HEAD_LENGTH * Math.cos(angle + ARROW_HEAD_ANGLE);
        double arrowHead2Y = endY - ARROW_HEAD_LENGTH * Math.sin(angle + ARROW_HEAD_ANGLE);

        // Draw arrowhead
        gc.strokeLine(endX, endY, arrowHead1X, arrowHead1Y);
        gc.strokeLine(endX, endY, arrowHead2X, arrowHead2Y);
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
