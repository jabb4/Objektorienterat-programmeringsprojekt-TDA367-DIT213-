package com.grouptwelve.roguelikegame.model.entities;

public class LoadEntities {
    private LoadEntities() {}
    public static void load() {
        //Try loading Entities into memory to put them in the factory registry.
        try {
            Class.forName("com.grouptwelve.roguelikegame.model.entities.Player");
            Class.forName("com.grouptwelve.roguelikegame.model.entities.enemies.Goblin");
            Class.forName("com.grouptwelve.roguelikegame.model.entities.enemies.Troll");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error, couldn't load class. Make sure there is no typo!");
        }
    }
}
