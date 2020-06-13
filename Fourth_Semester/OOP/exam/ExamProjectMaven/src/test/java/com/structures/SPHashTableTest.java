package com.structures;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SPHashTableTest {
    @Test
    void test() {
        HashTable<String, String> hashTable = new SPHashTable<String, String>();
        assertNull(hashTable.get("any"));
        hashTable.put("one", "Guccia");
        hashTable.put("two", "Muccia");
        hashTable.put("nine", "CoronaTime");
        hashTable.put("size", "XXL");

        assertEquals("Guccia", hashTable.get("one"));
        assertEquals("XXL", hashTable.get("size"));
        assertEquals(4, hashTable.getAllKeys().size());
    }

    @Test
    void testCustomCollection() {
        HashTable<String, String> hashTable = new SPHashTable<String, String>(64, LinkedList.class);
        assertNull(hashTable.get("any"));
        hashTable.put("one", "Guccia");
        hashTable.put("two", "Muccia");
        hashTable.put("nine", "CoronaTime");
        hashTable.put("size", "XXL");

        assertEquals("Guccia", hashTable.get("one"));
        assertEquals("XXL", hashTable.get("size"));
        assertEquals(4, hashTable.getAllKeys().size());
    }
}
