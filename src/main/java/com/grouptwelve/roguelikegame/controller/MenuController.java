package com.grouptwelve.roguelikegame.controller;

import java.io.IOException;

import com.grouptwelve.roguelikegame.model.Game;
import com.grouptwelve.roguelikegame.model.events.output.*;
import com.grouptwelve.roguelikegame.view.GameView;

import javafx.scene.control.Button;
import java.util.List;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MenuController implements InputEventListener {
    @FXML private Label actionLabel;
    @FXML private VBox root;
    
    private MenuNavigator menuNavigator;

    public void initialize(){
        // Extract available buttons from menu-view
        List<Button> menuButtons = root.getChildren().stream()
            .filter(node -> node instanceof Button)
            .map(node -> (Button) node)
            .toList();

        menuNavigator = new MenuNavigator(menuButtons);

        // Make sure scene exists and attach InputHandler
        Platform.runLater(() -> {
            Scene scene = root.getScene();
            if (scene != null) {
                InputHandler inputHandler = new InputHandler();
                inputHandler.setListener(this);  // MenuController will receive commands
                inputHandler.setupInputHandling(scene);

                root.requestFocus(); // Ensure scene receives key events
            }
        });
    }

    // ================= InputEventListener =================
    @Override
    public void onCommandPressed(Command command) {
        switch (command) {
            case MOVE_UP -> menuNavigator.moveUp();
            case MOVE_DOWN -> menuNavigator.moveDown();
            case SELECT -> menuNavigator.select();
        }
    }

    @Override
    public void onCommandReleased(Command command) {
        // Nothing needed for menu navigation
    }
    

    @FXML
    protected void onStartGamePressed() throws IOException {
        Stage stage = (Stage) root.getScene().getWindow();

        // Get the event manager (acts as event bus between model and controller)
        EventPublisher eventPublisher = new EventPublisher();

        LevelUpPublisher levelUpPublisher = (LevelUpPublisher) eventPublisher;
        EntityPublisher entityPublisher = (EntityPublisher) eventPublisher;
        ChooseBuffPublisher chooseBuffPublisher = (ChooseBuffPublisher) eventPublisher;

        //inputHandler.setListener(this);
        //eventPublisher.subscribeBuff(this);

        // Create game with event publisher
        Game game = new Game(entityPublisher,chooseBuffPublisher, levelUpPublisher);
        game.reset(); // To make sure that previous session doesnt effect new one

        // Load FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/grouptwelve/roguelikegame/game-view.fxml"));
        Parent root = loader.load();  // FXML creates the GameView instance

        // Create view and input handler
        GameView gameView = loader.getController();
        InputHandler inputHandler = new InputHandler();
        gameView.setGame(game);


        Scene gameScene = new Scene(root, 1280, 720);
        inputHandler.setupInputHandling(gameScene);
        
        // Apply font on scene
        gameScene.getStylesheets().add(getClass().getResource("/com/grouptwelve/roguelikegame/global.css").toExternalForm());
        
        GameController gameController = new GameController(game, gameView, inputHandler);
        inputHandler.setListener(gameController);
        System.out.println("created");
        gameController.addEventListener(game);

        entityPublisher.subscribeEntityDeath(gameController);
        chooseBuffPublisher.subscribeBuff(gameController);

        entityPublisher.subscribeEntityHit(gameView);
        entityPublisher.subscribeAttack(gameView);
        entityPublisher.subscribeEntityDeath(gameView);
        chooseBuffPublisher.subscribeBuff(gameView);

        gameView.setGameController(gameController); // Connect FXML components with GameController
        gameController.start();
        
        stage.setTitle("Inheritance of Violance");
        stage.setScene(gameScene);
        stage.show();
    }

    @FXML
    protected void onOptionsPressed() throws IOException {
        actionLabel.setText("Opening options...");

        Stage stage = (Stage) root.getScene().getWindow();

        FXMLLoader optionLoader = new FXMLLoader(getClass().getResource("/com/grouptwelve/roguelikegame/option-view.fxml"));
        Scene optionScene = new Scene(optionLoader.load(), 1280, 720);

        optionScene.getStylesheets().add(getClass().getResource("/com/grouptwelve/roguelikegame/global.css").toExternalForm());

        
        stage.setScene(optionScene);
        stage.show();
    }

    @FXML
    protected void onExitPressed() {
        System.exit(0);
    }
}