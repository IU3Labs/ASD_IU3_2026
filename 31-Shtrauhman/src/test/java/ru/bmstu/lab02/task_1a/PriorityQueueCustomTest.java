package ru.bmstu.lab02.task_1a;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Random;
import org.junit.jupiter.api.Test;

/**
 * Тесты задачи 1A: очередь с приоритетом на min-куче.
 */
class PriorityQueueCustomTest {

    private static final long SEED = 20261108L;

    @Test
    void shouldBeEmptyWhenCreated() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();

        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
    }

    @Test
    void shouldThrowWhenPollFromEmptyQueue() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();

        assertThrows(NoSuchElementException.class, queue::poll);
    }

    @Test
    void shouldThrowWhenPeekIntoEmptyQueue() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();

        assertThrows(NoSuchElementException.class, queue::peek);
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
    void shouldPeekMinimumWithoutRemoving() {
        PriorityQueueCustom<Integer> queue = queueOf(5, 1, 3);

        assertEquals(1, queue.peek());
        assertEquals(1, queue.peek());
        assertEquals(3, queue.size());
    }

    @Test
    void shouldPollInNonDecreasingOrder() {
        PriorityQueueCustom<Integer> queue = queueOf(7, 2, 9, 4, 1, 8, 3);

        assertEquals(List.of(1, 2, 3, 4, 7, 8, 9), drain(queue));
    }

    @Test
    void shouldKeepDuplicates() {
        PriorityQueueCustom<Integer> queue = queueOf(3, 1, 3, 1, 2);

        assertEquals(List.of(1, 1, 2, 3, 3), drain(queue));
    }

    @Test
    void shouldHandleAlreadySortedAndReversedInput() {
        PriorityQueueCustom<Integer> ascending = queueOf(1, 2, 3, 4, 5);
        PriorityQueueCustom<Integer> descending = queueOf(5, 4, 3, 2, 1);

        assertEquals(List.of(1, 2, 3, 4, 5), drain(ascending));
        assertEquals(List.of(1, 2, 3, 4, 5), drain(descending));
    }

    @Test
    void shouldGrowBeyondInitialCapacity() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(1);
        for (int i = 100; i > 0; i--) {
            queue.add(i);
        }

        assertEquals(100, queue.size());
        assertEquals(1, queue.peek());
    }

    @Test
    void shouldHandleExtremeIntegerValues() {
        PriorityQueueCustom<Integer> queue = queueOf(0, Integer.MAX_VALUE, Integer.MIN_VALUE, -1);

        assertEquals(List.of(Integer.MIN_VALUE, -1, 0, Integer.MAX_VALUE), drain(queue));
    }

    @Test
    void shouldWorkWithStrings() {
        PriorityQueueCustom<String> queue = new PriorityQueueCustom<>();
        queue.add("pear");
        queue.add("apple");
        queue.add("banana");

        assertEquals("apple", queue.poll());
        assertEquals("banana", queue.poll());
        assertEquals("pear", queue.poll());
    }

    @Test
    void shouldStayCorrectWhenAddAndPollAreInterleaved() {
        PriorityQueueCustom<Integer> queue = queueOf(5, 3);
        assertEquals(3, queue.poll());
        queue.add(1);
        queue.add(4);
        assertEquals(1, queue.poll());
        queue.add(2);

        assertEquals(List.of(2, 4, 5), drain(queue));
    }

    @Test
    void shouldBeReusableAfterBecomingEmpty() {
        PriorityQueueCustom<Integer> queue = queueOf(2, 1);
        drain(queue);
        queue.add(10);

        assertEquals(1, queue.size());
        assertEquals(10, queue.poll());
    }

    @Test
    void shouldMatchJavaPriorityQueueOnRandomOperations() {
        Random random = new Random(SEED);
        PriorityQueueCustom<Integer> actual = new PriorityQueueCustom<>();
        PriorityQueue<Integer> expected = new PriorityQueue<>();

        for (int step = 0; step < 100_000; step++) {
            // Примерно 60% добавлений и 40% извлечений: очередь растёт, но часто опустошается частично.
            if (expected.isEmpty() || random.nextInt(10) < 6) {
                int value = random.nextInt(1_000) - 500;
                actual.add(value);
                expected.add(value);
            } else {
                assertEquals(expected.poll(), actual.poll());
            }
            assertEquals(expected.size(), actual.size());
        }
    }

    @Test
    void shouldRejectNullElement() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();

        assertThrows(NullPointerException.class, () -> queue.add(null));
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldRejectNonPositiveCapacity() {
        assertThrows(IllegalArgumentException.class, () -> new PriorityQueueCustom<Integer>(0));
    }

    private static PriorityQueueCustom<Integer> queueOf(int... values) {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (int value : values) {
            queue.add(value);
        }
        return queue;
    }

    private static <T extends Comparable<? super T>> List<T> drain(PriorityQueueCustom<T> queue) {
        List<T> result = new ArrayList<>();
        while (!queue.isEmpty()) {
            result.add(queue.poll());
        }
        return result;
    }
}
