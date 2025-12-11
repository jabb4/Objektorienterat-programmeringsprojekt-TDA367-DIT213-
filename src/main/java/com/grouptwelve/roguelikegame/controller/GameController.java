package com.grouptwelve.roguelikegame.controller;

import com.grouptwelve.roguelikegame.model.Game;
import com.grouptwelve.roguelikegame.model.entities.Entity;
import com.grouptwelve.roguelikegame.model.events.input.AttackEvent;
import com.grouptwelve.roguelikegame.model.events.input.GameEventListener;
import com.grouptwelve.roguelikegame.model.events.input.MovementEvent;
import com.grouptwelve.roguelikegame.model.events.output.EventPublisher;
import com.grouptwelve.roguelikegame.model.events.output.GameEventPublisher;
import com.grouptwelve.roguelikegame.model.upgrades.UpgradeInterface;
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

/**
 * Coordinates the game loop and events.
 */
public class GameController implements InputEventListener, GameEventPublisher {
  private final Game game;
  private final GameView gameView;
  private final InputHandler inputHandler;
  private AnimationTimer gameLoop;
  private long lastUpdate;
  private boolean paused;
  private boolean levelUp;
  private boolean death;
  private boolean chooseBuff;
  private int selectedBuff = 1;

  // All systems that want to observe game events
  private final List<GameEventListener> eventListeners;

  public GameController(Game game, GameView gameView, InputHandler inputHandler, EventPublisher eventPublisher) {
    this.game = game;
    this.gameView = gameView;
    this.inputHandler = inputHandler;
    this.eventListeners = new ArrayList<>();
    this.lastUpdate = 0;
    this.paused = false;

    // Register listeners
    addEventListener(game);
    inputHandler.setListener(this);
    eventPublisher.subscribe(this);

    // TODO: Other systems that needs to react to events such as audio and
    // animations.
    // addEventListener(audioManager);
    // ...
  }

  /**
   * Registers a system to receive game events.
   *
   * @param listener The system to register
   */
  public void addEventListener(GameEventListener listener) {
    if (!eventListeners.contains(listener)) {
      eventListeners.add(listener);
    }
  }

  /**
   * Unregisters a system from receiving game events.
   *
   * @param listener The system to unregister
   */
  public void removeEventListener(GameEventListener listener) {
    eventListeners.remove(listener);
  }

  // ==================== Input Event Handling ====================

  @Override
  public void onCommandPressed(Command command) {
    handleCommand(command, true);
  }

  @Override
  public void onCommandReleased(Command command) {
    handleCommand(command, false);
  }

  /**
   * Translates input commands into game events.
   *
   * @param command   The command that was triggered
   * @param isPressed True if pressed, false if released
   */
  private void handleCommand(Command command, boolean isPressed) {
    if(chooseBuff)
    {
      handleCommandBuff(command, isPressed);
      return;
    }
    if (command == Command.PAUSE && isPressed && game.getPlayer().getAliveStatus() == true) {
      togglePause();
    }
    if (paused) {
      return;
    }
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

    // TODO: Handle other commands when implemented
  }

  /**
   * seperate handler for commands in choose buff state
   * @param command   The command that was triggered
   * @param isPressed True if pressed, false if released
   */
  private void handleCommandBuff(Command command, boolean isPressed)
  {
    if (command == Command.MOVE_LEFT && isPressed)
    {
      if(this.selectedBuff == 2) this.selectedBuff = 1;
      else this.selectedBuff = 0;

      gameView.updateSelectedLabel(selectedBuff);
    }
    else if (command == Command.MOVE_RIGHT && isPressed)
    {
      if(this.selectedBuff == 0) this.selectedBuff = 1;
      else this.selectedBuff = 2;
        gameView.updateSelectedLabel(selectedBuff);


    }
    else if (command == Command.SELECT && isPressed)
    {
      for (GameEventListener listener : eventListeners) {
        listener.onChooseBuff(selectedBuff);

      }
      paused = false;
      chooseBuff = false;
      gameView.clearBuffVisuals();
    }

  }
  // ==================== Event Creation ====================

  /**
   * Creates a movement event from currently active movement commands into a
   * vector.
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

    //gameView.updateDirectionLabel(event.getDx(), event.getDy());

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
    /*if (activeKeys.isEmpty()) {
      gameView.updateStatusLabel("No keys pressed");
    } else {
      gameView.updateStatusLabel("Active: " + String.join(", ", activeKeys));
    }*/
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
   * Starts the game loop.
   * Should be used for resuming game states
   */
  public void start() {
    gameLoop = new AnimationTimer() {
      @Override
      public void handle(long now) {
        if (lastUpdate == 0) {
          lastUpdate = now;
          return;
        }

        // Calculate delta time in seconds
        double deltaTime = (now - lastUpdate) / 1_000_000_000.0;
        lastUpdate = now;

        update(deltaTime);
        render();
      }
    };
    gameLoop.start();
  }

  /**
   * Updates the game state based on input and elapsed time.
   */
  private void update(double deltaTime) {
    // Update game logic
    if (paused)
      return;
    game.update(deltaTime);

    // Update time display
    gameView.updateGameTimeLabel(game.getGameTime());
  }

  /**
   * Renders the current game state.
   */
  private void render() {
    gameView.render(game, lastUpdate);
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

  // ==================== GameEventPublisher Implementation ====================

  @Override
  public void onAttackVisual(double x, double y, double size) {
    gameView.drawAttack(x, y, size);
  }

  @Override
  public void onPlayerDeath(double x, double y) {
    gameView.playerDied(x, y);
    paused = true;
  }

  @Override
  public void onEnemyHit(double x, double y, double damage, boolean isCritical) {
    gameView.showDamageNumber(x, y, damage, isCritical);
    gameView.spawnHitParticles(x, y);
  }

  @Override
  public void onEnemyDeath(double x, double y, int xpValue) {
    // Visual effects for enemy death can be added here
    // XP handling is done in the model layer
  }
  @Override
  public void onPlayerLevelUp(int level, UpgradeInterface[] upgrades)
  {
    chooseBuff = true;
    this.paused = true;
    String[] stringValues = new String[upgrades.length];
    for(int i = 0; i < upgrades.length; i++)
    {
      stringValues[i] ="Buff "+ (i + 1) + ":   " + upgrades[i].getName() + "          ";
    }
    gameView.updateBuffLabels(stringValues);

  }

// ==================== FXML ====================
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
      // currentUpgrades[0] = UpgradeRegistry.randomUpgrade();
      // currentUpgrades[1] = UpgradeRegistry.randomUpgrade();
      // currentUpgrades[2] = UpgradeRegistry.randomUpgrade();

      // Update button text with upgrade names
      // gameView.setUpgrade1().setText(currentUpgrades[0].getName());
      // gameView.setUpgrade2().setText(currentUpgrades[1].getName());
      // gameView.setUpgrade3().setText(currentUpgrades[2].getName());

      gameView.showLevelMenu(true);

  }

  public void triggerDeath() {
      death = !death;

      if (death) {
          stop();               
          // game.getPlayer().setAliveStatus(false); 
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
      Parent root = loader.load();
      GameView gameView = loader.getController();
      InputHandler inputHandler = new InputHandler();

      EventPublisher eventManager = new EventPublisher();
      
      Game game = new Game(eventManager);


      Scene scene = new Scene(root, 1280, 720);
      inputHandler.setupInputHandling(scene);
      
      // Create controller (subscribes to event manager)
      GameController gameController = new GameController(game, gameView, inputHandler, eventManager);
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
          // UpgradeInterface selected = currentUpgrades[index];
          // selected.apply(game.getPlayer());

          // Update health bar if max HP changed
          gameView.updateHealthBar(game.getPlayer().getHp(), game.getPlayer().getMaxHP(), game.getPlayer());

          // Resume game
          lastUpdate = 0;
          start();

          // Hide menu
          gameView.showLevelMenu(false);

          // System.out.println("Applied upgrade: " + selected.getName());
          System.out.println("Player's health: " + game.getPlayer().getHp());
      }
  }
}
