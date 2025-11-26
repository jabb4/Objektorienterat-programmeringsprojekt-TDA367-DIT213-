package com.grouptwelve.roguelikegame.model.EntitiesPackage;

public abstract class Enemy extends Entity {
    double targetDist;
    double attackDist;
    public Enemy(String name, double x, double y, double hp, int size, double maxHP) {
        super(name, x, y, hp, size, maxHP);
        this.velocity.setMaxSpeed(50); // Default enemy velocity
        this.attackDist = 50;
    }
    public void update(double deltaTime)
    {

        move(deltaTime);
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

    // TODO: override update method and move enemy AI into there
}

