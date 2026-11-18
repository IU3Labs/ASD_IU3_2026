
package ru.bmstu.lab2;

import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

/**
 * Тесты для собственной очереди с приоритетом.
 */
class PriorityQueueCustomTest {

    @Test
    void newQueueIsEmpty() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();

        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
    }

    @Test
    void addIncreasesSize() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();

        queue.add(10);
        queue.add(20);

        assertEquals(2, queue.size());
        assertFalse(queue.isEmpty());
    }

    @Test
    void peekReturnsMinimum() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();

        queue.add(8);
        queue.add(3);
        queue.add(10);
        queue.add(1);

        assertEquals(1, queue.peek());
    }

    @Test
    void peekDoesNotRemoveElement() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();

        queue.add(5);

        assertEquals(5, queue.peek());
        assertEquals(1, queue.size());
    }

    @Test
    void pollReturnsElementsInAscendingOrder() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();

        queue.add(8);
        queue.add(3);
        queue.add(10);
        queue.add(1);
        queue.add(5);

        assertEquals(1, queue.poll());
        assertEquals(3, queue.poll());
        assertEquals(5, queue.poll());
        assertEquals(8, queue.poll());
        assertEquals(10, queue.poll());
    }

    @Test
    void duplicateElementsAreHandled() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();

        queue.add(4);
        queue.add(2);
        queue.add(4);
        queue.add(2);

        assertEquals(2, queue.poll());
        assertEquals(2, queue.poll());
        assertEquals(4, queue.poll());
        assertEquals(4, queue.poll());
    }

    @Test
    void negativeNumbersAreHandled() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();

        queue.add(-1);
        queue.add(-10);
        queue.add(0);
        queue.add(5);

        assertEquals(-10, queue.poll());
        assertEquals(-1, queue.poll());
        assertEquals(0, queue.poll());
        assertEquals(5, queue.poll());
    }

    @Test
    void pollFromEmptyQueueThrowsException() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();

        assertThrows(NoSuchElementException.class, queue::poll);
    }

    @Test
    void peekFromEmptyQueueThrowsException() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();

        assertThrows(NoSuchElementException.class, queue::peek);
    }

    @Test
    void arrayExpandsAutomatically() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();

        for (int i = 100; i >= 0; i--) {
            queue.add(i);
        }

        assertEquals(101, queue.size());

        for (int i = 0; i <= 100; i++) {
            assertEquals(i, queue.poll());
        }

        assertTrue(queue.isEmpty());
    }

    @Test
    void stringsUseNaturalOrdering() {
        PriorityQueueCustom<String> queue = new PriorityQueueCustom<>();

        queue.add("banana");
        queue.add("apple");
        queue.add("cherry");

        assertEquals("apple", queue.poll());
        assertEquals("banana", queue.poll());
        assertEquals("cherry", queue.poll());
    }

    @Test
    void addingNullThrowsException() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();

        assertThrows(NullPointerException.class, () -> queue.add(null));
    }
}
