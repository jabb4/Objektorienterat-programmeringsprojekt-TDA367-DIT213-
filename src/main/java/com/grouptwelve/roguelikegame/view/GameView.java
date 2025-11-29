package com.grouptwelve.roguelikegame.view;

import com.grouptwelve.roguelikegame.controller.GameController;
import com.grouptwelve.roguelikegame.model.Game;
import com.grouptwelve.roguelikegame.model.EntitiesPackage.Player;
import com.grouptwelve.roguelikegame.model.EntitiesPackage.Enemy;
import com.grouptwelve.roguelikegame.model.EntitiesPackage.Entity;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class GameView {

    @FXML private StackPane root;
    @FXML private Canvas gameCanvas;
    @FXML private AnchorPane gameLayer;
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

    private double attackX;
    private double attackY;
    private double attackSize;
    private double attackTime; // remaining time to show

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
        blur.setRadius(show ? 10 : 0);
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
}
