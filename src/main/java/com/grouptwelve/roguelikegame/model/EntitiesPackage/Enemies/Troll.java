package com.grouptwelve.roguelikegame.model.EntitiesPackage.Enemies;

import com.grouptwelve.roguelikegame.model.EntitiesPackage.Enemy;
import com.grouptwelve.roguelikegame.model.Weapons.Club;

public class Troll extends Enemy {
    public Troll(double x, double y){
        super("Troll", x, y, 70, 15, 70);
        this.speed = 0;
        this.weapon = new Club();
    }

    static {
        EntityFactory.getInstance().reigisterEnitity("Troll", new Troll(0,0));
    }

    @Override
    public Entity createEntity(double x, double y) {
        return new Troll(x, y);
    }
}
