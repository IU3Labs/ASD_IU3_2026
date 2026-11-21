package ru.bmstu.lab2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Random;
import org.junit.jupiter.api.Test;

class PriorityQueueCustomTest {
    @Test
    void shouldStartEmpty() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        assertEquals(0, queue.size());
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldRejectPollFromEmptyQueue() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        assertThrows(NoSuchElementException.class, queue::poll);
    }

    @Test
    void shouldRejectPeekFromEmptyQueue() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        assertThrows(NoSuchElementException.class, queue::peek);
    }

    @Test
    void shouldRejectNull() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        assertThrows(NullPointerException.class, () -> queue.add(null));
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldHandleSingleElement() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(7);
        assertFalse(queue.isEmpty());
        assertEquals(7, queue.poll());
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldPeekWithoutRemoving() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(4);
        queue.add(1);
        assertEquals(1, queue.peek());
        assertEquals(1, queue.peek());
        assertEquals(2, queue.size());
    }

    @Test
    void shouldSortAscendingInput() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (int value = 0; value < 100; value++) {
            queue.add(value);
        }
        for (int value = 0; value < 100; value++) {
            assertEquals(value, queue.poll());
        }
    }

    @Test
    void shouldSortDescendingInputAndGrowArray() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (int value = 1000; value >= 1; value--) {
            queue.add(value);
        }
        assertEquals(1000, queue.size());
        for (int value = 1; value <= 1000; value++) {
            assertEquals(value, queue.poll());
        }
    }

    @Test
    void shouldPreserveDuplicates() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (int value : new int[]{2, 1, 2, 1, 2}) {
            queue.add(value);
        }
        for (int expected : new int[]{1, 1, 2, 2, 2}) {
            assertEquals(expected, queue.poll());
        }
    }

    @Test
    void shouldHandleNegativeAndExtremeValues() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (int value : new int[]{Integer.MAX_VALUE, 0, -5, Integer.MIN_VALUE}) {
            queue.add(value);
        }
        for (int expected : new int[]{Integer.MIN_VALUE, -5, 0, Integer.MAX_VALUE}) {
            assertEquals(expected, queue.poll());
        }
    }

    @Test
    void shouldSupportMaxHeap() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(Comparator.reverseOrder());
        for (int value : new int[]{-2, 5, 1, 5, Integer.MIN_VALUE, Integer.MAX_VALUE}) {
            queue.add(value);
        }
        for (int expected : new int[]{Integer.MAX_VALUE, 5, 5, 1, -2, Integer.MIN_VALUE}) {
            assertEquals(expected, queue.poll());
        }
    }

    @Test
    void shouldUseExplicitNaturalComparator() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(Comparator.naturalOrder());
        queue.add(8);
        queue.add(3);
        assertEquals(3, queue.poll());
        assertEquals(8, queue.poll());
    }

    @Test
    void shouldSortStringsNaturally() {
        PriorityQueueCustom<String> queue = new PriorityQueueCustom<>();
        queue.add("cat");
        queue.add("apple");
        queue.add("book");
        assertEquals("apple", queue.poll());
        assertEquals("book", queue.poll());
        assertEquals("cat", queue.poll());
    }

    @Test
    void shouldCompareObjectsWithoutComparable() {
        PriorityQueueCustom<Job> queue = new PriorityQueueCustom<>(Comparator.comparingInt(Job::priority));
        Job first = new Job("first", 1);
        Job second = new Job("second", 9);
        queue.add(second);
        queue.add(first);
        assertEquals(first, queue.poll());
        assertEquals(second, queue.poll());
    }

    @Test
    void shouldReuseQueueAfterDraining() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(10);
        queue.poll();
        queue.add(20);
        queue.add(5);
        assertEquals(5, queue.poll());
        assertEquals(20, queue.poll());
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldSiftDownThroughSingleLeftChild() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        assertEquals(1, queue.poll());
        assertEquals(2, queue.peek());
        assertEquals(2, queue.poll());
        assertEquals(3, queue.poll());
    }

    @Test
    void shouldMatchStandardMinHeapOnMixedOperations() {
        checkMixedOperations(Comparator.naturalOrder());
    }

    @Test
    void shouldMatchStandardMaxHeapOnMixedOperations() {
        checkMixedOperations(Comparator.reverseOrder());
    }

    private void checkMixedOperations(Comparator<Integer> comparator) {
        PriorityQueueCustom<Integer> actual = new PriorityQueueCustom<>(comparator);
        PriorityQueue<Integer> expected = new PriorityQueue<>(comparator);
        Random random = new Random(42);
        for (int step = 0; step < 10_000; step++) {
            if (expected.isEmpty() || random.nextBoolean()) {
                int value = random.nextInt(200) - 100;
                actual.add(value);
                expected.add(value);
            } else {
                assertEquals(expected.poll(), actual.poll());
            }
            assertEquals(expected.size(), actual.size());
            if (!expected.isEmpty()) {
                assertEquals(expected.peek(), actual.peek());
            }
        }
        while (!expected.isEmpty()) {
            assertEquals(expected.poll(), actual.poll());
        }
        assertTrue(actual.isEmpty());
    }

    private record Job(String name, int priority) {
    }
}
