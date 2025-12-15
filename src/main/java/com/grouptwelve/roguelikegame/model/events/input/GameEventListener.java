package com.grouptwelve.roguelikegame.model.events.input;

/**
 * Implement this to react to player actions like movement, attacks, and buff selection.
 */
public interface GameEventListener {
    /**
     * Called when the player's movement input changes.
     *
     * @param event contains the movement direction
     */
    void onMovement(MovementEvent event);

    /**
     * Called when the player attacks.
     */
    void onAttack();

    /**
     * Called when the player selects a buff.
     *
     * @param i the index of the selected buff in the upgrade array
     */
    void onApplyBuff(int i);
}
