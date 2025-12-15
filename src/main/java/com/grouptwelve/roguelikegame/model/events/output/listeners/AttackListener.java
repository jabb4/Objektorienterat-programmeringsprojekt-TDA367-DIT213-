package com.grouptwelve.roguelikegame.model.events.output.listeners;

import com.grouptwelve.roguelikegame.model.events.output.events.AttackEvent;

/**
 * Implement this to be notified when any entity performs an attack in the game.
 */
public interface AttackListener {
    /**
     * Called when an attack occurs.
     *
     * @param attackEvent contains details about the attack including attacker, position, range, and effects
     */
    void onAttack(AttackEvent attackEvent);
}
