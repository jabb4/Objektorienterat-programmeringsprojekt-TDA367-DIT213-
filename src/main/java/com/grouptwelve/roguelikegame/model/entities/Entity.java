package com.grouptwelve.roguelikegame.model.entities;

import javafx.scene.paint.Color;

public abstract class Entity {
    protected String name;
    protected double x, y;
    protected double hp;
    protected double speed;
    protected int size;
    protected double maxHP;
    protected double attackMultiplier;
    public Color color;
    // speed???
    // boolean isAlive???

    public Entity(String name, double x, double y, double hp, int size, double maxHP, double attackMultiplier, Color color){
        this.name = name;
        this.x = x;
        this.y = y;
        this.hp = hp;
        this.size = size;
        this.maxHP = maxHP;
        this.attackMultiplier = attackMultiplier;
        this.color = color;
    }

    public double getHp() {
        return hp;
    }

    public double getMaxHp() {
        return maxHP;
    }

    public double getSpeed() {
        return speed;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getSize() {
        return size;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    protected void setHp(double hp){
        this.hp = hp;
    }

    protected void increaseHp(double hp) {
        if((this.hp + hp) >= maxHP){
            this.hp = maxHP;
        }
        else{
            this.hp += hp;
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void move(int dx, int dy){
        this.x = x + (dx*speed);
        this.y = y + (dy*speed);
    }

    public void takeDamage(double dmg){
        hp -= dmg;
    }
    public void attack(Entity target){
        target.takeDamage(attackMultiplier);
    }

    @Override
    public String toString() {
        return "Entities{" +
                "name=" + name +
                ", x=" + x +
                ", y=" + y +
                ", hp=" + hp +
                ", speed=" + speed +
                ", size=" + size +
                '}';
    }

    public Color getColor() {
        return color;
    }
}
