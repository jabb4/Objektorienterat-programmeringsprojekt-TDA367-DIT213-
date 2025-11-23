package com.grouptwelve.roguelikegame.model.EntitiesPackage;

import com.grouptwelve.roguelikegame.model.Weapons.Sword;

public class Player extends Entity {

    public Player(double x, double y) {
        super("Player", x, y, 100, 10, 100);
        this.velocity.setMaxSpeed(100);
        this.weapon = new Sword();
    }
    /*@Override
    public void attack(EntitiesPackage.Entity target) {
        System.out.println(name + " attacks " + target.getName() + " for " + attackDmg + " damage!");
        target.takeDamage(attackDmg);
    }*/
    static {
        EntityFactory.getInstance().registerEntity("Player", new Player(0,0));
    }

    @Override
    public Player createEntity(double x, double y) {
        return new Player(x, y);
    }
}

