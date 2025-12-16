package com.grouptwelve.roguelikegame.controller;

import com.grouptwelve.roguelikegame.model.Game;
import com.grouptwelve.roguelikegame.model.entities.Obstacle;
import com.grouptwelve.roguelikegame.model.entities.ObstacleType;
import com.grouptwelve.roguelikegame.model.events.input.GameEventListener;
import com.grouptwelve.roguelikegame.model.events.input.MovementEvent;
import com.grouptwelve.roguelikegame.model.events.output.events.EntityDeathEvent;
import com.grouptwelve.roguelikegame.model.events.output.listeners.ChooseBuffListener;
import com.grouptwelve.roguelikegame.model.events.output.listeners.EntityDeathListener;
import com.grouptwelve.roguelikegame.model.events.output.publishers.*;
import com.grouptwelve.roguelikegame.model.upgrades.UpgradeInterface;
import com.grouptwelve.roguelikegame.view.GameView;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Coordinates the game loop and events.
 */
public class GameController implements InputEventListener, ChooseBuffListener, EntityDeathListener {
  private final Game game;
  private final GameView gameView;
  private final InputHandler inputHandler;
  private AnimationTimer gameLoop;
  private long lastUpdate = 0;
  private boolean paused = false;
  private boolean death;
  private boolean chooseBuff;
  private int selectedBuff = 1;
  private MenuNavigator menuNavigator;

  @FXML private VBox pauseMenu;    // Pause menu buttons
  @FXML private VBox deathMenu;    // Death menu buttons

  private MenuNavigator pauseMenuNavigator;
  private MenuNavigator deathMenuNavigator;

  // All systems that want to observe game events
  private final List<GameEventListener> eventListeners = new ArrayList<>();

  public GameController(Game game, GameView gameView, InputHandler inputHandler) {
    this.game = game;
    this.gameView = gameView;
    this.inputHandler = inputHandler;

    addEventListener(game);
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
    if (command == Command.PAUSE && isPressed && game.isPlayerAlive()) {
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
      notifyAttack();
    }
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
    else if ((command == Command.SELECT || command == Command.ATTACK) && isPressed)
    {
      for (GameEventListener listener : eventListeners) {
        listener.onApplyBuff(selectedBuff);
      }
      // TODO: Entity should publish a stat change event and the View should subscribe to update
      gameView.updateHealthBar(game.getPlayerHp(), game.getPlayerMaxHp()); // Unnecessary when not choosing health upgrade

      // Moving while pausing will continue the movement when you let go during pause
        // TODO: Generalize this to when game is pausing
      game.resetPlayerMovement();
      this.paused = false;
      chooseBuff = false;
      gameView.clearBuffVisuals();
      gameView.showLevelMenu(false);
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
  }

  /**
   * Notifies all listeners about an attack event.
   */
  private void notifyAttack() {
    for (GameEventListener listener : eventListeners) {
      listener.onAttack();
    }
  }

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
  public void onEntityDeath(EntityDeathEvent event) {
    Obstacle obstacle = event.getObstacle();
    if(obstacle.getObstacleType() == ObstacleType.PLAYER)
    {
        paused = true;
    }
    else
    {
        //TODO: implement show game statistics that are also not done
        System.out.println("enmy died");
    }
  }

  @Override
  public void onChooseBuff(UpgradeInterface[] upgrades)
  {
    chooseBuff = true;
    this.paused = true;

    // Update buttons with the new upgrades
      selectedBuff = 0;
      gameView.updateSelectedLabel(selectedBuff);
  }

// ==================== FXML ====================

  public void togglePause() {
      this.paused = !paused;

      if (paused) {   // stop the game loop
          gameView.showPauseMenu(true);
      } else {  // resume game loop
          gameView.showPauseMenu(false);
      }
  }

  public void resume() {
      if (paused) {
          paused = false;      
          gameView.showPauseMenu(false);
      }
  }

  public void playAgain() throws IOException {
      stop();

      Stage stage = (Stage) gameView.getRoot().getScene().getWindow();

      FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/grouptwelve/roguelikegame/game-view.fxml"));
      Parent root = loader.load();
      GameView gameView = loader.getController();
      InputHandler inputHandler = new InputHandler();

      EventPublisher eventPublisher = new EventPublisher();
      LevelUpPublisher levelUpPublisher = (LevelUpPublisher) eventPublisher;
      EntityPublisher entityPublisher = (EntityPublisher) eventPublisher;
      ChooseBuffPublisher chooseBuffPublisher = (ChooseBuffPublisher) eventPublisher;
      XpPublisher xpPublisher = (XpPublisher) eventPublisher;

      Game game = new Game(entityPublisher,chooseBuffPublisher, levelUpPublisher, xpPublisher);

      Scene scene = new Scene(root, 1280, 720);
      inputHandler.setupInputHandling(scene);
      
      // Create controller (subscribes to event manager)
      GameController gameController = new GameController(game, gameView, inputHandler);
      inputHandler.setListener(gameController);

      gameController.addEventListener(game);

      entityPublisher.subscribeEntityDeath(gameController);
      chooseBuffPublisher.subscribeBuff(gameController);

      entityPublisher.subscribeEntityHit(gameView);
      entityPublisher.subscribeAttack(gameView);
      entityPublisher.subscribeEntityDeath(gameView);
      chooseBuffPublisher.subscribeBuff(gameView);
      xpPublisher.subscribeXp(gameView);

      gameView.setGameController(gameController); // Connect FXML components with GameController
      gameView.setGameController(gameController);
      gameController.start();

      stage.setTitle("Roguelike Game");
      stage.setScene(scene);
      stage.show();
  }

  public void quit() throws IOException {
      stop();

      Stage stage = (Stage) gameView.getRoot().getScene().getWindow();

      FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("/com/grouptwelve/roguelikegame/menu-view.fxml"));
      Scene menuScene = new Scene(menuLoader.load(), 1280, 720);

      // Attach global CSS
      menuScene.getStylesheets().add(getClass().getResource("/com/grouptwelve/roguelikegame/global.css").toExternalForm());

      stage.setScene(menuScene);
      stage.show();
  }
}
