package ru.bmstu.lab2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Random;
import org.junit.jupiter.api.Test;

/** Проверяет операции и восстановление порядка двоичной кучи */
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
    }

    @Test
    void shouldRejectNullElement() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        assertThrows(NullPointerException.class, () -> queue.add(null));
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldPeekWithoutRemoving() {
        PriorityQueueCustom<Integer> queue = queueOf(7, 3, 5);
        assertEquals(3, queue.peek());
        assertEquals(3, queue.peek());
        assertEquals(3, queue.size());
    }

    @Test
    void shouldBecomeEmptyAfterPollingOnlyElement() {
        PriorityQueueCustom<Integer> queue = queueOf(7);
        assertEquals(7, queue.poll());
        assertTrue(queue.isEmpty());
        queue.add(4);
        assertEquals(4, queue.poll());
    }

    @Test
    void shouldPollInOrderWithDuplicatesAndNegativeValues() {
        PriorityQueueCustom<Integer> queue = queueOf(5, -2, 0, 5, -2, 1);
        assertEquals(List.of(-2, -2, 0, 1, 5, 5), drain(queue));
    }

    @Test
    void shouldGrowBeyondInitialCapacity() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (int value = 128; value >= 1; value--) {
            queue.add(value);
        }
        assertEquals(128, queue.size());
        for (int expected = 1; expected <= 128; expected++) {
            assertEquals(expected, queue.poll());
        }
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldRemoveRootAndRestoreOrder() {
        PriorityQueueCustom<Integer> queue = queueOf(1, 3, 2, 7, 5);
        assertTrue(queue.remove(1));
        assertEquals(List.of(2, 3, 5, 7), drain(queue));
    }

    @Test
    void shouldSiftReplacementDownAfterRemovingMiddleElement() {
        PriorityQueueCustom<Integer> queue = queueOf(1, 2, 3, 4, 5, 6, 7);
        assertTrue(queue.remove(2));
        assertEquals(List.of(1, 3, 4, 5, 6, 7), drain(queue));
    }

    @Test
    void shouldSiftReplacementUpAfterRemovingMiddleElement() {
        PriorityQueueCustom<Integer> queue = queueOf(1, 5, 2, 8, 9, 3, 4);
        assertTrue(queue.remove(8));
        assertEquals(List.of(1, 2, 3, 4, 5, 9), drain(queue));
    }

    @Test
    void shouldRemoveLastElement() {
        PriorityQueueCustom<Integer> queue = queueOf(1, 2, 3);
        assertTrue(queue.remove(3));
        assertEquals(List.of(1, 2), drain(queue));
    }

    @Test
    void shouldRemoveOneOccurrenceWhenDuplicatesExist() {
        PriorityQueueCustom<Integer> queue = queueOf(4, 4, 6);
        assertTrue(queue.remove(4));
        assertEquals(List.of(4, 6), drain(queue));
    }

    @Test
    void shouldReturnFalseForMissingValue() {
        PriorityQueueCustom<Integer> queue = queueOf(1, 4);
        assertFalse(queue.remove(9));
        assertEquals(List.of(1, 4), drain(queue));
    }

    @Test
    void shouldReturnFalseWhenRemovingFromEmptyQueue() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        assertFalse(queue.remove(1));
    }

    @Test
    void shouldMatchStandardQueueOnMixedOperations() {
        PriorityQueueCustom<Integer> actual = new PriorityQueueCustom<>();
        PriorityQueue<Integer> expected = new PriorityQueue<>();
        Random random = new Random(17);
        for (int iteration = 0; iteration < 4_000; iteration++) {
            int value = random.nextInt(-50, 51);
            int operation = random.nextInt(4);
            if (operation < 2) {
                actual.add(value);
                expected.add(value);
            } else if (operation == 2) {
                assertEquals(expected.remove(value), actual.remove(value));
            } else if (!expected.isEmpty()) {
                assertEquals(expected.poll(), actual.poll());
            }
            assertEquals(expected.size(), actual.size());
            assertEquals(expected.peek(), actual.peek());
        }
        while (!expected.isEmpty()) {
            assertEquals(expected.poll(), actual.poll());
        }
        assertTrue(actual.isEmpty());
    }

    private PriorityQueueCustom<Integer> queueOf(int... values) {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (int value : values) {
            queue.add(value);
        }
        return queue;
    }

    private List<Integer> drain(PriorityQueueCustom<Integer> queue) {
        List<Integer> values = new ArrayList<>();
        while (!queue.isEmpty()) {
            values.add(queue.poll());
        }
        return values;
    }
}
