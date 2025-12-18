package com.grouptwelve.roguelikegame.view;

import com.grouptwelve.roguelikegame.model.GameDrawInfo;
import com.grouptwelve.roguelikegame.model.combat.CombatResult;
import com.grouptwelve.roguelikegame.model.entities.Obstacle;
import com.grouptwelve.roguelikegame.model.entities.ObstacleType;
import com.grouptwelve.roguelikegame.model.entities.Player;
import com.grouptwelve.roguelikegame.model.events.output.events.*;
import com.grouptwelve.roguelikegame.model.events.output.listeners.*;
import com.grouptwelve.roguelikegame.model.upgrades.UpgradeInterface;
import com.grouptwelve.roguelikegame.view.effects.AttackVisualEffect;
import com.grouptwelve.roguelikegame.view.effects.DamageNumberEffect;
import com.grouptwelve.roguelikegame.view.effects.DeathEffect;
import com.grouptwelve.roguelikegame.view.effects.ParticleSystem;
import com.grouptwelve.roguelikegame.view.rendering.EntityRenderer;
import com.grouptwelve.roguelikegame.view.state.ObstacleData;
import com.grouptwelve.roguelikegame.view.state.ViewState;
import com.grouptwelve.roguelikegame.view.ui.BuffSelectionUI;
import com.grouptwelve.roguelikegame.view.ui.HudManager;
import com.grouptwelve.roguelikegame.view.ui.MenuManager;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class GameView implements AttackListener, EntityDeathListener,
        ChooseBuffListener, EntityHitListener, XpListener, HealthChangeListener {

    @FXML private StackPane root;
    @FXML private Canvas gameCanvas;
    @FXML private AnchorPane gameLayer;
    @FXML private AnchorPane gameObjectsLayer;
    @FXML private AnchorPane effectsLayer;
    @FXML private Rectangle hpFill;
    @FXML private Label hpLabel;
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
    private AttackVisualEffect attackVisualEffect;
    private HudManager hudManager;
    private MenuManager menuManager;
    private BuffSelectionUI buffSelectionUI;
    private EntityRenderer entityRenderer;
    private ButtonListener buttonListener;
    private GaussianBlur blur = new GaussianBlur(0);
    private static final double HIT_FLASH_DURATION = 0.15; // Flash in seconds

    // This is for setting "FXML" controller
    public void setButtonListener(ButtonListener listener) {
        this.buttonListener = listener;
    }

    @FXML
    private void initialize() {
        root.requestFocus();

        this.particleSystem = new ParticleSystem(effectsLayer);
        this.damageNumberEffect = new DamageNumberEffect(effectsLayer);
        this.deathEffect = new DeathEffect(effectsLayer);
        this.attackVisualEffect = new AttackVisualEffect(effectsLayer);
        this.hudManager = new HudManager(hpFill, hpLabel, levelFill, levelLabel, xpLabel, timerLabel);
        this.menuManager = new MenuManager(pauseMenu, deathMenu, upgradeMenu, gameLayer, blur);
        this.buffSelectionUI = new BuffSelectionUI(fireBuffBox, speedBuffBox, healthBuffBox);
        this.entityRenderer = new EntityRenderer(gameObjectsLayer, gameCanvas.getGraphicsContext2D(), viewState);
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
        entityRenderer.clear(gameCanvas.getWidth(), gameCanvas.getHeight());
        entityRenderer.renderPlayer(game.getPlayer());
        entityRenderer.renderEnemies(game.getEnemies());
    }

    @Override
    public void onAttack(AttackEvent attackEvent) {
        Obstacle attacker = attackEvent.getAttacker();
        boolean isPlayer = attacker.getObstacleType() == ObstacleType.PLAYER;
        attackVisualEffect.show(attackEvent.getX(), attackEvent.getY(), attackEvent.getRange(), isPlayer);
    }

    // ==================== Updating FXML UI Components ====================

    public void updateGameTimeLabel(double gameTime) {
        hudManager.updateGameTimeLabel(gameTime);
    }

    /**
     * Updates an given entity (player and enemies) hp bar
     * 
     * @param currentHp Entitiy's current hp 
     * @param maxHp Entity's max hp
     */
    public void updateHealthBar(double currentHp, double maxHp) {
        hudManager.updateHealthBar(currentHp, maxHp);
    }

    /**
     * Updates the FXML levelUpBar that displays player's xp state
     * 
     * @param xp Player's current xp amount
     * @param xpToNext The amount of xp to next level
     * @param level Player's level amount
     */
    public void updateLevelBar(int xp, int xpToNext, int level) {
        hudManager.updateLevelBar(xp, xpToNext, level);
    }

    /**
     * Highlights the selected buff button based on the index.
     * @param selectedBuff index of selected buff (0, 1, or 2)
     */
    public void updateSelectedLabel(int selectedBuff) {
        buffSelectionUI.updateSelectedLabel(selectedBuff);
    }

    /**
     * Clears buff selection highlights.
     */
    public void clearBuffVisuals() {
        buffSelectionUI.clearBuffVisuals();
    }

    @Override
    public void onChooseBuff(UpgradeEvent upgradeEvent) {
        UpgradeInterface[] upgrades = upgradeEvent.getUpgrades();
        buffSelectionUI.setBuffOptions(upgrades);
        showLevelMenu(true);
    }

    // ==================== FXML Layers state (pause, death, levelUp) ====================

    /**
     * Changes visibility of pause menu layer in FXML
     * 
     * @param show If show is true, then pause menu is set to be visible and blur is set to 10. If show is false, then nothing happens.
     */
    public void showPauseMenu(boolean show) {
        menuManager.showPauseMenu(show);
    }

    /**
     * Changes visibility of upgrade menu layer in FXML
     * 
     * @param show If show is true, then upgrade menu is set to be visible and blur is set to 10. If show is false, then nothing happens.
     */
    public void showLevelMenu(boolean show) {
        menuManager.showLevelMenu(show);
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
        menuManager.showDeathMenu();
    }

    // ==================== FXML Controls ====================
    
    @FXML private void onResume() {
        if (buttonListener != null) buttonListener.onResume();
    }
    @FXML private void onQuit() {
        if (buttonListener != null) buttonListener.onQuit();
    }
    @FXML private void onPlayAgain() {
        if (buttonListener != null) buttonListener.onPlayAgain();
    }
}
