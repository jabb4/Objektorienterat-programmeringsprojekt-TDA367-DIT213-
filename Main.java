public class Main {
    public static void main(String[] args) {
        Entities p = new Player(1,1);
        System.out.println(p.toString());

        Entities g = new Goblin(5,5);
        System.out.println(g.toString());

        Entities t = new Troll(3,3);
        System.out.println(t.toString());

        p.move(1, 0);

        System.out.println(p.toString());
    }
}
