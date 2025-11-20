package com.grouptwelve.roguelikegame.view;

import com.grouptwelve.roguelikegame.model.Game;
import com.grouptwelve.roguelikegame.model.EntitiesPackage.Player;
import com.grouptwelve.roguelikegame.model.EntitiesPackage.Enemy;
import javafx.animation.PauseTransition;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.List;

/**
 * Handles all rendering and visual presentation of the game.
 */
public class GameView {
    private final VBox root;
    private final Pane gamePane;
    private final Pane gamePaneSlow;


    private final Label positionLabel;
    private final Label directionLabel;
    private final Label statusLabel;
    private final Label gameTimeLabel;

    
    public GameView() {
        StackPane gameContainer = new StackPane();
        gamePaneSlow = new Pane();
        gamePane = new Pane();
        gamePane.setPrefSize(800, 500);
        gamePane.setStyle("-fx-background-color: #2a2a2a;");
        gameContainer.getChildren().addAll(gamePane, gamePaneSlow);

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
        
        root = new VBox(gameContainer, uiBox);
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
        List<Enemy> enemies = game.getEnemies();

        for(Enemy enemy : enemies)
        {
            if(enemy.getAliveStatus())
            {
                Circle enemyCircl = new Circle(enemy.getX(), enemy.getY(), enemy.getSize());
                enemyCircl.setFill(Color.RED);
                gamePane.getChildren().add(enemyCircl);
            }

        }
        
        // Update position label
        positionLabel.setText(String.format("Player Position: [%.1f, %.1f]", player.getX(), player.getY()));
        
    }

    /**
     * for a short timer draw attackCircle at attack-pos
     * @param x x-coordinate for attack
     * @param y y-coordinate for attack
     * @param size size to draw attackCircle
     */
    public void drawAttack(double x, double y, double size) {
        System.out.println(x + " " + y + " " + size);
        Circle attackCircle = new Circle(x, y, size);
        attackCircle.setFill(Color.VIOLET);
        gamePaneSlow.getChildren().add(attackCircle);

        //remove attackcircle after a short timer
        PauseTransition pause = new PauseTransition(Duration.seconds(0.1));
        pause.setOnFinished(_ -> gamePaneSlow.getChildren().remove(attackCircle));
        pause.play();

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
