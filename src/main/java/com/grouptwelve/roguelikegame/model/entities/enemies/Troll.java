package com.grouptwelve.roguelikegame.model.entities.enemies;

import javafx.scene.paint.Color;

public class Troll extends Enemy{
    public Troll(double x, double y){
        super("Troll", x, y, 70, 50, 70, 30, Color.RED);
        this.speed = 1;
    }
}
