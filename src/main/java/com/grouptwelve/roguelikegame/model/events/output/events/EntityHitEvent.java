package com.grouptwelve.roguelikegame.model.events.output.events;
import com.grouptwelve.roguelikegame.model.combat.CombatResult;
import com.grouptwelve.roguelikegame.model.entities.Obstacle;

/**
 * event class for storing hit event information
 */
public class EntityHitEvent {
    private Obstacle obstacle;
    private CombatResult combatResult;

    /**
     * construction that have all information as parameters
     * @param obstacle who was hit
     * @param combatResult damage info
     */
    public EntityHitEvent(Obstacle obstacle, CombatResult combatResult) {
        this.obstacle = obstacle;
        this.combatResult = combatResult;
    }

    public Obstacle getObstacle() {
        return obstacle;
    }
    public CombatResult getCombatResult() {
        return combatResult;
    }
}
