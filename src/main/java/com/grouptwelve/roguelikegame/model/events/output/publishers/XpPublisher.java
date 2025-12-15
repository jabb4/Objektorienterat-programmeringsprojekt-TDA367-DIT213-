package com.grouptwelve.roguelikegame.model.events.output.publishers;

import com.grouptwelve.roguelikegame.model.events.output.events.XpChangeEvent;
import com.grouptwelve.roguelikegame.model.events.output.listeners.XpListener;
/**
 * publisher interface that EventPublisher implements,
 * sub and unsubscribers for listeners to use
 * sends xp change events
 */
public interface XpPublisher {
    void subscribeXp(XpListener xpListener);
    void unsubscribeXp(XpListener xpListener);

    /**
     * called by game when an enemy has dies
     * @param xpChangeEvent contains xp values and level
     */
    void onUpdateXp(XpChangeEvent xpChangeEvent);
}
