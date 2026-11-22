package ru.bmstu.lab02.task1b;

import java.util.Comparator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Юнит-тесты для {@link PriorityQueueComparator} (задача 1B).
 */
class PriorityQueueComparatorTest {

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
}
