package com.grouptwelve.roguelikegame.model.entities.enemies;

public class Goblin extends Enemy{
    public Goblin(double x, double y){
        super("Goblin", x, y, 30, 30, 30, 10);
        this.speed = 3;
    }
}