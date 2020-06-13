package com.algorithms;

import java.util.Arrays;
import java.util.Comparator;

public interface SortingAlgorithm <T> {
    void sort(T[] arr, int size, Comparator<T> comparator);
}
