package com.del;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class Cache {
    private static Cache instance;

    public static Cache getInstance() {
        if(instance == null) {
            instance = new Cache();
        }
        return instance;
    }

    private Map<String, String> map;

    //private constructor to avoid new instantiations
    private Cache() {

    }

    public void put(String key, String value) {
        map.put(key, value);
    }

    public String get(String key) {
        return map.get(key);
    }
}