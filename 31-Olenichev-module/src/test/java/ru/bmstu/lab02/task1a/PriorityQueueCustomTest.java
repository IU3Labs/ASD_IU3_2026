package ru.bmstu.lab02.task1a;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Юнит-тесты для {@link PriorityQueueCustom} (задача 1A).
 */
class PriorityQueueCustomTest {

    @Test
    void shouldAddAndPeekSingleElement() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(42);
        assertEquals(42, queue.peek());
        assertEquals(1, queue.size());
    }

    @Test
    void shouldReturnMinElementWhenPolling() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(5);
        queue.add(1);
        queue.add(3);
        assertEquals(1, queue.poll());
        assertEquals(3, queue.poll());
        assertEquals(5, queue.poll());
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldUpdateSizeAndEmptyStatusCorrectly() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
        queue.add(10);
        assertFalse(queue.isEmpty());
        assertEquals(1, queue.size());
    }

    @Test
    void shouldNotRemoveElementWhenPeeking() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(7);
        queue.peek();
        assertEquals(1, queue.size());
        assertEquals(7, queue.peek());
    }
        @Test
    void shouldThrowExceptionWhenPollingEmptyQueue() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        assertThrows(NoSuchElementException.class, queue::poll);
    }

    @Test
    void shouldThrowExceptionWhenPeekingEmptyQueue() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        assertThrows(NoSuchElementException.class, queue::peek);
    }

    @Test
    void shouldThrowExceptionWhenAddingNull() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        assertThrows(IllegalArgumentException.class, () -> queue.add(null));
    }

    @Test
    void shouldHandleDuplicateElements() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(2);
        queue.add(2);
        queue.add(2);
        assertEquals(2, queue.poll());
        assertEquals(2, queue.poll());
        assertEquals(2, queue.poll());
        assertTrue(queue.isEmpty());
    }

}
