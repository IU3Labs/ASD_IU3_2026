package ru.bmstu.lab2;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Random;
import org.junit.jupiter.api.Test;

/**
 * Тесты задачи 1B: очередь с компаратором в режимах min-heap и max-heap.
 */
class PriorityQueueComparatorTest {

    /** Набор данных, общий для нескольких тестов. */
    private static final int[] DATA = {5, 3, 8, 1, 9, 2, 7};

    /**
     * Заполняет очередь элементами массива.
     */
    private static PriorityQueueCustom<Integer> fill(PriorityQueueCustom<Integer> queue, int[] data) {
        for (int x : data) {
            queue.add(x);
        }
        return queue;
    }

    /**
     * Извлекает все элементы очереди в массив.
     */
    private static int[] drain(PriorityQueueCustom<Integer> queue) {
        int[] result = new int[queue.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = queue.poll();
        }
        return result;
    }

    @Test
    void shouldWorkAsMinHeapWithNaturalComparator() {
        PriorityQueueCustom<Integer> queue = fill(new PriorityQueueCustom<>(Comparator.naturalOrder()), DATA);
        assertArrayEquals(new int[] {1, 2, 3, 5, 7, 8, 9}, drain(queue));
    }

    @Test
    void shouldWorkAsMaxHeapWithReverseComparator() {
        PriorityQueueCustom<Integer> queue = fill(new PriorityQueueCustom<>(Comparator.reverseOrder()), DATA);
        assertArrayEquals(new int[] {9, 8, 7, 5, 3, 2, 1}, drain(queue));
    }

    @Test
    void shouldUseNaturalOrderWhenComparatorIsNull() {
        PriorityQueueCustom<Integer> queue = fill(new PriorityQueueCustom<>((Comparator<Integer>) null), DATA);
        assertEquals(1, queue.peek());
    }

    @Test
    void shouldPeekMaximumInMaxHeap() {
        PriorityQueueCustom<Integer> queue = fill(new PriorityQueueCustom<>(Comparator.reverseOrder()), DATA);
        assertEquals(9, queue.peek());
        assertEquals(DATA.length, queue.size());
    }

    @Test
    void shouldKeepDuplicatesInMaxHeap() {
        PriorityQueueCustom<Integer> queue =
                fill(new PriorityQueueCustom<>(Comparator.reverseOrder()), new int[] {2, 5, 2, 5, 1});
        assertArrayEquals(new int[] {5, 5, 2, 2, 1}, drain(queue));
    }

    @Test
    void shouldGiveOppositeOrdersOnSameRandomData() {
        int[] data = new Random(2).ints(5_000).toArray();
        int[] min = drain(fill(new PriorityQueueCustom<>(Comparator.naturalOrder()), data));
        int[] max = drain(fill(new PriorityQueueCustom<>(Comparator.reverseOrder()), data));
        int[] sorted = data.clone();
        Arrays.sort(sorted);
        assertArrayEquals(sorted, min);
        for (int i = 0; i < sorted.length; i++) {
            assertEquals(sorted[sorted.length - 1 - i], max[i]);
        }
    }

    @Test
    void shouldOrderByCustomKey() {
        // Строки по длине: на вершине самая короткая
        PriorityQueueCustom<String> queue = new PriorityQueueCustom<>(Comparator.comparingInt(String::length));
        queue.add("ccc");
        queue.add("a");
        queue.add("bb");
        assertEquals("a", queue.poll());
        assertEquals("bb", queue.poll());
        assertEquals("ccc", queue.poll());
    }

    @Test
    void shouldOrderNonComparableObjectsWithComparator() {
        // Массивы не реализуют Comparable — работает только через компаратор
        PriorityQueueCustom<int[]> queue = new PriorityQueueCustom<>(Comparator.comparingInt(a -> a[0]));
        queue.add(new int[] {3});
        queue.add(new int[] {1});
        queue.add(new int[] {2});
        assertEquals(1, queue.poll()[0]);
        assertEquals(2, queue.poll()[0]);
    }

    @Test
    void shouldHandleExtremeValuesInMaxHeap() {
        PriorityQueueCustom<Integer> queue = fill(new PriorityQueueCustom<>(Comparator.reverseOrder()),
                new int[] {0, Integer.MIN_VALUE, Integer.MAX_VALUE, -1});
        assertArrayEquals(new int[] {Integer.MAX_VALUE, 0, -1, Integer.MIN_VALUE}, drain(queue));
    }

    @Test
    void shouldThrowOnPollFromEmptyMaxHeap() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(Comparator.reverseOrder());
        assertThrows(NoSuchElementException.class, queue::poll);
        assertThrows(NoSuchElementException.class, queue::peek);
    }

    @Test
    void shouldGrowMaxHeap() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(Comparator.reverseOrder());
        for (int i = 0; i < 1000; i++) {
            queue.add(i);
        }
        for (int i = 999; i >= 0; i--) {
            assertEquals(i, queue.poll());
        }
    }
}
