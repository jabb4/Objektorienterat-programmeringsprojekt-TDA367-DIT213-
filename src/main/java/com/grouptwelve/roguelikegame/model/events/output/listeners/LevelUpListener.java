package com.grouptwelve.roguelikegame.model.events.output.listeners;

/**
 * Implement this to be notified when the player gains a level.
 */
public interface LevelUpListener {
    /**
     * Called when the player levels up.
     */
    void onLevelUp();
}
