package com.grouptwelve.roguelikegame.model.EntitiesPackage;

import com.grouptwelve.roguelikegame.model.Velocity;
import com.grouptwelve.roguelikegame.model.DrawEventManager;
import com.grouptwelve.roguelikegame.model.Weapons.Weapon;


public abstract class Entity {
    protected String name;
    protected Entities type;
    protected double x, y;
    protected double hp;
    protected double maxHP;
    protected boolean isAlive;
    protected int size;
    protected Velocity velocity;
    //protected double attackDmg;

    // Facing direction (used for attack direction)
    protected double dirX;
    protected double dirY;

    protected Weapon weapon;

    public Entity(String name, Entities type, double x, double y, double hp, int size, double maxHP /*double attackDmg*/){
        this.name = name;
        this.type = type;
        this.x = x;
        this.y = y;
        this.hp = hp;
        this.maxHP = maxHP;
        this.size = size;
        this.velocity = new Velocity(100);
        this.isAlive = true;
        //this.attackDmg = attackDmg;
    }

    /**
     * Updates the entity's state each frame
     *
     * @param deltaTime Time since last update
     */
    protected abstract void update(double deltaTime);

    protected void move(double deltaTime)
    {
        x += velocity.getX() * deltaTime;
        y += velocity.getY() * deltaTime;
    }



    // ==================== Combat ====================

    public double getAttackPointX()
    {
        return this.x + this.dirX * 20;
    }

    public double getAttackPointY()
    {
        return this.y + this.dirY* 20;
    }

    /**
     * Applies damage to itself. Sets isAlive to false if HP drops to 0 or below.
     *
     * @param dmg Amount of damage to apply
     */
    public void takeDamage(double dmg)
    {
        this.hp -= dmg;

        if(this.hp <= 0)
        {
            this.isAlive = false;
        }
    }

    // ==================== Getters ====================

    public String getName() {
        return this.name;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getHp() {
        return this.hp;
    }

    public int getSize() {
        return this.size;
    }

    public boolean getAliveStatus()
    {
        return this.isAlive;
    }
    public void revive()
    {
        this.hp = maxHP;
        this.isAlive= true;
    }

    //fix later
    public Weapon getWeapon() {
        return this.weapon;
    }


    // ==================== Setters ====================

    public void setName(String name) {
        this.name = name;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setHp(double hp) {
        this.hp = hp;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Entities getType() {return this.type;}



    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
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


    @Override
    public String toString() {
        return "Entity{" +
                "name=" + name +
                ", x=" + x +
                ", y=" + y +
                ", hp=" + hp +
                ", size=" + size +
                ", weapon=" + weapon +
                '}';
    }

    public abstract Entity createEntity(double x, double y);
}

