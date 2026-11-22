package ru.bmstu.lab02.task1b;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Random;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Юнит-тесты для {@link PriorityQueueComparator} (задача 1B).
 */
class PriorityQueueComparatorTest {

    private static final long RANDOM_SEED = 42L;
    private static final int STRESS_SIZE = 5_000;

    @Test
    void shouldWorkAsMinHeapWithDefaultConstructor() {
        PriorityQueueComparator<Integer> minHeap = new PriorityQueueComparator<>();
        minHeap.add(30);
        minHeap.add(10);
        minHeap.add(20);

        assertEquals(10, minHeap.peek());
        assertEquals(10, minHeap.poll());
        assertEquals(20, minHeap.poll());
        assertEquals(30, minHeap.poll());
    }

    @Test
    void shouldWorkAsMinHeapWhenComparatorIsNull() {
        PriorityQueueComparator<Integer> minHeap = new PriorityQueueComparator<>(null);
        minHeap.add(5);
        minHeap.add(1);
        minHeap.add(9);

        assertEquals(1, minHeap.poll());
        assertEquals(5, minHeap.poll());
        assertEquals(9, minHeap.poll());
    }

    @Test
    void shouldWorkAsMaxHeapWithReverseOrderComparator() {
        PriorityQueueComparator<Integer> maxHeap =
                new PriorityQueueComparator<>(Comparator.reverseOrder());
        maxHeap.add(10);
        maxHeap.add(30);
        maxHeap.add(20);

        assertEquals(30, maxHeap.peek());
        assertEquals(30, maxHeap.poll());
        assertEquals(20, maxHeap.poll());
        assertEquals(10, maxHeap.poll());
    }

    @Test
    void shouldSupportCustomStringLengthComparator() {
        PriorityQueueComparator<String> byLength =
                new PriorityQueueComparator<>(Comparator.comparingInt(String::length));
        byLength.add("banana");
        byLength.add("kiwi");
        byLength.add("strawberry");

        assertEquals("kiwi", byLength.poll());
        assertEquals("banana", byLength.poll());
        assertEquals("strawberry", byLength.poll());
    }

    @Test
    void shouldHandleNegativeNumbersInMaxHeapMode() {
        PriorityQueueComparator<Integer> maxHeap =
                new PriorityQueueComparator<>(Comparator.reverseOrder());
        maxHeap.add(-100);
        maxHeap.add(-5);
        maxHeap.add(-50);

        assertEquals(-5, maxHeap.poll());
        assertEquals(-50, maxHeap.poll());
        assertEquals(-100, maxHeap.poll());
    }

    @Test
    void shouldThrowExceptionWhenPollingOrPeekingEmptyQueue() {
        PriorityQueueComparator<Integer> queue =
                new PriorityQueueComparator<>(Comparator.reverseOrder());
        assertTrue(queue.isEmpty());
        assertThrows(NoSuchElementException.class, queue::poll);
        assertThrows(NoSuchElementException.class, queue::peek);
    }

    @Test
    void shouldThrowExceptionWhenAddingNullElement() {
        PriorityQueueComparator<Integer> queue = new PriorityQueueComparator<>();
        assertThrows(IllegalArgumentException.class, () -> queue.add(null));
    }

    @Test
    void shouldHandleDuplicateElementsInMaxHeapMode() {
        PriorityQueueComparator<Integer> maxHeap =
                new PriorityQueueComparator<>(Comparator.reverseOrder());
        maxHeap.add(7);
        maxHeap.add(7);
        maxHeap.add(3);

        assertEquals(7, maxHeap.poll());
        assertEquals(7, maxHeap.poll());
        assertEquals(3, maxHeap.poll());
        assertTrue(maxHeap.isEmpty());
    }

    @Test
    void shouldExpandCapacityDynamicallyInMaxHeapMode() {
        PriorityQueueComparator<Integer> maxHeap =
                new PriorityQueueComparator<>(Comparator.reverseOrder());
        for (int i = 1; i <= 50; i++) {
            maxHeap.add(i);
        }
        assertEquals(50, maxHeap.size());
        assertFalse(maxHeap.isEmpty());
        for (int expected = 50; expected >= 1; expected--) {
            assertEquals(expected, maxHeap.poll());
        }
    }

    @Test
    void shouldExtractInNonIncreasingOrderOnStressDataset() {
        PriorityQueueComparator<Integer> maxHeap =
                new PriorityQueueComparator<>(Comparator.reverseOrder());
        Random random = new Random(RANDOM_SEED);
        for (int i = 0; i < STRESS_SIZE; i++) {
            maxHeap.add(random.nextInt());
        }
        int previous = Integer.MAX_VALUE;
        for (int i = 0; i < STRESS_SIZE; i++) {
            int current = maxHeap.poll();
            assertTrue(current <= previous);
            previous = current;
        }
    }
}
