package com.grouptwelve.roguelikegame.model.events.output.events;

import com.grouptwelve.roguelikegame.model.combat.CombatResult;
import com.grouptwelve.roguelikegame.model.effects.EffectInterface;
import com.grouptwelve.roguelikegame.model.entities.Entity;
import com.grouptwelve.roguelikegame.model.entities.Obstacle;

import java.util.List;

/**
 * Event data from an attack.
 */
public class AttackEvent {
    private final Obstacle attacker;
    private final double x;
    private final double y;
    private final double range;
    private final CombatResult combatResult;
    private final List<EffectInterface> effects;
    private final double knockbackStrength;

    /**
     * Creates a new attack event.
     *
     * @param attacker the entity that performed the attack, is obstacle to hide unnecessary information
     * @param x the x-coordinate of the attack
     * @param y the y-coordinate of the attack
     * @param range the range of the attack
     * @param combatResult the damage and critical hit info
     * @param effects the list of effects applied by this attack
     * @param knockbackStrength the knockback strength of the knockback
     */
    public AttackEvent(Obstacle attacker, double x, double y, double range, CombatResult combatResult, List<EffectInterface> effects, double knockbackStrength) {
        this.knockbackStrength = knockbackStrength;
        this.attacker = attacker;
        this.x = x;
        this.y = y;
        this.range = range;
        this.combatResult = combatResult;
        this.effects = effects;
    }

    public Obstacle getAttacker() {
        return attacker;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getRange() {
        return range;
    }

    public CombatResult getCombatResult() {
        return combatResult;
    }

    public List<EffectInterface> getEffects() {
        return effects;
    }

    public double getKnockbackStrength()
    {
        return knockbackStrength;
    }
}
