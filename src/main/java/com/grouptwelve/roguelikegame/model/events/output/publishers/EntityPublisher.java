package com.grouptwelve.roguelikegame.model.events.output.publishers;

import com.grouptwelve.roguelikegame.model.combat.CombatResult;
import com.grouptwelve.roguelikegame.model.entities.Entity;
import com.grouptwelve.roguelikegame.model.events.output.events.AttackEvent;
import com.grouptwelve.roguelikegame.model.events.output.events.EntityDeathEvent;
import com.grouptwelve.roguelikegame.model.events.output.events.EntityHitEvent;
import com.grouptwelve.roguelikegame.model.events.output.events.HealthChangeEvent;
import com.grouptwelve.roguelikegame.model.events.output.listeners.AttackListener;
import com.grouptwelve.roguelikegame.model.events.output.listeners.EntityDeathListener;
import com.grouptwelve.roguelikegame.model.events.output.listeners.EntityHitListener;
import com.grouptwelve.roguelikegame.model.events.output.listeners.HealthChangeListener;

/**
 * publisher interface that EventPublisher implements,
 * contains methods and that entities use to publish events
 * sub and unsubscribers for listeners to use
 */
public interface EntityPublisher
{
    void subscribeAttack(AttackListener attackListener);
    void unsubscribeAttack(AttackListener attackListener);
    /**
     * called by entities(player, enemy) sends information encapsulated in an attackEvent
     * @param attackEvent contains information such as position of attack and radius
     */
    void onAttack(AttackEvent attackEvent);

    void subscribeEntityDeath(EntityDeathListener entityDeathListener);
    void unsubscribeEntityDeath(EntityDeathListener entityDeathListener);
    void onEntityDeath(EntityDeathEvent event);

    void subscribeEntityHit(EntityHitListener entityHitListener);
    void unsubscribeEntityHit(EntityHitListener entityHitListener);

    /**
     * called when en enemy is hit
     * @param entityHitEvent contains who got hit and damage info
     */
    void onEntityHit(EntityHitEvent entityHitEvent);

    void subscribeHealthChange(HealthChangeListener healthChangeListener);
    void unsubscribeHealthChange(HealthChangeListener healthChangeListener);
    /**
     * called when an entity's health changes
     * @param healthChangeEvent contains entity and current hp/maxHp
     */
    void onHealthChange(HealthChangeEvent healthChangeEvent);

}
