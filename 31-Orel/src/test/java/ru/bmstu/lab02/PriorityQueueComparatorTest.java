package ru.bmstu.lab02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

/** Задача 1B: один класс работает и как min-heap, и как max-heap в зависимости от компаратора. */
class PriorityQueueComparatorTest {

    private static final int[] SAMPLE = {4, 10, -3, 7, 0, 10, 2};

    @Test
    void shouldWorkAsMinHeapWithNaturalOrderComparator() {
        PriorityQueueCustom<Integer> minHeap = new PriorityQueueCustom<>(Comparator.naturalOrder());
        addAll(minHeap, SAMPLE);

        assertEquals(List.of(-3, 0, 2, 4, 7, 10, 10), QueueTestUtils.drain(minHeap));
    }

    @Test
    void shouldWorkAsMaxHeapWithReversedComparator() {
        PriorityQueueCustom<Integer> maxHeap = new PriorityQueueCustom<>(Comparator.reverseOrder());
        addAll(maxHeap, SAMPLE);

        assertEquals(List.of(10, 10, 7, 4, 2, 0, -3), QueueTestUtils.drain(maxHeap));
    }

    @Test
    void shouldPeekMaximumInMaxHeap() {
        PriorityQueueCustom<Integer> maxHeap = new PriorityQueueCustom<>(Comparator.reverseOrder());
        addAll(maxHeap, SAMPLE);

        assertEquals(10, maxHeap.peek());
        assertEquals(SAMPLE.length, maxHeap.size());
    }

    @Test
    void shouldUseNaturalOrderWhenComparatorIsNull() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>((Comparator<Integer>) null);
        addAll(queue, SAMPLE);

        assertEquals(-3, queue.poll());
    }

    @Test
    void shouldGiveOppositeOrdersForMinAndMaxHeapsOnSameData() {
        Integer[] values = QueueTestUtils.randomIntegers(10_000, 1000, QueueTestUtils.SEED);
        PriorityQueueCustom<Integer> minHeap = new PriorityQueueCustom<>(Comparator.naturalOrder());
        PriorityQueueCustom<Integer> maxHeap = new PriorityQueueCustom<>(Comparator.reverseOrder());
        for (Integer value : values) {
            minHeap.add(value);
            maxHeap.add(value);
        }

        List<Integer> ascending = QueueTestUtils.drain(minHeap);
        List<Integer> descending = QueueTestUtils.drain(maxHeap);
        Collections.reverse(descending);

        assertEquals(ascending, descending);
    }

    @Test
    void shouldOrderByCustomKey() {
        PriorityQueueCustom<String> byLength = new PriorityQueueCustom<>(
                Comparator.comparingInt(String::length).thenComparing(Comparator.naturalOrder()));
        for (String word : new String[] {"ccc", "a", "bb", "dddd", "ab"}) {
            byLength.add(word);
        }

        assertEquals(List.of("a", "ab", "bb", "ccc", "dddd"), QueueTestUtils.drain(byLength));
    }

    @Test
    void shouldAcceptNonComparableElementsWithComparator() {
        PriorityQueueCustom<int[]> queue = new PriorityQueueCustom<>(Comparator.comparingInt(pair -> pair[0]));
        queue.add(new int[] {3, 30});
        queue.add(new int[] {1, 10});
        queue.add(new int[] {2, 20});

        assertEquals(10, queue.poll()[1]);
        assertEquals(20, queue.poll()[1]);
    }

    @Test
    void shouldThrowWhenPollingEmptyMaxHeap() {
        PriorityQueueCustom<Integer> maxHeap = new PriorityQueueCustom<>(Comparator.reverseOrder());

        assertThrows(NoSuchElementException.class, maxHeap::poll);
    }

    @Test
    void shouldKeepMaxHeapValidAfterManyOperations() {
        PriorityQueueCustom<Integer> maxHeap = new PriorityQueueCustom<>(Comparator.reverseOrder());
        Integer[] values = QueueTestUtils.randomIntegers(5_000, 100, QueueTestUtils.SEED);
        for (int i = 0; i < values.length; i++) {
            maxHeap.add(values[i]);
            if (i % 3 == 0) {
                maxHeap.poll();
            }
        }

        assertTrue(maxHeap.isValidHeap());
    }

    @Test
    void shouldMatchSortedArrayInDescendingOrder() {
        Integer[] values = QueueTestUtils.randomIntegers(1_000, 50, QueueTestUtils.SEED);
        PriorityQueueCustom<Integer> maxHeap = new PriorityQueueCustom<>(Comparator.reverseOrder());
        for (Integer value : values) {
            maxHeap.add(value);
        }
        Integer[] expected = values.clone();
        Arrays.sort(expected, Comparator.reverseOrder());

        assertEquals(Arrays.asList(expected), QueueTestUtils.drain(maxHeap));
    }

    private static void addAll(PriorityQueueCustom<Integer> queue, int[] values) {
        for (int value : values) {
            queue.add(value);
        }
    }
}
