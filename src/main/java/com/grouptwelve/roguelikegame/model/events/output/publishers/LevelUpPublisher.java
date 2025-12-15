package com.grouptwelve.roguelikegame.model.events.output.publishers;

import com.grouptwelve.roguelikegame.model.events.output.listeners.LevelUpListener;

/**
 * publisher interface that EventPublisher implements,
 * sub and unsubscribers for listeners to use
 * used for publishing level up event
 */
public interface LevelUpPublisher {
    void subscribeLevelUp(LevelUpListener levelUpListener);
    void unsubscribeLevelUp(LevelUpListener levelUpListener);

    /**
     * called by player when leveling up
     */
    void onLevelUp();
}
