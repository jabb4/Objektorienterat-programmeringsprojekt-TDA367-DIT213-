package com.grouptwelve.roguelikegame.model.EntitiesPackage.Enemies;

import com.grouptwelve.roguelikegame.model.EntitiesPackage.Enemy;
import com.grouptwelve.roguelikegame.model.EntitiesPackage.Entity;
import com.grouptwelve.roguelikegame.model.EntitiesPackage.EntityFactory;
import com.grouptwelve.roguelikegame.model.Weapons.Sword;

public class Goblin extends Enemy {
    public Goblin(double x, double y){
        super("Goblin", x, y, 30, 5, 30);
        this.speed = 70;
        this.weapon = new Sword();
    }

    // Put the Goblin into the EntityFactory (this is run when the class is loaded into memory for the first time)
    static {
        EntityFactory.getInstance().reigisterEnitity("Goblin", new Goblin(0,0));
    }

    @Override
    public Entity createEntity(double x, double y) {
        return new Goblin(x, y);
    }
}
