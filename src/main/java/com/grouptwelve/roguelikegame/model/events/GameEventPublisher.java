package com.grouptwelve.roguelikegame.model.events;

/**
 * Interface for publishing game events that external systems (view, audio, etc.) can listen to.
 * This interface is owned by the model layer, ensuring proper dependency direction.
 * 
 * Implementations of this interface receive notifications about game events
 * and can react accordingly (e.g., trigger visual effects, play sounds).
 */
public interface GameEventPublisher {
    
    /**
     * Called when an attack visual should be displayed.
     * @param x X coordinate of the attack center
     * @param y Y coordinate of the attack center
     * @param size Range/size of the attack
     */
    void onAttackVisual(double x, double y, double size);
    
    /**
     * Called when the player dies.
     * @param x X coordinate of death location
     * @param y Y coordinate of death location
     */
    void onPlayerDeath(double x, double y);
    
    /**
     * Called when an enemy is hit.
     * @param x X coordinate of the hit
     * @param y Y coordinate of the hit
     * @param damage Amount of damage dealt
     * @param isCritical Whether this was a critical hit
     */
    void onEnemyHit(double x, double y, double damage, boolean isCritical);
    
    /**
     * Called when an enemy dies.
     * @param x X coordinate of death location
     * @param y Y coordinate of death location
     * @param xpValue XP reward from the enemy
     */
    void onEnemyDeath(double x, double y, int xpValue);
}
