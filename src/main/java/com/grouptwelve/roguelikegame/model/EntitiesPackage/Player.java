package com.grouptwelve.roguelikegame.model.EntitiesPackage;

import com.grouptwelve.roguelikegame.model.EntitiesPackage.Entities;
import com.grouptwelve.roguelikegame.model.Weapons.Sword;

public class Player extends Entities {

    public Player(double x, double y) {
        super("Player", x, y, 100, 10, 100);
        this.speed = 100; //Vet inte vilken speed är bra.
        this.weapon = new Sword();
    }

    /*@Override
    public void attack(EntitiesPackage.Entities target) {
        System.out.println(name + " attacks " + target.getName() + " for " + attackDmg + " damage!");
        target.takeDamage(attackDmg);
    }*/
}

