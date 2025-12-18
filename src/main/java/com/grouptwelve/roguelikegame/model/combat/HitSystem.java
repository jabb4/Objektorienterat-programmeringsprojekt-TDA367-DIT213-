package com.grouptwelve.roguelikegame.model.combat;

import com.grouptwelve.roguelikegame.model.effects.EffectInterface;
import com.grouptwelve.roguelikegame.model.effects.KnockbackEffect;
import com.grouptwelve.roguelikegame.model.entities.Entity;
import com.grouptwelve.roguelikegame.model.entities.Player;

import java.util.List;

/**
 * System responsible for applying damage and effects to entities.
 * Separates damage logic from combat orchestration.
 */
public class HitSystem {

    /**
     * Applies damage to an entity.
     *
     * @param target The entity to damage
     * @param combatResult contains amount of damage to apply
     */
    public static void applyDamage(Entity target, CombatResult combatResult)
    {
        target.takeDamage(combatResult);
    }

    /**
     * Applies effects to a target entity.
     * Handles special cases like knockback direction calculation.
     *
     * @param target The entity to apply effects to
     * @param effects List of effects to apply
     * @param attackerX X coordinate of the attacker (for knockback direction)
     * @param attackerY Y coordinate of the attacker (for knockback direction)
     * @param knockbackStrength The attacker's knockback strength
     */
    public static void applyEffects(Entity target, List<EffectInterface> effects, double attackerX, double attackerY, double knockbackStrength) {
        // Calculate knockback direction if needed
        double[] direction = CollisionSystem.calculateDirection(attackerX, attackerY, target.getX(), target.getY());

        for (EffectInterface effect : effects) {
            if (effect instanceof KnockbackEffect knockback) {
                if (target instanceof Player) {
                    continue;  // Player is immune to knockback
                }
                knockback.setDirection(direction[0], direction[1]);
                knockback.setStrength(knockbackStrength);
            }
            effect.apply(target);
        }
    }
}
