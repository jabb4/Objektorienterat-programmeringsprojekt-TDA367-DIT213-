package com.grouptwelve.roguelikegame.model.EntitiesPackage;

import com.grouptwelve.roguelikegame.model.Weapons.Sword;

public class Player extends Entity {

    public Player(double x, double y) {
        super("Player", x, y, 100, 10, 100);
        this.velocity.setMaxSpeed(100);
        this.weapon = new Sword();
    }

    static {
        EntityFactory.getInstance().registerEntity("Player", new Player(0,0));
    }

    @Override
    public Player createEntity(double x, double y) {
        return new Player(x, y);
    }
}

