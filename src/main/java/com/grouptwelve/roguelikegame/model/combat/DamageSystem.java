package com.grouptwelve.roguelikegame.model.combat;

import com.grouptwelve.roguelikegame.model.effects.EffectInterface;
import com.grouptwelve.roguelikegame.model.effects.KnockbackEffect;
import com.grouptwelve.roguelikegame.model.entities.Entity;

import java.util.List;

/**
 * System responsible for applying damage and effects to entities.
 * Separates damage logic from combat orchestration.
 */
public class DamageSystem {

    /**
     * Applies damage to an entity.
     *
     * @param target The entity to damage
     * @param damage Amount of damage to apply
     * @return true if the entity died from this damage
     */
    public static boolean applyDamage(Entity target, double damage) {
        boolean wasAlive = target.getAliveStatus();
        target.takeDamage(damage);
        return wasAlive && !target.getAliveStatus();
    }

    /**
     * Applies effects to a target entity.
     * Handles special cases like knockback direction calculation.
     *
     * @param target The entity to apply effects to
     * @param effects List of effects to apply
     * @param attackX X coordinate of the attack (for knockback direction)
     * @param attackY Y coordinate of the attack (for knockback direction)
     */
    public static void applyEffects(Entity target, List<EffectInterface> effects,
                                    double attackX, double attackY) {
        // Calculate knockback direction if needed
        double[] direction = CollisionSystem.calculateDirection(
            attackX, attackY, target.getX(), target.getY()
        );

        for (EffectInterface effect : effects) {
            if (effect instanceof KnockbackEffect) {
                ((KnockbackEffect) effect).setDirection(direction[0], direction[1]);
            }
            effect.apply(target);
        }
    }

    /**
     * Applies effects without knockback direction calculation.
     *
     * @param target The entity to apply effects to
     * @param effects List of effects to apply
     */
    public static void applyEffects(Entity target, List<EffectInterface> effects) {
        for (EffectInterface effect : effects) {
            effect.apply(target);
        }
    }
}
