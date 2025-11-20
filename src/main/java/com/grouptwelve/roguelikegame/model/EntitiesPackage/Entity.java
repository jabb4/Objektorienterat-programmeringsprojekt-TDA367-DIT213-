package com.grouptwelve.roguelikegame.model.EntitiesPackage;

import com.grouptwelve.roguelikegame.model.Weapons.Weapon;


public abstract class Entity {
    protected String name;
    protected double x, y;
    protected double hp;
    protected double speed;
    protected int size;
    protected double maxHP;
    //protected double attackDmg;
    protected Weapon weapon;
    protected boolean isAlive;
    protected double dirX;
    protected double dirY;

    public Entity(String name, double x, double y, double hp, int size, double maxHP /*double attackDmg*/){
        this.name = name;
        this.x = x;
        this.y = y;
        this.hp = hp;
        this.size = size;
        this.maxHP = maxHP;
        this.isAlive = true;
        //this.attackDmg = attackDmg;
    }

    public double getHp() {
        return hp;
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

    public void setHp(double hp) {
        this.hp = hp;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void move(double dx, double dy, double deltaTime){
        if(dx != 0 || dy != 0 )
        {
            dirX = dx;
            dirY = dy;

        }

        // Normalize diagonal movement to maintain consistent speed
        if (dx != 0 && dy != 0) {
            double length = Math.sqrt(dx * dx + dy * dy);
            this.x += (dx / length) * speed * deltaTime;
            this.y += (dy / length) * speed * deltaTime;
        } else {
            this.x += dx * speed * deltaTime;
            this.y += dy * speed * deltaTime;
        }
    }

    public void takeDamage(double dmg)
    {
        hp -= dmg;
        if(hp < 0)
        {
            isAlive = false;
        }
    }
    /*public void attack(EntitiesPackage.Entity target){
        target.takeDamage(attackDmg);
    }*/
    public boolean getAliveStatus()
    {
        return isAlive;
    }
    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    //fix later
    public Weapon getWeapon() {
        return weapon;
    }

    public void attack() {

        if (weapon == null) return;
        System.out.println(dirX + " " + dirY);
        weapon.attack(this instanceof Player,getAttackPointX() , getAttackPointY());

    }
    public double getAttackPointX()
    {
        return x + dirX * 20;
    }
    public double getAttackPointY()
    {
        return y + dirY* 20;
    }


    @Override
    public String toString() {
        return "Entity{" +
                "name=" + name +
                ", x=" + x +
                ", y=" + y +
                ", hp=" + hp +
                ", speed=" + speed +
                ", size=" + size +
                ", weapon=" + weapon +
                '}';
    }

    public abstract Entity createEntity(double x, double y);
}

