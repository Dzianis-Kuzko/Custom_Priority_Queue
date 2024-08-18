package ru.clevertec.priority_queue;

import org.junit.jupiter.api.Test;
import ru.clevertec.priority_queue.api.Queue;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PriorityQueueTest {

    @Test
    void testSize() {
        Queue<Integer> queue = new PriorityQueue<>();

        assertEquals(0, queue.size(), "Queue should be empty initially");

        queue.add(10);
        assertEquals(1, queue.size(), "Queue size should be 1 after adding one element");


        queue.add(16);
        assertEquals(2, queue.size(), "Queue size should be 2 after adding another element");


        queue.add(3);
        assertEquals(3, queue.size(), "Queue size should be 3 after adding three elements");

        queue.poll();
        assertEquals(2, queue.size(), "Queue size should be 2 after polling one element");

        queue.poll();
        assertEquals(1, queue.size(), "Queue size should be 1 after polling another element");

        queue.poll();
        assertEquals(0, queue.size(), "Queue should be empty after polling all elements");
    }

    @Test
    void testPeek() {
        Queue<String> queue = new PriorityQueue<>();

        assertNull(queue.peek(), "Peek should return null for an empty queue");

        queue.add("Dima");
        queue.add("Anna");

        assertEquals("Anna", queue.peek(), "Peek should return the smallest element ('Anna')");

        assertEquals(2, queue.size(), "Queue size should remain 2 after calling peek()");

        assertEquals("Anna", queue.peek(), "Peek should consistently return 'Anna'");
    }

    @Test
    void testAddNullThrowsNullPointerException() {
        Queue<Character> queue = new PriorityQueue<>();

        assertThrows(NullPointerException.class, () -> {
            queue.add(null);
        });
    }

    @Test
    void testAddMoreElementsThanInitialCapacity() {
        Queue<Integer> queue = new PriorityQueue<>();

        queue.add(15);
        queue.add(10);
        queue.add(20);
        queue.add(25);
        queue.add(30);
        queue.add(5);
        queue.add(35);
        queue.add(40);
        queue.add(3);
        queue.add(50);

        assertEquals(10, queue.size(), "Queue size should be 10 after adding ten elements");

        assertEquals(3, queue.peek(), "The smallest element should be 3");

        assertEquals(3, queue.poll(), "The first element removed should be 3");
        assertEquals(5, queue.poll(), "The second element removed should be 5");
        assertEquals(10, queue.poll(), "The third element removed should be 10");
    }


    @Test
    void testWithComparator() {
        Queue<Integer> queue = new PriorityQueue<>(Comparator.reverseOrder());

        queue.add(15);
        queue.add(10);
        queue.add(20);
        queue.add(5);

        assertEquals(20, queue.poll(), "The first element removed should be 20");
        assertEquals(15, queue.poll(), "The second element removed should be 15");
        assertEquals(10, queue.poll(), "The third element removed should be 10");
        assertEquals(5, queue.poll(), "The fourth element removed should be 5");

        assertEquals(0, queue.size(), "Queue should be empty after removing all elements");
    }

    @Test
    void testAddNonComparableAndWithoutComparatorThrowsException() {
        PriorityQueue<Person> queue = new PriorityQueue<>();

        assertThrows(ClassCastException.class, () -> {
            queue.add(new Person());
        });
    }

    @Test
    void testPoll() {
        Queue<Integer> queue = new PriorityQueue<>();

        queue.add(10);
        queue.add(5);
        queue.add(20);
        queue.add(15);

        assertEquals(5, queue.poll(), "The first element removed should be 5");
        assertEquals(10, queue.poll(), "The second element removed should be 10");
        assertEquals(15, queue.poll(), "The third element removed should be 15");
        assertEquals(20, queue.poll(), "The fourth element removed should be 20");

        assertNull(queue.poll(), "Polling an empty queue should return null");
    }

    class Person {

    }
}