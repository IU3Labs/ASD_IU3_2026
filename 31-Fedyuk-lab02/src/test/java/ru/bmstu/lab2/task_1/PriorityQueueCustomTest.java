package ru.bmstu.lab2.task_1;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.bmstu.lab2.task_1.QueueTestSupport.drain;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

/** Проверки очереди: PriorityQueueCustomTest. */
class PriorityQueueCustomTest {
    private static final int LARGE_SIZE = 100_000;

    @Test
    void shouldStartEmpty() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
    }

    @Test
    void shouldRejectPollFromEmptyQueue() {
        assertThrows(NoSuchElementException.class, () -> new PriorityQueueCustom<Integer>().poll());
    }

    @Test
    void shouldRejectPeekFromEmptyQueue() {
        assertThrows(NoSuchElementException.class, () -> new PriorityQueueCustom<Integer>().peek());
    }

    @Test
    void shouldPreserveSizeWhenPeeking() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(5);
        queue.add(-1);
        assertEquals(-1, queue.peek());
        assertEquals(-1, queue.peek());
        assertEquals(2, queue.size());
    }

    @Test
    void shouldExtractValuesInIncreasingOrder() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (int value : new int[] {7, -3, 4, 0, 2}) {
            queue.add(value);
        }
        assertArrayEquals(new int[] {-3, 0, 2, 4, 7}, drain(queue));
    }

    @Test
    void shouldHandleDuplicatesAndIntegerLimits() {
        PriorityQueueCustom<Integer> queue =
                new PriorityQueueCustom<>(
                        new Integer[] {Integer.MAX_VALUE, 0, Integer.MIN_VALUE, 0});
        assertArrayEquals(new int[] {Integer.MIN_VALUE, 0, 0, Integer.MAX_VALUE}, drain(queue));
    }

    @Test
    void shouldRejectNullItemWithoutChangingSize() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        assertThrows(NullPointerException.class, () -> queue.add(null));
        assertEquals(0, queue.size());
    }

    @Test
    void shouldGrowAndExtractOneHundredThousandElements() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (int value = LARGE_SIZE - 1; value >= 0; value--) {
            queue.add(value);
        }
        assertEquals(LARGE_SIZE, queue.size());
        for (int expected = 0; expected < LARGE_SIZE; expected++) {
            assertEquals(expected, queue.poll());
        }
        assertTrue(queue.isEmpty());
    }
}
