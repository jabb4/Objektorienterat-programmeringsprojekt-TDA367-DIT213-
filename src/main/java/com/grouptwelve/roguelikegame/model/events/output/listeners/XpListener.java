package com.grouptwelve.roguelikegame.model.events.output.listeners;

import com.grouptwelve.roguelikegame.model.events.output.events.XpChangeEvent;

/**
 * Implement this to be notified when the player's XP is updated.
 */
public interface XpListener {
    /**
     * Called when the player's XP changes.
     *
     * @param xpChangeEvent contains the current XP, XP needed for next level, and current level
     */
    void onUpdateXP(XpChangeEvent xpChangeEvent);
}
