package ru.bmstu.lab2.task_1a;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import org.junit.jupiter.api.Test;

/**
 * Тесты задачи 1A — {@link PriorityQueueCustom}.
 */
class PriorityQueueCustomTest {

    /** Больше начальной ёмкости (16): проверяет многократное расширение массива. */
    private static final int MANY_ELEMENTS = 1000;

    /** Размер стресс-теста со случайными числами. */
    private static final int STRESS_SIZE = 100_000;

    /** Фиксированное зерно, чтобы тест был воспроизводимым. */
    private static final long SEED = 42L;

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
    void shouldThrowWhenPeekFromEmptyQueue() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();

        assertThrows(NoSuchElementException.class, queue::peek);
    }

    @Test
    void shouldReturnSingleElement() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(7);

        assertEquals(7, queue.peek());
        assertEquals(7, queue.poll());
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldPeekMinimumWithoutRemovingIt() {
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
        for (int value : new int[] {5, 3, 8, 1, 9, 2, 7}) {
            queue.add(value);
        }

        List<Integer> actual = pollAll(queue);

        assertEquals(List.of(1, 2, 3, 5, 7, 8, 9), actual);
    }

    @Test
    void shouldKeepDuplicates() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (int value : new int[] {2, 1, 2, 1, 2}) {
            queue.add(value);
        }

        assertEquals(List.of(1, 1, 2, 2, 2), pollAll(queue));
    }

    @Test
    void shouldHandleAlreadySortedAndReversedInput() {
        PriorityQueueCustom<Integer> ascending = new PriorityQueueCustom<>();
        PriorityQueueCustom<Integer> descending = new PriorityQueueCustom<>();
        List<Integer> expected = new ArrayList<>();
        for (int i = 0; i < MANY_ELEMENTS; i++) {
            ascending.add(i);
            descending.add(MANY_ELEMENTS - 1 - i);
            expected.add(i);
        }

        assertEquals(expected, pollAll(ascending));
        assertEquals(expected, pollAll(descending));
    }

    @Test
    void shouldGrowBeyondInitialCapacity() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (int i = MANY_ELEMENTS; i > 0; i--) {
            queue.add(i);
        }

        assertEquals(MANY_ELEMENTS, queue.size());
        assertEquals(1, queue.peek());
    }

    @Test
    void shouldWorkCorrectlyWhenAddAndPollAreInterleaved() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(10);
        queue.add(4);
        assertEquals(4, queue.poll());
        queue.add(6);
        queue.add(1);
        assertEquals(1, queue.poll());
        queue.add(8);

        assertEquals(List.of(6, 8, 10), pollAll(queue));
    }

    @Test
    void shouldMatchSortedOrderOnRandomData() {
        Random random = new Random(SEED);
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        List<Integer> expected = new ArrayList<>();
        for (int i = 0; i < STRESS_SIZE; i++) {
            int value = random.nextInt();
            queue.add(value);
            expected.add(value);
        }
        Collections.sort(expected);

        assertEquals(expected, pollAll(queue));
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldUseCustomComparator() {
        PriorityQueueCustom<String> queue = new PriorityQueueCustom<>(Comparator.comparingInt(String::length));
        queue.add("ccc");
        queue.add("a");
        queue.add("bb");

        assertEquals(List.of("a", "bb", "ccc"), pollAll(queue));
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
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldBeReusableAfterBecomingEmpty() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(3);
        queue.poll();
        queue.add(2);
        queue.add(1);

        assertEquals(List.of(1, 2), pollAll(queue));
    }

    private static <T> List<T> pollAll(PriorityQueueCustom<T> queue) {
        List<T> result = new ArrayList<>();
        while (!queue.isEmpty()) {
            result.add(queue.poll());
        }
        return result;
    }
}
