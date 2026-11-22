package ru.bmstu.lab02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Random;

import org.junit.jupiter.api.Test;

/** Задача 1A: базовые операции add / poll / peek / size / isEmpty и расширение массива. */
class PriorityQueueCustomTest {

    @Test
    void shouldBeEmptyAfterCreation() {
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
    void shouldThrowWhenPeekingEmptyQueue() {
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
        for (int value : new int[] {7, -2, 9, 0, 3, -5, 8}) {
            queue.add(value);
        }

        assertEquals(List.of(-5, -2, 0, 3, 7, 8, 9), QueueTestUtils.drain(queue));
    }

    @Test
    void shouldKeepAllDuplicates() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (int value : new int[] {2, 1, 2, 1, 2}) {
            queue.add(value);
        }

        assertEquals(List.of(1, 1, 2, 2, 2), QueueTestUtils.drain(queue));
    }

    @Test
    void shouldHandleAlreadySortedAndReversedInput() {
        PriorityQueueCustom<Integer> ascending = new PriorityQueueCustom<>();
        PriorityQueueCustom<Integer> descending = new PriorityQueueCustom<>();
        for (int i = 0; i < 100; i++) {
            ascending.add(i);
            descending.add(99 - i);
        }

        assertEquals(QueueTestUtils.drain(ascending), QueueTestUtils.drain(descending));
    }

    @Test
    void shouldGrowBeyondInitialCapacity() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        int count = PriorityQueueCustom.DEFAULT_CAPACITY * 4 + 1;
        for (int i = count; i > 0; i--) {
            queue.add(i);
        }

        assertEquals(count, queue.size());
        assertTrue(queue.capacity() >= count);
        assertEquals(1, queue.poll());
        assertTrue(queue.isValidHeap());
    }

    @Test
    void shouldUpdateSizeAfterEachOperation() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(3);
        queue.add(1);
        queue.poll();

        assertEquals(1, queue.size());
        assertFalse(queue.isEmpty());
    }

    @Test
    void shouldBeReusableAfterBecomingEmpty() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(10);
        queue.poll();
        queue.add(20);
        queue.add(15);

        assertEquals(List.of(15, 20), QueueTestUtils.drain(queue));
    }

    @Test
    void shouldRejectNullElement() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();

        assertThrows(NullPointerException.class, () -> queue.add(null));
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldRejectNonComparableElementWithoutComparator() {
        PriorityQueueCustom<Object> queue = new PriorityQueueCustom<>();

        assertThrows(ClassCastException.class, () -> queue.add(new Object()));
    }

    @Test
    void shouldSortStringsInNaturalOrder() {
        PriorityQueueCustom<String> queue = new PriorityQueueCustom<>();
        for (String word : new String[] {"pear", "apple", "fig", "banana"}) {
            queue.add(word);
        }

        assertEquals(List.of("apple", "banana", "fig", "pear"), QueueTestUtils.drain(queue));
    }

    @Test
    void shouldMatchStandardPriorityQueueOnRandomOperations() {
        PriorityQueueCustom<Integer> actual = new PriorityQueueCustom<>();
        PriorityQueue<Integer> expected = new PriorityQueue<>();
        Random random = new Random(QueueTestUtils.SEED);
        for (int step = 0; step < 20_000; step++) {
            // Вставок примерно в 2 раза больше, чем извлечений, чтобы очередь успела вырасти.
            if (expected.isEmpty() || random.nextInt(3) > 0) {
                int value = random.nextInt(1000);
                actual.add(value);
                expected.add(value);
            } else {
                assertEquals(expected.poll(), actual.poll());
            }
            assertEquals(expected.size(), actual.size());
        }
        assertTrue(actual.isValidHeap());
    }

    @Test
    void shouldSortLargeRandomInput() {
        Integer[] values = QueueTestUtils.randomIntegers(100_000, 1_000_000, QueueTestUtils.SEED);
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (Integer value : values) {
            queue.add(value);
        }
        Integer[] expected = values.clone();
        Arrays.sort(expected);

        assertEquals(Arrays.asList(expected), QueueTestUtils.drain(queue));
    }
}
