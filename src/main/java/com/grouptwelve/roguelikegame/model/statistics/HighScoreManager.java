package com.grouptwelve.roguelikegame.model.statistics;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Manages high scores using Java Properties file that is stored in the data directory.
 * Responsible for loading, saving, and comparing scores.
 */
public class HighScoreManager {
    
    private static final String DATA_DIR = "data";
    private static final String FILE_NAME = "highscore.properties";
    
    private static final String KEY_TIME = "timeSurvived";
    private static final String KEY_LEVEL = "levelReached";
    private static final String KEY_DAMAGE = "totalDamageDealt";
    private static final String KEY_DAMAGE_TAKEN = "totalDamageTaken";
    private static final String KEY_KILLS = "enemiesKilled";
    private static final String KEY_SCORE = "score";
    
    private HighScore currentBest;
    
    /**
     * Creates a new HighScoreManager and loads any existing high score from the directory.
     */
    public HighScoreManager() {
        this.currentBest = load();
    }
    
    /**
     * Gets the current best high score.
     *
     * Controller should call in MenuController.initialize() to display best score on main menu.
     * View should use the returned HighScore to show a best score label.
     * 
     * @return The current best HighScore, or null if no high score exists
     */
    public HighScore getCurrentBest() {
        return currentBest;
    }
    
    /**
     * Saves the statistics as the new high score if it beats the current best.
     *
     * Controller should call in GameController.onEntityDeath() after game.finalizeStatistics() to save the score and determine if it's a new best.
     * View should use the returned boolean to show/hide a "NEW BEST!" indicator.
     *
     * @param stats The game statistics to potentially save
     * @return true if this was a new best score and was saved, false otherwise
     */
    public boolean saveIfBest(GameStatistics stats) {
        int newScore = stats.calculateScore();
        if (currentBest == null || newScore > currentBest.score()) {
            currentBest = HighScore.fromStatistics(stats);
            save(currentBest);
            return true;
        }
        return false;
    }
    
    /**
     * Loads the high score from the properties file.
     * 
     * @return The loaded HighScore, or null if no file exists or loading fails
     */
    private HighScore load() {
        Path filePath = Paths.get(DATA_DIR, FILE_NAME);
        
        if (!Files.exists(filePath)) {
            return null;
        }
        
        try (InputStream input = Files.newInputStream(filePath)) {
            Properties props = new Properties();
            props.load(input);
            
            return new HighScore(
                Double.parseDouble(props.getProperty(KEY_TIME, "0")),
                Integer.parseInt(props.getProperty(KEY_LEVEL, "1")),
                Double.parseDouble(props.getProperty(KEY_DAMAGE, "0")),
                Double.parseDouble(props.getProperty(KEY_DAMAGE_TAKEN, "0")),
                Integer.parseInt(props.getProperty(KEY_KILLS, "0")),
                Integer.parseInt(props.getProperty(KEY_SCORE, "0"))
            );
        } catch (IOException | NumberFormatException e) {
            System.err.println("Failed to load high score: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Saves the high score to the properties file.
     * Creates the data directory if it doesn't exist.
     * 
     * @param score The HighScore to save
     */
    private void save(HighScore score) {
        Path dirPath = Paths.get(DATA_DIR);
        Path filePath = dirPath.resolve(FILE_NAME);
        
        try {
            // Create data directory if it doesn't exist
            Files.createDirectories(dirPath);
            
            Properties props = new Properties();
            props.setProperty(KEY_TIME, String.valueOf(score.timeSurvived()));
            props.setProperty(KEY_LEVEL, String.valueOf(score.levelReached()));
            props.setProperty(KEY_DAMAGE, String.valueOf(score.totalDamageDealt()));
            props.setProperty(KEY_DAMAGE_TAKEN, String.valueOf(score.totalDamageTaken()));
            props.setProperty(KEY_KILLS, String.valueOf(score.enemiesKilled()));
            props.setProperty(KEY_SCORE, String.valueOf(score.score()));
            
            try (OutputStream output = Files.newOutputStream(filePath)) {
                props.store(output, "Inheritance of Violence - High Score Data");
            }
        } catch (IOException e) {
            System.err.println("Failed to save high score: " + e.getMessage());
        }
    }
}
