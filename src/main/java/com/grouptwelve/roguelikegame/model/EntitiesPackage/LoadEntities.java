package EntitiesPackage;

public class LoadEntities {
    private LoadEntities() {}
    public static void load() {
        //Try loading Entities into memory to put them in the factory registry.
        try {
            Class.forName("EntitiesPackage.Player");
            Class.forName("EntitiesPackage.Enemies.Goblin");
            Class.forName("EntitiesPackage.Enemies.Troll");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
