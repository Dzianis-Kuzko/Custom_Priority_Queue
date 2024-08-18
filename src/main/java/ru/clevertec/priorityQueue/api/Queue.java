package ru.clevertec.priorityQueue.api;

public interface Queue<E> {
    boolean add(E e);

    E peek();

    E poll();

    int size();
}
