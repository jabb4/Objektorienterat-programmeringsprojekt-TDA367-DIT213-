package com.grouptwelve.roguelikegame.view.ui;

import com.grouptwelve.roguelikegame.model.statistics.GameStatistics;
import javafx.scene.control.Label;

/**
 * The statistics display on the death screen.
 * Shows detailed run statistics and indicates if a new high score was achieved.
 */
public class StatisticsDisplay {
    private final Label timeLabel;
    private final Label levelLabel;
    private final Label killsLabel;
    private final Label damageLabel;
    private final Label damageTakenLabel;
    private final Label scoreLabel;
    private final Label newBestLabel;

    public StatisticsDisplay(Label timeLabel, Label levelLabel, Label killsLabel, Label damageLabel, Label damageTakenLabel, Label scoreLabel, Label newBestLabel) {
        this.timeLabel = timeLabel;
        this.levelLabel = levelLabel;
        this.killsLabel = killsLabel;
        this.damageLabel = damageLabel;
        this.damageTakenLabel = damageTakenLabel;
        this.scoreLabel = scoreLabel;
        this.newBestLabel = newBestLabel;
    }

    /**
     * Updates the statistics display with the final game statistics.
     *
     * @param stats The game statistics from the completed run
     * @param isNewBest True if this run achieved a new high score
     */
    public void updateStatistics(GameStatistics stats, boolean isNewBest) {
        timeLabel.setText("TIME: " + formatTime(stats.getTimeSurvived()));
        levelLabel.setText("LEVEL: " + stats.getLevelReached());
        killsLabel.setText("KILLS: " + stats.getEnemiesKilled());
        damageLabel.setText("DAMAGE: " + (int) stats.getTotalDamageDealt());
        damageTakenLabel.setText("DAMAGE TAKEN: " + (int) stats.getTotalDamageTaken());
        scoreLabel.setText("SCORE: " + stats.calculateScore());
        
        newBestLabel.setVisible(isNewBest);
    }

    /**
     * Formats time in seconds to better readable format.
     *
     * @param seconds Time in seconds
     * @return Formatted time string
     */
    private String formatTime(double seconds) {
        int minutes = (int) (seconds / 60);
        int secs = (int) (seconds % 60);
        return String.format("%d:%02d", minutes, secs);
    }
}
