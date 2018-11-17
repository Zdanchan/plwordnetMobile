package com.pwr.bzapps.plwordnetmobile.database.entity;

import com.pwr.bzapps.plwordnetmobile.database.entity.cache.EntityCache;

public class EntityManager {
    static private EntityCache<String,Entity> entitiy_cache;
    static private final int MAX_CACHE_SIZE = 100;

    public static void putEntity(Entity entity){
        if(entitiy_cache ==null){
            entitiy_cache = new EntityCache<String, Entity>(MAX_CACHE_SIZE);
        }
        entitiy_cache.put(entity.getEntityID(),entity);
    }

    public static boolean contains(String id){
        if(entitiy_cache ==null){
            entitiy_cache = new EntityCache<String, Entity>(MAX_CACHE_SIZE);
        }
        return entitiy_cache.containsKey(id);
    }

    public static Entity getEntity(String id){
        if(entitiy_cache ==null){
            entitiy_cache = new EntityCache<String, Entity>(MAX_CACHE_SIZE);
        }
        return entitiy_cache.get(id);
    }

    public static void clearCache(){
        if(entitiy_cache ==null){
            entitiy_cache = new EntityCache<String, Entity>(MAX_CACHE_SIZE);
        }
        else{
            entitiy_cache.clear();
        }
    }
}
