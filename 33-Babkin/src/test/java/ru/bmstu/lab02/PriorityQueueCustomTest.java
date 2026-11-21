package ru.bmstu.lab02;

import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Random;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PriorityQueueCustomTest {

    @Test
    void shouldStartEmpty() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
    }

    @Test
    void shouldThrowWhenPollingEmptyQueue() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        assertThrows(NoSuchElementException.class, queue::poll);
    }

    @Test
    void shouldReturnNullWhenPeekingAtEmptyQueue() {
        assertNull(new PriorityQueueCustom<Integer>().peek());
    }

    @Test
    void shouldReturnMinimumWithoutRemovingIt() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(7);
        queue.add(-2);
        assertEquals(-2, queue.peek());
        assertEquals(-2, queue.peek());
        assertEquals(2, queue.size());
        assertFalse(queue.isEmpty());
    }

    @Test
    void shouldBecomeEmptyAfterPollingTheOnlyItem() {
        PriorityQueueCustom<String> queue = new PriorityQueueCustom<>();
        queue.add("one");
        assertEquals("one", queue.poll());
        assertTrue(queue.isEmpty());
        queue.add("two");
        assertEquals("two", queue.poll());
    }

    @Test
    void shouldKeepDuplicateValues() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(3);
        queue.add(1);
        queue.add(1);
        assertEquals(1, queue.poll());
        assertEquals(1, queue.poll());
        assertEquals(3, queue.poll());
    }

    @Test
    void shouldExpandAndDrainInAscendingOrder() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (int value = 1023; value >= 0; value--) {
            queue.add(value);
        }
        assertEquals(1024, queue.size());
        for (int value = 0; value < 1024; value++) {
            assertEquals(value, queue.poll());
        }
    }

    @Test
    void shouldBuildFromAnEmptyArrayAndAcceptNewItems() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(new Integer[0]);
        assertTrue(queue.isEmpty());
        queue.add(2);
        assertEquals(2, queue.poll());
    }

    @Test
    void shouldBuildFromACopyOfTheArray() {
        Integer[] values = {7, 2, 5, 1};
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(values);
        assertArrayEquals(new Integer[]{7, 2, 5, 1}, values);
        values[3] = -1;
        assertEquals(1, queue.poll());
        queue.add(0);
        assertEquals(0, queue.poll());
    }

    @Test
    void shouldHeapifyAndGrowAnArrayOfStrings() {
        String[] values = new String[16];
        for (int index = 0; index < values.length; index++) {
            values[index] = "z" + index;
        }
        PriorityQueueCustom<String> queue = new PriorityQueueCustom<>(values);
        queue.add("a");
        assertEquals("a", queue.poll());
        assertEquals(16, queue.size());
    }

    @Test
    void shouldRejectNulls() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        assertThrows(NullPointerException.class, () -> queue.add(null));
        assertThrows(NullPointerException.class, () -> new PriorityQueueCustom<>(new Integer[]{1, null}));
        assertThrows(NullPointerException.class, () -> new PriorityQueueCustom<Integer>(null));
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldMatchStandardQueueForBothWaysOfBuilding() {
        Random random = new Random(42);
        Integer[] values = new Integer[500];
        PriorityQueueCustom<Integer> inserted = new PriorityQueueCustom<>();
        PriorityQueue<Integer> expected = new PriorityQueue<>();
        for (int index = 0; index < values.length; index++) {
            int value = random.nextInt(100) - 50;
            values[index] = value;
            inserted.add(value);
            expected.add(value);
        }
        PriorityQueueCustom<Integer> heapified = new PriorityQueueCustom<>(values);
        while (!expected.isEmpty()) {
            int minimum = expected.remove();
            assertEquals(minimum, inserted.poll());
            assertEquals(minimum, heapified.poll());
        }
        assertTrue(inserted.isEmpty());
        assertTrue(heapified.isEmpty());
    }
}
