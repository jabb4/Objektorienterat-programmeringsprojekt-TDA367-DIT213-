package com.grouptwelve.roguelikegame.model.events.output.events;
import com.grouptwelve.roguelikegame.model.combat.CombatResult;
import com.grouptwelve.roguelikegame.model.entities.Obstacle;

/**
 * event class for storing hit event information
 */
public class EntityHitEvent {
    private Obstacle obstacle;
    private CombatResult combatResult;
    private double hp;
    private double maxHp;
    /**
     * construction that have all information as parameters
     * @param obstacle who was hit
     * @param combatResult damage info
     */
    public EntityHitEvent(Obstacle obstacle, CombatResult combatResult, double hp, double mapHp) {
        this.obstacle = obstacle;
        this.combatResult = combatResult;
        this.hp = hp;
        this.maxHp = mapHp;
    }

    public Obstacle getObstacle() {
        return obstacle;
    }
    public CombatResult getCombatResult() {
        return combatResult;
    }

    public double getHp() {
        return hp;
    }

    public double getMaxHp() {
        return maxHp;
    }
}
