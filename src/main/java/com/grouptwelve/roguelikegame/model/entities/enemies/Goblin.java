package com.grouptwelve.roguelikegame.model.entities.enemies;

import com.grouptwelve.roguelikegame.model.entities.Entities;
import com.grouptwelve.roguelikegame.model.entities.EntityFactory;
import com.grouptwelve.roguelikegame.model.weapons.Sword;

public class Goblin extends Enemy {
    private static final double GOBLIN_WIND_UP_TIME = 0.3;

    public Goblin(double x, double y){
        super("Goblin", Entities.GOBLIN, x, y, 50, 5, 30);
        this.velocity.setMaxSpeed(70);
        this.weapon = new Sword();
        this.windUpTime = GOBLIN_WIND_UP_TIME;
        this.xpValue = 20;
    }

    // Put the Goblin into the EntityFactory (this is run when the class is loaded into memory for the first time)
    static {
        EntityFactory.getInstance().registerEntity(Entities.GOBLIN, new Goblin(0,0));
    }

    @Override
    public Goblin createEntity(double x, double y) {
        return new Goblin(x, y);
    }
}
