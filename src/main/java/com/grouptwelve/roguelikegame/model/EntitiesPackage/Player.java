package com.grouptwelve.roguelikegame.model.EntitiesPackage;

import com.grouptwelve.roguelikegame.model.ControlEventManager;
import com.grouptwelve.roguelikegame.model.LevelPackage.LevelSystem;
import com.grouptwelve.roguelikegame.model.UpgradesPackage.AttributeUpgrades.MaxHpUpgrade;
import com.grouptwelve.roguelikegame.model.UpgradesPackage.EffectsUpgrades.KnockbackUpgrade;
import com.grouptwelve.roguelikegame.model.UpgradesPackage.WeaponUpgrades.DamageUpgrade;
import com.grouptwelve.roguelikegame.model.UpgradesPackage.WeaponUpgrades.RangeUpgrade;
import com.grouptwelve.roguelikegame.model.UpgradesPackage.AttributeUpgrades.SpeedUpgrade;
import com.grouptwelve.roguelikegame.model.UpgradesPackage.UpgradeInterface;
import com.grouptwelve.roguelikegame.model.WeaponsPackage.Sword;

public class Player extends Entity {
    private boolean wantMove;

    private LevelSystem levelSystem = new LevelSystem();


    public Player(double x, double y) {
        super("Player",Entities.PLAYER, x, y, 100, 10, 100);
        this.velocity.setMaxSpeed(150);
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


    private void onLevelUp() {
        System.out.println("LEVEL UP! New level: " + levelSystem.getLevel());

        // Example: pick a random upgrade manually for now:
        UpgradeInterface up = new DamageUpgrade(5);
        UpgradeInterface up2 = new RangeUpgrade(5);
        UpgradeInterface up3 = new SpeedUpgrade(20);
        UpgradeInterface up4 = new MaxHpUpgrade(20);
        UpgradeInterface up5 = new KnockbackUpgrade(50);

        up.apply(this);
        up2.apply(this);
        up3.apply(this);
        up4.apply(this);
        up5.apply(this);


        System.out.println("Upgrade acquired: " + up.getName());
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

