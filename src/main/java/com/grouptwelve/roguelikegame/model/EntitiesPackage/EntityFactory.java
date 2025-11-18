package EntitiesPackage;

import java.util.HashMap;

public class EntityFactory {
    private static final EntityFactory instance = new EntityFactory();
    private EntityFactory() {}
    public static EntityFactory getInstance() {
        return instance;
    }

    private final HashMap<String,Entity> entityRegistry = new HashMap<>();

    public void reigisterEnitity(String name, Entity entity){
        entityRegistry.put(name,entity);
    }

    public Entity createEntity(String name, double x, double y){
        return ((Entity)entityRegistry.get(name)).createEntity(x,y);
    }


}
