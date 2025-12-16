package com.grouptwelve.roguelikegame.model.entities;
import com.grouptwelve.roguelikegame.model.events.output.publishers.LevelUpPublisher;
import com.grouptwelve.roguelikegame.model.level.LevelSystem;
import com.grouptwelve.roguelikegame.model.weapons.Sword;

public class Player extends Entity {

    private static final int PLAYER_HP = 100;
    private static final int PLAYER_SIZE = 10;
    private static final int PLAYER_MAX_HP = 100;
    private static final int PLAYER_MAX_SPEED = 150;

    private final double startX;
    private final double startY;

    private boolean wantMove;
    private LevelUpPublisher levelUpPublisher;
    private LevelSystem levelSystem = new LevelSystem();

    public Player(double x, double y) {
        super("Player", x, y, PLAYER_HP, PLAYER_SIZE, PLAYER_MAX_HP);
        this.startX = x;
        this.startY = y;
        this.velocity.setMaxSpeed(PLAYER_MAX_SPEED);
        this.weapon = new Sword();
        this.wantMove = false;
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
        if (wantMove) {
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
            wantMove = true;

            // Normalize the vector (for diagonal movement)
            double length = Math.sqrt(dx * dx + dy * dy);
            double normDx = dx / length;
            double normDy = dy / length;

            velocity.set(normDx * velocity.getMaxSpeed(), normDy * velocity.getMaxSpeed());
        } else {
            // Stop moving
            wantMove = false;
            velocity.stop();
        }
    }

    @Override
    public void revive() {
        super.revive();
        this.x = startX;
        this.y = startY;
    }

    // ==================== Progression ====================

    /**
     * tells game when leveling up
     */
    public void setLevelUpPublisher(LevelUpPublisher publisher) {
        this.levelUpPublisher = publisher;
    }

    public void gainXP(int amount) {
        boolean leveledUp = levelSystem.addXP(amount);
        if (leveledUp) {
            levelUpPublisher.onLevelUp();
        }
    }

    // ==================== Getters ====================

    public int getXP() {
        return levelSystem.getXP();
    }

    public int getXPToNext() {
        return levelSystem.getXPToNext();
    }

    public int getLevel() {
        return levelSystem.getLevel();
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
