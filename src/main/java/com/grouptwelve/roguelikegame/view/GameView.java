package com.grouptwelve.roguelikegame.view;

import com.grouptwelve.roguelikegame.model.Game;
import com.grouptwelve.roguelikegame.model.entities.Player;
import com.grouptwelve.roguelikegame.model.entities.enemies.Enemy;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Handles all rendering and visual presentation of the game.
 */
public class GameView{
    private final VBox root;
    private final Pane gamePane;
    private final Pane gamePaneSlow;


    private final Label positionLabel;
    private final Label directionLabel;
    private final Label statusLabel;
    private final Label gameTimeLabel;
    private final Label bufflabals;
    private final Label selectedLabel;
    private final Random rand = new Random();


    
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

        bufflabals = new Label("Buffs: ");
        selectedLabel = new Label("selected: ");

        bufflabals.setTextFill(Color.WHITE);
        selectedLabel.setTextFill(Color.WHITE);

        
        // Layout
        VBox uiBox = new VBox(5, positionLabel, directionLabel, statusLabel, gameTimeLabel, bufflabals, selectedLabel);
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

        // Render enemies
        List<Enemy> enemies = game.getEnemies();
        for(Enemy enemy : enemies)
        {
            if(enemy.getAliveStatus())
            {
                Circle enemyCircle = new Circle(enemy.getX(), enemy.getY(), enemy.getSize());
                
                // Hit effect
                if (enemy.isHit()) {
                    enemyCircle.setFill(Color.WHITE);
                } else {
                    enemyCircle.setFill(Color.RED);
                }
                
                gamePane.getChildren().add(enemyCircle);
            }

        }
        // Update position label (TEMPORARY FOR DEBUGGING)
        positionLabel.setText(String.format("Player Position: [%.1f, %.1f]", player.getX(), player.getY()));
    }

    /**
     * for a short timer draw attackCircle at attack-pos
     * @param x x-coordinate for attack
     * @param y y-coordinate for attack
     * @param size size to draw attackCircle
     */

         public void drawAttack(double x, double y, double size) {
        //System.out.println(x + " " + y + " " + size);
        Circle attackCircle = new Circle(x, y, size);
        attackCircle.setFill(Color.VIOLET);
        gamePaneSlow.getChildren().add(attackCircle);

        //remove attackcircle after a short timer
        PauseTransition pause = new PauseTransition(Duration.seconds(0.1));
        pause.setOnFinished(_ -> gamePaneSlow.getChildren().remove(attackCircle));
        pause.play();

    }
    public  void clearBuffVisuals()
    {
        bufflabals.setText("Buffs: ");
    }
    public void updateBuffLabels(String[] buffs)
    {
        bufflabals.setText("Buffs: "+ buffs[0]+ buffs[1]+ buffs[2]);
    }
    public void updateSelectedLabel(int selectedIndex)
    {
        selectedLabel.setText(String.format("Selected buff %d", selectedIndex + 1));
    }
    /**
     * Updates the direction label. (TEMPORARY FOR DEBUGGING)
     * 
     * @param dx Horizontal direction
     * @param dy Vertical direction
     */
    public void updateDirectionLabel(int dx, int dy) {
        directionLabel.setText(String.format("Direction: [%d, %d]", dx, dy));
    }
    
    /**
     * Updates the status label. (TEMPORARY FOR DEBUGGING)
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


    /**
     * Plays the player death effect with ripple/shockwave, screen shake, and red flash.
     * 
     * @param x X position of the player
     * @param y Y position of the player
     */
    public void playerDied(double x, double y)
    {
        System.out.println("YOU DIED!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        
        // Brief freeze frame before effects start
        PauseTransition freeze = new PauseTransition(Duration.millis(100));
        freeze.setOnFinished(e -> {
            spawnRipple(x, y);
            showRedFlash();
        });
        freeze.play();
    }
    public void drawBuffs(String[] list)
    {
        for(String buff: list)
        {
            System.out.println(buff);
        }
    }

    /**
     * Creates an expanding ripple/shockwave effect from the death location.
     * 
     * @param x X position of the ripple center
     * @param y Y position of the ripple center
     */
    private void spawnRipple(double x, double y) {
        int rippleCount = 3;
        
        for (int i = 0; i < rippleCount; i++) {
            Circle ripple = new Circle(x, y, 10);
            ripple.setFill(Color.TRANSPARENT);
            ripple.setStroke(Color.WHITE);
            ripple.setStrokeWidth(3);
            gamePaneSlow.getChildren().add(ripple);
            
            // Delay each ripple slightly
            PauseTransition delay = new PauseTransition(Duration.millis(i * 150));
            delay.setOnFinished(e -> {
                // Expand outward
                ScaleTransition expand = new ScaleTransition(Duration.millis(500), ripple);
                expand.setFromX(1);
                expand.setFromY(1);
                expand.setToX(15);
                expand.setToY(15);
                
                // Fade out as it expands
                FadeTransition fade = new FadeTransition(Duration.millis(500), ripple);
                fade.setFromValue(1.0);
                fade.setToValue(0.0);
                fade.setOnFinished(ev -> gamePaneSlow.getChildren().remove(ripple));
                
                expand.play();
                fade.play();
            });
            delay.play();
        }
    }

    /**
     * Shows a brief red flash overlay on the screen.
     */
    private void showRedFlash() {
        Rectangle flash = new Rectangle(0, 0, 800, 500);
        flash.setFill(Color.RED);
        flash.setOpacity(0.4);
        gamePaneSlow.getChildren().add(flash);
        
        FadeTransition fadeFlash = new FadeTransition(Duration.millis(300), flash);
        fadeFlash.setFromValue(0.4);
        fadeFlash.setToValue(0.0);
        fadeFlash.setOnFinished(e -> gamePaneSlow.getChildren().remove(flash));
        fadeFlash.play();
    }

    /**
     * Displays a floating damage number that rises and fades out.
     * Critical hits are displayed larger and in a different color.
     * 
     * @param x X position of the damage
     * @param y Y position of the damage
     * @param damage Amount of damage to display
     * @param isCritical Whether this was a critical hit
     */
    public void showDamageNumber(double x, double y, double damage, boolean isCritical) {
        Label dmgLabel = new Label(String.format("%.0f", damage) + (isCritical ? "!" : ""));
        
        if (isCritical) {
            dmgLabel.setTextFill(Color.GOLD);
            dmgLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        } else {
            dmgLabel.setTextFill(Color.WHITE);
            dmgLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        }
        
        dmgLabel.setLayoutX(x - 10);
        dmgLabel.setLayoutY(y - 30);
        gamePaneSlow.getChildren().add(dmgLabel);

        // Float up animation
        TranslateTransition floatUp = new TranslateTransition(Duration.millis(400), dmgLabel);
        floatUp.setByY(-25);

        // Fade out animation
        FadeTransition fadeOut = new FadeTransition(Duration.millis(400), dmgLabel);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(e -> gamePaneSlow.getChildren().remove(dmgLabel));

        floatUp.play();
        fadeOut.play();
    }

    /**
     * Spawns particle effects at the hit location.
     * Particles burst outward and fade, self-cleaning via JavaFX animations.
     * 
     * @param x X position of the hit
     * @param y Y position of the hit
     */
    public void spawnHitParticles(double x, double y) {
        int particleCount = 8;
        
        for (int i = 0; i < particleCount; i++) {
            Circle particle = new Circle(x, y, 3, Color.WHITE);
            gamePaneSlow.getChildren().add(particle);
            
            // Random direction and distance
            double angle = rand.nextDouble() * 2 * Math.PI;
            double distance = 30 + rand.nextDouble() * 20;
            
            // Move outward
            TranslateTransition move = new TranslateTransition(Duration.millis(300), particle);
            move.setByX(Math.cos(angle) * distance);
            move.setByY(Math.sin(angle) * distance);
            
            // Fade out
            FadeTransition fade = new FadeTransition(Duration.millis(300), particle);
            fade.setFromValue(1.0);
            fade.setToValue(0.0);
            fade.setOnFinished(e -> gamePaneSlow.getChildren().remove(particle));
            
            move.play();
            fade.play();
        }
    }
}
