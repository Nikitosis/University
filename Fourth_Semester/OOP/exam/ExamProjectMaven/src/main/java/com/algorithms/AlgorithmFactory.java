package com.algorithms;

public class AlgorithmFactory<T> {
    public static <T> SortingAlgorithm<T> selectionSort(Class<T> tClass) {
        return new SelectionSort<T>();
    }

    public static <T> SortingAlgorithm<T> quickSort(Class<T> tClass) {
        return new QuickSort<T>();
    }

    public static <T> SortingAlgorithm<T> heapSort(Class<T> tClass) {
        return new HeapSort<>();
    }
}
