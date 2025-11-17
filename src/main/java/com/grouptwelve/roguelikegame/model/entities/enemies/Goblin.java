package com.grouptwelve.roguelikegame.model.entities.enemies;

import javafx.scene.paint.Color;

public class Goblin extends Enemy{
    public Goblin(double x, double y){
        super("Goblin", x, y, 30, 30, 30, 10, Color.RED);
        this.speed = 3;
    }
}