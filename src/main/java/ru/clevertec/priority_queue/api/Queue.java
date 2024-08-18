package ru.clevertec.priority_queue.api;

public interface Queue<E> {
    boolean add(E e);

    E peek();

    E poll();

    int size();
}
