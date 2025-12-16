package com.grouptwelve.roguelikegame.model.weapons;

import com.grouptwelve.roguelikegame.model.combat.CombatResult;
import com.grouptwelve.roguelikegame.model.effects.EffectInterface;

import java.util.List;

/**
 * Interface for weapon behavior.
 * Weapons handle damage calculation, cooldowns, and effects.
 * Combat resolution is handled by Entity via AttackHandler callback.
 */
public interface WeaponInterface {

    /**
     * Updates the weapon state (e.g., cooldown timer).
     *
     * @param deltaTime Time since last update in seconds
     */
    void update(double deltaTime);

    /**
     * Checks if the weapon is ready to attack.
     *
     * @return true if weapon can attack, false if on cooldown
     */
    boolean canAttack();

    /**
     * Refreshes the cooldown timer after an attack.
     */
    void refreshCooldown();

    /**
     * Resets the weapon
     */
    void reset();

    /**
     * Calculates damage for an attack, including critical hit chance.
     *
     * @return CombatResult containing the final damage and whether it was a critical hit
     */
    CombatResult calculateDamage();

    double getDamage();

    double getRange();

    void addRange(double amount);

    List<EffectInterface> getEffects();

    void addEffect(EffectInterface effect);
}
