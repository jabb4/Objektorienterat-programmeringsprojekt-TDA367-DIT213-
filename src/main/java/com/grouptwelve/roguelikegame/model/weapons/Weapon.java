package com.grouptwelve.roguelikegame.model.weapons;

import com.grouptwelve.roguelikegame.model.combat.CombatResult;
import com.grouptwelve.roguelikegame.model.effects.EffectInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Weapon implements WeaponInterface {

    private static final Random random = new Random();

    // Default weapon stats
    private static final double DEFAULT_CRIT_CHANCE = 0.05;      // 5%
    private static final double DEFAULT_CRIT_MULTIPLIER = 2.0;   // 2x damage

    protected double damage;
    protected double range;
    protected double attackCooldown;
    protected double cooldownRemaining = 0;
    protected double critChance = DEFAULT_CRIT_CHANCE;
    protected double critMultiplier = DEFAULT_CRIT_MULTIPLIER;
    protected double knockbackStrength;


    protected List<EffectInterface> effects = new ArrayList<>();

    /**
     * Creates a weapon with specified damage, range, attack cooldown, and knockback.
     * Default critical hit chance is 5% with 2x damage multiplier.
     *
     * @param damage Base damage of the weapon
     * @param range Attack range in pixels
     * @param attackCooldown Time in seconds between attacks
     * @param knockbackStrength Knockback force applied to enemies
     */
    public Weapon(double damage, double range, double attackCooldown, double knockbackStrength) {
        this.damage = damage;
        this.range = range;
        this.attackCooldown = attackCooldown;
        this.knockbackStrength = knockbackStrength;
    }

    public Weapon(Weapon weapon) {
        this.damage = weapon.damage;
        this.range = weapon.range;
        this.attackCooldown = weapon.attackCooldown;
        this.knockbackStrength = weapon.knockbackStrength;
    }

    /**
     * Updates the weapon's cooldown timer.
     *
     * @param deltaTime Time since last update in seconds
     */
    @Override
    public void update(double deltaTime) {
        if (cooldownRemaining > 0) {
            cooldownRemaining -= deltaTime;
        }
    }

    /**
     * @return copied weapon.
     */
    public abstract Weapon copy();

    /**
     * Checks if the weapon is ready to attack.
     *
     * @return true if cooldown has elapsed, false otherwise
     */
    @Override
    public boolean canAttack() {
        return cooldownRemaining <= 0;
    }

    /**
     * Resets the cooldown timer after an attack.
     */
    public void resetCooldown() {
        cooldownRemaining = attackCooldown;
    }

    /**
     * Calculates damage for an attack, including critical hit chance.
     * Rolls for critical hit based on critChance and applies critMultiplier if successful.
     *
     * @return CombatResult containing the final damage and whether it was a critical hit
     */
    @Override
    public CombatResult calculateDamage() {
        boolean isCritical = random.nextDouble() < critChance;
        double finalDamage = isCritical ? damage * critMultiplier : damage;
        return new CombatResult(finalDamage, isCritical);
    }

    @Override
    public double getDamage() { return damage; }

    public void addDamage(double amount) {
        this.damage += amount;
    }

    @Override
    public double getRange() { return range; }

    public void addRange(double amount) {
        this.range += amount;
    }

    /**
     * Gets the attack cooldown duration.
     *
     * @return Cooldown duration in seconds
     */
    public double getAttackCooldown() {
        return attackCooldown;
    }

    /**
     * Gets the critical hit chance.
     *
     * @return Critical hit chance (0.0 to 1.0)
     */
    public double getCritChance() {
        return critChance;
    }

    /**
     * Adds to the critical hit chance.
     * Capped at 1.0 (100%).
     *
     * @param amount Amount to add to crit chance
     */
    public void addCritChance(double amount) {
        this.critChance = Math.min(1.0, this.critChance + amount);
    }

    /**
     * Gets the critical hit damage multiplier.
     *
     * @return Critical hit multiplier
     */
    public double getCritMultiplier() {
        return critMultiplier;
    }

    /**
     * Adds to the critical hit damage multiplier.
     *
     * @param amount Amount to add to crit multiplier
     */
    public void addCritMultiplier(double amount) {
        this.critMultiplier += amount;
    }

    public double getKnockbackStrength() {
        return knockbackStrength;
    }

    public void addKnockback(double amount) {
        this.knockbackStrength += amount;
    }

    public void multiplyKnockback(double percent) {
        this.knockbackStrength *= (1 + percent);
    }

    @Override
    public List<EffectInterface> getEffects() { return effects; }

    @Override
    public void addEffect(EffectInterface effect) {
        effects.add(effect);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [DMG=" + damage + ", RANGE=" + range + "]";
    }

}
