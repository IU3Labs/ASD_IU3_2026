package ru.bmstu.lab02.task1d;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Юнит-тесты для {@link PriorityQueueHeapify} (задача 1D).
 */
class PriorityQueueHeapifyTest {

    @Test
    void shouldBuildValidHeapFromUnsortedArray() {
        Integer[] source = {9, 4, 7, 1, -2, 6, 5};
        PriorityQueueHeapify<Integer> queue = new PriorityQueueHeapify<>(source);

        assertEquals(7, queue.size());
        int[] expected = {-2, 1, 4, 5, 6, 7, 9};
        for (int item : expected) {
            assertEquals(item, queue.poll());
        }
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldHandleEmptyArrayInConstructor() {
        Integer[] empty = new Integer[0];
        PriorityQueueHeapify<Integer> queue = new PriorityQueueHeapify<>(empty);

        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
        assertThrows(NoSuchElementException.class, queue::poll);
    }

    @Test
    void shouldHandleSingleElementArray() {
        Integer[] single = {42};
        PriorityQueueHeapify<Integer> queue = new PriorityQueueHeapify<>(single);

        assertEquals(1, queue.size());
        assertEquals(42, queue.peek());
        assertEquals(42, queue.poll());
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldHandleReverseSortedArray() {
        Integer[] reverse = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        PriorityQueueHeapify<Integer> queue = new PriorityQueueHeapify<>(reverse);

        for (int expected = 1; expected <= 10; expected++) {
            assertEquals(expected, queue.poll());
        }
    }

    @Test
    void shouldHandleAlreadySortedArray() {
        Integer[] sorted = {1, 2, 3, 4, 5};
        PriorityQueueHeapify<Integer> queue = new PriorityQueueHeapify<>(sorted);

        for (int expected = 1; expected <= 5; expected++) {
            assertEquals(expected, queue.poll());
        }
    }
}

