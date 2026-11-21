package ru.bmstu.lab02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Random;
import org.junit.jupiter.api.Test;

class PriorityQueueCustomTest {
    @Test
    void shouldStartEmpty() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();

        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
        assertNull(queue.peek());
    }

    @Test
    void shouldThrowWhenPollingAnEmptyQueue() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();

        assertThrows(NoSuchElementException.class, queue::poll);
    }

    @Test
    void shouldPeekWithoutChangingSize() {
        PriorityQueueCustom<Integer> queue = queueOf(4, 2, 5);

        assertEquals(2, queue.peek());
        assertEquals(2, queue.peek());
        assertEquals(3, queue.size());
        assertFalse(queue.isEmpty());
    }

    @Test
    void shouldExtractElementsInNondecreasingOrderAfterGrowth() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (int item = 255; item >= -256; item--) {
            queue.add(item);
        }

        assertEquals(512, queue.size());
        for (int expected = -256; expected <= 255; expected++) {
            assertEquals(expected, queue.poll());
        }
        assertTrue(queue.isEmpty());
        assertNull(queue.peek());
    }

    @Test
    void shouldReuseQueueAfterRemovingTheOnlyElement() {
        PriorityQueueCustom<Integer> queue = queueOf(7);

        assertEquals(7, queue.poll());
        assertTrue(queue.isEmpty());
        queue.add(9);

        assertEquals(9, queue.poll());
        assertEquals(0, queue.size());
    }

    @Test
    void shouldRejectNullWithoutChangingState() {
        PriorityQueueCustom<Integer> queue = queueOf(4, 3);

        assertThrows(NullPointerException.class, () -> queue.add(null));
        assertEquals(3, queue.peek());
        assertEquals(2, queue.size());
    }

    @Test
    void shouldUseNaturalOrderForStrings() {
        PriorityQueueCustom<String> queue = new PriorityQueueCustom<>();
        queue.add("gamma");
        queue.add("alpha");
        queue.add("beta");

        assertEquals("alpha", queue.poll());
        assertEquals("beta", queue.poll());
        assertEquals("gamma", queue.poll());
    }

    @Test
    void shouldKeepDuplicatePrioritiesDuringPolling() {
        PriorityQueueCustom<Integer> queue = queueOf(3, 1, 2, 1, 2);

        assertPolls(queue, 1, 1, 2, 2, 3);
    }

    @Test
    void shouldRemoveRootAndRestoreHeap() {
        PriorityQueueCustom<Integer> queue = queueOf(1, 4, 2, 7, 6, 3);

        assertTrue(queue.remove(1));

        assertPolls(queue, 2, 3, 4, 6, 7);
    }

    @Test
    void shouldSiftReplacementDownAfterRemovingAnInnerNode() {
        PriorityQueueCustom<Integer> queue = queueOf(1, 3, 2, 7, 8, 4, 9);

        assertTrue(queue.remove(3));

        assertPolls(queue, 1, 2, 4, 7, 8, 9);
    }

    @Test
    void shouldSiftReplacementUpAfterRemovingANonLastLeaf() {
        PriorityQueueCustom<Integer> queue = queueOf(1, 10, 2, 12, 13, 3, 4);

        assertTrue(queue.remove(12));

        assertPolls(queue, 1, 2, 3, 4, 10, 13);
    }

    @Test
    void shouldRemoveOnlyTheFirstEqualItem() {
        PriorityQueueCustom<BigDecimal> queue = new PriorityQueueCustom<>();
        BigDecimal first = new BigDecimal("1.0");
        BigDecimal second = new BigDecimal("1.0");
        BigDecimal samePriority = new BigDecimal("1.00");
        queue.add(first);
        queue.add(second);
        queue.add(samePriority);

        assertTrue(queue.remove(new BigDecimal("1.0")));

        assertEquals(2, queue.size());
        assertSame(samePriority, queue.peek());
        assertSame(samePriority, queue.poll());
        assertSame(second, queue.poll());
    }

    @Test
    void shouldReturnFalseForMissingElement() {
        PriorityQueueCustom<Integer> queue = queueOf(5, 3, 4);

        assertFalse(queue.remove(8));

        assertPolls(queue, 3, 4, 5);
    }

    @Test
    void shouldReturnFalseWhenRemovingFromAnEmptyQueue() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();

        assertFalse(queue.remove(8));
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldMatchStandardQueueOnMixedOperations() {
        PriorityQueueCustom<Integer> actual = new PriorityQueueCustom<>();
        PriorityQueue<Integer> expected = new PriorityQueue<>();
        Random random = new Random(33);
        for (int operation = 0; operation < 5_000; operation++) {
            int item = random.nextInt(64);
            switch (random.nextInt(4)) {
                case 0, 1 -> {
                    expected.add(item);
                    actual.add(item);
                }
                case 2 -> assertEquals(expected.remove(item), actual.remove(item));
                default -> {
                    if (!expected.isEmpty()) {
                        assertEquals(expected.poll(), actual.poll());
                    }
                }
            }
            assertEquals(expected.size(), actual.size());
            assertEquals(expected.peek(), actual.peek());
        }
    }

    private static PriorityQueueCustom<Integer> queueOf(int... items) {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (int item : items) {
            queue.add(item);
        }
        return queue;
    }

    private static void assertPolls(PriorityQueueCustom<Integer> queue, int... items) {
        for (int expected : items) {
            assertEquals(expected, queue.poll());
        }
        assertTrue(queue.isEmpty());
    }
}
