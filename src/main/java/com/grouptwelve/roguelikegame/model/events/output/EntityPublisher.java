package com.grouptwelve.roguelikegame.model.events.output;
import com.grouptwelve.roguelikegame.model.combat.CombatResult;
import com.grouptwelve.roguelikegame.model.entities.Entity;
import com.grouptwelve.roguelikegame.model.events.output.events.AttackEvent;
import com.grouptwelve.roguelikegame.model.events.output.listeners.AttackListener;
import com.grouptwelve.roguelikegame.model.events.output.listeners.EntityDeathListener;
import com.grouptwelve.roguelikegame.model.events.output.listeners.EntityHitListener;

public interface EntityPublisher
{
    public void subscribeAttack(AttackListener attackListener);
    public void unsubscribeAttack(AttackListener attackListener);
    public void onAttack(AttackEvent attackEvent);

    public void subscribeEntityDeath(EntityDeathListener entityDeathListener);
    public void unsubscribeEntityDeath(EntityDeathListener entityDeathListener);
    public void onEntityDeath(Entity entity);

    public void subscribeEntityHit(EntityHitListener entityHitListener);
    public void unsubscribeEntityHit(EntityHitListener entityHitListener);
    public void onEntityHit(Entity entity, CombatResult combatResult);

}
