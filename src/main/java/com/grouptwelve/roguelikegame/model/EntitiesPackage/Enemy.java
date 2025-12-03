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

        double thisToTarget_dx = targetX - this.x;
        double thisToTarget_dy = targetY - this.y;
        this.targetDist = Math.sqrt(thisToTarget_dx * thisToTarget_dx + thisToTarget_dy * thisToTarget_dy);
        double normDx = thisToTarget_dx / this.targetDist;
        double normDy = thisToTarget_dy / this.targetDist;
        this.dirX = normDx;
        this.dirY = normDy;

        // Scale by maxSpeed to get velocity
        velocity.set(normDx * velocity.getMaxSpeed(), normDy * velocity.getMaxSpeed());


        // This is to avoid collision with other enemies
        for (Enemy other : enemies) {
            if (other == this) continue;

            double otherToTarget_dx = targetX - other.getX();
            double otherToTarget_dy = targetY - other.getY();
            double otherToTargetDist = Math.sqrt(otherToTarget_dx * otherToTarget_dx + otherToTarget_dy * otherToTarget_dy);

            double thisToOther_dx = this.x - other.getX();
            double thisToOther_dy = this.y - other.getY();
            double thisToOtherDist = Math.sqrt(thisToOther_dx * thisToOther_dx + thisToOther_dy * thisToOther_dy);


            // If they are overlapping (distance is less than physical size)
            if (thisToOtherDist < this.size + other.getSize() && otherToTargetDist <= this.targetDist) {
                // Handle edge case: perfect overlap (avoid division by zero)
                if (thisToOtherDist == 0) {thisToOtherDist = 0.00001;}

                // Cross product between thisToTarget and thisToOther (to determine which way is shorter for this enemy to go around the other enemy)
                double cross = thisToTarget_dx * thisToOther_dy - thisToTarget_dy * thisToOther_dx;

                // Now when I know which way around it should take, how do I make it turn that way?
                if (cross <= 0){
                    System.out.println("Passing other on right");
                    velocity.set(normDy * velocity.getMaxSpeed(), -normDx * velocity.getMaxSpeed());
                } else {
                    System.out.println("Passing other on left");
                    velocity.set(-normDy * velocity.getMaxSpeed(), normDx * velocity.getMaxSpeed());
                }
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

