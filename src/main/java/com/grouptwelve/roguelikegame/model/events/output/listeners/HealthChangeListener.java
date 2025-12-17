package com.grouptwelve.roguelikegame.model.events.output.listeners;

import com.grouptwelve.roguelikegame.model.events.output.events.HealthChangeEvent;

/**
 * Implement this to be notified when an entity's health changes.
 */
public interface HealthChangeListener {
    void onHealthChange(HealthChangeEvent event);
}
