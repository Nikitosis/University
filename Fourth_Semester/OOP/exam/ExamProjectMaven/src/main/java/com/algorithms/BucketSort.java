package com.algorithms;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class BucketSort{
    public <T extends Integer> void sort(T[] arr, int size, Comparator<T> comparator) {
        final int numberOfBuckets = (int) Math.sqrt(size);
        List<List<T>> buckets = new ArrayList<>(numberOfBuckets);

        for(int i = 0; i < numberOfBuckets; i++) {
            buckets.add(new ArrayList<>());
        }

        T max = findMax(arr, size);

        for (T num : arr) {
            buckets.get(hash(num, max, numberOfBuckets)).add(num);
        }

        for(List<T> bucket  : buckets){
            bucket.sort(comparator);
        }

        int curIndex = 0;
        for(List<T> bucket:buckets) {
            for(T data: bucket) {
                arr[curIndex] = data;
                curIndex++;
            }
        }
    }

    private <T extends Integer> T findMax(T[] arr, int size) {
        T maxNum = arr[0];
        for(int i=0;i<size;i++) {
            if(maxNum.compareTo(arr[i])>0) {
                maxNum = arr[i];
            }
        }
        return maxNum;
    }

    private int hash(int i, int max, int numberOfBuckets) {
        return (int) ((double) i / max * (numberOfBuckets - 1));
    }
}
