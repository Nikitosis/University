package com.algorithms;

import java.util.Comparator;

public class SelectionSort<T> implements SortingAlgorithm<T> {
    @Override
    public void sort(T[] arr, int size, Comparator<T> comparator) {
        for(int pivot = 0;pivot < size-1; pivot++) {
            int minIndex = findMinIndex(arr, size, pivot, comparator);
            T temp = arr[pivot];
            arr[pivot] = arr[minIndex];
            arr[minIndex] = temp;
        }
    }

    private int findMinIndex(T[] arr, int size, int start, Comparator<T> comparator) {
        int index = start;
        T min = arr[start];
        for(int i=start;i<size;i++) {
            if(comparator.compare(min, arr[i]) > 0) {
                min = arr[i];
                index = i;
            }
        }
        return index;
    }
}
