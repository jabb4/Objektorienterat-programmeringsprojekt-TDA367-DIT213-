package com.grouptwelve.roguelikegame.model.EventsPackage;

/**
 * Interface for objects that want to observe game events.
 * Different systems (Game logic, Audio, Animation, etc.) can implement this to react to player actions.
 */
public interface GameEventListener {
    void onMovement(MovementEvent event);
    void onAttack(AttackEvent event);

    // TODO: Extend with more events
    // onEnemyDeath(EnemyDeathEvent event);
    // onPlayerDeath(PlayerDeathEvent event);
    // onPlayerDamaged(PlayerDamagedEvent event);
    // onLevelUp(LevelUpEvent event);
}
