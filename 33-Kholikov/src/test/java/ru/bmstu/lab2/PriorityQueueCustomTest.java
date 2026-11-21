package ru.bmstu.lab2;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Random;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PriorityQueueCustomTest {
    @Test
    void shouldBuildUsableQueueFromEmptyArray() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(new Integer[0]);

        assertTrue(queue.isEmpty());
        assertNull(queue.peek());
        queue.add(5);
        assertPollOrder(queue, 5);
    }

    @Test
    void shouldBuildQueueFromSingleElement() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(new Integer[]{42});

        assertEquals(42, queue.peek());
        assertPollOrder(queue, 42);
    }

    @Test
    void shouldHeapifyUnsortedArrayWithDuplicates() {
        Integer[] array = {7, 4, 7, 2, -3, 4};
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(array);

        assertPollOrder(queue, -3, 2, 4, 4, 7, 7);
    }

    @Test
    void shouldPreserveInputOrderAndKeepAnIndependentCopy() {
        Integer[] input = {8, 1, 4};
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(input);

        assertArrayEquals(new Integer[]{8, 1, 4}, input);
        input[0] = -5;
        input[1] = -7;
        assertPollOrder(queue, 1, 4, 8);
    }

    @Test
    void shouldSupportInsertionsAndRemovalsAfterHeapify() {
        Integer[] descending = new Integer[33];
        for (int index = 0; index < descending.length; index++) {
            descending[index] = descending.length - index;
        }
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(descending);

        queue.add(0);
        assertTrue(queue.remove(33));
        assertEquals(33, queue.size());
        for (int value = 0; value < 33; value++) {
            assertEquals(value, queue.poll());
        }
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldRejectNullDuringHeapify() {
        assertThrows(NullPointerException.class, () -> new PriorityQueueCustom<Integer>(null));
        assertThrows(NullPointerException.class, () -> new PriorityQueueCustom<>(new Integer[]{3, null}));
    }

    @Test
    void shouldStartEmpty() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();

        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
    }

    @Test
    void shouldReturnNullWhenPeekingIntoEmptyQueue() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();

        assertNull(queue.peek());
    }

    @Test
    void shouldThrowWhenPollingEmptyQueue() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();

        assertThrows(NoSuchElementException.class, queue::poll);
    }

    @Test
    void shouldBecomeEmptyAfterPollingOnlyElement() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(42);

        assertFalse(queue.isEmpty());
        assertEquals(1, queue.size());
        assertEquals(42, queue.poll());
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
        assertNull(queue.peek());
    }

    @Test
    void shouldPeekRepeatedlyWithoutRemovingMinimum() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(8);
        queue.add(3);

        assertEquals(3, queue.peek());
        assertEquals(3, queue.peek());
        assertEquals(2, queue.size());
        assertEquals(3, queue.poll());
        assertEquals(8, queue.peek());
    }

    @Test
    void shouldMoveNewMinimumUpSeveralLevels() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (int value = 1; value <= 7; value++) {
            queue.add(value);
        }

        queue.add(0);

        assertEquals(0, queue.peek());
        assertEquals(0, queue.poll());
        assertEquals(1, queue.peek());
    }

    @Test
    void shouldSiftDownThroughRightChild() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (int value : new int[]{1, 4, 2, 5, 6, 3, 7}) {
            queue.add(value);
        }

        assertEquals(1, queue.poll());
        assertEquals(2, queue.poll());
        assertEquals(3, queue.peek());
    }

    @Test
    void shouldSiftDownWhenOnlyLeftChildRemains() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(1);
        queue.add(2);
        queue.add(3);

        assertEquals(1, queue.poll());
        assertEquals(2, queue.poll());
        assertEquals(3, queue.poll());
    }

    @Test
    void shouldReturnAllDuplicatesAndOrderExtremeValues() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        int[] input = {0, Integer.MAX_VALUE, -4, 0, Integer.MIN_VALUE, -4};
        for (int value : input) {
            queue.add(value);
        }

        int[] expected = {Integer.MIN_VALUE, -4, -4, 0, 0, Integer.MAX_VALUE};
        for (int value : expected) {
            assertEquals(value, queue.poll());
        }
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldKeepAllElementsAcrossSeveralExpansions() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        int elementCount = 257;
        for (int value = elementCount; value >= 1; value--) {
            queue.add(value);
        }

        assertEquals(elementCount, queue.size());
        for (int value = 1; value <= elementCount; value++) {
            assertEquals(value, queue.poll());
        }
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldAcceptNewElementsAfterBeingDrained() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(-10);
        queue.add(-5);
        queue.poll();
        queue.poll();

        queue.add(9);
        queue.add(4);

        assertEquals(2, queue.size());
        assertEquals(4, queue.poll());
        assertEquals(9, queue.poll());
    }

    @Test
    void shouldOrderStringsNaturally() {
        PriorityQueueCustom<String> queue = new PriorityQueueCustom<>();
        queue.add("pear");
        queue.add("apple");
        queue.add("banana");

        assertEquals("apple", queue.poll());
        assertEquals("banana", queue.poll());
        assertEquals("pear", queue.poll());
    }

    @Test
    void shouldRejectNullWithoutChangingQueue() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(7);

        assertThrows(NullPointerException.class, () -> queue.add(null));
        assertEquals(1, queue.size());
        assertEquals(7, queue.peek());
    }

    @Test
    void shouldRemoveRootAndExposeNextMinimum() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(3);
        queue.add(1);
        queue.add(2);

        assertTrue(queue.remove(1));
        assertEquals(2, queue.peek());
        assertPollOrder(queue, 2, 3);
    }

    @Test
    void shouldSiftReplacementDownAfterRemovingInternalElement() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (int value = 1; value <= 8; value++) {
            queue.add(value);
        }

        assertTrue(queue.remove(2));
        assertPollOrder(queue, 1, 3, 4, 5, 6, 7, 8);
    }

    @Test
    void shouldSiftReplacementUpAfterRemovingInternalElement() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (int value : new int[]{1, 9, 2, 10, 11, 3, 6, 20, 21, 22, 23, 4, 5}) {
            queue.add(value);
        }

        assertTrue(queue.remove(10));
        assertPollOrder(queue, 1, 2, 3, 4, 5, 6, 9, 11, 20, 21, 22, 23);
    }

    @Test
    void shouldReturnFalseWhenRemovingMissingElement() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(7);
        queue.add(3);
        queue.add(5);

        assertFalse(queue.remove(6));
        assertFalse(queue.remove(null));
        assertPollOrder(queue, 3, 5, 7);
    }

    @Test
    void shouldReturnFalseWhenRemovingFromEmptyQueue() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();

        assertFalse(queue.remove(3));
        assertTrue(queue.isEmpty());
        assertNull(queue.peek());
    }

    @Test
    void shouldRemoveOnlyElement() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(3);

        assertTrue(queue.remove(3));
        assertEquals(0, queue.size());
        assertNull(queue.peek());
    }

    @Test
    void shouldRemoveOnlyFirstEqualElement() {
        PriorityQueueCustom<String> queue = new PriorityQueueCustom<>();
        String first = "duplicate";
        String second = new String(first);
        queue.add(first);
        queue.add(second);

        assertTrue(queue.remove(first));
        assertEquals(1, queue.size());
        assertSame(second, queue.poll());
    }

    @Test
    void shouldUseEqualsForMatching() {
        PriorityQueueCustom<BigDecimal> queue = new PriorityQueueCustom<>();
        BigDecimal first = new BigDecimal("1.0");
        BigDecimal second = new BigDecimal("1.00");
        queue.add(first);
        queue.add(second);

        assertTrue(queue.remove(new BigDecimal("1.00")));
        assertEquals(1, queue.size());
        assertSame(first, queue.poll());
    }

    @Test
    void shouldMatchStandardQueueDuringMixedOperations() {
        PriorityQueueCustom<Integer> actual = new PriorityQueueCustom<>();
        PriorityQueue<Integer> expected = new PriorityQueue<>();
        Random random = new Random(42L);

        for (int step = 0; step < 3_000; step++) {
            int operation = random.nextInt(4);
            int value = random.nextInt(-200, 201);
            if (operation < 2) {
                expected.add(value);
                actual.add(value);
            } else if (operation == 2) {
                assertEquals(expected.remove(value), actual.remove(value));
            } else if (!expected.isEmpty()) {
                assertEquals(expected.poll(), actual.poll());
            }
            assertEquals(expected.peek(), actual.peek());
            assertEquals(expected.size(), actual.size());
        }

        while (!expected.isEmpty()) {
            assertEquals(expected.poll(), actual.poll());
        }
        assertTrue(actual.isEmpty());
    }

    private static void assertPollOrder(PriorityQueueCustom<Integer> queue, int... expected) {
        assertEquals(expected.length, queue.size());
        for (int value : expected) {
            assertEquals(value, queue.poll());
        }
        assertTrue(queue.isEmpty());
    }
}
