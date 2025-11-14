package EntitiesPackage;

import EntitiesPackage.Enemy;
import Weapons.Sword;

public class Goblin extends Enemy {
    public Goblin(double x, double y){
        super("EntitiesPackage.Goblin", x, y, 30, 5, 30);
        this.speed = 3;
        this.weapon = new Sword();
    }
}
