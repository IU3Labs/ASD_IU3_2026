package ru.bmstu.lab2;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Comparator;
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

    @Test
    void shouldWorkAsMaxHeap() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(Comparator.reverseOrder());
        for (int value : new int[] {2, 9, 1, 5, 9}) {
            queue.add(value);
        }
        assertOrder(queue, new int[] {9, 9, 5, 2, 1});
    }

    @Test
    void shouldWorkWithNaturalComparator() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(Comparator.naturalOrder());
        queue.add(8);
        queue.add(-3);
        assertOrder(queue, new int[] {-3, 8});
    }

    @Test
    void shouldUseNaturalOrderForNullComparator() {
        Comparator<Integer> comparator = null;
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(comparator);
        queue.add(3);
        queue.add(1);
        assertOrder(queue, new int[] {1, 3});
    }

    @Test
    void shouldUseCaseInsensitiveComparator() {
        PriorityQueueCustom<String> queue = new PriorityQueueCustom<>(String.CASE_INSENSITIVE_ORDER);
        queue.add("banana");
        queue.add("APPLE");
        assertEquals("APPLE", queue.poll());
        assertEquals("banana", queue.poll());
    }

    @Test
    void shouldMatchStandardMaxHeap() {
        PriorityQueueCustom<Integer> actual = new PriorityQueueCustom<>(Comparator.reverseOrder());
        PriorityQueue<Integer> expected = new PriorityQueue<>(Comparator.reverseOrder());
        Random random = new Random(31);
        for (int i = 0; i < 1000; i++) {
            int value = random.nextInt();
            actual.add(value);
            expected.add(value);
        }
        while (!expected.isEmpty()) {
            assertEquals(expected.poll(), actual.poll());
        }
    }

    @Test
    void shouldRemoveRoot() {
        PriorityQueueCustom<Integer> queue = createQueue(new int[] {1, 5, 3, 7});
        assertTrue(queue.remove(1));
        assertOrder(queue, new int[] {3, 5, 7});
    }

    @Test
    void shouldRestoreHeapDownAfterRemoval() {
        PriorityQueueCustom<Integer> queue = createQueue(new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
        assertTrue(queue.remove(2));
        assertOrder(queue, new int[] {1, 3, 4, 5, 6, 7, 8, 9, 10});
    }

    @Test
    void shouldRestoreHeapUpAfterRemoval() {
        PriorityQueueCustom<Integer> queue = createQueue(new int[] {1, 10, 2, 11, 12, 3, 4});
        assertTrue(queue.remove(11));
        assertOrder(queue, new int[] {1, 2, 3, 4, 10, 12});
    }

    @Test
    void shouldRemoveLastAndOnlyItem() {
        PriorityQueueCustom<Integer> queue = createQueue(new int[] {1, 3});
        assertTrue(queue.remove(3));
        assertTrue(queue.remove(1));
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldReturnFalseForMissingItem() {
        PriorityQueueCustom<Integer> queue = createQueue(new int[] {2, 3});
        assertFalse(queue.remove(9));
        assertOrder(queue, new int[] {2, 3});
    }

    @Test
    void shouldReturnFalseForEmptyQueueAndNull() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        assertFalse(queue.remove(4));
        assertFalse(queue.remove(null));
    }

    @Test
    void shouldRemoveOnlyOneDuplicate() {
        PriorityQueueCustom<Integer> queue = createQueue(new int[] {1, 2, 2, 3});
        assertTrue(queue.remove(2));
        assertOrder(queue, new int[] {1, 2, 3});
    }

    @Test
    void shouldRemoveWithReverseComparator() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(Comparator.reverseOrder());
        for (int value : new int[] {1, 5, 3, 4, 2}) {
            queue.add(value);
        }
        assertTrue(queue.remove(5));
        assertTrue(queue.remove(3));
        assertOrder(queue, new int[] {4, 2, 1});
    }

    @Test
    void shouldMatchStandardQueueWithRemovals() {
        Random random = new Random(31);
        PriorityQueueCustom<Integer> actual = new PriorityQueueCustom<>();
        PriorityQueue<Integer> expected = new PriorityQueue<>();
        for (int i = 0; i < 10000; i++) {
            int value = random.nextInt(100);
            int operation = random.nextInt(3);
            if (operation == 0) {
                actual.add(value);
                expected.add(value);
            } else if (operation == 1) {
                assertEquals(expected.remove(value), actual.remove(value));
            } else if (!expected.isEmpty()) {
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
    }

    @Test
    void shouldBuildHeapFromArray() {
        Integer[] values = {8, 3, 5, 1, 3};
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(values);
        assertOrder(queue, new int[] {1, 3, 3, 5, 8});
    }

    @Test
    void shouldNotChangeInputArray() {
        Integer[] values = {3, 1, 2};
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(values);
        queue.poll();
        queue.add(0);
        assertArrayEquals(new Integer[] {3, 1, 2}, values);
    }

    @Test
    void shouldBuildFromEmptyArrayAndThenGrow() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(new Integer[0]);
        assertTrue(queue.isEmpty());
        for (int i = 20; i >= 0; i--) {
            queue.add(i);
        }
        for (int i = 0; i <= 20; i++) {
            assertEquals(i, queue.poll());
        }
    }

    @Test
    void shouldBuildFromSingleItem() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(new Integer[] {7});
        assertOrder(queue, new int[] {7});
    }

    @Test
    void shouldRejectNullArrayAndNullElements() {
        Integer[] missing = null;
        assertThrows(NullPointerException.class, () -> new PriorityQueueCustom<Integer>(missing));
        Integer[] values = {1, null, 2};
        assertThrows(NullPointerException.class, () -> new PriorityQueueCustom<Integer>(values));
    }

    @Test
    void shouldBuildFromLargeDescendingArray() {
        Integer[] values = new Integer[100000];
        for (int i = 0; i < values.length; i++) {
            values[i] = values.length - i;
        }
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(values);
        for (int i = 1; i <= values.length; i++) {
            assertEquals(i, queue.poll());
        }
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldMatchStandardHeapFromRandomArray() {
        Integer[] values = new Integer[1000];
        Random random = new Random(31);
        for (int i = 0; i < values.length; i++) {
            values[i] = random.nextInt();
        }
        PriorityQueueCustom<Integer> actual = new PriorityQueueCustom<>(values);
        PriorityQueue<Integer> expected = new PriorityQueue<>();
        for (int value : values) {
            expected.add(value);
        }
        while (!expected.isEmpty()) {
            assertEquals(expected.poll(), actual.poll());
        }
    }
}
