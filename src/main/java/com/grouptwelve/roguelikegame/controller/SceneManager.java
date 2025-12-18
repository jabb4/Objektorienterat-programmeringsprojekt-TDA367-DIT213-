package com.grouptwelve.roguelikegame.controller;

import java.io.IOException;

import com.grouptwelve.roguelikegame.model.Game;
import com.grouptwelve.roguelikegame.model.events.output.publishers.ChooseBuffPublisher;
import com.grouptwelve.roguelikegame.model.events.output.publishers.EntityPublisher;
import com.grouptwelve.roguelikegame.model.events.output.publishers.EventPublisher;
import com.grouptwelve.roguelikegame.model.events.output.publishers.LevelUpPublisher;
import com.grouptwelve.roguelikegame.model.events.output.publishers.XpPublisher;
import com.grouptwelve.roguelikegame.view.GameView;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
* Handles transition between scenes and resetting game state after each session
*/
public class SceneManager {
    private final Stage stage;
    private final InputHandler inputHandler;
    
    // Screen resolution
    private int x = 1280; 
    private int y = 720;

    public SceneManager(Stage stage) {
        this.stage = stage;
        this.inputHandler = new InputHandler();
    }
    
    /** Loads menu-view.fxml with MenuController*/
    public void showMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/grouptwelve/roguelikegame/menu-view.fxml"));
        Parent root = loader.load(); 
        MenuController controller = loader.getController();
        controller.setSceneManager(this);

        Scene scene = createScene(root); 

        inputHandler.setupInputHandling(scene); 
        inputHandler.setListener(controller);

        stage.setScene(scene);
        stage.show();
    }

    /** Loads game-view.fxml with GameController*/
    public void startGame() throws IOException {

        // ================= RESET =================
        EventPublisher eventPublisher = new EventPublisher();
        LevelUpPublisher levelUpPublisher = (LevelUpPublisher) eventPublisher;
        EntityPublisher entityPublisher = (EntityPublisher) eventPublisher;
        ChooseBuffPublisher chooseBuffPublisher = (ChooseBuffPublisher) eventPublisher;
        XpPublisher xpPublisher = (XpPublisher) eventPublisher;


        Game game = new Game(entityPublisher, chooseBuffPublisher, levelUpPublisher, xpPublisher);

        // ==========================================

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/grouptwelve/roguelikegame/game-view.fxml"));
        Parent root = loader.load(); 
        
        GameView gameView = loader.getController();
        
        Scene scene = createScene(root);
        inputHandler.setupInputHandling(scene); 
        
        GameController controller = new GameController(game, gameView, inputHandler, this);
        inputHandler.setListener(controller);
        controller.addEventListener(game);


        entityPublisher.subscribeEntityDeath(controller);
        chooseBuffPublisher.subscribeBuff(controller);

        entityPublisher.subscribeEntityHit(gameView);
        entityPublisher.subscribeAttack(gameView);
        entityPublisher.subscribeHealthChange(gameView);
        entityPublisher.subscribeEntityDeath(gameView);
        chooseBuffPublisher.subscribeBuff(gameView);
        xpPublisher.subscribeXp(gameView);

        gameView.setButtonListener(controller);
        controller.start();

        stage.setScene(scene);
        stage.show();
    }

    // ==================== Helper functions ====================
    /** Applies font on each scene
     * @param root contains scene root
     */
    public Scene createScene(Parent root) {
        Scene scene = new Scene(root, x, y);
        scene.getStylesheets().add(getClass().getResource("/com/grouptwelve/roguelikegame/global.css").toExternalForm());
        return scene;
    }
    
}   
