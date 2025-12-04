package com.grouptwelve.roguelikegame.model.WeaponsPackage;

import com.grouptwelve.roguelikegame.model.EffectsPackage.EffectInterface;
import com.grouptwelve.roguelikegame.model.EntitiesPackage.Enemies.EnemyPool;
import com.grouptwelve.roguelikegame.model.EntitiesPackage.Enemy;
import com.grouptwelve.roguelikegame.model.EntitiesPackage.Player;
import com.grouptwelve.roguelikegame.model.events.GameEventPublisher;
import com.grouptwelve.roguelikegame.model.systems.CollisionSystem;
import com.grouptwelve.roguelikegame.model.systems.DamageSystem;

import java.util.List;
import java.util.function.Supplier;

/**
 * Orchestrates combat between entities.
 * Delegates to focused systems for specific responsibilities:
 * - CollisionSystem: Hit detection
 * - DamageSystem: Damage and effect application
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
     */
    public void attack(boolean isFriendly, double x, double y, double range, CombatResult combatResult, List<EffectInterface> effects) {
        if (isFriendly) {
            attackEnemies(x, y, range, combatResult, effects);
        } else {
            attackPlayer(x, y, range, combatResult.getDamage(), effects);
        }
    }

    /**
     * Handles player attacking enemies.
     */
    private void attackEnemies(double x, double y, double range, CombatResult combatResult, List<EffectInterface> effects) {
        double damage = combatResult.getDamage();
        boolean isCritical = combatResult.isCritical();
        List<Enemy> enemies = enemiesSupplier.get();
        
        // Find all enemies in range
        List<Enemy> hitEnemies = CollisionSystem.getEntitiesInRange(x, y, range, enemies);
        
        for (Enemy enemy : hitEnemies) {
            // Apply damage
            boolean died = DamageSystem.applyDamage(enemy, damage);
            
            // Publish hit event
            publishEnemyHit(enemy, damage, isCritical);
            
            if (died) {
                handleEnemyDeath(enemy, enemies);
            } else {
                // Apply effects to living enemies
                DamageSystem.applyEffects(enemy, effects, x, y);
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
    private void attackPlayer(double x, double y, double range, double damage, List<EffectInterface> effects) {
        if (CollisionSystem.isHit(x, y, range, player.getX(), player.getY(), player.getSize())) {
            boolean died = DamageSystem.applyDamage(player, damage);
            
            if (died && eventPublisher != null) {
                eventPublisher.onPlayerDeath(player.getX(), player.getY());
            }
            
            // Apply effects
            DamageSystem.applyEffects(player, effects);
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
