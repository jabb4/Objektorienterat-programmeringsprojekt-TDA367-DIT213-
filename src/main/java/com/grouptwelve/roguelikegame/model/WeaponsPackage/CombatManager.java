package com.grouptwelve.roguelikegame.model.WeaponsPackage;

import com.grouptwelve.roguelikegame.controller.GameController;
import com.grouptwelve.roguelikegame.model.ControlEventManager;
import com.grouptwelve.roguelikegame.model.EffectsPackage.EffectInterface;
import com.grouptwelve.roguelikegame.model.EffectsPackage.KnockbackEffect;
import com.grouptwelve.roguelikegame.model.EntitiesPackage.Enemies.EnemyPool;
import com.grouptwelve.roguelikegame.model.EntitiesPackage.Enemy;
import com.grouptwelve.roguelikegame.model.EntitiesPackage.Player;
import com.grouptwelve.roguelikegame.model.Game;

import java.util.List;

public class CombatManager
{
    private final Game game;
    //singleton instance
    private static CombatManager instance;

    // save the player and list of enemies
    private Player player;
    private List<Enemy> enemies;
    
    private GameController gameController;

    public void setGameController(GameController controller) {
        this.gameController = controller;
    }

    private CombatManager()
    {
        this.player = null;
        this.game = Game.getInstance();
        this.player = game.getPlayer();
    }

    /**
     * @return singleton instance
     */
    public static CombatManager getInstance()
    {
        if(instance == null) instance = new CombatManager();
        return instance;
    }

    /**
     *does damage from position with range to either enemies or player
     * @param isFriendly  that tells if the attacker is friendly or enemy
     * @param x x-coordinate for attack
     * @param y y-coordinate
     * @param range range of the attack
     * @param dmg damage
     * @return void, don't return anything, only deal damage to target without confirming to attacker if the attack hit or not
     */
    //add attack function with Entity instead of x,y,range
    //could instead return List<EntitiesPackage.Enemy> and then let weapon attack on enemies. good for not exposing specific weapond buffs here in combatManger. Now I assume there are no weapond buffs

    public void attack(boolean isFriendly, double x, double y, double range, double dmg, List<EffectInterface> effects)
    {
        System.out.println(x + " " + y + " range" + range);
        if (isFriendly)
        {
            //loop though all enemies and check if attack hit an enemy
            List<Enemy> enemies = this.game.getEnemies();
            for (int i=enemies.size()-1; i>=0; i--)
            {
                Enemy enemy = enemies.get(i);

                if(isHit(x, y, range, enemy.getX(), enemy.getY(), enemy.getSize()))
                {
                    enemy.takeDamage(dmg);
                                        
                    if(!enemy.getAliveStatus()){
                        EnemyPool.getInstance().returnEnemy(enemy);
                        enemies.remove(enemy);
                        continue;
                    }

                    // Fire hit event for visual feedback (damage numbers)
                    ControlEventManager.getInstance().onEnemyHit(enemy.getX(), enemy.getY(), dmg);

                    // Calculate knockback direction (from attack point to enemy)
                    double dirX = enemy.getX() - x;
                    double dirY = enemy.getY() - y;
                    double length = Math.sqrt(dirX * dirX + dirY * dirY);
                    if (length > 0) {
                        dirX /= length;
                        dirY /= length;
                    }

                    for(EffectInterface effectInterface : effects)
                    {
                        if (effectInterface instanceof KnockbackEffect) {
                            ((KnockbackEffect) effectInterface).setDirection(dirX, dirY);
                        }
                        effectInterface.apply(enemy);
                    }
                    System.out.println("attacked at: (" + x + ", " + y + "), with range:" + range +" EntitiesPackage.Enemy at: "  + enemy);
                }
            }
        }
        else
        {
            if(isHit(x, y, range, player.getX(), player.getY(), player.getSize())) {


//                PLAYER KNOCKBACK
//                double dirX = player.getX() - x;
//                double dirY = player.getY() - y;
//                double length = Math.sqrt(dirX * dirX + dirY * dirY);
//                if (length > 0) {
//                    dirX /= length;
//                    dirY /= length;
//                }


//                PLAYER KNOCKBACK
//                double dirX = player.getX() - x;
//                double dirY = player.getY() - y;
//                double length = Math.sqrt(dirX * dirX + dirY * dirY);
//                if (length > 0) {
//                    dirX /= length;
//                    dirY /= length;
//                }

                // player.takeDamage(dmg);
                gameController.takeDamage(player, dmg);
                for(EffectInterface effectInterface : effects)
                {
//                    PLAYER KNOCKBACK
//                    if (effectInterface instanceof KnockbackEffect) {
//                        ((KnockbackEffect) effectInterface).setDirection(dirX, dirY);
//                    }
                    effectInterface.apply(player);
                }
                System.out.println("attacked at: (" + x + ", " + y + "), EntitiesPackage.Player at: "  + player);
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
}

