package com.grouptwelve.roguelikegame.model.EntitiesPackage;

import com.grouptwelve.roguelikegame.model.ControlEventManager;
import com.grouptwelve.roguelikegame.model.WeaponsPackage.Sword;

public class Player extends Entity {
    private boolean wantMove;
    private double attackRange;

    public Player(double x, double y) {
        super("Player",Entities.PLAYER, x, y, 100, 10, 100);
        this.velocity.setMaxSpeed(150);
        this.weapon = new Sword();
        this.wantMove = false;
    }

    @Override
    public void update(double deltaTime)
    {
        super.update(deltaTime); // Handle knockback

        if (wantMove)
        {
            move((deltaTime));
        }
    }

    /**
     * Sets the movement direction and updates velocity.
     *
     * @param dx x-component of movement vector
     * @param dy y-component of movement vector
     */
    public void setMovementDirection(double dx, double dy) {
        if (dx != 0 || dy != 0) {
            this.dirX = dx;
            this.dirY = dy;

            // Normalize the vector (for diagonal movement)
            double length = Math.sqrt(dx * dx + dy * dy);
            double normDx = dx / length;
            double normDy = dy / length;

            // Scale by maxSpeed to get velocity
            wantMove = true;
            velocity.set(normDx * velocity.getMaxSpeed(), normDy * velocity.getMaxSpeed());
        } else {
            // Stop moving
            wantMove = false;
            velocity.stop();
        }
    }

    @Override
    public void takeDamage(double dmg)
    {
        this.hp -= dmg;

        if(this.hp <= 0)
        {
            ControlEventManager.getInstance().playerDied(this.x, this.y);
        }
    }

    static {
        EntityFactory.getInstance().registerEntity(Entities.PLAYER, new Player(0,0));
    }

    @Override
    public Player createEntity(double x, double y) {
        return new Player(x, y);
    }
}

