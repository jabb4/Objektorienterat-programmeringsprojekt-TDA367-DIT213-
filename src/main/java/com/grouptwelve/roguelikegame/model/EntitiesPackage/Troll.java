package com.grouptwelve.roguelikegame.model.EntitiesPackage;

import com.grouptwelve.roguelikegame.model.EntitiesPackage.Enemy;
import com.grouptwelve.roguelikegame.model.Weapons.Club;

public class Troll extends Enemy {
    public Troll(double x, double y){
        super("Troll", x, y, 70, 15, 70);
        this.speed = 1;
        this.weapon = new Club();
    }
}
