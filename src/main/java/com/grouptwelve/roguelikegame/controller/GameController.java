package com.grouptwelve.roguelikegame.controller;

import com.grouptwelve.roguelikegame.model.ControllEventManager;
import com.grouptwelve.roguelikegame.model.EventsPackage.AttackEvent;
import com.grouptwelve.roguelikegame.model.EventsPackage.GameEventListener;
import com.grouptwelve.roguelikegame.model.EventsPackage.MovementEvent;
import com.grouptwelve.roguelikegame.model.Game;
import com.grouptwelve.roguelikegame.view.ControllerListener;
import com.grouptwelve.roguelikegame.view.GameView;
import javafx.animation.AnimationTimer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Coordinates the game loop and events.
 */
public class GameController implements InputEventListener, ControllerListener {
    private final Game game;
    private final GameView gameView;
    private final InputHandler inputHandler;
    private AnimationTimer gameLoop;
    private long lastUpdate;
    private boolean paused;

    // All systems that want to observe game events
    private List<GameEventListener> eventListeners;

    public GameController(Game game, GameView gameView, InputHandler inputHandler) {
        this.game = game;
        this.gameView = gameView;
        this.inputHandler = inputHandler;
        this.eventListeners = new ArrayList<>();
        this.lastUpdate = 0;
        this.paused = false;

        // Register listeners
        addEventListener(game);
        inputHandler.setListener(this);
        ControllEventManager.getInstance().subscribe(this);

        // TODO: Other systems that needs to react to events such as audio and animations.
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
     * @param command The command that was triggered
     * @param isPressed True if pressed, false if released
     */
    private void handleCommand(Command command, boolean isPressed) {
        if (command == Command.PAUSE && isPressed) {
            togglePause();
        }
        if(paused) return;
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
                game.getPlayer().getWeapon().getRange()
        );
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

        // Update view to show attack
        gameView.drawAttack(event.getAttackX(), event.getAttackY(), event.getRange());
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
        if(paused) return;
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

    @Override
    public void drawAttack(double x, double y, double size) {
        gameView.drawAttack(x,y,size);
    }

    @Override
    public void playerDied(double x, double y) {
        gameView.playerDied(x, y);
        paused = true;
    }

    @Override
    public void onEnemyHit(double x, double y, double damage) {
        gameView.showDamageNumber(x, y, damage);
        gameView.spawnHitParticles(x, y);
    }

    private void togglePause()
    {
        this.paused = !paused;
        System.out.println("paaaaaaaaaaaaaaaaaaaause!!!!!!!!!!!!!!!!!!!");
    }
}
