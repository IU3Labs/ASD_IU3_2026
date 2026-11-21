package ru.bmstu.lab2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Random;
import org.junit.jupiter.api.Test;

/** Проверки очереди: обычные случаи, границы и сравнение со стандартной реализацией. */
class PriorityQueueCustomTest {
    private PriorityQueueCustom<Integer> createQueue(int[] values) {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (int value : values) {
            queue.add(value);
        }
        return queue;
    }

    private void assertOrder(PriorityQueueCustom<Integer> queue, int[] expected) {
        assertEquals(expected.length, queue.size());
        for (int value : expected) {
            assertEquals(value, queue.poll());
        }
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldStartEmpty() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
    }

    @Test
    void shouldAddAndPollOneItem() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(7);
        assertFalse(queue.isEmpty());
        assertEquals(1, queue.size());
        assertEquals(7, queue.poll());
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldKeepItemAfterPeek() {
        PriorityQueueCustom<Integer> queue = createQueue(new int[] {4, 1, 3});
        assertEquals(1, queue.peek());
        assertEquals(1, queue.peek());
        assertEquals(3, queue.size());
    }

    @Test
    void shouldPollInAscendingOrder() {
        PriorityQueueCustom<Integer> queue = createQueue(new int[] {8, 3, 5, 1});
        assertOrder(queue, new int[] {1, 3, 5, 8});
    }

    @Test
    void shouldKeepDuplicates() {
        PriorityQueueCustom<Integer> queue = createQueue(new int[] {3, 1, 3, 1});
        assertOrder(queue, new int[] {1, 1, 3, 3});
    }

    @Test
    void shouldHandleNegativeAndExtremeValues() {
        PriorityQueueCustom<Integer> queue = createQueue(new int[] {Integer.MAX_VALUE, 0, Integer.MIN_VALUE, -1});
        assertOrder(queue, new int[] {Integer.MIN_VALUE, -1, 0, Integer.MAX_VALUE});
    }

    @Test
    void shouldGrowArray() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (int i = 1000; i >= 0; i--) {
            queue.add(i);
        }
        for (int i = 0; i <= 1000; i++) {
            assertEquals(i, queue.poll());
        }
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldAllowAddingAfterPolling() {
        PriorityQueueCustom<Integer> queue = createQueue(new int[] {4, 2});
        assertEquals(2, queue.poll());
        queue.add(1);
        assertOrder(queue, new int[] {1, 4});
    }

    @Test
    void shouldAllowReusingEmptyQueue() {
        PriorityQueueCustom<Integer> queue = createQueue(new int[] {2});
        queue.poll();
        queue.add(9);
        assertEquals(9, queue.poll());
    }

    @Test
    void shouldThrowWhenPollingEmptyQueue() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        assertThrows(NoSuchElementException.class, () -> queue.poll());
    }

    @Test
    void shouldThrowWhenPeekingEmptyQueue() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        assertThrows(NoSuchElementException.class, () -> queue.peek());
    }

    @Test
    void shouldRejectNullWithoutChangingSize() {
        PriorityQueueCustom<Integer> queue = createQueue(new int[] {3});
        assertThrows(NullPointerException.class, () -> queue.add(null));
        assertOrder(queue, new int[] {3});
    }

    @Test
    void shouldOrderStringsNaturally() {
        PriorityQueueCustom<String> queue = new PriorityQueueCustom<>();
        queue.add("cat");
        queue.add("apple");
        queue.add("bird");
        assertEquals("apple", queue.poll());
        assertEquals("bird", queue.poll());
        assertEquals("cat", queue.poll());
    }

    @Test
    void shouldMatchStandardQueueOnMixedOperations() {
        Random random = new Random(31);
        PriorityQueueCustom<Integer> actual = new PriorityQueueCustom<>();
        PriorityQueue<Integer> expected = new PriorityQueue<>();
        for (int i = 0; i < 10000; i++) {
            if (expected.isEmpty() || random.nextBoolean()) {
                int value = random.nextInt(1000) - 500;
                expected.add(value);
                actual.add(value);
            } else {
                assertEquals(expected.poll(), actual.poll());
            }
            assertEquals(expected.size(), actual.size());
            assertEquals(expected.isEmpty(), actual.isEmpty());
            if (!expected.isEmpty()) {
                assertEquals(expected.peek(), actual.peek());
            }
        }
        while (!expected.isEmpty()) {
            assertEquals(expected.poll(), actual.poll());
        }
    }
}
