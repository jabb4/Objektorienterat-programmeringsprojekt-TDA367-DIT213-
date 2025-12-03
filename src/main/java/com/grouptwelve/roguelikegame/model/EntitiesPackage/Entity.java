package com.grouptwelve.roguelikegame.model.EntitiesPackage;

import com.grouptwelve.roguelikegame.model.ControlEventManager;
import com.grouptwelve.roguelikegame.model.EffectsPackage.ActiveEffectPackage.ActiveEffect;
import com.grouptwelve.roguelikegame.model.Velocity;
import com.grouptwelve.roguelikegame.model.WeaponsPackage.Weapon;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Entity {
    protected String name;
    protected Entities type;
    protected double x, y;
    protected double hp;
    protected double maxHP;
    protected boolean isAlive;
    protected int size;
    protected Velocity velocity;

    protected double dirX;
    protected double dirY;

    protected Weapon weapon;

    protected boolean isHit;
    protected double hitTimer;

    private List<ActiveEffect> activeEffects = new ArrayList<>();


    public Entity(String name, Entities type, double x, double y, double hp, int size, double maxHP){
        this.name = name;
        this.type = type;
        this.x = x;
        this.y = y;
        this.hp = hp;
        this.maxHP = maxHP;
        this.size = size;
        this.velocity = new Velocity(100);
        this.isAlive = true;
        this.isHit = false;
        this.hitTimer = 0.0;
    }

    protected void update(double deltaTime) {
        // Update movement
        velocity.update(deltaTime);

        // Update hit flash timer
        if (isHit && (hitTimer -= deltaTime) <= 0) {
            isHit = false;
        }

        // === Update all active effects ===
        Iterator<ActiveEffect> it = activeEffects.iterator();
        while (it.hasNext()) {
            ActiveEffect effect = it.next();
            effect.update(this, deltaTime);

            if (effect.isFinished()) {
                it.remove();
            }
        }
    }


    public void addEffect(ActiveEffect effect) {
        activeEffects.add(effect);
    }

    protected void move(double deltaTime) {
        x += velocity.getX() * deltaTime;
        y += velocity.getY() * deltaTime;
    }

    // ==================== Combat ====================

    public double getAttackPointX() {
        return this.x + this.dirX * 20;
    }

    public double getAttackPointY() {
        return this.y + this.dirY * 20;
    }

    public void takeDamage(double dmg) {
        this.hp -= dmg;

        if (this.hp <= 0) {
            this.isAlive = false;
        }
    }

    public void attack() {
        if (this.weapon == null) return;
        this.weapon.attack(this instanceof Player, this.getAttackPointX(), this.getAttackPointY());
        ControlEventManager.getInstance().drawAttack(this.getAttackPointX(), this.getAttackPointY(), weapon.getRange());
    }

    public double getMaxHP() {
        return this.maxHP;
    }

    public void setMaxHP(double maxHP) {
        this.maxHP = maxHP;
    }


    public void applyKnockback(double dirX, double dirY, double strength) {
        velocity.applyKnockback(dirX, dirY, strength);
    }

    // ==================== Speed Control ====================

    public double getMoveSpeed() {
        return velocity.getMaxSpeed();
    }

    public void setMoveSpeed(double speed) {
        velocity.setMaxSpeed(speed);
    }

    public void increaseMoveSpeed(double amount) {
        velocity.setMaxSpeed(velocity.getMaxSpeed() + amount);
    }

    public void multiplyMoveSpeed(double multiplier) {
        velocity.setMaxSpeed(velocity.getMaxSpeed() * multiplier);
    }

    // ==================== Getters ====================

    public String getName() { return this.name; }
    public double getX() { return this.x; }
    public double getY() { return this.y; }
    public double getHp() { return this.hp; }
    public int getSize() { return this.size; }
    public boolean getAliveStatus() { return this.isAlive; }

    public void revive() {
        this.hp = maxHP;
        this.isAlive = true;
    }

    public Weapon getWeapon() { return this.weapon; }
    public Entities getType() { return this.type; }
    public boolean isHit() { return this.isHit; }

    // ==================== Setters ====================

    public void setName(String name) { this.name = name; }
    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
    public void setHp(double hp) { this.hp = hp; }
    public void setSize(int size) { this.size = size; }
    public void setWeapon(Weapon weapon) { this.weapon = weapon; }

    public void setHit(boolean hit, double duration) {
        this.isHit = hit;
        this.hitTimer = duration;
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
