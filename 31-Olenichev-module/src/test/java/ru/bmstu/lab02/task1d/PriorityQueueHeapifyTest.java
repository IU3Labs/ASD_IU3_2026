package ru.bmstu.lab02.task1d;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Random;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Юнит-тесты для {@link PriorityQueueHeapify} (задача 1D).
 */
class PriorityQueueHeapifyTest {

    private static final long RANDOM_SEED = 42L;
    private static final int STRESS_SIZE = 10_000;

    @Test
    void shouldBuildValidHeapFromUnsortedArray() {
        Integer[] source = {9, 4, 7, 1, -2, 6, 5};
        PriorityQueueHeapify<Integer> queue = new PriorityQueueHeapify<>(source);

        assertEquals(7, queue.size());
        int[] expected = {-2, 1, 4, 5, 6, 7, 9};
        for (int item : expected) {
            assertEquals(item, queue.poll());
        }
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldHandleEmptyArrayInConstructor() {
        Integer[] empty = new Integer[0];
        PriorityQueueHeapify<Integer> queue = new PriorityQueueHeapify<>(empty);

        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
        assertThrows(NoSuchElementException.class, queue::poll);
    }

    @Test
    void shouldHandleSingleElementArray() {
        Integer[] single = {42};
        PriorityQueueHeapify<Integer> queue = new PriorityQueueHeapify<>(single);

        assertEquals(1, queue.size());
        assertEquals(42, queue.peek());
        assertEquals(42, queue.poll());
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldHandleReverseSortedArray() {
        Integer[] reverse = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        PriorityQueueHeapify<Integer> queue = new PriorityQueueHeapify<>(reverse);

        for (int expected = 1; expected <= 10; expected++) {
            assertEquals(expected, queue.poll());
        }
    }

    @Test
    void shouldHandleAlreadySortedArray() {
        Integer[] sorted = {1, 2, 3, 4, 5};
        PriorityQueueHeapify<Integer> queue = new PriorityQueueHeapify<>(sorted);

        for (int expected = 1; expected <= 5; expected++) {
            assertEquals(expected, queue.poll());
        }
    }

    @Test
    void shouldHandleArrayWithDuplicates() {
        Integer[] duplicates = {5, 1, 5, 1, 3, 3};
        PriorityQueueHeapify<Integer> queue = new PriorityQueueHeapify<>(duplicates);

        int[] expected = {1, 1, 3, 3, 5, 5};
        for (int item : expected) {
            assertEquals(item, queue.poll());
        }
    }

    @Test
    void shouldNotModifyOriginalInputArray() {
        Integer[] source = {5, 3, 1, 4, 2};
        Integer[] copy = Arrays.copyOf(source, source.length);

        PriorityQueueHeapify<Integer> queue = new PriorityQueueHeapify<>(source);
        assertEquals(1, queue.peek());
        assertEquals(Arrays.asList(copy), Arrays.asList(source));
    }

    @Test
    void shouldAllowAddingMoreElementsAfterHeapify() {
        Integer[] source = {10, 20, 30};
        PriorityQueueHeapify<Integer> queue = new PriorityQueueHeapify<>(source);
        queue.add(5);
        queue.add(25);

        int[] expected = {5, 10, 20, 25, 30};
        for (int item : expected) {
            assertEquals(item, queue.poll());
        }
    }

    @Test
    void shouldThrowExceptionWhenArrayIsNullOrContainsNull() {
        assertThrows(IllegalArgumentException.class, () -> new PriorityQueueHeapify<Integer>((Integer[]) null));
        Integer[] withNull = {1, null, 2};
        assertThrows(IllegalArgumentException.class, () -> new PriorityQueueHeapify<>(withNull));
    }

    @Test
    void shouldExtractInNonDecreasingOrderOnStressArray() {
        Random random = new Random(RANDOM_SEED);
        Integer[] data = new Integer[STRESS_SIZE];
        for (int i = 0; i < STRESS_SIZE; i++) {
            data[i] = random.nextInt();
        }

        PriorityQueueHeapify<Integer> queue = new PriorityQueueHeapify<>(data);
        int prev = Integer.MIN_VALUE;
        for (int i = 0; i < STRESS_SIZE; i++) {
            int cur = queue.poll();
            assertTrue(cur >= prev);
            prev = cur;
        }
    }
}
