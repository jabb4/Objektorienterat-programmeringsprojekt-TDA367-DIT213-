package com.grouptwelve.roguelikegame.view;

import com.grouptwelve.roguelikegame.model.Game;
import com.grouptwelve.roguelikegame.model.entities.Player;
import com.grouptwelve.roguelikegame.model.entities.enemies.Enemy;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Handles all rendering and visual presentation of the game.
 */
public class GameView {
    private final VBox root;
    private final Pane gamePane;
    private final Label positionLabel;
    private final Label directionLabel;
    private final Label statusLabel;
    private final Label gameTimeLabel;
    
    public GameView() {
        gamePane = new Pane();
        gamePane.setPrefSize(800, 500);
        gamePane.setStyle("-fx-background-color: #2a2a2a;");

        // Labels
        positionLabel = new Label("Player Position: [0.0, 0.0]");
        positionLabel.setTextFill(Color.WHITE);
        directionLabel = new Label("Direction: [0, 0]");
        directionLabel.setTextFill(Color.WHITE);
        statusLabel = new Label("No keys pressed");
        statusLabel.setTextFill(Color.WHITE);
        gameTimeLabel = new Label("Time elapsed: 0:00");
        gameTimeLabel.setTextFill(Color.WHITE);
        
        // Layout
        VBox uiBox = new VBox(5, positionLabel, directionLabel, statusLabel, gameTimeLabel);
        uiBox.setStyle("-fx-padding: 10; -fx-background-color: #1a1a1a;");
        
        root = new VBox(gamePane, uiBox);
    }
    
    /**
     * Renders the current game state.
     * 
     * @param game The game model to render
     */
    public void render(Game game) {
        // Clear previous frame
        gamePane.getChildren().clear();
        
        // Render player
        Player player = game.getPlayer();
        Circle playerCircle = new Circle(player.getX(), player.getY(), player.getSize());
        playerCircle.setFill(Color.LIGHTBLUE);
        gamePane.getChildren().add(playerCircle);

        // TODO: Render enemies
        // NOTE: use game.getEnemies and iterate through all the elements to render them all.
        
        // Update position label
        positionLabel.setText(String.format("Player Position: [%.1f, %.1f]", player.getX(), player.getY()));
        
    }
    
    /**
     * Updates the direction label.
     * 
     * @param dx Horizontal direction
     * @param dy Vertical direction
     */
    public void updateDirectionLabel(int dx, int dy) {
        directionLabel.setText(String.format("Direction: [%d, %d]", dx, dy));
    }
    
    /**
     * Updates the status label.
     * 
     * @param status Status text to display
     */
    public void updateStatusLabel(String status) {
        statusLabel.setText(status);
    }
    
    /**
     * Updates the game time label.
     * 
     * @param gameTime Game time in seconds
     */
    public void updateGameTimeLabel(double gameTime) {
        int minutes = (int) (gameTime / 60);
        int seconds = (int) (gameTime % 60);
        gameTimeLabel.setText(String.format("Time: %d:%02d", minutes, seconds));
    }
    
    public Pane getRoot() {
        return root;
    }
}
