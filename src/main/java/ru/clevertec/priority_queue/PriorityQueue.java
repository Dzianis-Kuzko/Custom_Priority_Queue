package ru.clevertec.priority_queue;

import ru.clevertec.priority_queue.api.Queue;

import java.util.Arrays;
import java.util.Comparator;

public class PriorityQueue<E> implements Queue<E> {
    private static final int DEFAULT_INITIAL_CAPACITY = 8;

    private final Comparator<? super E> comparator;

    private Object[] elements;

    private int size;

    public PriorityQueue() {
        this(null);
    }

    public PriorityQueue(Comparator<? super E> comparator) {
        this.elements = new Object[DEFAULT_INITIAL_CAPACITY];
        this.comparator = comparator;
        this.size = 0;
    }


    @Override
    public boolean add(E e) {
        if (e == null) {
            throw new NullPointerException();
        }

        if (comparator == null && !(e instanceof Comparable)) {
            throw new ClassCastException();
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
    public E poll() {
        E result = (E) elements[0];

        if (size > 0) {
            elements[0] = elements[size - 1];
            elements[size - 1] = null;
            size--;

            if (comparator == null) {
                siftDownComparable();
            } else {
                siftDownUsingComparator();
            }

        }
        return result;
    }

    private void siftUp(E e) {
        int k = size;

        while (k > 0) {
            int parent = (k - 1) / 2;
            E p = (E) elements[parent];

            int comparisonResult;

            if (comparator == null) {
                comparisonResult = ((Comparable<? super E>) e).compareTo(p);
            } else {
                comparisonResult = comparator.compare(e, p);
            }

            if (comparisonResult >= 0) {
                break;
            }

            elements[k] = p;
            k = parent;
        }
        elements[k] = e;
    }

    public void siftDownComparable() {
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

    public void siftDownUsingComparator() {
        int parent = 0;
        int halfSize = size / 2;

        while (parent < halfSize) {
            int left = parent * 2 + 1;
            int right = left + 1;

            E p = (E) elements[parent];

            if (right < size && (comparator.compare((E) elements[left], (E) elements[right]) > 0)) {
                if (comparator.compare(p, (E) elements[right]) <= 0) {
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
