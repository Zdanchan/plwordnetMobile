package com.pwr.bzapps.plwordnetmobile.database.entity.cache;

import java.util.LinkedHashMap;
import java.util.Map;

public class EntityCache<K,V> extends LinkedHashMap<K,V> {

    private int max_cache_size;

    public EntityCache(int max_cache_size){
        super();
        this.max_cache_size=max_cache_size;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry eldest) {
        return size() > max_cache_size;
    }

    public int getMax_cache_size() {
        return max_cache_size;
    }

    public void setMax_cache_size(int max_cache_size) {
        this.max_cache_size = max_cache_size;
    }
}
