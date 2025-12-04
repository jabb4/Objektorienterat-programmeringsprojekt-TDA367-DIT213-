package com.grouptwelve.roguelikegame.model.events.output;

import com.grouptwelve.roguelikegame.model.combat.CombatResult;
import com.grouptwelve.roguelikegame.model.effects.EffectInterface;
import com.grouptwelve.roguelikegame.model.entities.Entity;

import java.util.List;

/**
 * Listener interface for entity attack events.
 * 
 * Implemented by classes that need to respond when an entity attacks
 * (e.g., Game class handles combat resolution and visual effects).
 * 
 * This follows the Observer pattern - entities notify listeners when
 * they attack, without needing to know how combat is resolved.
 */
public interface AttackListener {
    
    /**
     * Called when an entity performs an attack.
     *
     * @param attacker The entity that performed the attack
     * @param x X coordinate of the attack point
     * @param y Y coordinate of the attack point
     * @param range Range of the attack
     * @param result The damage calculation result (includes crit info)
     * @param effects List of effects to apply on hit
     */
    void onEntityAttacked(Entity attacker, double x, double y, 
                          double range, CombatResult result, 
                          List<EffectInterface> effects);
}
