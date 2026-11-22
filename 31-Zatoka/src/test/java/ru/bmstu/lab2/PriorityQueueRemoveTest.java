package ru.bmstu.lab2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;
import org.junit.jupiter.api.Test;

/** Удаление по значению при сохранении свойства кучи. */
class PriorityQueueRemoveTest {
    @Test
    void shouldReturnFalseForEmptyQueue() {
        assertFalse(new PriorityQueueCustom<Integer>().remove(1));
    }

    @Test
    void shouldReturnFalseForAbsentValue() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(2);
        assertFalse(queue.remove(3));
        assertEquals(1, queue.size());
    }

    @Test
    void shouldRemoveRoot() {
        PriorityQueueCustom<Integer> queue = queue(1, 4, 2, 5);
        assertTrue(queue.remove(1));
        assertEquals(2, queue.poll());
        assertEquals(4, queue.poll());
        assertEquals(5, queue.poll());
    }

    @Test
    void shouldRemoveMiddleValue() {
        PriorityQueueCustom<Integer> queue = queue(3, 8, 4, 9, 5, 6);
        assertTrue(queue.remove(8));
        assertEquals(3, queue.poll());
        assertEquals(4, queue.poll());
        assertEquals(5, queue.poll());
        assertEquals(6, queue.poll());
        assertEquals(9, queue.poll());
    }

    @Test
    void shouldRemoveLastElement() {
        PriorityQueueCustom<Integer> queue = queue(1, 2, 3);
        assertTrue(queue.remove(3));
        assertEquals(2, queue.size());
    }

    @Test
    void shouldOnlyRemoveOneDuplicate() {
        PriorityQueueCustom<Integer> queue = queue(2, 2, 2);
        assertTrue(queue.remove(2));
        assertEquals(2, queue.size());
    }

    @Test
    void shouldRestoreHeapAfterReplacingInternalValue() {
        PriorityQueueCustom<Integer> queue = queue(1, 10, 2, 11, 12, 3, 4, 13);
        assertTrue(queue.remove(11));
        int previous = Integer.MIN_VALUE;
        while (!queue.isEmpty()) {
            int next = queue.poll();
            assertTrue(previous <= next);
            previous = next;
        }
    }

    @Test
    void shouldRemoveFromMaxHeap() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(Comparator.reverseOrder());
        for (int value : new int[] {1, 5, 3, 4}) {
            queue.add(value);
        }
        assertTrue(queue.remove(5));
        assertEquals(4, queue.poll());
        assertEquals(3, queue.poll());
    }

    @Test
    void shouldAllowNullLookup() {
        PriorityQueueCustom<Integer> queue = queue(1, 2);
        assertFalse(queue.remove(null));
    }

    @Test
    void shouldRestoreHeapBySiftingUp() {
        PriorityQueueCustom<Integer> queue = queue(1, 50, 2, 60, 70, 3, 4,
                80, 90, 100, 110, 5);
        assertTrue(queue.remove(60));
        int previous = Integer.MIN_VALUE;
        while (!queue.isEmpty()) {
            int current = queue.poll();
            assertTrue(previous <= current);
            previous = current;
        }
    }

    @Test
    void shouldRemoveFirstMatchingObjectByEquals() {
        PriorityQueueCustom<String> queue = new PriorityQueueCustom<>();
        queue.add(new String("alpha"));
        queue.add("beta");
        assertTrue(queue.remove(new String("alpha")));
        assertEquals("beta", queue.poll());
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldMatchStandardQueueAfterRandomRemovals() {
        Random random = new Random(31);
        PriorityQueueCustom<Integer> actual = new PriorityQueueCustom<>();
        PriorityQueue<Integer> expected = new PriorityQueue<>();
        for (int i = 0; i < 3_000; i++) {
            int value = random.nextInt(101) - 50;
            if (i % 3 == 0) {
                assertEquals(expected.remove(value), actual.remove(value));
            } else {
                expected.add(value);
                actual.add(value);
            }
            assertEquals(expected.size(), actual.size());
            assertEquals(expected.peek(), actual.peek());
        }
        while (!expected.isEmpty()) {
            assertEquals(expected.poll(), actual.poll());
        }
        assertTrue(actual.isEmpty());
    }

    private static PriorityQueueCustom<Integer> queue(int... values) {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (int value : values) {
            queue.add(value);
        }
        return queue;
    }
}
