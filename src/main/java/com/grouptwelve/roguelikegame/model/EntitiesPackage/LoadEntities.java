package com.grouptwelve.roguelikegame.model.EntitiesPackage;

public class LoadEntities {
    private LoadEntities() {}
    public static void load() {
        //Try loading Entities into memory to put them in the factory registry.
        try {
            Class.forName("com.grouptwelve.roguelikegame.model.EntitiesPackage.Player");
            Class.forName("com.grouptwelve.roguelikegame.model.EntitiesPackage.Enemies.Goblin");
            Class.forName("com.grouptwelve.roguelikegame.model.EntitiesPackage.Enemies.Troll");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error, couldn't load class. Make sure there is no typo!");
        }
    }
}
