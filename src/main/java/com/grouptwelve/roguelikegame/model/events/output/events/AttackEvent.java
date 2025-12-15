package com.grouptwelve.roguelikegame.model.events.output.events;

import com.grouptwelve.roguelikegame.model.combat.CombatResult;
import com.grouptwelve.roguelikegame.model.effects.EffectInterface;
import com.grouptwelve.roguelikegame.model.entities.Entity;

import java.util.List;

public class AttackEvent {
    private final Entity attacker;
    private final double x;
    private final double y;
    private final double range;
    private final CombatResult combatResult;
    private final List<EffectInterface> effects;
    private final double knockbackStrength;

    public AttackEvent(Entity attacker, double x, double y, double range, CombatResult combatResult, List<EffectInterface> effects, double knockbackStrength) {
        this.knockbackStrength = knockbackStrength;
        this.attacker = attacker;
        this.x = x;
        this.y = y;
        this.range = range;
        this.combatResult = combatResult;
        this.effects = effects;
    }

    public Entity getAttacker() {
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
