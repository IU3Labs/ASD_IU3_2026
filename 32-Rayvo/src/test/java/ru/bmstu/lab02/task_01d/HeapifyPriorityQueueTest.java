package ru.bmstu.lab02.task_01d;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Random;

import org.junit.jupiter.api.Test;

/** Проверяет heapify, независимость от массива и линейное число сравнений. */
class HeapifyPriorityQueueTest {
    @Test
    void shouldBuildFromEmptyArray() {
        HeapifyPriorityQueue<Integer> queue = new HeapifyPriorityQueue<>(new Integer[0]);
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
        assertThrows(NoSuchElementException.class, queue::poll);
    }

    @Test
    void shouldBuildFromSingleElement() {
        HeapifyPriorityQueue<Integer> queue = new HeapifyPriorityQueue<>(new Integer[] {12});
        assertEquals(1, queue.size());
        assertPollOrder(queue, 12);
    }

    @Test
    void shouldBuildFromAscendingArrayWithNullComparator() {
        HeapifyPriorityQueue<Integer> queue = new HeapifyPriorityQueue<>(new Integer[] {1, 2, 3, 4, 5}, null);
        assertPollOrder(queue, 1, 2, 3, 4, 5);
    }

    @Test
    void shouldBuildFromDescendingArray() {
        HeapifyPriorityQueue<Integer> queue = new HeapifyPriorityQueue<>(new Integer[] {6, 5, 4, 3, 2, 1});
        assertPollOrder(queue, 1, 2, 3, 4, 5, 6);
    }

    @Test
    void shouldKeepDuplicatesAndExtremeValues() {
        Integer[] values = {Integer.MAX_VALUE, -2, 0, -2, Integer.MIN_VALUE};
        HeapifyPriorityQueue<Integer> queue = new HeapifyPriorityQueue<>(values);
        assertPollOrder(queue, Integer.MIN_VALUE, -2, -2, 0, Integer.MAX_VALUE);
    }

    @Test
    void shouldCopyInputWithoutModifyingOrSharingArray() {
        Integer[] values = {8, 3, 5, 1};
        Integer[] original = values.clone();
        HeapifyPriorityQueue<Integer> queue = new HeapifyPriorityQueue<>(values);
        assertArrayEquals(original, values);
        values[3] = 99;
        queue.add(0);
        assertPollOrder(queue, 0, 1, 3, 5, 8);
        assertEquals(99, values[3]);
    }

    @Test
    void shouldRejectNullArray() {
        assertThrows(NullPointerException.class, () -> new HeapifyPriorityQueue<Integer>((Integer[]) null));
    }

    @Test
    void shouldRejectNullArrayElement() {
        Integer[] values = {3, null, 1};
        assertThrows(NullPointerException.class, () -> new HeapifyPriorityQueue<>(values));
        assertArrayEquals(new Integer[] {3, null, 1}, values);
    }

    @Test
    void shouldBuildMaxHeapFromRandomArray() {
        Integer[] values = new Random(105).ints(1000).boxed().toArray(Integer[]::new);
        HeapifyPriorityQueue<Integer> queue = new HeapifyPriorityQueue<>(values, Comparator.reverseOrder());
        Arrays.sort(values, Comparator.reverseOrder());
        for (Integer expected : values) {
            assertEquals(expected, queue.poll());
        }
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldSupportGrowthAndRemovalAfterHeapify() {
        Integer[] values = new Integer[32];
        Arrays.setAll(values, index -> index + 1);
        HeapifyPriorityQueue<Integer> queue = new HeapifyPriorityQueue<>(values);
        queue.add(0);
        queue.add(33);
        assertTrue(queue.remove(16));
        for (int expected = 0; expected <= 33; expected++) {
            if (expected != 16) {
                assertEquals(expected, queue.poll());
            }
        }
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldSupportEmptyQueueConstructors() {
        HeapifyPriorityQueue<Integer> minHeap = new HeapifyPriorityQueue<>();
        HeapifyPriorityQueue<Integer> maxHeap = new HeapifyPriorityQueue<>(Comparator.reverseOrder());
        minHeap.add(2);
        minHeap.add(1);
        maxHeap.add(2);
        maxHeap.add(1);
        assertPollOrder(minHeap, 1, 2);
        assertPollOrder(maxHeap, 2, 1);
    }

    @Test
    void shouldBuildWithLinearNumberOfComparisons() {
        int count = 8192;
        Integer[] values = new Integer[count];
        Arrays.setAll(values, index -> count - index);
        int[] heapifyComparisons = {0};
        Comparator<Integer> comparator = (first, second) -> {
            heapifyComparisons[0]++;
            return Integer.compare(first, second);
        };
        HeapifyPriorityQueue<Integer> queue = new HeapifyPriorityQueue<>(values, comparator);
        assertTrue(heapifyComparisons[0] < 2 * count, "Число сравнений heapify должно быть линейным");
        assertEquals(count, queue.size());
        for (int expected = 1; expected <= count; expected++) {
            assertEquals(expected, queue.poll());
        }
    }

    private void assertPollOrder(HeapifyPriorityQueue<Integer> queue, Integer... expected) {
        for (Integer value : expected) {
            assertEquals(value, queue.poll());
        }
        assertTrue(queue.isEmpty());
    }
}
