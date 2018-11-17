package com.pwr.bzapps.plwordnetmobile.service.cache;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class StringCache {
    private static Map<String,StringWithDate> cache;

    public static void putString(String key,String value){
        checkIfCacheIsAvailable();
        cache.put(key,new StringWithDate(new Date(),value));
    }

    public static String getStringValue(String key){
        checkIfCacheIsAvailable();
        StringWithDate stringWithDate = cache.get(key);
        return (stringWithDate != null ? stringWithDate.value : null);
    }

    public static Date getDate(String key){
        checkIfCacheIsAvailable();
        StringWithDate stringWithDate = cache.get(key);
        return (stringWithDate != null ? stringWithDate.creation_date : null);
    }

    public static void clearCache(){
        checkIfCacheIsAvailable();
        cache.clear();
    }

    private static void checkIfCacheIsAvailable(){
        if(cache==null){
            cache= new HashMap<String,StringWithDate>();
        }
    }

    private static class StringWithDate{
        Date creation_date;
        String value;

        public StringWithDate(Date creation_date, String value) {
            this.creation_date = creation_date;
            this.value = value;
        }

        public Date getCreation_date() {
            return creation_date;
        }

        public void setCreation_date(Date creation_date) {
            this.creation_date = creation_date;
        }
    }
}
