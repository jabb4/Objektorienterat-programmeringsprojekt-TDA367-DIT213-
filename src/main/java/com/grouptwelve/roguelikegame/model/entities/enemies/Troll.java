package com.grouptwelve.roguelikegame.model.entities.enemies;

<<<<<<< HEAD
import javafx.scene.paint.Color;

public class Troll extends Enemy{
    public Troll(double x, double y){
        super("Troll", x, y, 70, 50, 70, 30, Color.RED);
        this.speed = 1;
=======
import com.grouptwelve.roguelikegame.model.entities.Entities;
import com.grouptwelve.roguelikegame.model.entities.EntityFactory;
import com.grouptwelve.roguelikegame.model.weapons.Club;

public class Troll extends Enemy {
    private static final double TROLL_WIND_UP_TIME = 0.5;

    public Troll(double x, double y){
        super("Troll", Entities.TROLL, x, y, 70, 15, 70);
        this.velocity.setMaxSpeed(50);
        this.weapon = new Club();
        this.attackRange = this.weapon.getRange();
        this.windUpTime = TROLL_WIND_UP_TIME;
        this.xpValue = 50;
    }

    static {
        EntityFactory.getInstance().registerEntity(Entities.TROLL, new Troll(0,0));
    }

    @Override
    public Troll createEntity(double x, double y) {
        return new Troll(x, y);
>>>>>>> 3e28f0b68b466e2b8b11b7c8469cc8e5680e4fef
    }
}
