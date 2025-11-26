package com.grouptwelve.roguelikegame.model.EntitiesPackage;

public abstract class Enemy extends Entity {
    double targetDist;
    double attackRange;
    double attackCooldown;
    double cooldownRemaining;
    public Enemy(String name, double x, double y, double hp, int size, double maxHP) {
        super(name, x, y, hp, size, maxHP);
        this.velocity.setMaxSpeed(50); // Default enemy velocity
        this.attackRange = 50;
        this.attackCooldown = 0.2;
    }
    public void setTargetPos(double tx, double ty)
    {
        double dx = tx - this.x;
        double dy = ty - this.y;

        // Normalize the vector (for diagonal movement)
        // Normalize the vector (for diagonal movement)
        double length = Math.sqrt(dx * dx + dy * dy);
        double normDx = dx / length;
        double normDy = dy / length;
        this.dirX = normDx;
        this.dirY = normDy;

        // Scale by maxSpeed to get velocity
        velocity.set(normDx * velocity.getMaxSpeed(), normDy * velocity.getMaxSpeed());



    }
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

