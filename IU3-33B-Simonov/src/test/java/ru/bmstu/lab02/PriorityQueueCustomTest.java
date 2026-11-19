package ru.bmstu.lab02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Random;
import org.junit.jupiter.api.Test;

/**
 * Проверяет порядок извлечения, работу компаратора и граничные случаи очереди.
 */
class PriorityQueueCustomTest {
    @Test
    void shouldStartEmpty() {
        var queue = new PriorityQueueCustom<Integer>();
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
    }

    @Test
    void shouldThrowWhenPollingEmptyQueue() {
        var queue = new PriorityQueueCustom<Integer>();
        assertThrows(NoSuchElementException.class, queue::poll);
    }

    @Test
    void shouldReturnNullWhenPeekingEmptyQueue() {
        var queue = new PriorityQueueCustom<Integer>();
        assertNull(queue.peek());
    }

    @Test
    void shouldPeekWithoutRemoving() {
        var queue = new PriorityQueueCustom<Integer>();
        queue.add(10);
        queue.add(-3);
        assertEquals(-3, queue.peek());
        assertEquals(-3, queue.peek());
        assertEquals(2, queue.size());
        assertFalse(queue.isEmpty());
    }

    @Test
    void shouldExtractInAscendingOrder() {
        var queue = new PriorityQueueCustom<Integer>();
        for (int x : new int[]{5, -1, 7, 0, 2}) {
            queue.add(x);
        }
        for (int x : new int[]{-1, 0, 2, 5, 7}) {
            assertEquals(x, queue.poll());
        }
    }

    @Test
    void shouldKeepDuplicates() {
        var queue = new PriorityQueueCustom<Integer>();
        queue.add(4);
        queue.add(2);
        queue.add(2);
        assertEquals(2, queue.poll());
        assertEquals(2, queue.poll());
        assertEquals(4, queue.poll());
    }

    @Test
    void shouldBeReusableAfterEmptying() {
        var queue = new PriorityQueueCustom<Integer>();
        queue.add(9);
        assertEquals(9, queue.poll());
        assertTrue(queue.isEmpty());
        queue.add(3);
        queue.add(6);
        assertEquals(3, queue.poll());
        assertEquals(1, queue.size());
        assertEquals(6, queue.peek());
    }

    @Test
    void shouldGrowAndKeepOrder() {
        var queue = new PriorityQueueCustom<Integer>();
        for (int i = 999; i >= 0; i--) {
            queue.add(i);
        }
        assertEquals(1000, queue.size());
        for (int i = 0; i < 1000; i++) {
            assertEquals(i, queue.poll());
        }
    }

    @Test
    void shouldWorkAsMaxHeap() {
        var queue = new PriorityQueueCustom<Integer>(Comparator.reverseOrder());
        for (int x : new int[]{2, 7, 4, -1, 7}) {
            queue.add(x);
        }
        assertEquals(7, queue.peek());
        for (int x : new int[]{7, 7, 4, 2, -1}) {
            assertEquals(x, queue.poll());
        }
    }

    @Test
    void shouldUseNaturalOrderForNullComparator() {
        var queue = new PriorityQueueCustom<String>(null);
        queue.add("z");
        queue.add("a");
        assertEquals("a", queue.poll());
    }

    @Test
    void shouldCompareItemsWithoutNaturalOrder() {
        var queue = new PriorityQueueCustom<int[]>(Comparator.comparingInt(a -> a[0]));
        int[] first = {2};
        queue.add(new int[]{5});
        queue.add(first);
        assertEquals(first, queue.poll());
    }

    @Test
    void shouldRejectNullElements() {
        var queue = new PriorityQueueCustom<Integer>();
        assertThrows(NullPointerException.class, () -> queue.add(null));
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldMatchStandardQueueWithInterleavedOperations() {
        var random = new Random(33);
        var expected = new PriorityQueue<Integer>();
        var actual = new PriorityQueueCustom<Integer>();
        for (int i = 0; i < 3000; i++) {
            if (expected.isEmpty() || random.nextBoolean()) {
                int x = random.nextInt(100);
                expected.add(x);
                actual.add(x);
            } else {
                assertEquals(expected.poll(), actual.poll());
            }
            assertEquals(expected.peek(), actual.peek());
            assertEquals(expected.size(), actual.size());
        }
    }
}
