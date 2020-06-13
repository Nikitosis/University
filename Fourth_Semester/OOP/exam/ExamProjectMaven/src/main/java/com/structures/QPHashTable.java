package com.structures;

import com.sun.org.apache.regexp.internal.RE;

import java.util.ArrayList;
import java.util.List;

public class QPHashTable<K, V> implements HashTable<K, V> {
    private final static int DEFAULT_SIZE = 60;

    public enum CellType{
        EMPTY, DELETED, FULL
    }
    private class Cell<K,V>{
        public K key;
        public V val;
        public CellType type;
        public Cell(K key, V val, CellType type) {
            this.key = key;
            this.val = val;
            this.type = type;
        }
    }

    private List<Cell<K,V>> buckets;

    public QPHashTable(int size) {
        initBuckets(size);
    }

    public QPHashTable() {
        initBuckets(DEFAULT_SIZE);
    }

    private void initBuckets(int size) {
        buckets = new ArrayList<>();
        for(int i=0;i<size;i++) {
            buckets.add(new Cell<K,V>(null, null, CellType.EMPTY));
        }
    }

    @Override
    public void put(K key, V val) {
        for(int i=0;i<buckets.size();i++) {
            Cell curCell = buckets.get(getCellIndex(key, i));
            if(curCell.type==CellType.EMPTY || curCell.type==CellType.DELETED) {
                curCell.type=CellType.FULL;
                curCell.val = val;
                curCell.key = key;
                return;
            }
        }
    }

    @Override
    public void remove(K key) {
        for(int i=0;i<buckets.size();i++) {
            Cell curCell = buckets.get(getCellIndex(key, i));
            if(curCell.type == CellType.FULL && curCell.key.equals(key)) {
                curCell.key = null;
                curCell.val = null;
                curCell.type = CellType.DELETED;
                return;
            }
            if(curCell.type == CellType.EMPTY) {
                return;
            }
        }
    }

    @Override
    public V get(K key) {
        for(int i=0;i<buckets.size();i++) {
            Cell curCell = buckets.get(getCellIndex(key, i));
            if(curCell.type == CellType.FULL && curCell.key.equals(key)) {
                return (V)curCell.val;
            }
            if(curCell.type == CellType.EMPTY) {
                return null;
            }
        }
        return null;
    }

    @Override
    public List<K> getAllKeys() {
        List<K> res = new ArrayList<>();
        for(Cell cell:buckets) {
            if(cell.type==CellType.FULL) {
                res.add((K)cell.key);
            }
        }
        return res;
    }

    private int getCellIndex(K key, int iteration) {
        return (key.hashCode()+iteration*iteration) % buckets.size();
    }
}
