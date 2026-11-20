package ru.bmstu.lab2.task_1;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.bmstu.lab2.task_1.QueueTestSupport.drain;

import org.junit.jupiter.api.Test;

/** Проверки очереди: PriorityQueueRemovalTest. */
class PriorityQueueRemovalTest {

    @Test
    void shouldRemoveRoot() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(new Integer[] {5, 1, 3, 2});
        assertTrue(queue.remove(1));
        assertArrayEquals(new int[] {2, 3, 5}, drain(queue));
    }

    @Test
    void shouldSiftUpAfterRemovingMiddleElement() {
        PriorityQueueCustom<Integer> queue =
                new PriorityQueueCustom<>(new Integer[] {1, 10, 2, 11, 12, 3, 4});
        assertTrue(queue.remove(11));
        assertArrayEquals(new int[] {1, 2, 3, 4, 10, 12}, drain(queue));
    }

    @Test
    void shouldSiftDownAfterRemovingMiddleElement() {
        PriorityQueueCustom<Integer> queue =
                new PriorityQueueCustom<>(new Integer[] {1, 2, 3, 4, 5, 6, 7});
        assertTrue(queue.remove(2));
        assertArrayEquals(new int[] {1, 3, 4, 5, 6, 7}, drain(queue));
    }

    @Test
    void shouldRemoveLastElement() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(new Integer[] {1, 2, 3});
        assertTrue(queue.remove(3));
        assertArrayEquals(new int[] {1, 2}, drain(queue));
    }

    @Test
    void shouldRemoveOnlyOneDuplicate() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(new Integer[] {2, 2, 2});
        assertTrue(queue.remove(2));
        assertEquals(2, queue.size());
        assertArrayEquals(new int[] {2, 2}, drain(queue));
    }

    @Test
    void shouldReturnFalseForMissingElement() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(new Integer[] {1, 2});
        assertFalse(queue.remove(9));
        assertFalse(queue.remove(null));
        assertEquals(2, queue.size());
    }

    @Test
    void shouldReturnFalseWhenRemovingFromEmptyQueue() {
        assertFalse(new PriorityQueueCustom<Integer>().remove(1));
    }
}
