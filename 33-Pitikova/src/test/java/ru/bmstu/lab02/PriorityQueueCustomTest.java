package ru.bmstu.lab02;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Random;
import org.junit.jupiter.api.Test;

/** Проверяет операции очереди и построение кучи из массива. */
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
    void shouldThrowWhenPeekingAtEmptyQueue() {
        var queue = new PriorityQueueCustom<Integer>();
        assertThrows(NoSuchElementException.class, queue::peek);
    }

    @Test
    void shouldFindNewMinimumWithoutRemovingIt() {
        var queue = new PriorityQueueCustom<Integer>();
        queue.add(9);
        queue.add(2);
        assertEquals(2, queue.peek());
        assertEquals(2, queue.peek());
        assertEquals(2, queue.size());
        assertFalse(queue.isEmpty());
    }

    @Test
    void shouldReturnToEmptyStateAndAcceptNewItem() {
        var queue = new PriorityQueueCustom<String>();
        queue.add("б");
        assertEquals("б", queue.poll());
        assertTrue(queue.isEmpty());
        queue.add("а");
        assertEquals("а", queue.poll());
    }

    @Test
    void shouldKeepDuplicateValues() {
        var queue = new PriorityQueueCustom<>(new Integer[] {4, 1, 4, 1, -3});
        assertArrayEquals(new Integer[] {-3, 1, 1, 4, 4}, drain(queue));
    }

    @Test
    void shouldGrowAndOrderValuesAddedInDescendingOrder() {
        var queue = new PriorityQueueCustom<Integer>();
        for (int value = 100; value > 0; value--) {
            queue.add(value);
        }
        assertEquals(100, queue.size());
        for (int value = 1; value <= 100; value++) {
            assertEquals(value, queue.poll());
        }
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldHandleEmptyAndSingletonArrays() {
        assertTrue(new PriorityQueueCustom<>(new Integer[0]).isEmpty());
        var queue = new PriorityQueueCustom<>(new Integer[] {7});
        assertEquals(7, queue.poll());
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldBuildHeapWithoutSharingItsInputArray() {
        Integer[] values = {8, 4, 7, 3, 2, 1};
        var queue = new PriorityQueueCustom<>(values);
        assertArrayEquals(new Integer[] {8, 4, 7, 3, 2, 1}, values);
        values[0] = -20;
        assertArrayEquals(new Integer[] {1, 2, 3, 4, 7, 8}, drain(queue));
    }

    @Test
    void shouldMatchSortingOnRandomArray() {
        Random random = new Random(42);
        Integer[] values = random.ints(2_000, -1_000, 1_001).boxed().toArray(Integer[]::new);
        Integer[] expected = values.clone();
        Arrays.sort(expected);
        assertArrayEquals(expected, drain(new PriorityQueueCustom<>(values)));
    }

    @Test
    void shouldMatchStandardQueueWhenAddsAndPollsAreInterleaved() {
        var queue = new PriorityQueueCustom<Integer>();
        var expected = new PriorityQueue<Integer>();
        Random random = new Random(42);
        for (int step = 0; step < 3_000; step++) {
            if (expected.isEmpty() || random.nextBoolean()) {
                int value = random.nextInt();
                queue.add(value);
                expected.add(value);
            } else {
                assertEquals(expected.remove(), queue.poll());
            }
            assertEquals(expected.size(), queue.size());
        }
        while (!expected.isEmpty()) {
            assertEquals(expected.remove(), queue.poll());
        }
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldRejectNullItemsAndArrays() {
        var queue = new PriorityQueueCustom<Integer>();
        assertThrows(NullPointerException.class, () -> queue.add(null));
        assertTrue(queue.isEmpty());
        assertThrows(NullPointerException.class, () -> new PriorityQueueCustom<Integer>((Integer[]) null));
        assertThrows(NullPointerException.class, () -> new PriorityQueueCustom<>(new Integer[] {1, null}));
    }

    @Test
    void shouldUseNaturalOrderWhenComparatorIsNull() {
        var queue = new PriorityQueueCustom<Integer>((Comparator<Integer>) null);
        queue.add(5);
        queue.add(1);
        assertArrayEquals(new Integer[] {1, 5}, drain(queue));
    }

    @Test
    void shouldReturnValuesInDescendingOrderWithReverseComparator() {
        var queue = new PriorityQueueCustom<Integer>(Comparator.reverseOrder());
        queue.add(5);
        queue.add(2);
        queue.add(7);
        queue.add(7);
        assertEquals(7, queue.peek());
        assertArrayEquals(new Integer[] {7, 7, 5, 2}, drain(queue));
    }

    @Test
    void shouldOrderNonComparableValuesUsingComparator() {
        record Job(int priority) {}
        var queue = new PriorityQueueCustom<Job>(Comparator.comparingInt(Job::priority));
        queue.add(new Job(9));
        queue.add(new Job(1));
        assertEquals(new Job(1), queue.poll());
        assertEquals(new Job(9), queue.poll());
        var natural = new PriorityQueueCustom<Job>();
        assertThrows(ClassCastException.class, () -> natural.add(new Job(1)));
    }

    @Test
    void shouldRestoreHeapWhenRemovingRoot() {
        var queue = new PriorityQueueCustom<>(new Integer[] {1, 3, 2, 5, 4});
        assertTrue(queue.remove(1));
        assertArrayEquals(new Integer[] {2, 3, 4, 5}, drain(queue));
    }

    @Test
    void shouldSiftUpWhenReplacementPrecedesItsNewParent() {
        var queue = new PriorityQueueCustom<>(new Integer[] {1, 5, 2, 6, 7, 3, 4});
        assertTrue(queue.remove(6));
        assertArrayEquals(new Integer[] {1, 2, 3, 4, 5, 7}, drain(queue));
    }

    @Test
    void shouldSiftDownWhenRemovingAnInternalNode() {
        var queue = new PriorityQueueCustom<>(new Integer[] {1, 2, 3, 4, 5, 6, 7});
        assertTrue(queue.remove(2));
        assertArrayEquals(new Integer[] {1, 3, 4, 5, 6, 7}, drain(queue));
    }

    @Test
    void shouldReturnFalseWhenItemIsMissingOrQueueIsEmpty() {
        var queue = new PriorityQueueCustom<>(new Integer[] {1, 2});
        assertFalse(queue.remove(42));
        assertFalse(queue.remove(null));
        assertArrayEquals(new Integer[] {1, 2}, drain(queue));
        assertFalse(queue.remove(1));
    }

    @Test
    void shouldRemoveOnlyOneOccurrenceFromMaxHeap() {
        var queue = new PriorityQueueCustom<Integer>(Comparator.reverseOrder());
        for (int value : new int[] {8, 5, 5, 1}) {
            queue.add(value);
        }
        assertTrue(queue.remove(1));
        assertTrue(queue.remove(5));
        assertArrayEquals(new Integer[] {8, 5}, drain(queue));
    }

    private static Integer[] drain(PriorityQueueCustom<Integer> queue) {
        Integer[] result = new Integer[queue.size()];
        for (int index = 0; index < result.length; index++) {
            result[index] = queue.poll();
        }
        return result;
    }
}
