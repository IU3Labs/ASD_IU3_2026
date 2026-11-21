package ru.bmstu.lab02;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.bmstu.lab02.QueueTestSupport.drain;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;

/** Задача 1D: построение кучи из массива и независимость от исходного массива. */
class PriorityQueueHeapifyTest {
    private static final int LARGE_ARRAY_SIZE = 4_096;
    private static final long RANDOM_SEED = 20261121L;
    private static final int LINEAR_COMPARISON_LIMIT_FACTOR = 4;

    @Test
    void shouldHeapifyUnorderedArrayWithDuplicates() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(new Integer[]{5, -2, 5, 0, 1, -2});

        assertEquals(6, queue.size());
        assertEquals(List.of(-2, -2, 0, 1, 5, 5), drain(queue));
    }

    @Test
    void shouldAcceptEmptyArrayAndAllowLaterInsertions() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(new Integer[0]);
        assertTrue(queue.isEmpty());
        queue.add(2);
        queue.add(1);

        assertEquals(List.of(1, 2), drain(queue));
    }

    @Test
    void shouldHeapifySingleElementArray() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(new Integer[]{42});

        assertEquals(1, queue.size());
        assertEquals(42, queue.poll());
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldRejectNullArray() {
        assertThrows(NullPointerException.class, () -> new PriorityQueueCustom<Integer>((Integer[]) null));
    }

    @Test
    void shouldRejectNullElementInsideArray() {
        Integer[] values = {2, null, 1};

        assertThrows(NullPointerException.class, () -> new PriorityQueueCustom<>(values));
    }

    @Test
    void shouldNotObserveLaterChangesToOriginalArray() {
        Integer[] original = {3, 1, 2};
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(original);
        Arrays.fill(original, 99);

        assertEquals(List.of(1, 2, 3), drain(queue));
    }

    @Test
    void shouldNotChangeOriginalArrayWhenBuildingOrUpdatingQueue() {
        Integer[] original = {3, 1, 2};
        Integer[] expected = original.clone();
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(original);
        assertArrayEquals(expected, original);
        queue.poll();
        queue.remove(2);
        queue.add(0);

        assertArrayEquals(expected, original);
        assertEquals(List.of(0, 3), drain(queue));
    }

    @Test
    void shouldHeapifyWithMaxComparator() {
        Integer[] values = {3, 7, 1, 7, 2};
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(values, Comparator.reverseOrder());

        assertEquals(List.of(7, 7, 3, 2, 1), drain(queue));
    }

    @Test
    void shouldMatchSortingForLargeRandomArray() {
        Random random = new Random(RANDOM_SEED);
        Integer[] values = new Integer[LARGE_ARRAY_SIZE];
        for (int index = 0; index < values.length; index++) {
            values[index] = random.nextInt();
        }
        Integer[] expected = values.clone();
        Arrays.sort(expected);
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(values);

        assertEquals(Arrays.asList(expected), drain(queue));
    }

    @Test
    void shouldUseLinearNumberOfComparisonsDuringHeapify() {
        Integer[] descending = new Integer[LARGE_ARRAY_SIZE];
        for (int index = 0; index < descending.length; index++) {
            descending[index] = descending.length - index;
        }
        AtomicInteger comparisons = new AtomicInteger();
        Comparator<Integer> comparator = (left, right) -> {
            comparisons.incrementAndGet();
            return Integer.compare(left, right);
        };
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(descending, comparator);

        // Подсчёт сравнений не зависит от нагрузки на компьютер и отличает heapify от repeated add.
        assertTrue(comparisons.get() <= LINEAR_COMPARISON_LIMIT_FACTOR * descending.length);
        assertEquals(descending.length, queue.size());
        assertEquals(1, queue.peek());
    }
}