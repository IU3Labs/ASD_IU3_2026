package ru.bmstu.lab02.task1c;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
}
