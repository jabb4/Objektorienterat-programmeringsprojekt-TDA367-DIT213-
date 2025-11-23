package com.grouptwelve.roguelikegame.model.EventsPackage;

/**
 * Interface for objects that want to observe game events.
 * Different systems (Game logic, Audio, Animation, Particles, etc.)
 * can implement this to react to player actions.
 *
 * This follows the Observer pattern and enables loose coupling
 * between the input system and game systems.
 */
public interface GameEventListener {
    void onMovement(MovementEvent event);

    void onAttack(AttackEvent event);

    // TODO: Extend with more events
    // onPlayerLevelUp(LevelUpEvent event); - show upgrade screen
}
