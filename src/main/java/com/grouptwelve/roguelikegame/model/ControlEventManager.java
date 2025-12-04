package com.grouptwelve.roguelikegame.model;

import com.grouptwelve.roguelikegame.model.events.GameEventPublisher;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages game event publishing from the model layer.
 * Acts as an event bus that forwards events to all subscribers.
 * 
 * Implements GameEventPublisher so it can be injected into model classes
 * that need to publish events without knowing about subscribers.
 */
public class ControlEventManager implements GameEventPublisher {
    
    private static ControlEventManager instance;
    private final List<GameEventPublisher> listeners;

    private ControlEventManager() {
        listeners = new ArrayList<>();
    }

    public static ControlEventManager getInstance() {
        if (instance == null) {
            instance = new ControlEventManager();
        }
        return instance;
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

    /**
     * @deprecated Use onAttackVisual instead
     */
    @Deprecated
    public void drawAttack(double x, double y, double size) {
        onAttackVisual(x, y, size);
    }

    /**
     * @deprecated Use onPlayerDeath instead
     */
    @Deprecated
    public void playerDied(double x, double y) {
        onPlayerDeath(x, y);
    }

    /**
     * @deprecated Use onEnemyHit with isCritical=false instead
     */
    @Deprecated
    public void onEnemyHit(double x, double y, double damage) {
        onEnemyHit(x, y, damage, false);
    }

    /**
     * @deprecated Use onEnemyHit with isCritical=true instead
     */
    @Deprecated
    public void onEnemyCritHit(double x, double y, double damage) {
        onEnemyHit(x, y, damage, true);
    }

    /**
     * @deprecated Use onEnemyDeath instead
     */
    @Deprecated
    public void enemyDied(double x, double y, int xp) {
        onEnemyDeath(x, y, xp);
    }
}
