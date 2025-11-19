package com.grouptwelve.roguelikegame.model.EntitiesPackage;

import java.util.HashMap;

public class EntityFactory {
    private static final EntityFactory instance = new EntityFactory();
    private EntityFactory() {}
    public static EntityFactory getInstance() {
        return instance;
    }

    private final HashMap<String,Entity> entityRegistry = new HashMap<>();

    public void registerEntity(String name, Entity entity){
        entityRegistry.put(name,entity);
    }

    public Entity createEntity(String name, double x, double y){
        Entity entity = entityRegistry.get(name);
        if (entity == null){
            throw new IllegalArgumentException("Entity name '" + name + "' is not registered in the entity registry.");
        }
        return entity.createEntity(x,y);
    }


}
