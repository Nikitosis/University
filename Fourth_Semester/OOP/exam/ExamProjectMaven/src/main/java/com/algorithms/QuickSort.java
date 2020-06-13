package com.algorithms;

import java.util.Comparator;

public class QuickSort<T> implements SortingAlgorithm<T> {
    @Override
    public void sort(T[] arr, int size, Comparator<T> comparator) {
        quickSort(arr, 0, size-1, comparator);
    }

    private void quickSort(T[] arr, int low, int high, Comparator<T> comparator) {
        if(low < high) {
            int partIndex = partition(arr, low, high, comparator);

            quickSort(arr, low, partIndex - 1, comparator);
            quickSort(arr, partIndex+1, high, comparator);
        }
    }

    private int partition(T[] arr, int low, int high, Comparator<T> comparator) {
        T pivot = arr[high];

        int smallerIndex = low - 1;

        for(int i=low;i<=high-1;i++) {
            if(comparator.compare(arr[i], pivot) < 0) {
                smallerIndex++;
                T temp = arr[smallerIndex];
                arr[smallerIndex] = arr[i];
                arr[i] = temp;
            }
        }

        T temp = arr[smallerIndex + 1];
        arr[smallerIndex+1] = arr[high];
        arr[high] = temp;

        return smallerIndex+1;
    }
}
