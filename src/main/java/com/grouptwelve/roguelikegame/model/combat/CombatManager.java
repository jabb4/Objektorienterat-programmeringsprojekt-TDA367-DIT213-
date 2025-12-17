package com.grouptwelve.roguelikegame.model.combat;

import com.grouptwelve.roguelikegame.model.effects.EffectInterface;
import com.grouptwelve.roguelikegame.model.entities.Player;
import com.grouptwelve.roguelikegame.model.entities.enemies.Enemy;
import com.grouptwelve.roguelikegame.model.events.output.events.AttackEvent;
import com.grouptwelve.roguelikegame.model.events.output.listeners.AttackListener;

import java.util.List;
import java.util.function.Supplier;

/**
 * Handles combat between entities.
 * - CollisionSystem: Hit detection
 * - HitSystem: Damage and effect application
 */
public class CombatManager implements AttackListener {
    private final Player player;
    private final Supplier<List<Enemy>> enemiesSupplier;

    /**
     * Creates a CombatManager with explicit dependencies.
     *
     * @param player The player entity
     * @param enemiesSupplier Supplier that provides the current list of enemies

     */
    public CombatManager(Player player, Supplier<List<Enemy>> enemiesSupplier) {
        this.player = player;
        this.enemiesSupplier = enemiesSupplier;
    }

    /**
     * Performs an attack from a position, hitting either enemies or the player.
     * @param attackEvent contains attack information
     */
    @Override
    public void onAttack(AttackEvent attackEvent) {
        if (attackEvent.getAttacker() instanceof Player) {
            attackEnemies(attackEvent.getX(), attackEvent.getY(), attackEvent.getRange(), attackEvent.getCombatResult(), attackEvent.getEffects(), attackEvent.getKnockbackStrength());
        } else {
            attackPlayer(attackEvent.getX(), attackEvent.getY(), attackEvent.getRange(), attackEvent.getCombatResult(), attackEvent.getEffects(), attackEvent.getKnockbackStrength());
        }
    }

    /**
     * Handles player attacking enemies.
     */
    private void attackEnemies(double x, double y, double range, CombatResult combatResult, List<EffectInterface> effects, double knockbackStrength) {
        List<Enemy> enemies = enemiesSupplier.get();
        List<Enemy> hitEnemies = CollisionSystem.getEntitiesInRange(x, y, range, enemies);
        
        for (Enemy enemy : hitEnemies) {
            HitSystem.applyEffects(enemy, effects, x, y, knockbackStrength);
            HitSystem.applyDamage(enemy, combatResult);
        }
    }

    /**
     * Handles enemies attacking player.
     */
    private void attackPlayer(double x, double y, double range,  CombatResult combatResult, List<EffectInterface> effects, double knockbackStrength) {
        if (CollisionSystem.isHit(x, y, range, player.getX(), player.getY(), player.getSize())) {
            HitSystem.applyDamage(player,combatResult );
            HitSystem.applyEffects(player, effects, x, y, knockbackStrength);
        }
    }
}
