package ru.bmstu.lab02.task_01c;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

import org.junit.jupiter.api.Test;

/** Проверяет удаление по значению и оба направления восстановления кучи. */
class RemovablePriorityQueueTest {
    @Test
    void shouldReturnFalseWhenRemovingFromEmptyQueue() {
        RemovablePriorityQueue<Integer> queue = new RemovablePriorityQueue<>();
        assertFalse(queue.remove(5));
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldRemoveRoot() {
        RemovablePriorityQueue<Integer> queue = queueOf(4, 1, 6, 3, 2);
        assertTrue(queue.remove(1));
        assertPollOrder(queue, 2, 3, 4, 6);
    }

    @Test
    void shouldSiftDownAfterRemovingMiddleElement() {
        RemovablePriorityQueue<Integer> queue = queueOf(1, 2, 3, 4, 5, 6, 7, 8, 9);
        assertTrue(queue.remove(2));
        assertPollOrder(queue, 1, 3, 4, 5, 6, 7, 8, 9);
    }

    @Test
    void shouldSiftUpAfterRemovingMiddleElement() {
        RemovablePriorityQueue<Integer> queue = queueOf(0, 10, 1, 11, 12, 2, 3);
        assertTrue(queue.remove(11));
        assertPollOrder(queue, 0, 1, 2, 3, 10, 12);
    }

    @Test
    void shouldRemoveLastArrayElement() {
        RemovablePriorityQueue<Integer> queue = queueOf(1, 2, 3, 4);
        assertTrue(queue.remove(4));
        assertPollOrder(queue, 1, 2, 3);
    }

    @Test
    void shouldPreserveQueueWhenValueIsAbsent() {
        RemovablePriorityQueue<Integer> queue = queueOf(2, 1, 3);
        assertFalse(queue.remove(99));
        assertEquals(3, queue.size());
        assertPollOrder(queue, 1, 2, 3);
    }

    @Test
    void shouldRemoveOnlyOneOccurrence() {
        RemovablePriorityQueue<Integer> queue = queueOf(2, 2, 1, 2);
        assertTrue(queue.remove(2));
        assertEquals(3, queue.size());
        assertPollOrder(queue, 1, 2, 2);
    }

    @Test
    void shouldRemoveSingleElementAndAllowReuse() {
        RemovablePriorityQueue<Integer> queue = queueOf(7);
        assertTrue(queue.remove(7));
        assertTrue(queue.isEmpty());
        assertFalse(queue.remove(7));
        queue.add(4);
        assertEquals(4, queue.poll());
    }

    @Test
    void shouldReturnFalseForNullValue() {
        RemovablePriorityQueue<Integer> queue = queueOf(1, 2);
        assertFalse(queue.remove(null));
        assertPollOrder(queue, 1, 2);
    }

    @Test
    void shouldRemoveFromMaxHeap() {
        RemovablePriorityQueue<Integer> queue = new RemovablePriorityQueue<>(Comparator.reverseOrder());
        for (int value : new int[] {1, 5, 3, 7, 2, 6, 4}) {
            queue.add(value);
        }
        assertTrue(queue.remove(7));
        assertTrue(queue.remove(3));
        assertPollOrder(queue, 6, 5, 4, 2, 1);
    }

    @Test
    void shouldUseEqualsInsteadOfComparatorEquality() {
        RemovablePriorityQueue<String> queue = new RemovablePriorityQueue<>(Comparator.comparingInt(String::length));
        queue.add("aa");
        queue.add("bb");
        queue.add("x");
        assertFalse(queue.remove("cc"));
        assertTrue(queue.remove(new String("aa")));
        assertEquals("x", queue.poll());
        assertEquals("bb", queue.poll());
    }

    @Test
    void shouldMatchStandardQueueOnMixedOperations() {
        RemovablePriorityQueue<Integer> actual = new RemovablePriorityQueue<>();
        PriorityQueue<Integer> expected = new PriorityQueue<>();
        Random random = new Random(104);
        for (int step = 0; step < 5000; step++) {
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
            assertEquals(expected.isEmpty(), actual.isEmpty());
            assertEquals(expected.peek(), actual.peek());
        }
        while (!expected.isEmpty()) {
            assertEquals(expected.poll(), actual.poll());
        }
    }

    private RemovablePriorityQueue<Integer> queueOf(Integer... values) {
        RemovablePriorityQueue<Integer> queue = new RemovablePriorityQueue<>();
        for (Integer value : values) {
            queue.add(value);
        }
        return queue;
    }

    private void assertPollOrder(RemovablePriorityQueue<Integer> queue, Integer... expected) {
        for (Integer value : expected) {
            assertEquals(value, queue.poll());
        }
        assertTrue(queue.isEmpty());
    }
}
