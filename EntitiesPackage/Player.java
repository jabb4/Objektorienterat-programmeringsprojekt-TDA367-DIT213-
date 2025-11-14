package EntitiesPackage;

import EntitiesPackage.Entities;

public class Player extends Entities {

    public Player(double x, double y) {
        super("EntitiesPackage.Player", x, y, 100, 10, 100);
        this.speed = 5; //Vet inte vilken speed är bra.
    }

    /*@Override
    public void attack(EntitiesPackage.Entities target) {
        System.out.println(name + " attacks " + target.getName() + " for " + attackDmg + " damage!");
        target.takeDamage(attackDmg);
    }*/
}

