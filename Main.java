import EffectsPackage.FireEffect;
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
        System.out.println(" ");

        System.out.println(p.getName() + " has a: " + p.getWeapon().toString());
        System.out.println(g.getName() + " has a: " + g.getWeapon().toString());
        System.out.println(t.getName() + " has a: " + t.getWeapon().toString());
        System.out.println(" ");


        System.out.println(p.getName() + " attackerar Troll!");
        p.attack(t);
        System.out.println(t.getName() + " har nu: " + t.getHp() + " hp!");
        System.out.println(" ");

        p.getWeapon().addRange(2);
        p.getWeapon().addDamage(15);
        p.getWeapon().addEffect(new FireEffect(5));

        System.out.println(p.getName() + " has upgradet his weapon to: " + p.getWeapon().toString() + " " + p.getWeapon().getEffects().toString());
        System.out.println(" ");

        System.out.println(p.getName() + " attackerar Troll!");
        p.attack(t);
        System.out.println(t.getName() + " har nu: " + t.getHp() + " hp!");



    }
}
