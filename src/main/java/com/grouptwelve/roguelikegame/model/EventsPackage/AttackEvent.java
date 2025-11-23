package com.grouptwelve.roguelikegame.model.EventsPackage;

/**
 * This event is fired when the player initiates an attack.
 * The actual attack logic is handled by CombatManager.
 *
 * This event is useful for:
 * - Audio system (play attack sound)
 * - Animation system (play attack animation)
 * - View (draw attack visual feedback)
 */
public class AttackEvent {
   private double attackX;
   private double attackY;
   private double range;

    /**
     * Creates a new attack event.
     *
     * @param attackX X coordinate of the attack point
     * @param attackY Y coordinate of the attack point
     * @param range Range/radius of the attack
     */
    public AttackEvent(double attackX, double attackY, double range) {
        this.attackX = attackX;
        this.attackY = attackY;
        this.range = range;
    }

    // ==================== Getters ====================

    public double getAttackX() {
        return attackX;
    }

    public double getAttackY() {
        return attackY;
    }

    public double getRange() {
        return range;
    }

    // TODO: Attack direction vector (for knockback feature)
}
