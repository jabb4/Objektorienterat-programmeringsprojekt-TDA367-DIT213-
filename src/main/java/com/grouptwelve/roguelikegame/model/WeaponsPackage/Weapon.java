package com.grouptwelve.roguelikegame.model.WeaponsPackage;

import com.grouptwelve.roguelikegame.model.EffectsPackage.EffectInterface;
import com.grouptwelve.roguelikegame.model.UpgradesPackage.UpgradeInterface;

import java.util.ArrayList;
import java.util.List;

public abstract class Weapon implements WeaponInterface {

    protected double damage;
    protected double range;
    protected double attackCooldown;
    protected double cooldownRemaining;
    protected List<EffectInterface> effects = new ArrayList<>();

    /**
     * Creates a weapon with specified damage, range, and attack cooldown.
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
    }

    /**
     * Updates the weapon's cooldown timer.
     *
     * @param deltaTime Time since last update in seconds
     */
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
    public boolean canAttack() {
        return cooldownRemaining <= 0;
    }

    /**
     * Resets the cooldown timer after an attack.
     */
    private void resetCooldown() {
        cooldownRemaining = attackCooldown;
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
        
        CombatManager.getInstance().attack(isPlayer, x, y, range, damage, effects);
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
