package com.grouptwelve.roguelikegame.model.EntitiesPackage;

import java.util.List;

public abstract class Enemy extends Entity {
    double targetDist;
    double attackRange;
    double attackCooldown;
    double cooldownRemaining;

    public Enemy(String name, Entities type, double x, double y, double hp, int size, double maxHP) {
        super(name, type, x, y, hp, size, maxHP);
        this.velocity.setMaxSpeed(50); // Default enemy velocity
        this.attackRange = 50;
        this.attackCooldown = 0.2;
    }

    public void setTargetPos(double targetX, double targetY, List<Enemy> enemies)
    {

        double targetdx = targetX - this.x;
        double targetdy = targetY - this.y;
        this.targetDist = Math.sqrt(targetdx * targetdx + targetdy * targetdy);
        double normDx = targetdx / this.targetDist;
        double normDy = targetdy / this.targetDist;
        this.dirX = normDx;
        this.dirY = normDy;

        // Scale by maxSpeed to get velocity
        velocity.set(normDx * velocity.getMaxSpeed(), normDy * velocity.getMaxSpeed());


        // This is to avoid collision with other enemies
        for (Enemy other : enemies) {
            if (other == this) continue;

            double dx = this.x - other.getX();
            double dy = this.y - other.getY();
            double dist = Math.sqrt(dx * dx + dy * dy);

            // If they are overlapping (distance is less than physical size)
            if (dist < this.size + other.getSize()) {
                // Handle edge case: perfect overlap (avoid division by zero)
                if (dist == 0) {dist = 0.00001;}




                this.velocity.set(velocity.getX(), velocity.getY());

                System.out.println(velocity.getX() + " " + velocity.getY());
            }
        }
    }

    @Override
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

    // TODO: override update method and move enemy AI into there
}

