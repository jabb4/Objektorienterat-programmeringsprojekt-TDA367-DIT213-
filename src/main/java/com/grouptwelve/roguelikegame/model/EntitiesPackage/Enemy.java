package com.grouptwelve.roguelikegame.model.EntitiesPackage;

import com.grouptwelve.roguelikegame.model.DrawEventManager;

public abstract class Enemy extends Entity {
    private double targetDist;
    protected double attackRange;
    private double attackCooldown;
    private double cooldownRemaining;
    public Enemy(String name, Entities type, double x, double y, double hp, int size, double maxHP) {
        super(name, type, x, y, hp, size, maxHP);
        this.speed = 50; // Default
        this.targetDist = 0;
        this.attackRange = 40;
        this.attackCooldown = 0.2;

    }

    /**
     *  stores distance to target which is used for knowing when to attack in update.
     *  updates direction variables so it points toward target with normalised values
     * @param x x-coordinate for player most cases
     * @param y y-coordinate for player most cases
     */
    public void setTargetPos(double x, double y)
    {
        x =  ((x - this.x));
        y =  ((y - this.y));

        targetDist =  Math.sqrt(x*x + y*y);

        //normalize
        dirX = x/  targetDist;
        dirY = y/ targetDist;
    }

    /**
     * enemies always walk towards player and if close enough stop and attack
     * cooldown variables is meant as a attack indicator for the player, when enemies get close enough to player they wait a liitle before starting to attack so that the player has time to dodge.
     * when enemies are in range of attacking they don't move
     * @param deltaTime time between frames
     */
    public void update(double deltaTime)
    {

        if(targetDist < attackRange)
        {
            // when enemy enters the attackRange timer starts and only resets if player leaves range or attack happened
            if(cooldownRemaining > 0){
                cooldownRemaining -= deltaTime;
            }

            if(cooldownRemaining <= 0)
            {
                cooldownRemaining = attackCooldown;
                attack();
            }
        }
        else
        {
            cooldownRemaining = attackCooldown;
            move(deltaTime);

        }
    }
}

