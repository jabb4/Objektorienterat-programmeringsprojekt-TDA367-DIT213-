package com.grouptwelve.roguelikegame.model.entities;

import com.grouptwelve.roguelikegame.model.Velocity;
import com.grouptwelve.roguelikegame.model.combat.CombatResult;
import com.grouptwelve.roguelikegame.model.effects.EffectInterface;
import com.grouptwelve.roguelikegame.model.effects.active.ActiveEffect;
import com.grouptwelve.roguelikegame.model.events.output.events.EntityHitEvent;
import com.grouptwelve.roguelikegame.model.events.output.events.HealthChangeEvent;
import com.grouptwelve.roguelikegame.model.events.output.publishers.EntityPublisher;
import com.grouptwelve.roguelikegame.model.events.output.events.AttackEvent;
import com.grouptwelve.roguelikegame.model.events.output.events.EntityDeathEvent;
import com.grouptwelve.roguelikegame.model.weapons.Weapon;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * class that represent object that can move and attack(player and Enemies)
 * contains both information but also methods how they should behave
 * abstract class that should not be created without extending it, because this is just a template
 * use composition to store objects like velocity and weapon
 */
public abstract class Entity implements Obstacle{
    protected String name;
    protected double x, y;
    protected double hp;
    protected double maxHP;
    protected boolean isAlive;
    protected int size;
    protected Velocity velocity;
    protected ObstacleType obstacleType;

    protected double dirX;
    protected double dirY;

    protected Weapon weapon;
    private static final double ATTACK_OFFSET = 20;

    protected EntityPublisher entityPublisher;

    private final List<ActiveEffect> activeEffects = new ArrayList<>();

    public Entity(String name, double x, double y, double hp, int size, double maxHP){
        this.name = name;
        this.x = x;
        this.y = y;
        this.hp = hp;
        this.maxHP = maxHP;
        this.size = size;
        this.velocity = new Velocity(100);
        this.isAlive = true;
    }

    /**
     * Updates the entity's state each frame.
     * Currently, handles velocity, knockback, weapon cooldown, and hit effects.
     *
     * @param deltaTime Time since last update
     */
    protected void update(double deltaTime) {
        velocity.update(deltaTime);

        if (weapon != null) {
            weapon.update(deltaTime);
        }

        Iterator<ActiveEffect> it = activeEffects.iterator();
        while (it.hasNext()) {
            ActiveEffect effect = it.next();
            effect.update(this, deltaTime);

            if (effect.isFinished()) {
                it.remove();
            }
        }
    }

    /**
     * Sets some default values for the entity which should represent a revived state
     * This should not be used as a reset because it will keep certain stats that have been upgraded during the game
     */
    public void revive()
    {
        this.hp = maxHP;
        this.isAlive = true;

        //Reset Velocity
        this.velocity.reset();

        // Clear effects
        this.activeEffects.clear();

        // Reset weapon
        if (weapon != null) {
            weapon.reset();
        }
    }

    /**
     * applies effects to entity
     * @param effect ex fire
     */
    public void addEffect(ActiveEffect effect) {
        activeEffects.add(effect);
    }


    /**
     * Returns the first active effect of the specified type, if present.
     * @param type the class of the active effect to search for
     * @param <T>  the specific type of active effect
     * @return the active effect of the given type, or {@code null} if none is present
     */
    public <T extends ActiveEffect> T getActiveEffect(Class<T> type) {
        for (ActiveEffect effect : activeEffects) {
            if (type.isInstance(effect)) {
                return type.cast(effect);
            }
        }
        return null;
    }


    /**
     * moves entitiy based on direction and speed
     * @param deltaTime -multiply change by delta time so frame rate not affect speed
     */
    protected void move(double deltaTime) {
        x += velocity.getX() * deltaTime;
        y += velocity.getY() * deltaTime;
    }

    // ==================== Combat ====================

    /**
     * returns the position where entity attacks
     * (its own size plus weapon range) far away from center of entity
     * @return x-coordinate
     */
    public double getAttackPointX() {
        return this.x + this.dirX * (ATTACK_OFFSET);
    }
    /**
     * returns the position where entity attacks
     * (its own size plus weapon range) far away from center of entity
     * @return y-coordinate
     */
    public double getAttackPointY() {
        return this.y + this.dirY* ( weapon.getRange());
    }

    /**
     * Applies damage to itself. Sets isAlive to false if HP drops to 0 or below.
     * publishes onEntityHit and onEntityDeath events
     * @param combatResult damage ifo
     */
    public void takeDamage(CombatResult combatResult) {
        if (!isAlive) return;

        double dmg = combatResult.getDamage();
        setHp(this.hp - dmg);

        if (entityPublisher != null) {
            //fire hit event for damage numbers
            entityPublisher.onEntityHit(
                    new EntityHitEvent(this, combatResult, hp, maxHP)
            );
        }

        if (this.hp <= 0) {
            isAlive = false;
            if (entityPublisher != null) {
                entityPublisher.onEntityDeath(
                        new EntityDeathEvent(this, x, y)
                );
            }
        }
    }



    /**
     * Attempts to attack using the equipped weapon.
     * If an AttackListener is set, it will be notified to handle combat resolution.
     *
     * @return true if the attack was performed, false if weapon on cooldown or no weapon
     */
    public boolean attack() {
        if (this.weapon == null || entityPublisher == null) return false;
        if (!this.weapon.canAttack()) return false;

        // Reset weapon cooldown
        this.weapon.refreshCooldown();

        CombatResult result = weapon.calculateDamage();
        entityPublisher.onAttack(new AttackEvent(this, getAttackPointX(), getAttackPointY(), weapon.getRange(), result, weapon.getEffects(),  weapon.getKnockbackStrength()));

        return true;
    }

    /**
     * Applies a knockback force to this entity.
     *
     * @param dirX Normalized x direction of knockback
     * @param dirY Normalized y direction of knockback
     * @param strength The strength/speed of the knockback
     */
    public void applyKnockback(double dirX, double dirY, double strength) {
        velocity.applyKnockback(dirX, dirY, strength);
    }

    // ==================== Speed Control ====================

    public double getMoveSpeed() {
        return velocity.getMaxSpeed();
    }

    public void increaseMoveSpeed(double amount) {
        velocity.setMaxSpeed(velocity.getMaxSpeed() + amount);
    }

    public void multiplyMoveSpeed(double multiplier) {
        velocity.setMaxSpeed(velocity.getMaxSpeed() * multiplier);
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

    public double getMaxHP() {
        return this.maxHP;
    }

    public int getSize() {
        return this.size;
    }

    public boolean getAliveStatus() {
        return this.isAlive;
    }

    public ObstacleType getObstacleType() {
        return this.obstacleType;
    }
    public double getWeaponRange() {
        return weapon.getRange();
    }

    public void addWeaponRange(double amount) {
        weapon.addRange(amount);
    }

    public double getWeaponDamage() {
        return weapon.getDamage();
    }

    public void addWeaponDamage(double amount) {
        weapon.addDamage(amount);
    }

    public double getWeaponCritChance() {
        return weapon.getCritChance();
    }

    public void addWeaponCritChance(double amount) {
        weapon.addCritChance(amount);
    }

    public double getWeaponCritMultiplier() {
        return weapon.getCritMultiplier();
    }

    public void addWeaponCritMultiplier(double amount) {
        weapon.addCritMultiplier(amount);
    }

    public double getWeaponKnockbackStrength() {
        return weapon.getKnockbackStrength();
    }

    public void addWeaponKnockbackStrength(double amount) {
        weapon.addKnockbackStrength(amount);
    }

    public List<EffectInterface> getWeaponEffects() {
        return weapon.getEffects();
    }

    public void addWeaponEffect(EffectInterface effect) {
        weapon.addEffect(effect);
    }

    // ==================== Setters ====================

    public void setEntityPublisher(EntityPublisher publisher) {
        this.entityPublisher = publisher;
    }

    public void setMaxHP(double maxHP) {
        this.maxHP = maxHP;
        if (entityPublisher != null) {
            entityPublisher.onHealthChange(new HealthChangeEvent(this, this.hp, this.maxHP));
        }
    }


    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void heal(double amount) {
        if ((this.hp + amount) >= this.maxHP){
            setHp(this.maxHP);
        }
        else{
            setHp(this.hp + amount);
        }
    }

    public void setHp(double hp) {
        this.hp = hp;
        if (entityPublisher != null) {
            entityPublisher.onHealthChange(new HealthChangeEvent(this, this.hp, this.maxHP));
        }
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
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
}