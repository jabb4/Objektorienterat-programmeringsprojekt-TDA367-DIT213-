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

    public void avoidCollision(List<Enemy> enemies)
    {
        for (Enemy other : enemies) {
            if (other == this) continue;

            double dx = this.x - other.x;
            double dy = this.y - other.y;
            double dist = Math.sqrt(dx * dx + dy * dy);

            // If they are overlapping (distance is less than physical size)
            if (dist < this.size + other.getSize()) {

                // Handle edge case: perfect overlap (avoid division by zero)
                if (dist == 0) {
                    dx = Math.random() - 0.5;
                    dy = Math.random() - 0.5;
                    dist = 0.001;
                }

                double overlap = this.size + other.getSize() - dist;

                // 2. Calculate direction to push 'this' enemy away
                double pushX = dx / dist;
                double pushY = dy / dist;

                // 3. DIRECT POSITION CORRECTION
                // Move 'this' enemy out immediately.
                // We use a factor of 0.5 because the *other* enemy will also run this code,
                // so they effectively push each other apart equally.
                double pushStrength = 2;

                this.velocity.set(100,100);
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

