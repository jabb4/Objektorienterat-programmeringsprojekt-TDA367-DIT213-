package com.grouptwelve.roguelikegame.model.EntitiesPackage;

import com.grouptwelve.roguelikegame.model.Weapons.Sword;

public class Player extends Entity {

    private boolean wantMove;
    public Player(double x, double y) {
        super("Player", x, y, 100, 10, 100);
        this.velocity.setMaxSpeed(100);
        this.weapon = new Sword();
        this.wantMove = false;


    }
    public void update(double deltaTime)
    {
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
            velocity.set(normDx * velocity.getMaxSpeed(), normDy * velocity.getMaxSpeed());
        } else {
            // Stop moving
            velocity.stop();
        }
    }

    static {
        EntityFactory.getInstance().registerEntity("Player", new Player(0,0));
    }

    @Override
    public Player createEntity(double x, double y) {
        return new Player(x, y);
    }
}

