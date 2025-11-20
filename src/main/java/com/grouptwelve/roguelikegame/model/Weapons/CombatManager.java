package com.grouptwelve.roguelikegame.model.Weapons;

import com.grouptwelve.roguelikegame.model.EffectsPackage.EffectInterface;
//import EffectsPackage;
import com.grouptwelve.roguelikegame.model.EntitiesPackage.Enemy;
import com.grouptwelve.roguelikegame.model.EntitiesPackage.Player;
//import EntitiesPackage.Goblin;
//import EntitiesPackage.Player;

import java.util.ArrayList;
import java.util.List;

public class CombatManager
{
/*
    public static void main(String[] args)
    {
        Player player = new Player(2, 2);
        Enemy goblin = new Goblin(0, 0);
        Enemy goblin2 = new Goblin(10, 0);
        Enemy goblin3 = new Goblin(3, 2);

        CombatManager combatManager = CombatManager.getInstance();
        combatManager.addEnemy(goblin);
        combatManager.addEnemy(goblin2);
        combatManager.addEnemy(goblin3);

        combatManager.removeEnemy(goblin3);

        player.attack();
        //combatManager.attack(true, player.getX(), player.getY(), 1, 50);


        Scanner sc = new Scanner(System.in);
        System.out.println("\n\n");
        System.out.println("Goblin1 with position (" + goblin.getX() + ", " + goblin.getY() + ")" + "    with size(radius):" + goblin.getSize());
        System.out.println("Goblin2 with position (" + goblin2.getX() + ", " + goblin2.getY() + ")" + "    with size(radius):" + goblin2.getSize());
        System.out.println("write your attack with: x-coordinate y-coordinate");
        while(true)
        {
            double x = sc.nextDouble();
            double y = sc.nextDouble();
            //double range = sc.nextDouble();
            //int dmg  = sc.nextInt();
            player.setX(x);
            player.setY(y);
            //player.getWeapon().addRange(range);


            player.attack();
        }


    }

 */
    //singleton instance
    private static CombatManager instance;
    // save the player and list of enemies
    private Player player;
    private List<Enemy> enemies;

    private CombatManager()
    {
        this.enemies = new ArrayList<>();
        this.player = null;
    }

    /**
     * @return singleton instance
     */
    public static CombatManager getInstance()
    {
        if(instance == null) instance = new CombatManager();
        return instance;
    }
    public void setPlayer(Player player)
    {
        this.player = player;
    }

    /**
     *does damage from position with range to either enemies or player
     * @param isFriendly  that tells if the attacker is friendly or enemy
     * @param x x-coordinate for attack
     * @param y y-coordinate
     * @param range range of the attack
     * @param dmg damage
     * @return void, dont return anything, only deal damage to target without confirming to attacker if the attack hit or not
     */
    //add attack function with Entity instead of x,y,range
    //could instead return List<EntitiesPackage.Enemy> and then let weapon attack on enemies. good for not exposing specific weapond buffs here in combatManger. Now I assume there are no weapond buffs
    //add knockback?

    public void attack(boolean isFriendly, double x, double y, double range, double dmg, List<EffectInterface> effects)
    {
        System.out.println(x + " " + y + " range" + range);
        if (isFriendly)
        {
            //loop though alla enemies and check if attack hit an enemy
            for (Enemy enemy : enemies)
            {
                if(!enemy.getAliveStatus()) continue; // enemy is already dead
                if(isHit(x, y, range, enemy.getX(), enemy.getY(), enemy.getSize()))
                {
                    enemy.takeDamage(dmg);
                    for(EffectInterface effectInterface : effects)
                    {
                        effectInterface.apply(enemy);
                    }
                    System.out.println("attacked at: (" + x + ", " + y + "), with range:" + range +" EntitiesPackage.Enemy at: "  + enemy.toString());
                }
            }
        }
        else
        {
            if(isHit(x, y, range, player.getX(), player.getY(), player.getSize())) {

                player.takeDamage(dmg);
                for(EffectInterface effectInterface : effects)
                {
                    effectInterface.apply(player);
                }
                System.out.println("attacked at: (" + x + ", " + y + "), EntitiesPackage.Player at: "  + player.toString());
            }
        }
    }

    /**
     *checks if attack at position (x1, y1) with attack range "range" hits target at (x2, y2) with size "size"
     */
    public static boolean isHit(double x1, double y1, double range, double x2, double y2, double size)
    {
        double deltaX = (x1 - x2);
        double deltaY = (y1 - y2);

        //pythagoras, could maybe not squt and instead compare this with squared rangSum for better performance?
        double distance =  Math.sqrt(deltaX*deltaX + deltaY*deltaY);

        double rangeSum = range + size;

        return(distance < rangeSum);
    }
    /**
     *
     * @param enemy enemy to add to list of enemies
     */
    public void addEnemy(Enemy enemy)
    {
        enemies.add(enemy);
    }
    /**
     *
     * @param enemy to remove from list
     */
    public void removeEnemy(Enemy enemy)
    {
        enemies.remove(enemy);
    }
}

