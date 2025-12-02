package com.grouptwelve.roguelikegame.model.EntitiesPackage;

public abstract class Enemy extends Entity {
    protected double targetDist;
    protected double attackRange;

    public Enemy(String name, Entities type, double x, double y, double hp, int size, double maxHP) {
        super(name, type, x, y, hp, size, maxHP);
        this.velocity.setMaxSpeed(50); // Default enemy velocity
        this.attackRange = 50;
    }

    /**
     * Sets the target position for the enemy to move towards.
     * Calculates direction and updates velocity.
     *
     * @param tx Target x-coordinate
     * @param ty Target y-coordinate
     */
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

    @Override
    public void update(double deltaTime)
    {
        super.update(deltaTime);

        if (targetDist < attackRange)
        {
            velocity.stop(); // Stop intentional movement, knockback still applies

            // Attack when weapon is ready (cooldown handled by weapon)
            if (weapon != null && weapon.canAttack())
            {
                attack();
            }
        }

        move(deltaTime); // Always apply movement, which includes knockback
    }
}
