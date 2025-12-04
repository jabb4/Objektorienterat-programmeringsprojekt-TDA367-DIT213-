package com.grouptwelve.roguelikegame.view;

import com.grouptwelve.roguelikegame.controller.GameController;
import com.grouptwelve.roguelikegame.model.Game;
import com.grouptwelve.roguelikegame.model.EntitiesPackage.Player;
import com.grouptwelve.roguelikegame.model.EntitiesPackage.Enemy;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;

import com.grouptwelve.roguelikegame.model.EntitiesPackage.Entity;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class GameView {

    @FXML private StackPane root;
    @FXML private Canvas gameCanvas;
    @FXML private AnchorPane gameLayer;
    @FXML private AnchorPane uiButtonsLayer;
    @FXML private Rectangle hpBackground;
    @FXML private Rectangle hpFill;
    @FXML private Label hpLabel;
    @FXML private Rectangle levelBackground;
    @FXML private Rectangle levelFill;
    @FXML private Label levelLabel;
    @FXML private ImageView firstItemImage;
    @FXML private Label actionLabel;
    @FXML private VBox pauseMenu;
    @FXML private VBox deathMenu;
    @FXML private VBox levelUpMenu;
    @FXML private VBox levelUpMenuVertical;
    @FXML private Button fireBuffBox;
    @FXML private Button speedBuffBox;
    @FXML private Button healthBuffBox;
    @FXML private Rectangle firstItem;
    @FXML private Rectangle secondItem;
    @FXML private Rectangle thirdItem;

    private Rectangle highlightedItem = null;

    private GraphicsContext gc;
    private Game game;
    private GameController gameController;
    private GaussianBlur blur = new GaussianBlur(0);
    Random rand;

    private double attackX;
    private double attackY;
    private double attackSize;
    private double attackTime;

    public void setGame(Game game) {
        this.game = game;
    }

    public void setGameController(GameController controller) {
        this.gameController = controller;
    }

    @FXML
    private void initialize() {
        // TEMPORARY IMAGE ON SLOT 1
        Image sword = new Image(getClass().getResourceAsStream("/com/grouptwelve/roguelikegame/img/sword1.png"));
        firstItemImage.setImage(sword);
        highlightItem(1); // Weapon in first slot is selected automatically
        root.requestFocus();

        this.rand = new Random();
        this.gc = gameCanvas.getGraphicsContext2D();
        gameLayer.setEffect(blur);
    }

    public Parent getRoot() {
        return root;
    }

    public void render(Game game, double deltaTime) {
        // Clear canvas
        gc.setFill(Color.web("#2a2a2a"));
        gc.fillRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());

        // Draw player
        Player player = game.getPlayer();
        gc.setFill(Color.LIGHTBLUE);
        gc.fillOval(player.getX() - player.getSize()/2, player.getY() - player.getSize()/2,
                    player.getSize(), player.getSize());

        // Draw enemies and HP bars
        List<Enemy> enemies = game.getEnemies();
        gc.setFill(Color.RED);
        for (Enemy enemy : enemies) {
            if (!enemy.getAliveStatus()) continue;
            
            // Draw the enemy
            if (enemy.getAliveStatus()) {
                gc.fillOval(enemy.getX() - enemy.getSize()/2, enemy.getY() - enemy.getSize()/2,
                            enemy.getSize(), enemy.getSize());
            }

            // Draw HP bar background
            double barWidth = 30; // width of full HP bar
            double barHeight = 5; // height of HP bar
            gc.setFill(Color.GRAY);
            gc.fillRect(enemy.getX() - barWidth / 2, enemy.getY() - enemy.getSize() / 2 - 10, barWidth, barHeight);

            // Draw HP bar foreground
            double hpPercentage = enemy.getHp() / enemy.getMaxHp();
            gc.setFill(Color.RED); // or red
            gc.fillRect(enemy.getX() - barWidth / 2, enemy.getY() - enemy.getSize() / 2 - 10, barWidth * hpPercentage, barHeight);
        }

        // Draw attack if active
        if (attackTime > 0) {
            gc.setFill(Color.VIOLET);
            gc.fillOval(attackX - attackSize/2, attackY - attackSize/2, attackSize, attackSize);
            attackTime -= deltaTime; // decrease remaining time
        }

        // Update labels
        // actionLabel.setText(String.format("Player Position: [%.1f, %.1f]", player.getX(), player.getY()));
    }

    // public void drawAttack(double x, double y, double size) {
    //     gc.setFill(Color.VIOLET);
    //     gc.fillOval(x - size/2, y - size/2, size, size);
    // }

    public void showAttack(double x, double y, double size, double durationSeconds) {
        this.attackX = x;
        this.attackY = y;
        this.attackSize = size;
        this.attackTime = durationSeconds;
    }

    public void updateDirectionLabel(int dx, int dy) {
        // actionLabel.setText(String.format("Direction: [%d, %d]", dx, dy));
    }

    public void updateStatusLabel(String status) {
        // actionLabel.setText(status);
    }

    public void updateGameTimeLabel(double gameTime) {
        // int minutes = (int) (gameTime / 60);
        // int seconds = (int) (gameTime % 60);
        // actionLabel.setText(String.format("Time: %d:%02d", minutes, seconds));
    }

    public void showPauseMenu(boolean show) {
        pauseMenu.setVisible(show);
        pauseMenu.setDisable(!show);    // Only focus on pauseMenu 
        gameLayer.setDisable(show);     // No other UI buttons beside pauseMenu
        uiButtonsLayer.setDisable(show);
        blur.setRadius(show ? 10 : 0);

        if (show) {
        Platform.runLater(() -> pauseMenu.getChildren().get(1).requestFocus());
    }
    }

    public void showLevelMenu(boolean show) {
        levelUpMenu.setVisible(show);
        blur.setRadius(show ? 10 : 0);
    }

    public void showLevelMenu2(boolean show) {
        levelUpMenuVertical.setVisible(show);
        blur.setRadius(show ? 10 : 0);
    }

    public void showDeathMenu(boolean show) {
        deathMenu.setVisible(show);
        blur.setRadius(show ? 10 : 0);
    }

    public void updateHealthBar(double currentHp, double maxHp, Entity entity) {
        double percentage = currentHp / maxHp;

        if (entity instanceof Player) {
            hpFill.setWidth(200 * percentage);

            // Update HP label
            hpLabel.setText(currentHp + " / " + maxHp);

        } else if (entity instanceof Enemy) {
            ((Enemy) entity).getHpBar().setWidth(200 * percentage);
        }
    }

    @FXML
    private void onResume() {
        gameController.resume();
    }

    @FXML
    private void onQuit() throws IOException {
        gameController.quit();
    }

     @FXML
    private void onPlayAgain() throws IOException {
        gameController.playAgain();
    }


    @FXML
    protected void onSpawnEnemy() {
        gameController.spawnEnemy();
    }

    @FXML
    protected void onRemoveEnemy() {
        gameController.removeEnemy();        
    }

    @FXML
    protected void onTakeDamage() {
        gameController.takeDamage(game.getPlayer(), 25);
    }

    @FXML
    protected void onLevelUp() {
        gameController.triggerLevelUp();
    }

    @FXML
    protected void onLevelUp2() {
        gameController.triggerLevelUp2();
    }

    @FXML
    protected void onDie() {
        gameController.triggerDeath();
    }


    @FXML
    protected void onBack() throws IOException {
        gameController.back();
    }

    @FXML
    protected void onOptions() throws IOException {
        
    }

    @FXML
    protected void onSelectUpgrade1() {
        gameController.upgrade1();
    }

    @FXML
    protected void onSelectUpgrade2() {
        gameController.upgrade2();
    }

    @FXML
    protected void onSelectUpgrade3() {
        gameController.upgrade3();
    }

    public void highlightItem(int index) {
        // Reset previous highlight
        if (highlightedItem != null) {
            highlightedItem.setStroke(null); // remove border
            highlightedItem.setStrokeWidth(0);
        }

        // Highlight new item
        switch (index) {
            case 1:
                highlightedItem = firstItem;
                break;
            case 2:
                highlightedItem = secondItem;
                break;
            case 3:
                highlightedItem = thirdItem;
                break;
        }

        // Apply highlight style
        highlightedItem.setStroke(javafx.scene.paint.Color.YELLOW);
        highlightedItem.setStrokeWidth(3);
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

        gameController.triggerDeath();
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
            gameLayer.getChildren().add(ripple);
            
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
                fade.setOnFinished(ev -> gameLayer.getChildren().remove(ripple));
                
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
        gameLayer.getChildren().add(flash);
        
        FadeTransition fadeFlash = new FadeTransition(Duration.millis(300), flash);
        fadeFlash.setFromValue(0.4);
        fadeFlash.setToValue(0.0);
        fadeFlash.setOnFinished(e -> gameLayer.getChildren().remove(flash));
        fadeFlash.play();
    }

    /**
     * Displays a floating damage number that rises and fades out.
     * 
     * @param x X position of the damage
     * @param y Y position of the damage
     * @param damage Amount of damage to display
     */
    public void showDamageNumber(double x, double y, double damage) {
        Label dmgLabel = new Label(String.format("%.0f", damage));
        dmgLabel.setTextFill(Color.WHITE);
        dmgLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        dmgLabel.setLayoutX(x - 10);
        dmgLabel.setLayoutY(y - 30);
        gameLayer.getChildren().add(dmgLabel);

        // Float up animation
        TranslateTransition floatUp = new TranslateTransition(Duration.millis(400), dmgLabel);
        floatUp.setByY(-25);

        // Fade out animation
        FadeTransition fadeOut = new FadeTransition(Duration.millis(400), dmgLabel);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(e -> gameLayer.getChildren().remove(dmgLabel));

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
            gameLayer.getChildren().add(particle);
            
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
            fade.setOnFinished(e -> gameLayer.getChildren().remove(particle));
            
            move.play();
            fade.play();
        }
    }
}
