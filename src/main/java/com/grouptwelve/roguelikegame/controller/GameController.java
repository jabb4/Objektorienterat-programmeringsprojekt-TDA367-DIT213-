package com.grouptwelve.roguelikegame.controller;

import com.grouptwelve.roguelikegame.model.ControlEventManager;
import com.grouptwelve.roguelikegame.model.EventsPackage.AttackEvent;
import com.grouptwelve.roguelikegame.model.EventsPackage.GameEventListener;
import com.grouptwelve.roguelikegame.model.EventsPackage.MovementEvent;
import com.grouptwelve.roguelikegame.model.UpgradesPackage.UpgradeInterface;
import com.grouptwelve.roguelikegame.model.UpgradesPackage.UpgradeLogic.UpgradeRegistry;
import com.grouptwelve.roguelikegame.model.Game;
import com.grouptwelve.roguelikegame.view.ControllerListener;
import com.grouptwelve.roguelikegame.model.EntitiesPackage.EntityFactory;
import com.grouptwelve.roguelikegame.model.EntitiesPackage.Player;
import com.grouptwelve.roguelikegame.model.EntitiesPackage.Entity;
import com.grouptwelve.roguelikegame.model.EntitiesPackage.Enemy;
import com.grouptwelve.roguelikegame.model.EntitiesPackage.Entities;
import com.grouptwelve.roguelikegame.model.WeaponsPackage.CombatManager;
import com.grouptwelve.roguelikegame.view.GameView;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Objects;

/**
 * Coordinates the game loop and events.
 */
public class GameController implements InputEventListener, ControllerListener {
    private final Game game;
    private final GameView gameView;
    private final InputHandler inputHandler;
    private AnimationTimer gameLoop;
    private long lastUpdate;
    // All systems that want to observe game events
    private final List<GameEventListener> eventListeners;
    double deltaTime;
    private boolean paused = false;
    private boolean death = false;
    private boolean levelUp = false;
    private double elapsedTime = 0; // For the timer in game-view (seconds)

    private UpgradeInterface[] currentUpgrades = new UpgradeInterface[3];

    public GameController(Game game, GameView gameView, InputHandler inputHandler) {
        this.game = game;
        this.gameView = gameView;
        this.inputHandler = inputHandler;
        this.eventListeners = new ArrayList<>();
        this.lastUpdate = 0;

        addEventListener(game);
        inputHandler.setListener(this);
        ControlEventManager.getInstance().subscribe(this);

        // Set this controller in CombatManager
        CombatManager.getInstance().setGameController(this);
    }

    public void addEventListener(GameEventListener listener) {
        if (!eventListeners.contains(listener)) {
            eventListeners.add(listener);
        }
    }

    // TODO: Handle other commands when implemented

  

  // ==================== Event Creation ====================

    @Override
    public void onCommandReleased(Command command) {
        handleCommand(command, false);
    }

    
    @Override
    public void onCommandPressed(Command command) {
        handleCommand(command, true);
    }

    /**
     * Translates input commands into game events.
     *
     * @param command The command that was triggered
     * @param isPressed True if pressed, false if released
     */
    private void handleCommand(Command command, boolean isPressed) {
        if (command == Command.PAUSE && isPressed && game.getPlayer().getAliveStatus()) {
            togglePause();
        }
        if(paused || levelUp)  return;
        // Handle movement commands (trigger on both press and release)
        if (command.isMovement()) {
            // Movement changed - recalculate and notify
            MovementEvent event = createMovementEvent();
            notifyMovement(event);
        }

        // Handle action commands (only on press)
        else if (command == Command.ATTACK && isPressed) {
            AttackEvent event = createAttackEvent();
            notifyAttack(event);
        }

        else if (command.isSelect() && isPressed) {
            switch (command) {
                case SELECT_1 -> gameView.highlightItem(1);
                case SELECT_2 -> gameView.highlightItem(2);
                case SELECT_3 -> gameView.highlightItem(3);
        }
        }

        // TODO: Handle other commands when implemented

    }

    // ==================== Event Creation ====================

    /**
     * Creates a movement event from currently active movement commands into a vector.
     *
     * @return MovementEvent containing the current movement direction
     */
    private MovementEvent createMovementEvent() {
        Set<Command> activeCommands = inputHandler.getActiveCommands();

        int dx = 0, dy = 0;
        if (activeCommands.contains(Command.MOVE_LEFT)) dx -= 1;
        if (activeCommands.contains(Command.MOVE_RIGHT)) dx += 1;
        if (activeCommands.contains(Command.MOVE_UP)) dy -= 1;
        if (activeCommands.contains(Command.MOVE_DOWN)) dy += 1;

    return new MovementEvent(dx, dy);
  }

  /**
   * Creates an attack event based on current player state.
   * Gets attack position and range from the player's weapon.
   *
   * @return AttackEvent containing attack information
   */
  private AttackEvent createAttackEvent() {
    // TODO: Improve to accommodate Law of Demeter pattern
    return new AttackEvent(
        game.getPlayer().getAttackPointX(),
        game.getPlayer().getAttackPointY(),
        game.getPlayer().getWeapon().getRange());
  }

  // TODO: Add more event creation methods as features are implemented

  // ==================== Event Notification ====================

  /**
   * Notifies all listeners about a movement event.
   *
   * @param event The movement event to broadcast
   */
  private void notifyMovement(MovementEvent event) {
    for (GameEventListener listener : eventListeners) {
      listener.onMovement(event);
    }

    gameView.updateDirectionLabel(event.getDx(), event.getDy());

    // TEMPORARY FOR DEBUGGING
    updateStatusDisplay();
  }

  /**
   * TEMPORARY FOR DEBUGGING
   * Updates the status display based on currently active commands.
   * Shows which keys are currently pressed.
   */
  private void updateStatusDisplay() {
    List<String> activeKeys = getStrings();

    // Update label
    if (activeKeys.isEmpty()) {
      gameView.updateStatusLabel("No keys pressed");
    } else {
      gameView.updateStatusLabel("Active: " + String.join(", ", activeKeys));
    }
    // TODO: Add more event creation methods as features are implemented
  }


    private List<String> getStrings() {
        Set<Command> activeCommands = inputHandler.getActiveCommands();
        List<String> activeKeys = new ArrayList<>();

        // Check movement keys
        if (activeCommands.contains(Command.MOVE_UP)) activeKeys.add("UP");
        if (activeCommands.contains(Command.MOVE_DOWN)) activeKeys.add("DOWN");
        if (activeCommands.contains(Command.MOVE_LEFT)) activeKeys.add("LEFT");
        if (activeCommands.contains(Command.MOVE_RIGHT)) activeKeys.add("RIGHT");

        // Check action keys
        if (activeCommands.contains(Command.ATTACK)) activeKeys.add("ATTACK");
        return activeKeys;
    }

  /**
   * Notifies all listeners about an attack event.
   *
   * @param event The attack event to broadcast
   */
  private void notifyAttack(AttackEvent event) {
    for (GameEventListener listener : eventListeners) {
      listener.onAttack(event);
    }
  }

  // TODO: Add notification methods for other events

  // ==================== Game Loop ====================

  /**
   * Updates the game state based on input and elapsed time.
   */
  private void update(double deltaTime) {
    // Update game logic
    if (paused)
      return;
    game.update(deltaTime);

  }
    /**
     * Starts the game loop.
     * Should be used for resuming game states
     */
    public void start() {
        lastUpdate = System.nanoTime(); // Reset timer
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (lastUpdate == 0) {
                    lastUpdate = now;
                    return;
                }
                
                // Calculate delta time in seconds
                deltaTime = (now - lastUpdate) / 1_000_000_000.0;
                lastUpdate = now;
                
                update(deltaTime);
                render();

                // Update timer
                elapsedTime += deltaTime;
                gameView.updateGameTimeLabel(elapsedTime);

            }
        };
        gameLoop.start();
    }
    /**
     * Renders the current game state.
     */
    private void render() {
        gameView.render(game, deltaTime);
    }
    
    /**
     * Stops the game loop.
     * Should be used for pausing game states
     */
    public void stop() {
        if (gameLoop != null) {
            gameLoop.stop();
        }
    }
  

    @Override
    public void drawAttack(double x, double y, double size) {
         gameView.drawAttack(x,y,size);
    }

    @Override
    public void playerDied(double x, double y) {
        game.getPlayer().setAliveStatus(false);
        gameView.playerDied(x, y);
        stop();
    }

  @Override
  public void onEnemyHit(double x, double y, double damage) {
    gameView.showDamageNumber(x, y, damage, false);
    gameView.spawnHitParticles(x, y);
  }

  @Override
  public void onEnemyCritHit(double x, double y, double damage) {
    gameView.showDamageNumber(x, y, damage, true);
    gameView.spawnHitParticles(x, y);
  }

    public void togglePause() {
        paused = !paused;

        if (paused) {
            stop();                // stop the game loop
            gameView.showPauseMenu(true);
        } else {
            gameView.showPauseMenu(false);
            if (levelUp) return;
            lastUpdate = 0;        // prevents deltaTime spike
            start();               // resume game loop
        }
    }

    public void triggerLevelUp() {
        levelUp = true;
        stop();     

        // Generate 3 random upgrades
        currentUpgrades[0] = UpgradeRegistry.randomUpgrade();
        currentUpgrades[1] = UpgradeRegistry.randomUpgrade();
        currentUpgrades[2] = UpgradeRegistry.randomUpgrade();

        // Update button text with upgrade names
        gameView.setUpgrade1().setText(currentUpgrades[0].getName());
        gameView.setUpgrade2().setText(currentUpgrades[1].getName());
        gameView.setUpgrade3().setText(currentUpgrades[2].getName());

        gameView.showLevelMenu(true);

    }
    public void triggerDeath() {
        death = !death;

        if (death) {
            stop();               
            game.getPlayer().setAliveStatus(false); 
            gameView.showDeathMenu(true);
        } else {
            lastUpdate = 0;        
            start();               
            gameView.showDeathMenu(false);
        }
    }

    public void takeDamage(Entity entity, double damage) {
        if (!entity.getAliveStatus()) return; // already dead

        // Apply damage
        entity.takeDamage(damage);

        // Update health bar
        gameView.updateHealthBar(entity.getHp(), entity.getMaxHP(), entity);
    }

    public void levelUp(int xp, int xptonext, int level) {
        gameView.updateLevelBar(xp, xptonext, level);
    }

    public void resume() {
        if (paused) {
            paused = false;
            lastUpdate = 0;        
            start();               
            gameView.showPauseMenu(false);
        }
    }

    public void playAgain() throws IOException {
        stop();
        game.reset();

        Stage stage = (Stage) gameView.getRoot().getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/grouptwelve/roguelikegame/game-view.fxml"));
        Parent root = loader.load();  // FXML creates the GameView instance

        GameView gameView = loader.getController();
        Game game = Game.getInstance();
        gameView.setGame(game);
        InputHandler inputHandler = new InputHandler();

        Scene scene = new Scene(root, 800, 600);
        inputHandler.setupInputHandling(scene);
        
        GameController gameController = new GameController(game, gameView, inputHandler);
        gameView.setGameController(gameController);
        gameController.start();
        
        stage.setTitle("Roguelike Game");
        stage.setScene(scene);
        stage.show();

    }

    public void quit() throws IOException {
        stop();
        game.reset();

        Stage stage = (Stage) gameView.getRoot().getScene().getWindow();

        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("/com/grouptwelve/roguelikegame/menu-view.fxml"));
        Scene menuScene = new Scene(menuLoader.load(), 800, 600);

        // Attach global CSS
        menuScene.getStylesheets().add(getClass().getResource("/com/grouptwelve/roguelikegame/global.css").toExternalForm());

        stage.setScene(menuScene);
        stage.show();
    }

    public void upgrade1() {
        applyUpgrade(0);
    }

    public void upgrade2() {
        applyUpgrade(1);
    }

    public void upgrade3() {
        applyUpgrade(2);
    }

    public void applyUpgrade(int index) {
        if (levelUp) {
            levelUp = false;

            // Apply to player
            UpgradeInterface selected = currentUpgrades[index];
            selected.apply(game.getPlayer());

            // Update health bar if max HP changed
            gameView.updateHealthBar(game.getPlayer().getHp(), game.getPlayer().getMaxHP(), game.getPlayer());

            // Resume game
            lastUpdate = 0;
            start();

            // Hide menu
            gameView.showLevelMenu(false);

            System.out.println("Applied upgrade: " + selected.getName());
            System.out.println("Player's health: " + game.getPlayer().getHp());
        }
    }
}
