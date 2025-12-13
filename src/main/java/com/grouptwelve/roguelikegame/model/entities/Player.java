package com.grouptwelve.roguelikegame.model.entities;

import com.grouptwelve.roguelikegame.model.entities.enemies.Enemies;
import com.grouptwelve.roguelikegame.model.events.LevelUpListener;
import com.grouptwelve.roguelikegame.model.level.LevelSystem;
import com.grouptwelve.roguelikegame.model.weapons.Sword;

public class Player extends Entity {

    private static final int PLAYER_HP = 100;
    private static final int PLAYER_SIZE = 10;
    private static final int PLAYER_MAX_HP = 100;
    private static final int PLAYER_MAX_SPEED = 150;

    private boolean wantMove;
    private LevelUpListener levelUpListener;
    private LevelSystem levelSystem = new LevelSystem();

    public Player(double x, double y) {
        super("Player", x, y, PLAYER_HP, PLAYER_SIZE, PLAYER_MAX_HP);
        this.velocity.setMaxSpeed(PLAYER_MAX_SPEED);
        this.weapon = new Sword();
        this.wantMove = false;
    }

    public void gainXP(int amount) {
        boolean leveledUp = levelSystem.addXP(amount);
        if (leveledUp) {
            onLevelUp();
        }
    }

    public LevelSystem getLevelSystem() {
        return levelSystem;
    }

    /**
     * tells game when leveling up
     */
    private void onLevelUp() {
        levelUpListener.onLevelUp(levelSystem.getLevel());
    }
    public void setLevelUpListener(LevelUpListener listener) {
        this.levelUpListener = listener;
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
            this.isAlive = false;
            // Player death event is now published by Game/CombatManager
        }
    }

    @Override
    public String toString() {
        return "Player{" +
                "pos=(" + x + ", " + y + ")" +
                ", hp=" + hp + "/" + maxHP +
                ", level=" + levelSystem.getLevel() +
                ", xp=" + levelSystem.getXP() + "/" + levelSystem.getXPToNext() +
                ", speed=" + getMoveSpeed() +
                ", weapon=" + weapon +
                '}';
    }
}
