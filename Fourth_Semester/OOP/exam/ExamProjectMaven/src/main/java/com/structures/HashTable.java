package com.structures;

import java.util.List;

public interface HashTable<K, V> {
    void put(K key, V val);
    void remove(K key);
    V get(K key);
    List<K> getAllKeys();
}
