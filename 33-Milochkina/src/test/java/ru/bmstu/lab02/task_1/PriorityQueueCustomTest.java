package ru.bmstu.lab02.task_1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Random;
import org.junit.jupiter.api.Test;

/**
 * Проверяет операции двоичной кучи и совпадение со стандартной очередью
 */
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
        assertThrows(NoSuchElementException.class, () -> new PriorityQueueCustom<Integer>().poll());
    }

    @Test
    void shouldPeekWithoutRemoving() {
        PriorityQueueCustom<Integer> queue = queueOf(3, 1, 2);
        assertEquals(1, queue.peek());
        assertEquals(1, queue.peek());
        assertEquals(3, queue.size());
        assertFalse(queue.isEmpty());
    }

    @Test
    void shouldExtractInNondecreasingOrder() {
        assertPolls(queueOf(5, -2, 0, -2, 3), -2, -2, 0, 3, 5);
    }

    @Test
    void shouldGrowBeyondInitialCapacity() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (int value = 255; value >= 0; value--) {
            queue.add(value);
        }
        assertEquals(256, queue.size());
        for (int value = 0; value < 256; value++) {
            assertEquals(value, queue.poll());
        }
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldExtractInReverseOrderWithComparator() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(Comparator.reverseOrder());
        for (int value : List.of(2, 5, -1, 5)) {
            queue.add(value);
        }
        assertPolls(queue, 5, 5, 2, -1);
    }

    @Test
    void shouldSupportNonComparableItemsWithComparator() {
        Object low = new Object();
        Object high = new Object();
        PriorityQueueCustom<Object> queue = new PriorityQueueCustom<>(
                Comparator.comparingInt(item -> item == low ? 0 : 1));
        queue.add(high);
        queue.add(low);
        assertEquals(low, queue.poll());
        assertEquals(high, queue.poll());
    }

    @Test
    void shouldRemoveRootAndRestoreHeap() {
        PriorityQueueCustom<Integer> queue = queueOf(3, 1, 5, 2, 4);
        assertTrue(queue.remove(1));
        assertPolls(queue, 2, 3, 4, 5);
    }

    @Test
    void shouldSiftDownAfterRemovingFromMiddle() {
        PriorityQueueCustom<Integer> queue = queueOf(1, 3, 2, 6, 7, 4, 5, 8);
        assertTrue(queue.remove(3));
        assertPolls(queue, 1, 2, 4, 5, 6, 7, 8);
    }

    @Test
    void shouldSiftUpAfterRemovingFromMiddle() {
        PriorityQueueCustom<Integer> queue = queueOf(1, 5, 2, 9, 10, 3, 4);
        assertTrue(queue.remove(9));
        assertPolls(queue, 1, 2, 3, 4, 5, 10);
    }

    @Test
    void shouldRemoveOnlyOneEqualElement() {
        PriorityQueueCustom<Integer> queue = queueOf(3, 2, 2, 5);
        assertTrue(queue.remove(2));
        assertPolls(queue, 2, 3, 5);
    }

    @Test
    void shouldReturnFalseForMissingValue() {
        PriorityQueueCustom<Integer> queue = queueOf(3, 1, 2);
        assertFalse(queue.remove(8));
        assertPolls(queue, 1, 2, 3);
    }

    @Test
    void shouldReturnFalseWhenRemovingFromEmptyQueue() {
        assertFalse(new PriorityQueueCustom<Integer>().remove(1));
    }

    @Test
    void shouldBuildHeapWithoutChangingOrSharingInput() {
        Integer[] input = {7, 4, 3, 0, 1};
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(input);
        assertEquals(List.of(7, 4, 3, 0, 1), List.of(input));
        input[0] = -5;
        assertPolls(queue, 0, 1, 3, 4, 7);
    }

    @Test
    void shouldAddAfterBuildingFromEmptyArray() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(new Integer[0]);
        queue.add(5);
        assertPolls(queue, 5);
    }

    @Test
    void shouldRejectNullWithoutChangingSize() {
        PriorityQueueCustom<Integer> queue = queueOf(3);
        assertThrows(NullPointerException.class, () -> queue.add(null));
        assertPolls(queue, 3);
    }

    @Test
    void shouldMatchStandardQueueOnMixedOperations() {
        checkAgainstStandardQueue(Comparator.naturalOrder());
        checkAgainstStandardQueue(Comparator.reverseOrder());
    }

    private static void checkAgainstStandardQueue(Comparator<Integer> order) {
        PriorityQueueCustom<Integer> actual = new PriorityQueueCustom<>(order);
        PriorityQueue<Integer> expected = new PriorityQueue<>(order);
        Random random = new Random(33);
        for (int step = 0; step < 5_000; step++) {
            int value = random.nextInt(60) - 30;
            int operation = random.nextInt(5);
            if (operation < 3) {
                actual.add(value);
                expected.add(value);
            } else if (operation == 3) {
                assertEquals(expected.remove(value), actual.remove(value));
            } else if (!expected.isEmpty()) {
                assertEquals(expected.poll(), actual.poll());
            }
            assertEquals(expected.peek(), actual.peek());
            assertEquals(expected.size(), actual.size());
        }
        while (!expected.isEmpty()) {
            assertEquals(expected.poll(), actual.poll());
        }
        assertTrue(actual.isEmpty());
    }

    private static PriorityQueueCustom<Integer> queueOf(int... values) {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (int value : values) {
            queue.add(value);
        }
        return queue;
    }

    private static void assertPolls(PriorityQueueCustom<Integer> queue, int... expected) {
        for (int value : expected) {
            assertEquals(value, queue.poll());
        }
        assertTrue(queue.isEmpty());
    }
}
