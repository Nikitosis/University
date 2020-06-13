package com.structures;

import com.algorithms.SortingAlgorithm;

import java.util.Comparator;

public interface List <T>{
    public Iterator<T> iterator();

    public void addLast(T data);

    public void addFirst(T data);

    public void add(int index, T data);

    public T get(int index);

    public void remove(int index);

    public void set(int index, T data);

    public int size();

    public void sort(SortingAlgorithm<T> sortingAlgorithm, Comparator<T> comparator);

    public T[] toArray();
}
