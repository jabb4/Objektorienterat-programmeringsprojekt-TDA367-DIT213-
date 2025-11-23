package com.grouptwelve.roguelikegame.model.EntitiesPackage;

import com.grouptwelve.roguelikegame.model.DrawEventManager;
import com.grouptwelve.roguelikegame.model.Weapons.Sword;

import java.util.EventListener;

public class Player extends Entity {
    private boolean wantMove;
    public Player(double x, double y) {
        super("Player",Entities.PLAYER, x, y, 100, 10, 100);
        this.speed = 100; // Pixels per second
        this.weapon = new Sword();
    }

    /**
     * sets players direction
     * @param x dir
     * @param y dir
     */
    public void setDir(int x, int y)
    {
        if(x != 0 || y != 0 )
        {
            double distance =  Math.sqrt(x*x + y*y);

            //normalize
            dirX = x/  distance;
            dirY = y/ distance;

            this.wantMove = true;
        }
        else this.wantMove = false;
    }

    /**
     * player always move as long as one button is pressed and that is indicated by wantMove
     * @param deltaTime time between frames
     */
    public void update(double deltaTime)
    {
        if(this.wantMove)
        {
            this.move(deltaTime);
        }
    }

    @Override
    public void takeDamage(double dmg)
    {
        this.hp -= dmg;

        if(this.hp <= 0)
        {
            DrawEventManager.getInstance().playerDied();
        }
    }


    static {
        EntityFactory.getInstance().registerEntity(Entities.PLAYER, new Player(0,0));
    }

    /*@Override
    public void attack(EntitiesPackage.Entity target) {
        System.out.println(name + " attacks " + target.getName() + " for " + attackDmg + " damage!");
        target.takeDamage(attackDmg);
    }*/

    @Override
    public Player createEntity(double x, double y) {
        return new Player(x, y);
    }
}

