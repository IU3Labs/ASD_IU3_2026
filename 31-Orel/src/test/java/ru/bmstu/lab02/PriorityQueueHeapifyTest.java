package ru.bmstu.lab02;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.jupiter.api.Test;

/** Задача 1D: построение кучи из массива за O(n). */
class PriorityQueueHeapifyTest {

    private static final int LARGE_SIZE = 100_000;

    @Test
    void shouldBuildValidHeapFromArray() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(new Integer[] {9, 4, 7, 1, -2, 6, 5});

        assertTrue(queue.isValidHeap());
        assertEquals(7, queue.size());
        assertEquals(-2, queue.peek());
    }

    @Test
    void shouldPollBuiltHeapInSortedOrder() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(new Integer[] {3, 1, 4, 1, 5, 9, 2, 6});

        assertEquals(List.of(1, 1, 2, 3, 4, 5, 6, 9), QueueTestUtils.drain(queue));
    }

    @Test
    void shouldBuildEmptyQueueFromEmptyArray() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(new Integer[0]);

        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldBuildQueueFromSingleElement() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(new Integer[] {5});

        assertEquals(5, queue.poll());
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldNotModifySourceArray() {
        Integer[] source = {5, 3, 1};

        new PriorityQueueCustom<>(source).poll();

        assertArrayEquals(new Integer[] {5, 3, 1}, source);
    }

    @Test
    void shouldAllowAddingAfterBuildingFromArray() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(new Integer[] {4, 2});
        for (int i = 10; i < 10 + PriorityQueueCustom.DEFAULT_CAPACITY * 2; i++) {
            queue.add(i);
        }
        queue.add(0);

        assertEquals(0, queue.poll());
        assertEquals(2, queue.poll());
        assertTrue(queue.isValidHeap());
    }

    @Test
    void shouldBuildMaxHeapFromArrayWithComparator() {
        PriorityQueueCustom<Integer> maxHeap = new PriorityQueueCustom<>(
                new Integer[] {3, 8, 1, 9, 4}, Comparator.reverseOrder());

        assertEquals(List.of(9, 8, 4, 3, 1), QueueTestUtils.drain(maxHeap));
    }

    @Test
    void shouldRejectNullArray() {
        assertThrows(NullPointerException.class, () -> new PriorityQueueCustom<>((Integer[]) null));
    }

    @Test
    void shouldRejectNullElementInArray() {
        Integer[] source = {1, null, 3};

        assertThrows(NullPointerException.class, () -> new PriorityQueueCustom<>(source));
    }

    @Test
    void shouldGiveSameOrderAsSequentialAdd() {
        Integer[] values = QueueTestUtils.randomIntegers(LARGE_SIZE, 1_000_000, QueueTestUtils.SEED);
        PriorityQueueCustom<Integer> built = new PriorityQueueCustom<>(values);
        PriorityQueueCustom<Integer> added = new PriorityQueueCustom<>();
        for (Integer value : values) {
            added.add(value);
        }
        Integer[] sorted = values.clone();
        Arrays.sort(sorted);

        assertTrue(built.isValidHeap());
        assertEquals(Arrays.asList(sorted), QueueTestUtils.drain(built));
        assertEquals(Arrays.asList(sorted), QueueTestUtils.drain(added));
    }

    @Test
    void shouldUseLinearNumberOfComparisons() {
        // Сортированный по убыванию массив — худший случай: почти каждый элемент тонет до листа
        // (при построении) и всплывает до корня (при последовательных add).
        Integer[] descending = new Integer[LARGE_SIZE];
        for (int i = 0; i < LARGE_SIZE; i++) {
            descending[i] = LARGE_SIZE - i;
        }
        AtomicLong heapifyComparisons = new AtomicLong();
        AtomicLong addComparisons = new AtomicLong();

        new PriorityQueueCustom<>(descending, countingComparator(heapifyComparisons));
        PriorityQueueCustom<Integer> added = new PriorityQueueCustom<>(countingComparator(addComparisons));
        for (Integer value : descending) {
            added.add(value);
        }

        // Теоретическая оценка алгоритма Флойда — не более 2n сравнений.
        assertTrue(heapifyComparisons.get() <= 2L * LARGE_SIZE, "heapify: " + heapifyComparisons.get());
        // Последовательные add в худшем случае делают порядка n * log2(n) ≈ 1.5 млн сравнений.
        assertTrue(addComparisons.get() > 5 * heapifyComparisons.get(), "add: " + addComparisons.get());
    }

    private static Comparator<Integer> countingComparator(AtomicLong counter) {
        return (first, second) -> {
            counter.incrementAndGet();
            return Integer.compare(first, second);
        };
    }
}
