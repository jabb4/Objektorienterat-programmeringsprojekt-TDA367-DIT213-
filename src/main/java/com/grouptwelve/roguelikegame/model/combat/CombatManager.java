package com.grouptwelve.roguelikegame.model.combat;

import com.grouptwelve.roguelikegame.model.effects.EffectInterface;
import com.grouptwelve.roguelikegame.model.entities.Player;
import com.grouptwelve.roguelikegame.model.entities.enemies.Enemy;
import com.grouptwelve.roguelikegame.model.entities.enemies.EnemyPool;
import com.grouptwelve.roguelikegame.model.events.output.GameEventPublisher;

import java.util.List;
import java.util.function.Supplier;

/**
 * Orchestrates combat between entities.
 * Delegates to focused systems for specific responsibilities:
 * - CollisionSystem: Hit detection
 * - HitSystem: Damage and effect application
 * 
 * This class handles combat flow and game-level concerns like XP and death.
 */
public class CombatManager {
    
    private final Player player;
    private final Supplier<List<Enemy>> enemiesSupplier;
    private final GameEventPublisher eventPublisher;

    /**
     * Creates a CombatManager with explicit dependencies.
     *
     * @param player The player entity
     * @param enemiesSupplier Supplier that provides the current list of enemies
     * @param eventPublisher Publisher for combat events (can be null for no events)
     */
    public CombatManager(Player player, Supplier<List<Enemy>> enemiesSupplier, GameEventPublisher eventPublisher) {
        this.player = player;
        this.enemiesSupplier = enemiesSupplier;
        this.eventPublisher = eventPublisher;
    }

    /**
     * Performs an attack from a position, hitting either enemies or the player.
     *
     * @param isFriendly true if the attacker is friendly (player), false for enemies
     * @param x x-coordinate of the attack
     * @param y y-coordinate of the attack
     * @param range range of the attack
     * @param combatResult the damage calculation result (includes crit info)
     * @param effects list of effects to apply on hit
     * @param knockbackStrength the attacker's knockback strength
     */
    public void attack(boolean isFriendly, double x, double y, double range, CombatResult combatResult, List<EffectInterface> effects, double knockbackStrength) {
        if (isFriendly) {
            attackEnemies(x, y, range, combatResult, effects, knockbackStrength);
        } else {
            attackPlayer(x, y, range, combatResult.getDamage(), effects, knockbackStrength);
        }
    }

    /**
     * Handles player attacking enemies.
     */
    private void attackEnemies(double x, double y, double range, CombatResult combatResult, List<EffectInterface> effects, double knockbackStrength) {
        double damage = combatResult.getDamage();
        boolean isCritical = combatResult.isCritical();
        List<Enemy> enemies = enemiesSupplier.get();
        
        // Find all enemies in range
        List<Enemy> hitEnemies = CollisionSystem.getEntitiesInRange(x, y, range, enemies);
        
        for (Enemy enemy : hitEnemies) {
            // Apply damage
            boolean died = HitSystem.applyDamage(enemy, damage);
            
            // Publish hit event
            publishEnemyHit(enemy, damage, isCritical);
            
            if (died) {
                handleEnemyDeath(enemy, enemies);
            } else {
                // Apply effects to living enemies
                HitSystem.applyEffects(enemy, effects, x, y, knockbackStrength);
            }
        }
    }

    /**
     * Handles enemy death: grants XP, publishes event, returns to pool.
     */
    private void handleEnemyDeath(Enemy enemy, List<Enemy> enemies) {
        player.gainXP(enemy.getXpValue());
        
        if (eventPublisher != null) {
            eventPublisher.onEnemyDeath(enemy.getX(), enemy.getY(), enemy.getXpValue());
        }

        EnemyPool.getInstance().returnEnemy(enemy);
        enemies.remove(enemy);
    }

    /**
     * Handles enemies attacking player.
     */
    private void attackPlayer(double x, double y, double range, double damage, List<EffectInterface> effects, double knockbackStrength) {
        if (CollisionSystem.isHit(x, y, range, player.getX(), player.getY(), player.getSize())) {
            boolean died = HitSystem.applyDamage(player, damage);
            
            if (died && eventPublisher != null) {
                eventPublisher.onPlayerDeath(player.getX(), player.getY());
            }
            
            // Apply effects
            HitSystem.applyEffects(player, effects, x, y, knockbackStrength);
        }
    }

    /**
     * Publishes enemy hit event if publisher is available.
     */
    private void publishEnemyHit(Enemy enemy, double damage, boolean isCritical) {
        if (eventPublisher != null) {
            eventPublisher.onEnemyHit(enemy.getX(), enemy.getY(), damage, isCritical);
        }
    }
}
