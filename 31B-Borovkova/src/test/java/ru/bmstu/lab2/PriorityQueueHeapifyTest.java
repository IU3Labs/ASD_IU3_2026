package ru.bmstu.lab2;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Random;
import org.junit.jupiter.api.Test;

/**
 * Тесты задачи 1D: построение кучи из массива за O(n).
 */
class PriorityQueueHeapifyTest {

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

    /**
     * Переводит int[] в Integer[].
     */
    private static Integer[] boxed(int[] data) {
        return Arrays.stream(data).boxed().toArray(Integer[]::new);
    }

    @Test
    void shouldBuildFromEmptyArray() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(new Integer[0]);
        assertTrue(queue.isEmpty());
        assertThrows(NoSuchElementException.class, queue::peek);
    }

    @Test
    void shouldBuildFromSingleElement() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(new Integer[] {5});
        assertEquals(1, queue.size());
        assertEquals(5, queue.poll());
    }

    @Test
    void shouldBuildFromUnsortedArray() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(boxed(new int[] {5, 3, 8, 1, 9, 2, 7}));
        assertArrayEquals(new int[] {1, 2, 3, 5, 7, 8, 9}, drain(queue));
    }

    @Test
    void shouldBuildFromSortedArray() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(boxed(new int[] {1, 2, 3, 4, 5}));
        assertArrayEquals(new int[] {1, 2, 3, 4, 5}, drain(queue));
    }

    @Test
    void shouldBuildFromReverseSortedArray() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(boxed(new int[] {9, 7, 5, 3, 1, 0}));
        assertArrayEquals(new int[] {0, 1, 3, 5, 7, 9}, drain(queue));
    }

    @Test
    void shouldBuildWithDuplicates() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(boxed(new int[] {3, 1, 3, 1, 2, 2}));
        assertArrayEquals(new int[] {1, 1, 2, 2, 3, 3}, drain(queue));
    }

    @Test
    void shouldNotModifySourceArray() {
        Integer[] source = {4, 2, 5, 1};
        new PriorityQueueCustom<>(source).poll();
        assertArrayEquals(new Integer[] {4, 2, 5, 1}, source);
    }

    @Test
    void shouldAllowAddAfterBuild() {
        // Массив длины 16 занимает всю начальную ёмкость — следующий add вызовет расширение
        Integer[] source = new Integer[16];
        for (int i = 0; i < source.length; i++) {
            source[i] = i + 10;
        }
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(source);
        queue.add(1);
        queue.add(100);
        assertEquals(18, queue.size());
        assertEquals(1, queue.poll());
        assertEquals(10, queue.poll());
    }

    @Test
    void shouldBuildMaxHeapWithComparator() {
        PriorityQueueCustom<Integer> queue =
                new PriorityQueueCustom<>(boxed(new int[] {5, 3, 8, 1}), Comparator.reverseOrder());
        assertArrayEquals(new int[] {8, 5, 3, 1}, drain(queue));
    }

    @Test
    void shouldMatchSequentialAddsOnRandomData() {
        int[] data = new Random(4).ints(100_000).toArray();
        PriorityQueueCustom<Integer> built = new PriorityQueueCustom<>(boxed(data));
        PriorityQueueCustom<Integer> added = new PriorityQueueCustom<>();
        for (int x : data) {
            added.add(x);
        }
        assertArrayEquals(drain(added), drain(built));
    }

    @Test
    void shouldRejectNullArray() {
        assertThrows(NullPointerException.class, () -> new PriorityQueueCustom<>((Integer[]) null));
    }

    @Test
    void shouldRejectNullElement() {
        assertThrows(NullPointerException.class, () -> new PriorityQueueCustom<>(new Integer[] {1, null, 3}));
    }
}
