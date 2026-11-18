package ru.bmstu.lab02.task_01a;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

/** Проверяет базовую очередь, границы и расширение динамического массива. */
class PriorityQueueCustomTest {
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
        assertEquals(0, queue.size());
    }

    @Test
    void shouldPeekWithoutRemovingMinimum() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(8);
        queue.add(3);
        assertEquals(3, queue.peek());
        assertEquals(3, queue.peek());
        assertEquals(2, queue.size());
    }

    @Test
    void shouldHandleSingleElement() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(42);
        assertFalse(queue.isEmpty());
        assertEquals(42, queue.poll());
        assertTrue(queue.isEmpty());
        assertNull(queue.peek());
    }

    @Test
    void shouldPollUnsortedInputInNondecreasingOrder() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (int value : new int[] {7, 3, 9, 1, 4, 2}) {
            queue.add(value);
        }
        assertPollOrder(queue, 1, 2, 3, 4, 7, 9);
    }

    @Test
    void shouldKeepDuplicateElements() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(5);
        queue.add(5);
        queue.add(5);
        assertPollOrder(queue, 5, 5, 5);
    }

    @Test
    void shouldHandleNegativeAndExtremeValues() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (int value : new int[] {0, Integer.MAX_VALUE, -1, Integer.MIN_VALUE}) {
            queue.add(value);
        }
        assertPollOrder(queue, Integer.MIN_VALUE, -1, 0, Integer.MAX_VALUE);
    }

    @Test
    void shouldGrowArraySeveralTimes() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (int value = 2000; value > 0; value--) {
            queue.add(value);
        }
        assertEquals(2000, queue.size());
        for (int expected = 1; expected <= 2000; expected++) {
            assertEquals(expected, queue.poll());
        }
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldAllowReuseAfterDraining() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(2);
        queue.poll();
        queue.add(9);
        queue.add(1);
        assertPollOrder(queue, 1, 9);
    }

    @Test
    void shouldRejectNullWithoutChangingQueue() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(4);
        assertThrows(NullPointerException.class, () -> queue.add(null));
        assertEquals(1, queue.size());
        assertEquals(4, queue.peek());
    }

    @Test
    void shouldHandleAlreadySortedInput() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (int value = 1; value <= 6; value++) {
            queue.add(value);
        }
        assertPollOrder(queue, 1, 2, 3, 4, 5, 6);
    }

    @Test
    void shouldSupportInterleavedAddAndPoll() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(6);
        queue.add(2);
        assertEquals(2, queue.poll());
        queue.add(1);
        queue.add(5);
        assertPollOrder(queue, 1, 5, 6);
        assertThrows(NoSuchElementException.class, queue::poll);
    }

    private void assertPollOrder(PriorityQueueCustom<Integer> queue, Integer... expected) {
        for (Integer value : expected) {
            assertEquals(value, queue.poll());
        }
        assertTrue(queue.isEmpty());
    }
}
