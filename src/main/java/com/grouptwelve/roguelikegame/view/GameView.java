package com.grouptwelve.roguelikegame.view;

import com.grouptwelve.roguelikegame.controller.GameController;
import com.grouptwelve.roguelikegame.controller.MenuNavigator;
import com.grouptwelve.roguelikegame.model.Game;
import com.grouptwelve.roguelikegame.model.GameDrawInfo;
import com.grouptwelve.roguelikegame.model.combat.CombatResult;
import com.grouptwelve.roguelikegame.model.entities.Obstacle;
import com.grouptwelve.roguelikegame.model.entities.ObstacleType;
import com.grouptwelve.roguelikegame.model.entities.Player;
import com.grouptwelve.roguelikegame.model.entities.enemies.Enemy;
import com.grouptwelve.roguelikegame.model.events.output.events.*;
import com.grouptwelve.roguelikegame.model.events.output.listeners.*;
import com.grouptwelve.roguelikegame.model.upgrades.UpgradeInterface;
import com.grouptwelve.roguelikegame.view.effects.DamageNumberEffect;
import com.grouptwelve.roguelikegame.view.effects.DeathEffect;
import com.grouptwelve.roguelikegame.view.effects.ParticleSystem;
import com.grouptwelve.roguelikegame.view.state.ObstacleData;
import com.grouptwelve.roguelikegame.view.state.ViewState;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.IOException;
import java.util.List;

public class GameView implements AttackListener, EntityDeathListener,
        ChooseBuffListener, EntityHitListener, XpListener, HealthChangeListener {

    @FXML private StackPane root;
    @FXML private Canvas gameCanvas;
    @FXML private AnchorPane gameLayer;
    @FXML private AnchorPane gameObjectsLayer;
    @FXML private AnchorPane effectsLayer;
    @FXML private Rectangle hpBackground;
    @FXML private Rectangle hpFill;
    @FXML private Label hpLabel;
    @FXML private Rectangle levelBackground;
    @FXML private Rectangle levelFill;
    @FXML private Label levelLabel;
    @FXML private Label xpLabel;
    @FXML private Label timerLabel;
    @FXML private VBox pauseMenu;
    @FXML private VBox deathMenu;
    @FXML private VBox upgradeMenu;
    @FXML private Button fireBuffBox;
    @FXML private Button speedBuffBox;
    @FXML private Button healthBuffBox;


    private final ViewState viewState = new ViewState();
    private ParticleSystem particleSystem;
    private DamageNumberEffect damageNumberEffect;
    private DeathEffect deathEffect;
    private GraphicsContext gc;
    private GameController gameController;
    private GaussianBlur blur = new GaussianBlur(0);
    private static final double HIT_FLASH_DURATION = 0.15; // Flash in seconds

    // This is for setting "FXML" controller
    public void setGameController(GameController controller) {
        this.gameController = controller;
    }


    @FXML
    private void initialize() {
        root.requestFocus();

        this.gc = gameCanvas.getGraphicsContext2D();
        this.particleSystem = new ParticleSystem(effectsLayer);
        this.damageNumberEffect = new DamageNumberEffect(effectsLayer);
        this.deathEffect = new DeathEffect(effectsLayer);
        gameLayer.setEffect(blur);
    }

    public Parent getRoot() {
        return root;
    }

    /**
     * renders the game visuals by polling information that changes all the time(enemy and player position)
     * reads from hashMap that updates when events happen see methods below.
     * @param game model info interface containing only gets methods
     */
    public void render(GameDrawInfo game) {
        // Clear the canvas
        gc.clearRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
        // Clear previous frame
        gameObjectsLayer.getChildren().clear();

        Obstacle player = game.getPlayer();
        playerRenderer(player);

        List<Obstacle> enemies = game.getEnemies();
        enemiesRenderer(enemies);

    }

    /**
     * Only get obstacle information for player and uses it to draw the player
     * @param player obstacle info
     */
    public void playerRenderer(Obstacle player)
    {
        Circle playerCircle = new Circle(player.getX(), player.getY(), player.getSize());
        playerCircle.setFill(Color.LIGHTBLUE);
        playerCircle.setManaged(false);
        gameObjectsLayer.getChildren().add(playerCircle);
    }

    /**
     * draws the list of enemies, this includes the actual body and hp bar
     * @param enemies list of Obstacle info for enemies
     */
    public void enemiesRenderer(List<Obstacle> enemies)
    {
        for(Obstacle enemy : enemies)
        {
            Circle enemyCircle = new Circle(enemy.getX(), enemy.getY(), enemy.getSize());
            ObstacleData data = viewState.getObstacleData(enemy);
            double barWidth = 40;
            double barHeight = 5;
            double barOffset = 10;

            // HP bar background
            gc.setFill(Color.GRAY);
            gc.fillRect(enemy.getX() - barWidth / 2,
                    enemy.getY() - enemy.getSize() - barOffset,
                    barWidth, barHeight);

            double hpPercentage;

            if(data != null) // data has been updated by some event so its not default values
            {
                enemyCircle.setFill(data.getColor());
                hpPercentage = data.getHp() / data.getMaxHp();
            }
            else //no value has been changed since spawning so use default values
            {
                enemyCircle.setFill(Color.RED);
                hpPercentage = 1; //full bar 100%
            }
            enemyCircle.setManaged(false);
            gameObjectsLayer.getChildren().add(enemyCircle);

            gc.setFill(Color.RED);
            gc.fillRect(enemy.getX() - barWidth / 2,
                    enemy.getY() - enemy.getSize() - barOffset,
                    barWidth * hpPercentage, barHeight); // use percent
        }
    }

    @Override
    public void onAttack(AttackEvent attackEvent) {

        Circle attackCircle = new Circle(attackEvent.getX(), attackEvent.getY(), attackEvent.getRange());
        Obstacle attacker = attackEvent.getAttacker();
        if(attacker.getObstacleType() == ObstacleType.PLAYER) attackCircle.setFill(Color.BLUE);
        else  attackCircle.setFill(Color.VIOLET);

        attackCircle.setManaged(false);
        effectsLayer.getChildren().add(attackCircle);

        //remove attackcircle after a short timer
        PauseTransition pause = new PauseTransition(Duration.seconds(0.1));
        pause.setOnFinished(_ -> effectsLayer.getChildren().remove(attackCircle));
        pause.play();
    }

    // ==================== FOR DEBUGGING PURPOSES ====================
    public void updateDirectionLabel(int dx, int dy) {
        // actionLabel.setText(String.format("Direction: [%d, %d]", dx, dy));
    }

    public void updateStatusLabel(String status) {
        // actionLabel.setText(status);
    }

    // ==================== Updating FXML UI Components ====================
    public void updateGameTimeLabel(double gameTime) {
        int minutes = (int) (gameTime / 60);
        int seconds = (int) (gameTime % 60);
        timerLabel.setText(String.format("%d:%02d", minutes, seconds));
    }

    /**
     * Updates an given entity (player and enemies) hp bar
     * 
     * @param currentHp Entitiy's current hp 
     * @param maxHp Entity's max hp
     */
    public void updateHealthBar(double currentHp, double maxHp) {
        double percentage = currentHp / maxHp;

        hpFill.setWidth(200 * percentage);

        // Update HP label
        int roundedHp = (int)Math.round(currentHp);
        int roundedMaxHp = (int)Math.round(maxHp);
        hpLabel.setText(roundedHp + " / " + roundedMaxHp);

    }

    /**
     * Updates the FXML levelUpBar that displays player's xp state
     * 
     * @param xp Player's current xp amount
     * @param xpToNext The amount of xp to next level
     * @param level Player's level amount
     */
    public void updateLevelBar(int xp, int xpToNext, int level) {
        double percentage = (double) xp / xpToNext;
        levelFill.setWidth(200 * percentage);  // adjust width according to percentage
        levelLabel.setText("LVL: " + level);
        xpLabel.setText(xp + "/" + xpToNext);
    }

    /**
     * Highlights the selected buff button based on the index.
     * @param selectedBuff index of selected buff (0, 1, or 2)
     */
    public void updateSelectedLabel(int selectedBuff) {
        // Reset all buttons' border
        fireBuffBox.setStyle("-fx-border-color: transparent;");
        speedBuffBox.setStyle("-fx-border-color: transparent;");
        healthBuffBox.setStyle("-fx-border-color: transparent;");

        // Highlight selected button
        switch (selectedBuff) {
            case 0 -> fireBuffBox.setStyle("-fx-border-color: orange; -fx-border-width: 3;");
            case 1 -> speedBuffBox.setStyle("-fx-border-color: cyan; -fx-border-width: 3;");
            case 2 -> healthBuffBox.setStyle("-fx-border-color: lime; -fx-border-width: 3;");
        }
    }

    /**
     * Clears buff selection highlights.
     */
    public void clearBuffVisuals() {
        fireBuffBox.setStyle("-fx-border-color: transparent;");
        speedBuffBox.setStyle("-fx-border-color: transparent;");
        healthBuffBox.setStyle("-fx-border-color: transparent;");
    }


    @Override
    public void onChooseBuff(UpgradeEvent upgradeEvent) {
        UpgradeInterface[] upgrades = upgradeEvent.getUpgrades();
        fireBuffBox.setText(upgrades[0].getName());
        speedBuffBox.setText(upgrades[1].getName());
        healthBuffBox.setText(upgrades[2].getName());
        // Show level up menu
        showLevelMenu(true);
    }

    // ==================== FXML Layers state (pause, death, levelUp) ====================
    /**
     * Changes visibility of pause menu layer in FXML
     * 
     * @param show If show is true, then pause menu is set to be visible and blur is set to 10. If show is false, then nothing happens.
     */
    public void showPauseMenu(boolean show) {
        pauseMenu.setVisible(show);
        pauseMenu.setDisable(!show);    // Only focus on pauseMenu 
        gameLayer.setDisable(show);     // No other UI buttons beside pauseMenu
        blur.setRadius(show ? 10 : 0);
    }

    /**
     * Changes visibility of upgrade menu layer in FXML
     * 
     * @param show If show is true, then upgrade menu is set to be visible and blur is set to 10. If show is false, then nothing happens.
     */
    public void showLevelMenu(boolean show) {
        upgradeMenu.setVisible(show);
        blur.setRadius(show ? 10 : 0);
    }

    // ==================== GameListeners ====================

    @Override
    public void onUpdateXP(XpChangeEvent xpChangeEvent) {
        updateLevelBar(xpChangeEvent.getTotalXP(), xpChangeEvent.getXPtoNext(), xpChangeEvent.getLevel());
    }

    // ==================== Effects ====================

    @Override
    public void onEntityDeath(EntityDeathEvent event)
    {
        Obstacle obstacle = event.getObstacle();
        if(obstacle.getObstacleType() == ObstacleType.PLAYER)
        {
            playerDied(event.getX(), event.getY());
        }
        else
        {
            ObstacleData data = viewState.getObstacleData(obstacle);
            if(data != null)
            {

                //change to read so next time we reuse this enemy its full health and red(
                PauseTransition pause = new PauseTransition(Duration.seconds(HIT_FLASH_DURATION));
                pause.setOnFinished(_ -> viewState.setObstacleData(obstacle, Color.RED, data.getMaxHp(), data.getMaxHp()));
                pause.play();
            }

        }
    }
    @Override
    public void onEntityHit(EntityHitEvent entityHitEvent)
    {
        Obstacle obstacle = entityHitEvent.getObstacle();
        CombatResult combatResult = entityHitEvent.getCombatResult();
        if(obstacle.getObstacleType() != ObstacleType.PLAYER)
        {
            damageNumberEffect.show(obstacle.getX(), obstacle.getY(), combatResult.getDamage(), combatResult.isCritical());
            particleSystem.spawnHitParticles(obstacle.getX(), obstacle.getY());
            viewState.setObstacleData(obstacle, Color.WHITE, entityHitEvent.getHp(), entityHitEvent.getMaxHp());

            // Timer to change color back to red after duration
            PauseTransition pause = new PauseTransition(Duration.seconds(HIT_FLASH_DURATION));
            pause.setOnFinished(_ -> viewState.setObstacleData(obstacle, Color.RED, entityHitEvent.getHp(), entityHitEvent.getMaxHp()));
            pause.play();
        }
    }

    @Override
    public void onHealthChange(HealthChangeEvent event) {
        if (event.getEntity() instanceof Player) {
            updateHealthBar(event.getHp(), event.getMaxHp());
        }
    }

    /**
     * Plays the player death effect with ripple/shockwave, screen shake, red flash and toggles death menu.
     * 
     * @param x X position of the player
     * @param y Y position of the player
     */
    public void playerDied(double x, double y)
    {
        deathEffect.play(x, y);
        deathMenu.setVisible(true);
        blur.setRadius(10);
    }

    // ==================== FXML Controls ====================
    @FXML private void onResume() { gameController.resume(); }
    @FXML private void onQuit() throws IOException { gameController.quit(); }
    @FXML private void onPlayAgain() throws IOException { gameController.playAgain(); }
}
