package com.grouptwelve.roguelikegame.controller;

import com.grouptwelve.roguelikegame.model.Game;
import com.grouptwelve.roguelikegame.model.EntitiesPackage.EntityFactory;
import com.grouptwelve.roguelikegame.model.EntitiesPackage.Player;
import com.grouptwelve.roguelikegame.model.EntitiesPackage.Entity;
import com.grouptwelve.roguelikegame.model.EntitiesPackage.Enemy;
import com.grouptwelve.roguelikegame.model.Weapons.CombatManager;
import com.grouptwelve.roguelikegame.view.GameView;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Coordinates the game loop, connecting user input to model updates and view rendering.
 */
public class GameController {
    private final Game game;
    private final GameView gameView;
    private final InputHandler inputHandler;
    private AnimationTimer gameLoop;
    private long lastUpdate;
    double deltaTime;
    private boolean paused = false;
    private boolean death = false;
    private boolean levelUp = false;
    private boolean levelUp2 = false;
    
    public GameController(Game game, GameView gameView, InputHandler inputHandler) {
        this.game = game;
        this.gameView = gameView;
        this.inputHandler = inputHandler;
        inputHandler.setGameController(this);
        this.lastUpdate = 0;

        // Give CombatManager a reference to this controller
        CombatManager.getInstance().setGameController(this);
        CombatManager.getInstance().setPlayer(game.getPlayer());
        
    }
    
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
                deltaTime = (now - lastUpdate) / 1_000_000_000.0;
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
        // Handle input
        int[] dir = inputHandler.getMovementDirection();
        game.movePlayer(dir[0], dir[1], deltaTime);
        
        // Update game logic
        game.update(deltaTime);
        
        // Update UI displays
        gameView.updateDirectionLabel(dir[0], dir[1]);
        gameView.updateGameTimeLabel(game.getGameTime());
        updateStatusDisplay();
    }

    public void onKeyPress(KeyCode key)
    {
        switch (key)
        {
            case K:
                if (!paused) {
                    System.out.println("k");
                    game.playerAttack();
                    gameView.showAttack(game.getPlayer().getAttackPointX(), game.getPlayer().getAttackPointY(), game.getPlayer().getWeapon().getRange(), 0.1);
                }
                break;
            case ESCAPE:
                if (!death) {
                    System.out.println("esc");
                    togglePause();
                    break;
                }
            case DIGIT1: 
                gameView.highlightItem(1);
                break;
            case DIGIT2:
                gameView.highlightItem(2);
                break;
            case DIGIT3:
                gameView.highlightItem(3);
                break;
        }
    }
    /**
     * Renders the current game state.
     */
    private void render() {
        gameView.render(game, deltaTime);
    }
    
    /**
     * Updates the status label based on active keys.
     */
    private void updateStatusDisplay() {
        List<String> activeKeys = new ArrayList<>();
        if (inputHandler.isMoveUp()) activeKeys.add("UP");
        if (inputHandler.isMoveDown()) activeKeys.add("DOWN");
        if (inputHandler.isMoveLeft()) activeKeys.add("LEFT");
        if (inputHandler.isMoveRight()) activeKeys.add("RIGHT");
        
        if (activeKeys.isEmpty()) {
            gameView.updateStatusLabel("No keys pressed");
        } else {
            gameView.updateStatusLabel("Active: " + String.join(", ", activeKeys));
        }
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

    public void togglePause() {
        paused = !paused;

        if (paused) {
            stop();                // stop the game loop
            gameView.showPauseMenu(true);
        } else {
            lastUpdate = 0;        // prevents deltaTime spike
            start();               // resume game loop
            gameView.showPauseMenu(false);
        }
    }

    public void triggerLevelUp() {
        levelUp = !levelUp;

        if (levelUp) {
            stop();                
            gameView.showLevelMenu(true);
        } else {
            lastUpdate = 0;        
            start();               
            gameView.showLevelMenu(false);
        }
    }

    // Design 2
    public void triggerLevelUp2() {
        levelUp2 = !levelUp2;

        if (levelUp2) {
            stop();                
            gameView.showLevelMenu2(true);
        } else {
            lastUpdate = 0;        
            start();               
            gameView.showLevelMenu2(false);
        }
    }

    public void triggerDeath() {
        death = !death;

        if (death) {
            stop();                
            gameView.showDeathMenu(true);
        } else {
            lastUpdate = 0;        
            start();               
            gameView.showDeathMenu(false);
        }
    }

    public void spawnEnemy() {
        Set<String> entityNames = EntityFactory.getInstance().getRegisteredEntityNames();

        // Filter only enemies (optional: if you also registered Player in factory)
        List<String> enemyTypes = entityNames.stream()
                                         .filter(name -> !name.equals("Player"))
                                         .toList();

        if (enemyTypes.isEmpty()) return;

        String type = enemyTypes.get((int) (Math.random() * enemyTypes.size()));

        double x = Math.random() * 800; // random spawn position
        double y = Math.random() * 600;

        Entity entity = EntityFactory.getInstance().createEntity(type, x, y);

        if (entity instanceof Enemy enemy) {
            game.getEnemies().add(enemy);
            CombatManager.getInstance().addEnemy(enemy);
        } 
    }

    public void removeEnemy() {
        // Remove from CombatManager
        for (Enemy enemy : game.getEnemies()) {
            CombatManager.getInstance().removeEnemy(enemy);
        }
        // Clear the list in the Game model
        game.getEnemies().clear();
    }

    public void takeDamage(Entity entity, double damage) {
        if (!entity.getAliveStatus()) return; // already dead

        // Apply damage
        entity.takeDamage(damage);

        // Update health bar
        gameView.updateHealthBar(entity.getHp(), entity.getMaxHp(), entity);

        // If the entity is the player, trigger death menu
        if (entity instanceof Player && !entity.getAliveStatus()) {
            triggerDeath();
        }
        // Player player = game.getPlayer();

        // if (!player.getAliveStatus()) return; // Ignore damage if dead already

        // // Apply damage
        // player.takeDamage(damage);

        // // Update health bar
        // gameView.updateHealthBar(player.getHp(), player.getMaxHp());

        // // Check alive status
        // if (!player.getAliveStatus()) {
        //     triggerDeath();
        // }
    }

    public void resume() {
        if (paused) {
            paused = false;
            lastUpdate = 0;        
            start();               
            gameView.showPauseMenu(false);
        }
    }

    public void back() throws IOException {
        Stage stage = (Stage) gameView.getRoot().getScene().getWindow();

        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("/com/grouptwelve/roguelikegame/menu-view.fxml"));
        Scene menuScene = new Scene(menuLoader.load(), 800, 600);

        // Attach global CSS
        menuScene.getStylesheets().add(getClass().getResource("/com/grouptwelve/roguelikegame/global.css").toExternalForm());

        stage.setScene(menuScene);
        stage.show();
    }

    public void playAgain() throws IOException {
        Stage stage = (Stage) gameView.getRoot().getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/grouptwelve/roguelikegame/game-view.fxml"));
        Parent root = loader.load();  // FXML creates the GameView instance

        GameView gameView = loader.getController();
        Game game = new Game();
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
        Stage stage = (Stage) gameView.getRoot().getScene().getWindow();

        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("/com/grouptwelve/roguelikegame/menu-view.fxml"));
        Scene menuScene = new Scene(menuLoader.load(), 800, 600);

        // Attach global CSS
        menuScene.getStylesheets().add(getClass().getResource("/com/grouptwelve/roguelikegame/global.css").toExternalForm());

        stage.setScene(menuScene);
        stage.show();
    }

    public void upgrade1() {
        if (levelUp || levelUp2) {
            levelUp = false;
            levelUp2 = false;
            lastUpdate = 0;        
            start();               
            gameView.showLevelMenu(false);
            gameView.showLevelMenu2(false);
        }
    }

    public void upgrade2() {
        if (levelUp || levelUp2) {
            levelUp = false;
            levelUp2 = false;
            lastUpdate = 0;        
            start();               
            gameView.showLevelMenu(false);
            gameView.showLevelMenu2(false);
        }
    }

    public void upgrade3() {
        if (levelUp || levelUp2) {
            levelUp = false;
            levelUp2 = false;
            lastUpdate = 0;        
            start();               
            gameView.showLevelMenu(false);
            gameView.showLevelMenu2(false);
        }
    }

}
