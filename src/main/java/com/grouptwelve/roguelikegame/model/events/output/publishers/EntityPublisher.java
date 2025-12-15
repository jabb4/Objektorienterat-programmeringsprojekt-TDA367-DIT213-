package com.grouptwelve.roguelikegame.model.events.output.publishers;
import com.grouptwelve.roguelikegame.model.combat.CombatResult;
import com.grouptwelve.roguelikegame.model.entities.Entity;
import com.grouptwelve.roguelikegame.model.events.output.events.AttackEvent;
import com.grouptwelve.roguelikegame.model.events.output.listeners.AttackListener;
import com.grouptwelve.roguelikegame.model.events.output.listeners.EntityDeathListener;
import com.grouptwelve.roguelikegame.model.events.output.listeners.EntityHitListener;

public interface EntityPublisher
{
    void subscribeAttack(AttackListener attackListener);
    void unsubscribeAttack(AttackListener attackListener);
    void onAttack(AttackEvent attackEvent);

    void subscribeEntityDeath(EntityDeathListener entityDeathListener);
    void unsubscribeEntityDeath(EntityDeathListener entityDeathListener);
    void onEntityDeath(Entity entity);

    void subscribeEntityHit(EntityHitListener entityHitListener);
    void unsubscribeEntityHit(EntityHitListener entityHitListener);
    void onEntityHit(Entity entity, CombatResult combatResult);

}
