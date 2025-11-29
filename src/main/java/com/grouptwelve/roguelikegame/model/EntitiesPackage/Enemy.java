package com.grouptwelve.roguelikegame.model.EntitiesPackage;

import javafx.scene.shape.Rectangle;

public abstract class Enemy extends Entity {
    protected Rectangle hpBar;
    double targetDist;
    protected double attackRange;
    double attackCooldown;
    double cooldownRemaining;

    public Enemy(String name, Entities type, double x, double y, double hp, int size, double maxHP) {
        super(name, type, x, y, hp, size, maxHP);
        this.velocity.setMaxSpeed(50); // Default enemy velocity
        this.attackCooldown = 0.5;

        if (this.weapon != null) {
            this.attackRange = this.weapon.getRange();
        }
    }

    public void setTargetPos(double tx, double ty)
    {
        double dx = tx - this.x;
        double dy = ty - this.y;

        // Normalize the vector (for diagonal movement)
        double length = Math.sqrt(dx * dx + dy * dy);
        targetDist = length;
        double normDx = dx / length;
        double normDy = dy / length;
        this.dirX = normDx;
        this.dirY = normDy;

        // Scale by maxSpeed to get velocity
        velocity.set(normDx * velocity.getMaxSpeed(), normDy * velocity.getMaxSpeed());
    }

    public Rectangle getHpBar() { return hpBar; }
    public void setHpBar(Rectangle hpBar) { this.hpBar = hpBar; }
    public double getMaxHp() { return maxHP; }

    @Override
    public void update(double deltaTime)
    {
        if (!this.getAliveStatus()) return; // Skip if enemy is dead

        super.update(deltaTime);

        if(targetDist < attackRange)
        {
            velocity.stop(); // Stop intentional movement, knockback still applies

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
        }

        move(deltaTime); // Always apply movement, which includes knockback
    }
}


