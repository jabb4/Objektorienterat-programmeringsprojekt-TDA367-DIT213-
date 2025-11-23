package com.grouptwelve.roguelikegame.model.EntitiesPackage;

import com.grouptwelve.roguelikegame.model.DrawEventManager;
import com.grouptwelve.roguelikegame.model.Weapons.Weapon;


public abstract class Entity {
    protected String name;
    protected Entities type;
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



    public Entity(String name, Entities type, double x, double y, double hp, int size, double maxHP /*double attackDmg*/){
        this.name = name;
        this.type = type;
        this.x = x;
        this.y = y;
        this.hp = hp;
        this.size = size;
        this.maxHP = maxHP;
        this.isAlive = true;
        //this.attackDmg = attackDmg;
    }

    public double getHp() {
        return this.hp;
    }

    public double getSpeed() {
        return this.speed;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public int getSize() {
        return this.size;
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
        return this.name;
    }

    public Entities getType() {return this.type;}

    public abstract void update(double deltaTime);

    protected void move(double deltaTime){

        this.x += this.dirX * this.speed * deltaTime;
        this.y += this.dirY * this.speed * deltaTime;

        // Normalize diagonal movement to maintain consistent speed
        /*
        if (dx != 0 && dy != 0) {
            double length = Math.sqrt(dx * dx + dy * dy);
            this.x += (dx / length) * this.speed * deltaTime;
            this.y += (dy / length) * this.speed * deltaTime;
        } else {

        }

         */
    }

    public void takeDamage(double dmg)
    {
        this.hp -= dmg;

        if(this.hp <= 0)
        {
            this.isAlive = false;
        }
    }

    /*public void attack(EntitiesPackage.Entity target){
        target.takeDamage(attackDmg);
    }*/
    public boolean getAliveStatus()
    {
        return this.isAlive;
    }
    public void  revive()
    {
        this.isAlive = true;
        this.hp = this.maxHP;
    }
    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    //fix later
    public Weapon getWeapon() {
        return this.weapon;
    }

    /**
     * use weapon to attack at attackPosition
     * tell eventManger to draw this event
     */
    public void attack() {
        if (this.weapon == null) return;
        System.out.println(this.dirX + " " + this.dirY);
        this.weapon.attack(this instanceof Player, this.getAttackPointX() , this.getAttackPointY());
        DrawEventManager.getInstance().drawAttack(this.getAttackPointX() , this.getAttackPointY(), weapon.getRange());

    }
    //fix later
    public double getAttackPointX()
    {
        return this.x + this.dirX * 20;
    }

    public double getAttackPointY()
    {
        return this.y + this.dirY* 20;
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

