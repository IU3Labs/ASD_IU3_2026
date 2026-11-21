
package ru.bmstu.lab2;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Модульные тесты очереди с приоритетом для задания 1A.
 */
class PriorityQueueCustomTest {

    @Test
    void newQueueIsEmpty() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();

        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
    }

    @Test
    void addingOneElementChangesSize() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();

        queue.add(10);

        assertEquals(1, queue.size());
        assertFalse(queue.isEmpty());
    }

    @Test
    void peekReturnsMinimum() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(7);
        queue.add(2);
        queue.add(5);

        assertEquals(2, queue.peek());
    }

    @Test
    void peekDoesNotRemoveElement() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(4);

        assertEquals(4, queue.peek());
        assertEquals(1, queue.size());
    }

    @Test
    void pollReturnsElementsInAscendingOrder() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(8);
        queue.add(3);
        queue.add(6);
        queue.add(1);

        assertEquals(1, queue.poll());
        assertEquals(3, queue.poll());
        assertEquals(6, queue.poll());
        assertEquals(8, queue.poll());
    }

    @Test
    void pollDecreasesSize() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(3);
        queue.add(1);

        queue.poll();

        assertEquals(1, queue.size());
    }

    @Test
    void pollLastElementMakesQueueEmpty() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(9);

        assertEquals(9, queue.poll());
        assertTrue(queue.isEmpty());
    }

    @Test
    void duplicateElementsArePreserved() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(2);
        queue.add(2);
        queue.add(1);

        assertEquals(1, queue.poll());
        assertEquals(2, queue.poll());
        assertEquals(2, queue.poll());
    }

    @Test
    void negativeNumbersAreOrderedCorrectly() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(-3);
        queue.add(0);
        queue.add(-10);

        assertEquals(-10, queue.poll());
        assertEquals(-3, queue.poll());
        assertEquals(0, queue.poll());
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
    void pollOnEmptyQueueThrowsException() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();

        assertThrows(NoSuchElementException.class, queue::poll);
    }

    @Test
    void peekOnEmptyQueueThrowsException() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();

        assertThrows(NoSuchElementException.class, queue::peek);
    }
}
