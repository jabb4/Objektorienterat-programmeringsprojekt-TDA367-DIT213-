import EntitiesPackage.Entities;
import EntitiesPackage.Goblin;
import EntitiesPackage.Player;
import EntitiesPackage.Troll;
import UpgradesPackage.DamageUpgrade;
import Weapons.Sword;

public class Main {
    public static void main(String[] args) {

        Entities p = new Player(1,1);
        System.out.println(p.toString());

        Entities g = new Goblin(5,5);
        System.out.println(g.toString());

        Entities t = new Troll(3,3);
        System.out.println(t.toString());

        p.setWeapon(new Sword());
        System.out.println(p.getWeapon().getDamage());

        System.out.println("EntitiesPackage.Goblin hp: " + g.getHp());
        System.out.println("EntitiesPackage.Player attacks");
        p.attack(g);
        System.out.println("EntitiesPackage.Goblin hp: " + g.getHp());


        System.out.println("Weapons.Sword damage: " + p.getWeapon().getDamage());
        p.getWeapon().upgrade(new DamageUpgrade(10));
        System.out.println("Weapons.Sword damage: " + p.getWeapon().getDamage());

        System.out.println("EntitiesPackage.Player attacks");
        p.attack(g);
        System.out.println("EntitiesPackage.Goblin hp: " + g.getHp());
    }
}
