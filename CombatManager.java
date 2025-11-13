
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CombatManager
{

    public static void main(String[] args)
    {
        Player player = new Player(2, 2);
        Enemy goblin = new Goblin(0, 0);
        Enemy goblin2 = new Goblin(10, 0);
        Enemy goblin3 = new Goblin(3, 2);
        CombatManager combatManager = new CombatManager(player, new ArrayList<>(List.of(goblin, goblin2)));
        combatManager.addEnemy(goblin3);
        combatManager.removeEnemy(goblin3);

        combatManager.attack(false, goblin.getX(), goblin.getY(), 1, 50);
        //combatManager.attack(true, player.getX(), player.getY(), 1, 50);


        Scanner sc = new Scanner(System.in);
        System.out.println("\n\n");
        System.out.println("Goblin1 with position (" + goblin.getX() + ", " + goblin.getY() + ")" + "    with size(radius):" + goblin.getSize());
        System.out.println("Goblin2 with position (" + goblin2.getX() + ", " + goblin2.getY() + ")" + "    with size(radius):" + goblin2.getSize());
        System.out.println("write your attack with: x-coordinate y-coordinate range attackRange Damage");
        while(true)
        {
            double x = sc.nextDouble();
            double y = sc.nextDouble();
            double range = sc.nextDouble();
            int dmg  = sc.nextInt();
            combatManager.attack(true, x, y, range, dmg);
        }
    }
    // save the player and list of enemies
    private final Player player;
    private List<Enemy>  enemies;

    //create manager with initial player and enemies, maybe not enemies here because are they ever enemies when starting the game?
    public CombatManager(Player player, List<Enemy> enemies)
    {
        this.enemies = enemies;
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
    //could instead return List<Enemy> and then let weapon attack on enemies. good for not exposing specific weapond buffs here in combatManger. Now I assume there are no weapond buffs
    //add knockback?

    public void attack(boolean isFriendly, double x, double y, double range, double dmg)
    {
        if (isFriendly)
        {
            //loop though alla enemies and check if attack hit an enemy
            for (Enemy enemy : enemies)
            {
                if(isHit(x, y, range, enemy.getX(), enemy.getY(), enemy.getSize()))
                {
                    enemy.takeDamage(dmg);
                    System.out.println("attacked at: (" + x + ", " + y + "), with range:" + range +" Enemy at: "  + enemy.toString());
                }
            }
        }
        else
        {
            if(isHit(x, y, range, player.getX(), player.getY(), player.getSize())) {

                player.takeDamage(dmg);
                System.out.println("attacked at: (" + x + ", " + y + "), Player at: "  + player.toString());
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
        double distance =  Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));

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

