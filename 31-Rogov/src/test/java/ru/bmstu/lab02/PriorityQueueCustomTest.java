package ru.bmstu.lab02;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Random;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Проверяет естественный порядок, компараторы и восстановление кучи после удаления. */
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
    void shouldPeekWithoutRemoving() {
        PriorityQueueCustom<Integer> queue = queueOf(4, 2, 7);

        assertEquals(2, queue.peek());
        assertEquals(2, queue.peek());
        assertEquals(3, queue.size());
        assertFalse(queue.isEmpty());
    }

    @Test
    void shouldPollInNaturalOrderIncludingDuplicatesAndIntLimits() {
        PriorityQueueCustom<Integer> queue = queueOf(0, Integer.MAX_VALUE, -7, -7, Integer.MIN_VALUE);

        assertEquals(List.of(Integer.MIN_VALUE, -7, -7, 0, Integer.MAX_VALUE), drain(queue));
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldPollInDescendingOrderWithReverseComparator() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(Comparator.reverseOrder());
        for (int value : new int[] { 3, 1, 5, 3, -2 }) {
            queue.add(value);
        }

        assertEquals(5, queue.peek());
        assertEquals(List.of(5, 3, 3, 1, -2), drain(queue));
    }

    @Test
    void shouldAcceptNonComparableItemsWithComparator() {
        PriorityQueueCustom<Job> queue = new PriorityQueueCustom<>(Comparator.comparingInt(Job::priority));
        Job first = new Job(1, "first");
        Job second = new Job(2, "second");
        queue.add(second);
        queue.add(first);

        assertEquals(List.of(first, second), drain(queue));
    }

    @Test
    void shouldRejectNullWithoutChangingQueue() {
        PriorityQueueCustom<Integer> queue = queueOf(3);

        assertThrows(NullPointerException.class, () -> queue.add(null));
        assertEquals(List.of(3), drain(queue));
    }

    @Test
    void shouldRejectNonComparableItemsWithoutComparator() {
        PriorityQueueCustom<Job> queue = new PriorityQueueCustom<>();

        assertThrows(ClassCastException.class, () -> queue.add(new Job(1, "first")));
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldGrowAndDrainInOrder() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (int value = 1_024; value >= 1; value--) {
            queue.add(value);
        }

        for (int expected = 1; expected <= 1_024; expected++) {
            assertEquals(expected, queue.poll());
        }
        assertEquals(0, queue.size());
    }

    @Test
    void shouldRemoveRootAndRestoreHeap() {
        PriorityQueueCustom<Integer> queue = queueOf(3, 1, 4, 2, 5);

        assertTrue(queue.remove(1));
        assertEquals(2, queue.peek());
        assertEquals(List.of(2, 3, 4, 5), drain(queue));
    }

    @Test
    void shouldSiftReplacementDownAfterRemovingInternalItem() {
        PriorityQueueCustom<Integer> queue = queueOf(1, 2, 3, 4, 5, 6, 7, 8, 9);

        assertTrue(queue.remove(2));
        assertEquals(List.of(1, 3, 4, 5, 6, 7, 8, 9), drain(queue));
    }

    @Test
    void shouldSiftReplacementUpAfterRemovingFromAnotherBranch() {
        PriorityQueueCustom<Integer> queue = queueOf(49, 7, 38, 10, 1, 33, 14, 34, 20, 0);

        assertTrue(queue.remove(38));
        assertEquals(List.of(0, 1, 7, 10, 14, 20, 33, 34, 49), drain(queue));
    }

    @Test
    void shouldRemoveOnlyOneOccurrence() {
        PriorityQueueCustom<Integer> queue = queueOf(2, 1, 2, 3, 2);

        assertTrue(queue.remove(2));
        assertEquals(4, queue.size());
        assertEquals(List.of(1, 2, 2, 3), drain(queue));
    }

    @Test
    void shouldRemoveByEqualityEvenWhenComparatorRanksTwoItemsTheSame() {
        PriorityQueueCustom<Job> queue = new PriorityQueueCustom<>(Comparator.comparingInt(Job::priority));
        Job first = new Job(1, "first");
        queue.add(first);
        queue.add(new Job(1, "second"));

        assertTrue(queue.remove(new Job(1, "second")));
        assertEquals(List.of(first), drain(queue));
    }

    @Test
    void shouldLeaveQueueUnchangedWhenItemIsAbsent() {
        PriorityQueueCustom<Integer> queue = queueOf(2, 1);

        assertFalse(queue.remove(7));
        assertEquals(List.of(1, 2), drain(queue));
    }

    @Test
    void shouldReturnFalseWhenRemovingFromEmptyQueue() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();

        assertFalse(queue.remove(3));
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldMatchStandardQueueDuringMixedOperations() {
        PriorityQueueCustom<Integer> actual = new PriorityQueueCustom<>(Comparator.reverseOrder());
        PriorityQueue<Integer> expected = new PriorityQueue<>(Comparator.reverseOrder());
        Random random = new Random(42);

        for (int step = 0; step < 10_000; step++) {
            int value = random.nextInt(-100, 101);
            switch (random.nextInt(4)) {
                case 0, 1 -> {
                    actual.add(value);
                    expected.add(value);
                }
                case 2 -> assertEquals(expected.remove(value), actual.remove(value));
                default -> {
                    if (!expected.isEmpty()) {
                        assertEquals(expected.poll(), actual.poll());
                    }
                }
            }
            assertEquals(expected.peek(), actual.peek());
            assertEquals(expected.size(), actual.size());
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

    private <T> List<T> drain(PriorityQueueCustom<T> queue) {
        List<T> items = new ArrayList<>();
        while (!queue.isEmpty()) {
            items.add(queue.poll());
        }
        return items;
    }

    private record Job(int priority, String name) {
    }
}
