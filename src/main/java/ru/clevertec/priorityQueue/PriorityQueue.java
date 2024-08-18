package ru.clevertec.priorityQueue;

import ru.clevertec.priorityQueue.api.Queue;

import java.util.Arrays;

public class PriorityQueue<E> implements Queue<E> {
    private static final int DEFAULT_INITIAL_CAPACITY = 8;

    private Object[] elements;

    private int size;

    public PriorityQueue() {
        this.elements = new Object[DEFAULT_INITIAL_CAPACITY];
        this.size = 0;
    }

    @Override
    public boolean add(E e) {
        if (e == null) {
            throw new NullPointerException();
        }
        if (elements.length == size) {
            grow();
        }

        siftUp(e);
        size++;

        return true;
    }

    @Override
    public E peek() {
        return (E) elements[0];
    }

    @Override
    public E pool() {
        E result = (E) elements[0];

        if (size > 0) {
            elements[0] = elements[size - 1];
            elements[size - 1] = null;
            size--;
            siftDown();
        }
        return result;
    }

    @Override
    public int size() {
        return this.size;
    }

    private void siftUp(E e) {
        int k = size;

        while (k > 0) {
            int parent = (k - 1) / 2;
            E p = (E) elements[parent];
            if (((Comparable<? super E>) e).compareTo(p) > 0) {
                break;
            }
            elements[k] = p;
            k = parent;
        }
        elements[k] = e;
    }

    public void siftDown() {
        int parent = 0;
        int halfSize = size / 2;

        while (parent < halfSize) {
            int left = parent * 2 + 1;
            int right = left + 1;

            Comparable<? super E> p = (Comparable<? super E>) elements[parent];

            if (right < size && ((Comparable<? super E>) elements[left]).compareTo((E) elements[right]) > 0) {
                if (p.compareTo((E) elements[right]) <= 0) {
                    break;
                }
                swap(parent, right, elements);
                parent = right;

            } else {
                swap(parent, left, elements);
                parent = left;
            }
        }
    }

    private void grow() {
        elements = Arrays.copyOf(elements, size + size / 2);
    }

    private static <E> void swap(int parent, int child, Object[] elements) {
        E temp = (E) elements[child];
        elements[child] = elements[parent];
        elements[parent] = temp;
    }

    @Override
    public String toString() {
        return "PriorityQueue{" +
                "elements=" + Arrays.toString(elements) +
                '}';
    }
}
