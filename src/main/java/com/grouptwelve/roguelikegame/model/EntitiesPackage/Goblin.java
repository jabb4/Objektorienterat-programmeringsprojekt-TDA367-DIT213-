package com.grouptwelve.roguelikegame.model.EntitiesPackage;

import com.grouptwelve.roguelikegame.model.EntitiesPackage.Enemy;
import com.grouptwelve.roguelikegame.model.Weapons.Sword;

public class Goblin extends Enemy {
    public Goblin(double x, double y){
        super("Goblin", x, y, 30, 5, 30);
        this.speed = 70;
        this.weapon = new Sword();
    }
}
