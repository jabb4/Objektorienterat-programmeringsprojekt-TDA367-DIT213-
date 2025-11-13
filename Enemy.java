
public class Enemy extends Entities {

    public Enemy(String name, double x, double y, double hp, int size, double maxHP, double attackDmg) {
        super(name, x, y, hp, size, maxHP, attackDmg);
        this.speed = 2; //Default fiendehastighet
    }
}

