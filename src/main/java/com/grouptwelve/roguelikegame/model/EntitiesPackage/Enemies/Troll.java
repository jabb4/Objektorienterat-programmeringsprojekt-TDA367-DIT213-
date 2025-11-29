package com.grouptwelve.roguelikegame.model.EntitiesPackage.Enemies;

import com.grouptwelve.roguelikegame.model.EntitiesPackage.Entities;
import com.grouptwelve.roguelikegame.model.EntitiesPackage.Enemy;
import com.grouptwelve.roguelikegame.model.EntitiesPackage.EntityFactory;
import com.grouptwelve.roguelikegame.model.WeaponsPackage.Club;

public class Troll extends Enemy {
    public Troll(double x, double y){
        super("Troll", Entities.TROLL, x, y, 70, 15, 70);
        this.velocity.setMaxSpeed(50);
        this.weapon = new Club();
    }

    static {
        EntityFactory.getInstance().registerEntity(Entities.TROLL, new Troll(0,0));
    }

    @Override
    public Troll createEntity(double x, double y) {
        return new Troll(x, y);
    }
}
