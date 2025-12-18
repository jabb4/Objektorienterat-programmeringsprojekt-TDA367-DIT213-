package com.grouptwelve.roguelikegame.controller;

import com.grouptwelve.roguelikegame.model.Game;
import com.grouptwelve.roguelikegame.model.entities.Obstacle;
import com.grouptwelve.roguelikegame.model.entities.ObstacleType;
import com.grouptwelve.roguelikegame.model.events.input.GameEventListener;
import com.grouptwelve.roguelikegame.model.events.input.MovementEvent;
import com.grouptwelve.roguelikegame.model.events.output.events.EntityDeathEvent;
import com.grouptwelve.roguelikegame.model.events.output.events.UpgradeEvent;
import com.grouptwelve.roguelikegame.model.events.output.listeners.ChooseBuffListener;
import com.grouptwelve.roguelikegame.model.events.output.listeners.EntityDeathListener;
import com.grouptwelve.roguelikegame.model.statistics.HighScoreManager;
import com.grouptwelve.roguelikegame.view.ButtonListener;
import com.grouptwelve.roguelikegame.view.GameView;
import javafx.animation.AnimationTimer;
import javafx.scene.control.Button;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Coordinates the game loop and events.
 */
public class GameController implements InputEventListener, ChooseBuffListener, EntityDeathListener, ButtonListener {
  private final Game game;
  private final GameView gameView;
  private final InputHandler inputHandler;
  private final SceneManager sceneManager;
  private final HighScoreManager highScoreManager;
  
  private AnimationTimer gameLoop;
  private long lastUpdate = 0;
  private boolean paused = false;
  private boolean chooseBuff;
  private int selectedBuff = 1;
  private MenuNavigator menuNavigator;

  // All systems that want to observe game events
  private final List<GameEventListener> eventListeners = new ArrayList<>();

  public GameController(Game game, GameView gameView, InputHandler inputHandler, SceneManager sceneManager, HighScoreManager highScoreManager) {
    this.game = game;
    this.gameView = gameView;
    this.inputHandler = inputHandler;
    this.sceneManager = sceneManager;
    this.highScoreManager = highScoreManager;

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
      if (paused && !chooseBuff) {
          if (isPressed){
              switch (command) {
                  case MOVE_UP -> menuNavigator.moveUp();
                  case MOVE_DOWN -> menuNavigator.moveDown();
                  case SELECT -> menuNavigator.select();
                  case PAUSE -> togglePauseMenu();
              }
          }
      }
      else if (chooseBuff)
      {
          handleCommandBuff(command, isPressed);
      }
      else if (command == Command.PAUSE && isPressed && game.isPlayerAlive()) {
          togglePauseMenu();
      }
      else if (command.isMovement()) {
          // Movement changed - recalculate and notify
          MovementEvent event = createMovementEvent();
          notifyMovement(event);
      }
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
      if (command == Command.MOVE_UP && isPressed)
      {
          if(this.selectedBuff == 2) this.selectedBuff = 1;
          else this.selectedBuff = 0;
          gameView.updateSelectedLabel(selectedBuff);
      }
      else if (command == Command.MOVE_DOWN && isPressed)
      {
          if(this.selectedBuff == 0) this.selectedBuff = 1;
          else this.selectedBuff = 2;
          gameView.updateSelectedLabel(selectedBuff);
      }
      else if (command == Command.SELECT && isPressed)
      {
          for (GameEventListener listener : eventListeners) {
            listener.onApplyBuff(selectedBuff);
      }

      unpause();
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
    gameView.render(game);
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
    if (obstacle.getObstacleType() == ObstacleType.PLAYER) {
        pause();
        
        // statistics (prints to console for testing)
        game.finalizeStatistics();
        
        List<Button> menuButtons = gameView.getRoot().lookupAll(".death-menu-button").stream()
                .filter(node -> node instanceof Button)
                .map(node -> (Button) node)
                .toList();

        menuNavigator = new MenuNavigator(menuButtons);
    }
  }

  @Override
  public void onChooseBuff(UpgradeEvent upgradeEvent)
  {
      chooseBuff = true;
      pause();

    // Update buttons with the new upgrades
      selectedBuff = 0;
      gameView.updateSelectedLabel(selectedBuff);
  }

    /**
     * Pause the game loop and stop any player movement
     */
  public void pause() {
      this.paused = true;
      game.resetPlayerMovement();
  }

    /**
     * Unpause the game
     */
  public void unpause() {
      this.paused = false;
  }

    /**
     * Toggles the game state. If the game is paused it will be unpaused, if it is unpaused it will be paused
     */
  public void togglePause(){
      if (this.paused) {
          unpause();
      } else pause();
  }

// ==================== FXML ====================

  public void togglePauseMenu() {
      togglePause();

      if (paused) {   // stop the game loop
          gameView.showPauseMenu(true);
          List<Button> menuButtons = gameView.getRoot().lookupAll(".pause-menu-button").stream()
                  .filter(node -> node instanceof Button)
                  .map(node -> (Button) node)
                  .toList();

          menuNavigator = new MenuNavigator(menuButtons);

      } else {  // resume game loop
          gameView.showPauseMenu(false);
      }
    }

    @Override
    public void onResume() {
        if (paused) {
            unpause();
            gameView.showPauseMenu(false);
        }
    }

    @Override
    public void onPlayAgain() {
        try {
            stop();
            sceneManager.startGame();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onQuit() {
        try {
            stop();
            sceneManager.showMenu();
        } catch (IOException e) {
            e.printStackTrace(); // Handle exception here so View doesn't have to
        }
    }
}
