package ru.bmstu.lab2;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;
import org.junit.jupiter.api.Test;

/** Линейное построение кучи из существующего массива. */
class PriorityQueueHeapifyTest {
    @Test
    void shouldBuildFromUnsortedArray() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(new Integer[] {8, 3, 5, 1});
        for (int expected : new int[] {1, 3, 5, 8}) {
            assertEquals(expected, queue.poll());
        }
    }

    @Test
    void shouldBuildEmptyQueue() {
        assertTrue(new PriorityQueueCustom<>(new Integer[0]).isEmpty());
    }

    @Test
    void shouldBuildOneElement() {
        assertEquals(5, new PriorityQueueCustom<>(new Integer[] {5}).poll());
    }

    @Test
    void shouldRetainDuplicates() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(new Integer[] {4, 4, 1});
        assertEquals(1, queue.poll());
        assertEquals(4, queue.poll());
        assertEquals(4, queue.poll());
    }

    @Test
    void shouldNotChangeInputArray() {
        Integer[] array = {9, 1, 3};
        new PriorityQueueCustom<>(array);
        assertArrayEquals(new Integer[] {9, 1, 3}, array);
    }

    @Test
    void shouldRejectNullArray() {
        assertThrows(NullPointerException.class, () -> new PriorityQueueCustom<Integer>((Integer[]) null));
    }

    @Test
    void shouldRejectNullItemInArray() {
        assertThrows(NullPointerException.class, () -> new PriorityQueueCustom<>(new Integer[] {1, null}));
    }

    @Test
    void shouldMatchSortedRandomArray() {
        Random random = new Random(31);
        Integer[] array = new Integer[10_000];
        for (int index = 0; index < array.length; index++) {
            array[index] = random.nextInt();
        }
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(array);
        int previous = Integer.MIN_VALUE;
        while (!queue.isEmpty()) {
            int current = queue.poll();
            assertTrue(previous <= current);
            previous = current;
        }
    }
}
