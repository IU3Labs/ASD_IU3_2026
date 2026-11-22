package ru.bmstu.lab02.task1c;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Юнит-тесты для {@link PriorityQueueRemovable} (задача 1C).
 */
class PriorityQueueRemovableTest {

    @Test
    void shouldRemoveRootElementAndRestoreHeap() {
        PriorityQueueRemovable<Integer> queue = new PriorityQueueRemovable<>();
        queue.add(10);
        queue.add(20);
        queue.add(30);
        queue.add(40);

        assertTrue(queue.remove(10));
        assertEquals(3, queue.size());
        assertEquals(20, queue.poll());
        assertEquals(30, queue.poll());
        assertEquals(40, queue.poll());
    }

    @Test
    void shouldRemoveElementFromMiddleAndTriggerSiftDown() {
        PriorityQueueRemovable<Integer> queue = new PriorityQueueRemovable<>();
        int[] values = {10, 20, 30, 40, 50, 60, 70};
        for (int value : values) {
            queue.add(value);
        }

        assertTrue(queue.remove(20));
        assertEquals(6, queue.size());
        int[] expected = {10, 30, 40, 50, 60, 70};
        for (int item : expected) {
            assertEquals(item, queue.poll());
        }
    }

    @Test
    void shouldRemoveElementFromMiddleAndTriggerSiftUp() {
        PriorityQueueRemovable<Integer> queue = new PriorityQueueRemovable<>();
        int[] values = {10, 50, 15, 60, 70, 20, 12};
        for (int value : values) {
            queue.add(value);
        }

        assertTrue(queue.remove(50));
        int[] expected = {10, 12, 15, 20, 60, 70};
        for (int item : expected) {
            assertEquals(item, queue.poll());
        }
    }

    @Test
    void shouldRemoveLastLeafElementWithoutBreakingHeap() {
        PriorityQueueRemovable<Integer> queue = new PriorityQueueRemovable<>();
        queue.add(1);
        queue.add(2);
        queue.add(3);

        assertTrue(queue.remove(3));
        assertEquals(2, queue.size());
        assertEquals(1, queue.poll());
        assertEquals(2, queue.poll());
    }

    @Test
    void shouldRemoveOnlyFirstOccurrenceOfDuplicateElement() {
        PriorityQueueRemovable<Integer> queue = new PriorityQueueRemovable<>();
        queue.add(5);
        queue.add(2);
        queue.add(5);
        queue.add(8);

        assertTrue(queue.remove(5));
        assertEquals(3, queue.size());
        assertEquals(2, queue.poll());
        assertEquals(5, queue.poll());
        assertEquals(8, queue.poll());
    }

    @Test
    void shouldReturnFalseWhenRemovingNonExistentElement() {
        PriorityQueueRemovable<Integer> queue = new PriorityQueueRemovable<>();
        queue.add(5);
        queue.add(10);

        assertFalse(queue.remove(99));
        assertEquals(2, queue.size());
    }

    @Test
    void shouldReturnFalseWhenRemovingFromEmptyQueue() {
        PriorityQueueRemovable<Integer> queue = new PriorityQueueRemovable<>();
        assertFalse(queue.remove(1));
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldReturnFalseWhenRemovingNull() {
        PriorityQueueRemovable<Integer> queue = new PriorityQueueRemovable<>();
        queue.add(1);
        assertFalse(queue.remove(null));
        assertEquals(1, queue.size());
    }

    @Test
    void shouldBecomeEmptyAfterRemovingSingleElement() {
        PriorityQueueRemovable<Integer> queue = new PriorityQueueRemovable<>();
        queue.add(42);

        assertTrue(queue.remove(42));
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
        assertThrows(NoSuchElementException.class, queue::peek);
    }

    @Test
    void shouldMaintainHeapInvariantAfterMultipleRemovals() {
        PriorityQueueRemovable<Integer> queue = new PriorityQueueRemovable<>();
        for (int i = 1; i <= 20; i++) {
            queue.add(i);
        }
        assertTrue(queue.remove(1));
        assertTrue(queue.remove(10));
        assertTrue(queue.remove(20));
        assertTrue(queue.remove(7));

        assertEquals(16, queue.size());
        int prev = Integer.MIN_VALUE;
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            assertTrue(cur > prev);
            prev = cur;
        }
    }
}
