package com.structures;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class QPHashTableTest {
    @Test
    void test() {
        HashTable<String, String> hashTable = new QPHashTable<String, String>();
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
    void testCustomSize() {
        HashTable<String, String> hashTable = new QPHashTable<>(10);
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
