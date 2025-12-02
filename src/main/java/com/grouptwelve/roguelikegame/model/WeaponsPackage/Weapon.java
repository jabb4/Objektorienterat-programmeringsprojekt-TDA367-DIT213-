package com.grouptwelve.roguelikegame.model.WeaponsPackage;

import com.grouptwelve.roguelikegame.model.EffectsPackage.EffectInterface;
import com.grouptwelve.roguelikegame.model.UpgradesPackage.UpgradeInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Weapon implements WeaponInterface {

    private static final Random random = new Random();

    protected double damage;
    protected double range;
    protected double attackCooldown;
    protected double cooldownRemaining;
    protected double critChance;
    protected double critMultiplier;
    protected List<EffectInterface> effects = new ArrayList<>();

    /**
     * Creates a weapon with specified damage, range, and attack cooldown.
     * Default critical hit chance is 5% with 2x damage multiplier.
     *
     * @param damage Base damage of the weapon
     * @param range Attack range in pixels
     * @param attackCooldown Time in seconds between attacks
     */
    public Weapon(double damage, double range, double attackCooldown) {
        this.damage = damage;
        this.range = range;
        this.attackCooldown = attackCooldown;
        this.cooldownRemaining = 0;
        this.critChance = 0.05;    // 5% default crit chance
        this.critMultiplier = 2.0; // 2x damage on crit
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
    public CombatResult calculateDamage() {
        boolean isCritical = random.nextDouble() < critChance;
        double finalDamage = isCritical ? damage * critMultiplier : damage;
        return new CombatResult(finalDamage, isCritical);
    }

    /**
     * Attempts to perform an attack. Only succeeds if the weapon is off cooldown.
     *
     * @param isPlayer true if the attacker is the player, false for enemies
     * @param x x-coordinate of the attack point
     * @param y y-coordinate of the attack point
     * @return true if the attack was performed, false if on cooldown
     */
    @Override
    public boolean attack(boolean isPlayer, double x, double y)
    {
        if (!canAttack()) {
            return false;
        }
        
        CombatResult result = calculateDamage();
        CombatManager.getInstance().attack(isPlayer, x, y, range, result, effects);
        resetCooldown();
        return true;
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

    @Override
    public List<EffectInterface> getEffects() { return effects; }

    @Override
    public void addEffect(EffectInterface effect) {
        effects.add(effect);
    }

    @Override
    public void upgrade(UpgradeInterface upgrade) {
        upgrade.applyTo(this);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [DMG=" + damage + ", RANGE=" + range + "]";
    }

}
