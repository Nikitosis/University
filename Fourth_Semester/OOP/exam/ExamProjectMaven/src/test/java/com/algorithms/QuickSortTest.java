package com.algorithms;

import com.structures.CircularList;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuickSortTest {

    @Test
    public void test() {
        CircularList<Integer> list = new CircularList<>();
        list.addLast(3);
        list.addFirst(5);
        list.addLast(10);

        Comparator<Integer> comparator = (a, b)->{
            if(a<b){
                return -1;
            } else if(a.equals(b)) {
                return 0;
            } else {
                return 1;
            }
        };
        list.sort(AlgorithmFactory.quickSort(Integer.class), comparator);

        assertEquals(3, list.get(0));
        assertEquals(5, list.get(1));
        assertEquals(10, list.get(2));
    }
}
