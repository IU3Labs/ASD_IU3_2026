package ru.bmstu.lab2;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Random;
import org.junit.jupiter.api.Test;

/**
 * Тесты задачи 1A: базовые операции очереди с приоритетом в естественном порядке.
 */
class PriorityQueueCustomTest {

    /**
     * Извлекает все элементы очереди в массив.
     */
    private static int[] drain(PriorityQueueCustom<Integer> queue) {
        int[] result = new int[queue.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = queue.poll();
        }
        return result;
    }

    @Test
    void shouldBeEmptyAfterCreation() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
    }

    @Test
    void shouldThrowOnPollFromEmpty() {
        assertThrows(NoSuchElementException.class, () -> new PriorityQueueCustom<Integer>().poll());
    }

    @Test
    void shouldThrowOnPeekFromEmpty() {
        assertThrows(NoSuchElementException.class, () -> new PriorityQueueCustom<Integer>().peek());
    }

    @Test
    void shouldReturnSingleElement() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(42);
        assertEquals(42, queue.peek());
        assertEquals(42, queue.poll());
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldPeekWithoutRemoving() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(5);
        queue.add(1);
        queue.add(3);
        assertEquals(1, queue.peek());
        assertEquals(1, queue.peek());
        assertEquals(3, queue.size());
    }

    @Test
    void shouldPollInNonDecreasingOrder() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (int x : new int[] {5, 3, 8, 1, 9, 2, 7}) {
            queue.add(x);
        }
        assertArrayEquals(new int[] {1, 2, 3, 5, 7, 8, 9}, drain(queue));
    }

    @Test
    void shouldKeepDuplicates() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (int x : new int[] {2, 1, 2, 1, 2}) {
            queue.add(x);
        }
        assertArrayEquals(new int[] {1, 1, 2, 2, 2}, drain(queue));
    }

    @Test
    void shouldHandleNegativeAndExtremeValues() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (int x : new int[] {0, Integer.MAX_VALUE, -7, Integer.MIN_VALUE, 3}) {
            queue.add(x);
        }
        assertArrayEquals(new int[] {Integer.MIN_VALUE, -7, 0, 3, Integer.MAX_VALUE}, drain(queue));
    }

    @Test
    void shouldGrowBeyondInitialCapacity() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (int i = 1000; i > 0; i--) {
            queue.add(i);
        }
        assertEquals(1000, queue.size());
        for (int i = 1; i <= 1000; i++) {
            assertEquals(i, queue.poll());
        }
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldMatchSortOnRandomData() {
        Random random = new Random(1);
        int[] data = random.ints(10_000, -1000, 1000).toArray();
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (int x : data) {
            queue.add(x);
        }
        Arrays.sort(data);
        assertArrayEquals(data, drain(queue));
    }

    @Test
    void shouldWorkAfterInterleavedAddAndPoll() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(5);
        queue.add(3);
        assertEquals(3, queue.poll());
        queue.add(1);
        queue.add(4);
        assertEquals(1, queue.poll());
        assertEquals(4, queue.poll());
        assertEquals(5, queue.poll());
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldBeReusableAfterBecomingEmpty() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(1);
        queue.poll();
        assertThrows(NoSuchElementException.class, queue::poll);
        queue.add(7);
        assertEquals(7, queue.peek());
        assertFalse(queue.isEmpty());
    }

    @Test
    void shouldRejectNull() {
        assertThrows(NullPointerException.class, () -> new PriorityQueueCustom<Integer>().add(null));
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
}
