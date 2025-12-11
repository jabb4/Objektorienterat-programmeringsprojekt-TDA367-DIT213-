package com.grouptwelve.roguelikegame.model.combat;

/**
 * Encapsulates the result of a damage calculation.
 * Used to communicate damage amount and critical hit status between
 * the weapon and combat manager.
 */
public class CombatResult {
    private final double damage;
    private final boolean isCritical;

    /**
     * Creates a new combat result.
     *
     * @param damage The calculated damage amount
     * @param isCritical Whether this was a critical hit
     */
    public CombatResult(double damage, boolean isCritical) {
        this.damage = damage;
        this.isCritical = isCritical;
    }

    /**
     * Gets the damage amount.
     *
     * @return The damage value
     */
    public double getDamage() {
        return damage;
    }

    /**
     * Checks if this was a critical hit.
     *
     * @return true if critical hit, false otherwise
     */
    public boolean isCritical() {
        return isCritical;
    }

    @Override
    public String toString() {
        return "CombatResult{damage=" + damage + ", isCritical=" + isCritical + "}";
    }
}
