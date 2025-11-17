package com.grouptwelve.roguelikegame;

import com.grouptwelve.roguelikegame.model.entities.Player;
import com.grouptwelve.roguelikegame.model.entities.enemies.Enemy;
import com.grouptwelve.roguelikegame.model.entities.enemies.Troll;
import com.grouptwelve.roguelikegame.model.entities.enemies.Goblin;


import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameController {
    @FXML private StackPane root;
    @FXML private Label actionLabel;
    @FXML private AnchorPane gameLayer;
    @FXML private VBox pauseMenu;
    @FXML private VBox deathMenu;
    @FXML private Canvas gameCanvas;
    @FXML private Rectangle hpFill;
    @FXML private Label levelLabel;
    @FXML private ImageView firstItemImage;
    @FXML private VBox levelUpMenu;

    private final double maxHpWidth = 200;
    private int level = 1;

    private GaussianBlur blur = new GaussianBlur(0);
    private Random random = new Random();
    Renderer renderer = new Renderer();
    GraphicsContext gc;

    private Player player;
    private List<Enemy> enemies = new ArrayList<>();
    private final List<Class<? extends Enemy>> enemyTypes = List.of(Troll.class, Goblin.class);

    public void initialize() {
        gc = gameCanvas.getGraphicsContext2D(); // init gameCanvas
        gameLayer.setEffect(blur); // init blur effect on canvas + HUD

        // TEMPORARY IMAGE ON SLOT 1
        Image sword = new Image(getClass().getResourceAsStream("/com/grouptwelve/roguelikegame/img/sword1.png"));
        firstItemImage.setImage(sword);

        // Init Player
        player = new Player(0, 0);

        // Player postition on map - center of canvas
        double centerX = (gameCanvas.getWidth() - player.getSize()) / 2;
        double centerY = (gameCanvas.getHeight() - player.getSize()) / 2;

        player.setX(centerX);
        player.setY(centerY);

        // Call draw to draw player and enemies
        draw();

        // Pause toggled to ESCAPE 
        InputManager.get().bind(KeyCode.ESCAPE, this::togglePause);
        gameLayer.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                InputManager.get().attach(newScene);
            }
        });

    }

    private void togglePause() {
        if (deathMenu.isVisible()) return;
            boolean isPaused = pauseMenu.isVisible();
            pauseMenu.setVisible(!isPaused);
            blur.setRadius(!isPaused ? 10 : 0); // if true -> 10, false -> 0
    }

    @FXML
    private void onResume() {
        pauseMenu.setVisible(false);
        blur.setRadius(0);
    }

    @FXML
    private void onQuit() throws IOException {
        Stage stage = (Stage) root.getScene().getWindow();

        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("menu-view.fxml"));
        Scene menuScene = new Scene(menuLoader.load(), 800, 600);
        
        stage.setScene(menuScene);
        stage.show();
    }

     @FXML
    private void onPlayAgain() {
        // Reset UI
        hpFill.setWidth(maxHpWidth);
        deathMenu.setVisible(false);
        blur.setRadius(0);
        level = 1;
        levelLabel.setText("LVL: " + level);

        // Reset player position
        player.setX((gameCanvas.getWidth() - player.getSize()) / 2);
        player.setY((gameCanvas.getHeight() - player.getSize()) / 2);

        // Reset enemies
        enemies.clear(); 

        // Redraw
        draw();
    }

    private void draw() {
        gc.clearRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
        renderer.draw(gc, player);

        for (Enemy enemy : enemies) {
            renderer.draw(gc, enemy);
        }
    }

    @FXML
    protected void onSpawnEnemy() {
        try {
            // Pick a random enemy class
            int index = random.nextInt(enemyTypes.size());
            Class<? extends Enemy> enemyClass = enemyTypes.get(index);

            // Enemy's position on map
            double x = random.nextDouble() * gameCanvas.getWidth();
            double y = random.nextDouble() * gameCanvas.getHeight();

            // TEMPORARY SOLUTION - MIGHT CHANGE STRUCTOR FOR ENEMIES
            Enemy enemy = enemyClass.getDeclaredConstructor(double.class, double.class).newInstance(x, y);
            enemies.add(enemy);
            draw();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onRemoveEnemy() {
        enemies.clear();
        draw();
    }

    @FXML
    protected void onTakeDamage() {
        double currentWidth = hpFill.getWidth();
        double newWidth = currentWidth - (maxHpWidth * 0.2);

        if (newWidth <= 0) {
            newWidth = 0;
            deathMenu.setVisible(true);
            blur.setRadius(10);
        }
        hpFill.setWidth(newWidth);
    }

    @FXML
    protected void onLevelUp() {
        level++;
        levelLabel.setText("LVL: " + level);
        levelUpMenu.setVisible(true);
    }

    @FXML
    protected void onDie() {
        deathMenu.setVisible(true);
        blur.setRadius(10);
    }


    @FXML
    protected void onBack() throws IOException {
        Stage stage = (Stage) root.getScene().getWindow();

        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("menu-view.fxml"));
        Scene menuScene = new Scene(menuLoader.load(), 800, 600);
        
        stage.setScene(menuScene);
        stage.show();
    }

    @FXML
    protected void onSelectUpgrade1() {
        levelUpMenu.setVisible(false);
    }

    @FXML
    protected void onSelectUpgrade2() {
        levelUpMenu.setVisible(false);
    }

    @FXML
    protected void onSelectUpgrade3() {
        levelUpMenu.setVisible(false);
    }
}
