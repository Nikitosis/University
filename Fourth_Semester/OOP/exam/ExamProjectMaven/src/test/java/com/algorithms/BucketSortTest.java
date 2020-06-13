package com.algorithms;

import com.structures.CircularList;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BucketSortTest {
    @Test
    public void test() {
        Integer[] arr = new Integer[3];
        arr[0] = 5;
        arr[1] = 10;
        arr[2] = 3;

        Comparator<Integer> comparator = (a, b)->{
            if(a<b){
                return -1;
            } else if(a.equals(b)) {
                return 0;
            } else {
                return 1;
            }
        };

        BucketSort bucketSort = new BucketSort();
        bucketSort.sort(arr, 3, comparator);

        assertEquals(3, arr[0]);
        assertEquals(5, arr[1]);
        assertEquals(10, arr[2]);
    }


}
