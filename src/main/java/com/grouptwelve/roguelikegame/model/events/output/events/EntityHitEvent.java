package com.grouptwelve.roguelikegame.model.events.output.events;
import com.grouptwelve.roguelikegame.model.combat.CombatResult;
import com.grouptwelve.roguelikegame.model.entities.Entity;

/**
 * event class for storing hit event information
 */
public class EntityHitEvent {
    private Entity entity;
    private CombatResult combatResult;

    /**
     * construction that have all information as parameters
     * @param entity who was hit
     * @param combatResult damage info
     */
    public EntityHitEvent(Entity entity, CombatResult combatResult)
    {
        this.entity = entity;
        this.combatResult = combatResult;
    }

    public Entity getEntity() {
        return entity;
    }
    public CombatResult getCombatResult()
    {
        return combatResult;
    }
}
