package com.grouptwelve.roguelikegame.model.entities.enemies;

public class Troll extends Enemy{
    public Troll(double x, double y){
        super("Troll", x, y, 70, 15, 70, 30);
        this.speed = 1;
    }
}
