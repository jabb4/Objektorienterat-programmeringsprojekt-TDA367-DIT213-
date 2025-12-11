package com.grouptwelve.roguelikegame.model.events.output;

import com.grouptwelve.roguelikegame.model.upgrades.UpgradeInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages game event publishing from the model layer.
 * Acts as an event bus that forwards events to all subscribers.
 * 
 * Implements GameEventPublisher so it can be injected into model classes
 * that need to publish events without knowing about subscribers.
 */
public class EventPublisher implements GameEventPublisher {
    

    private final List<GameEventPublisher> listeners;

    public EventPublisher() {
        listeners = new ArrayList<>();
    }



    /**
     * Subscribe to receive game events.
     * @param listener The listener to add
     */
    public void subscribe(GameEventPublisher listener) {
        listeners.add(listener);
    }

    /**
     * Unsubscribe from game events.
     * @param listener The listener to remove
     */
    public void unsubscribe(GameEventPublisher listener) {
        listeners.remove(listener);
    }

    // ==================== GameEventPublisher Implementation ====================

    @Override
    public void onAttackVisual(double x, double y, double size) {
        for (GameEventPublisher listener : listeners) {
            listener.onAttackVisual(x, y, size);
        }
    }
    @Override
    public void onPlayerLevelUp(int level, UpgradeInterface[] upgrades)
    {
        for (GameEventPublisher listener : listeners) {
            listener.onPlayerLevelUp(level, upgrades);
        }
    }

    @Override
    public void onPlayerDeath(double x, double y) {
        for (GameEventPublisher listener : listeners) {
            listener.onPlayerDeath(x, y);
        }
    }

    @Override
    public void onEnemyHit(double x, double y, double damage, boolean isCritical) {
        for (GameEventPublisher listener : listeners) {
            listener.onEnemyHit(x, y, damage, isCritical);
        }
    }

    @Override
    public void onEnemyDeath(double x, double y, int xpValue) {
        for (GameEventPublisher listener : listeners) {
            listener.onEnemyDeath(x, y, xpValue);
        }
    }

    // ==================== Legacy Methods (for backward compatibility) ====================


}
