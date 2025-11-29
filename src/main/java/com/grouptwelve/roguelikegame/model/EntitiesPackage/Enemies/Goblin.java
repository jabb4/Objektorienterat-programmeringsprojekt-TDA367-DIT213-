package com.grouptwelve.roguelikegame.model.EntitiesPackage.Enemies;

import com.grouptwelve.roguelikegame.model.EntitiesPackage.Entities;
import com.grouptwelve.roguelikegame.model.EntitiesPackage.Enemy;
import com.grouptwelve.roguelikegame.model.EntitiesPackage.EntityFactory;
import com.grouptwelve.roguelikegame.model.WeaponsPackage.Sword;

public class Goblin extends Enemy {
    public Goblin(double x, double y){
        super("Goblin", Entities.GOBLIN, x, y, 30, 5, 30);
        this.velocity.setMaxSpeed(70);
        this.weapon = new Sword();
        this.attackRange = this.weapon.getRange();
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
