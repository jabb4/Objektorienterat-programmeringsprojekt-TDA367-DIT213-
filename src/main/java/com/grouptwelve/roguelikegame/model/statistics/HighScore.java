package com.grouptwelve.roguelikegame.model.statistics;

/**
 * Record representing a completed game run's final statistics.
 * Used for storing and comparing high scores.
 * 
 * @param timeSurvived The time survived in seconds
 * @param levelReached The level reached by the player
 * @param totalDamageDealt The total damage dealt during the run
 * @param totalDamageTaken The total damage taken during the run
 * @param enemiesKilled The number of enemies killed
 * @param score The calculated final score
 */
public record HighScore(double timeSurvived, int levelReached, double totalDamageDealt, double totalDamageTaken, int enemiesKilled, int score) {

    /**
     * Creates a HighScore from GameStatistics.
     * Converting run statistics to a high score record.
     * 
     * @param stats The game statistics to convert
     * @return A new HighScore instance with the statistics values
     */
    public static HighScore fromStatistics(GameStatistics stats) {
        return new HighScore(stats.getTimeSurvived(), stats.getLevelReached(), stats.getTotalDamageDealt(), stats.getTotalDamageTaken(), stats.getEnemiesKilled(), stats.calculateScore());
    }
}
