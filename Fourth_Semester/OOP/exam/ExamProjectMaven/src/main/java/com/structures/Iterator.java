package com.structures;

public interface Iterator<T> {
    default boolean hasNext() {throw new UnsupportedOperationException("hasNext");}
    default T next() {throw new UnsupportedOperationException("next");}
    default void remove() {throw new UnsupportedOperationException("remove");}
    default void set(T e) {throw new UnsupportedOperationException("set");}
    default void add(T e) {throw new UnsupportedOperationException("add");}
    default T get() {throw new UnsupportedOperationException("get");}
}
