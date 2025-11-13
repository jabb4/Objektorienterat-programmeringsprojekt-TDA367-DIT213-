public class Player extends Entities {

    public Player(double x, double y) {
        super("Player", x, y, 100, 10, 100, 20);
        this.speed = 5; //Vet inte vilken speed är bra.
    }

    @Override
    public void attack(Entities target) {
        System.out.println(name + " attacks " + target.getName() + " for " + attackMultiplier + " damage!");
        target.takeDamage(attackMultiplier);
    }
}

