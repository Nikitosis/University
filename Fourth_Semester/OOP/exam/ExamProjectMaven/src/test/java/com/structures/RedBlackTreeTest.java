package com.structures;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.EmptyStackException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RedBlackTreeTest {

    @Test
    void test() {
        int[] values = {5, 8, 1, -4, 6, -2, 0, 7};

        Comparator<Integer> comparator = (a, b)->{
            if(a<b){
                return -1;
            } else if(a.equals(b)) {
                return 0;
            } else {
                return 1;
            }
        };

        RedBlackTree<Integer> rbTree = new RedBlackTree<>(comparator);
        for (int v : values) {
            rbTree.insert(v);
        }

        assertTrue(rbTree.contains(5));
        assertTrue(rbTree.contains(8));
        assertTrue(rbTree.contains(1));
        assertTrue(rbTree.contains(-4));
        assertTrue(rbTree.contains(6));
        assertTrue(rbTree.contains(-2));
        assertTrue(rbTree.contains(0));
        assertTrue(rbTree.contains(7));

        assertFalse(rbTree.contains(1000));

        assertEquals(values.length, rbTree.size());
    }

    @Test
    void testIterator() {
        int[] values = {8,1,5};

        Comparator<Integer> comparator = (a, b)->{
            if(a<b){
                return -1;
            } else if(a.equals(b)) {
                return 0;
            } else {
                return 1;
            }
        };

        RedBlackTree<Integer> rbTree = new RedBlackTree<>(comparator);
        for (int v : values) {
            rbTree.insert(v);
        }
        Iterator iterator = rbTree.iterator();

        assertEquals(1, iterator.next());
        assertEquals(5, iterator.next());
        assertEquals(8, iterator.next());
        assertThrows(EmptyStackException.class, ()->iterator.next());
    }
}
