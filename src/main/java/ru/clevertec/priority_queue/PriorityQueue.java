package ru.clevertec.priority_queue;

import ru.clevertec.priority_queue.api.Queue;

import java.util.Arrays;
import java.util.Comparator;

public class PriorityQueue<E> implements Queue<E> {
    private static final int DEFAULT_INITIAL_CAPACITY = 8;

    private final Comparator<? super E> comparator;
    private E[] elements;
    private int size;

    public PriorityQueue() {
        this(null);
    }

    public PriorityQueue(Comparator<? super E> comparator) {
        this.elements = (E[]) new Object[DEFAULT_INITIAL_CAPACITY];
        this.comparator = comparator;
        this.size = 0;
    }

    @Override
    public boolean add(E e) {
        if (e == null) {
            throw new NullPointerException();
        }

        if (comparator == null && !(e instanceof Comparable)) {
            throw new ClassCastException("Elements must be comparable or a comparator must be provided");
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
        return elements[0];
    }

    @Override
    public E poll() {
        if (size == 0) {
            return null;
        }

        E result = elements[0];
        elements[0] = elements[size - 1];
        elements[size - 1] = null;
        size--;

        siftDown();

        return result;
    }

    private void siftUp(E e) {
        int k = size;

        while (k > 0) {
            int parent = (k - 1) / 2;
            E p = elements[parent];

            if (compare(e, p) >= 0) {
                break;
            }

            elements[k] = p;
            k = parent;
        }
        elements[k] = e;
    }

    private void siftDown() {
        int parent =0;
        int halfSize = size / 2;

        while (parent < halfSize) {
            int left = parent * 2 + 1;
            int right = left + 1;
            int smallest = left;

            if (right < size && compare(elements[left], elements[right]) > 0) {
                smallest = right;
            }

            if (compare(elements[parent], elements[smallest]) <= 0) {
                break;
            }

            swap(parent, smallest, elements);
            parent = smallest;
        }
    }

    private int compare(E e1, E e2) {
        if (comparator == null) {
            return ((Comparable<? super E>) e1).compareTo(e2);
        } else {
            return comparator.compare(e1, e2);
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
    public int size() {
        return this.size;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");

        for (int i = 0; i < size; i++) {
            sb.append(elements[i]);
            if (i < size - 1) {
                sb.append(", ");
            }
        }

        sb.append("]");
        return sb.toString();
    }
}
