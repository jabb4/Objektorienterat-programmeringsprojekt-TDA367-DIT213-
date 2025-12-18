package com.grouptwelve.roguelikegame.model.statistics;

/**
 * Tracks statistics for a single game run.
 */
public class GameStatistics {
    
    // Scoring weights
    private static final double TIME_WEIGHT = 2.0;
    private static final double LEVEL_WEIGHT = 100.0;
    private static final double KILLS_WEIGHT = 10.0;
    private static final double DAMAGE_WEIGHT = 0.1;
    
    private double timeSurvived;
    private int levelReached;
    private double totalDamageDealt;
    private double totalDamageTaken;
    private int enemiesKilled;

    /**
     * Creates a new GameStatistics instance with default values.
     */
    public GameStatistics() {
        reset();
    }

    /**
     * Resets all statistics to their initial values.
     */
    public void reset() {
        timeSurvived = 0;
        levelReached = 1;
        totalDamageDealt = 0;
        totalDamageTaken = 0;
        enemiesKilled = 0;
    }

    /**
     * Adds damage to the total damage dealt.
     * 
     * @param damage The amount of damage to add
     */
    public void addDamage(double damage) {
        totalDamageDealt += damage;
    }

    /**
     * Adds damage to the total damage taken.
     * 
     * @param damage The amount of damage taken
     */
    public void addDamageTaken(double damage) {
        totalDamageTaken += damage;
    }

    /**
     * Increments the enemy kill counter by one.
     */
    public void incrementKills() {
        enemiesKilled++;
    }

    /**
     * Sets the total time survived.
     * 
     * @param time The time in seconds
     */
    public void setTimeSurvived(double time) {
        this.timeSurvived = time;
    }

    /**
     * Sets the level reached by the player.
     * 
     * @param level The player's level
     */
    public void setLevelReached(int level) {
        this.levelReached = level;
    }

    /**
     * Calculates the total score based on a scoring formula.
     *
     * @return The calculated score as an integer
     */
    public int calculateScore() {
        return (int) ((timeSurvived * TIME_WEIGHT) + (levelReached * LEVEL_WEIGHT) + (enemiesKilled * KILLS_WEIGHT) + (totalDamageDealt * DAMAGE_WEIGHT));
    }

    // ==================== Getters ====================

    public double getTimeSurvived() {
        return timeSurvived;
    }

    public int getLevelReached() {
        return levelReached;
    }

    public double getTotalDamageDealt() {
        return totalDamageDealt;
    }

    public int getEnemiesKilled() {
        return enemiesKilled;
    }

    public double getTotalDamageTaken() {
        return totalDamageTaken;
    }
}
