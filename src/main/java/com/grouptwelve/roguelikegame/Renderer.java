package com.grouptwelve.roguelikegame;

import com.grouptwelve.roguelikegame.model.entities.Entity;
import javafx.scene.canvas.GraphicsContext;

public class Renderer {
    public void draw(GraphicsContext gc, Entity e) {
        gc.setFill(e.getColor());
        gc.fillOval(e.getX(), e.getY(), e.getSize(), e.getSize());
    }
}
