package ru.bmstu.lab02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.bmstu.lab02.QueueTestSupport.drain;

import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

/** Задача 1A: базовые операции, порядок извлечения и расширение массива. */
class PriorityQueueBaseTest {
    private static final int GROWTH_ELEMENT_COUNT = 2_048;

    @Test
    void shouldStartEmpty() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();

        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
        assertNull(queue.peek());
    }

    @Test
    void shouldThrowWhenPollingEmptyQueue() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();

        assertThrows(NoSuchElementException.class, queue::poll);
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
    }

    @Test
    void shouldPeekWithoutRemovingSingleElement() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(42);

        assertFalse(queue.isEmpty());
        assertEquals(42, queue.peek());
        assertEquals(42, queue.peek());
        assertEquals(1, queue.size());
        assertEquals(42, queue.poll());
        assertTrue(queue.isEmpty());
        assertNull(queue.peek());
    }

    @Test
    void shouldPollInAscendingOrderAfterDescendingInsertions() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (int value : new int[]{5, 4, 3, 2, 1}) {
            queue.add(value);
        }

        assertEquals(List.of(1, 2, 3, 4, 5), drain(queue));
    }

    @Test
    void shouldOrderNegativeValuesAndIntegerLimits() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (int value : new int[]{0, Integer.MAX_VALUE, -1, Integer.MIN_VALUE, 1}) {
            queue.add(value);
        }

        assertEquals(List.of(Integer.MIN_VALUE, -1, 0, 1, Integer.MAX_VALUE), drain(queue));
    }

    @Test
    void shouldKeepAllDuplicateValues() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (int value : new int[]{3, 1, 3, 1, 3}) {
            queue.add(value);
        }

        assertEquals(5, queue.size());
        assertEquals(List.of(1, 1, 3, 3, 3), drain(queue));
    }

    @Test
    void shouldGrowAndPreserveEveryElement() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (int value = GROWTH_ELEMENT_COUNT; value > 0; value--) {
            queue.add(value);
        }

        assertEquals(GROWTH_ELEMENT_COUNT, queue.size());
        for (int expected = 1; expected <= GROWTH_ELEMENT_COUNT; expected++) {
            assertEquals(expected, queue.poll());
        }
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldSupportInterleavedInsertionsAndExtractions() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(5);
        queue.add(2);
        assertEquals(2, queue.poll());
        queue.add(1);
        queue.add(4);
        assertEquals(1, queue.poll());
        queue.add(3);

        assertEquals(List.of(3, 4, 5), drain(queue));
    }

    @Test
    void shouldRejectNullWithoutChangingContents() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(2);
        queue.add(1);

        assertThrows(NullPointerException.class, () -> queue.add(null));
        assertEquals(2, queue.size());
        assertEquals(List.of(1, 2), drain(queue));
    }

    @Test
    void shouldUseNaturalOrderForStrings() {
        PriorityQueueCustom<String> queue = new PriorityQueueCustom<>();
        queue.add("pear");
        queue.add("apple");
        queue.add("banana");

        assertEquals(List.of("apple", "banana", "pear"), drain(queue));
    }

    @Test
    void shouldAllowReuseAfterAllElementsWerePolled() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(2);
        assertEquals(2, queue.poll());
        queue.add(3);
        queue.add(1);

        assertEquals(List.of(1, 3), drain(queue));
        assertThrows(NoSuchElementException.class, queue::poll);
    }
}