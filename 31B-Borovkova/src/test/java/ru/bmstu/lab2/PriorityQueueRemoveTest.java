package ru.bmstu.lab2;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import org.junit.jupiter.api.Test;

/**
 * Тесты задачи 1C: удаление по значению с восстановлением свойства кучи.
 */
class PriorityQueueRemoveTest {

    /**
     * Создаёт min-очередь из элементов массива.
     */
    private static PriorityQueueCustom<Integer> of(int... data) {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
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
    void shouldRemoveRoot() {
        PriorityQueueCustom<Integer> queue = of(5, 3, 8, 1, 9);
        assertTrue(queue.remove(1));
        assertEquals(3, queue.peek());
        assertArrayEquals(new int[] {3, 5, 8, 9}, drain(queue));
    }

    @Test
    void shouldRemoveFromMiddle() {
        PriorityQueueCustom<Integer> queue = of(1, 2, 3, 4, 5, 6, 7);
        assertTrue(queue.remove(3));
        assertArrayEquals(new int[] {1, 2, 4, 5, 6, 7}, drain(queue));
    }

    @Test
    void shouldRemoveLastElement() {
        PriorityQueueCustom<Integer> queue = of(1, 2, 3);
        assertTrue(queue.remove(3)); // в такой куче 3 лежит последним в массиве
        assertArrayEquals(new int[] {1, 2}, drain(queue));
    }

    @Test
    void shouldReturnFalseForMissingElement() {
        PriorityQueueCustom<Integer> queue = of(1, 2, 3);
        assertFalse(queue.remove(42));
        assertEquals(3, queue.size());
    }

    @Test
    void shouldReturnFalseOnEmptyQueue() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        assertFalse(queue.remove(1));
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldReturnFalseForNull() {
        assertFalse(of(1, 2).remove(null));
    }

    @Test
    void shouldRemoveOnlyOneOccurrence() {
        PriorityQueueCustom<Integer> queue = of(2, 2, 2, 1);
        assertTrue(queue.remove(2));
        assertArrayEquals(new int[] {1, 2, 2}, drain(queue));
    }

    @Test
    void shouldRemoveSingleElement() {
        PriorityQueueCustom<Integer> queue = of(7);
        assertTrue(queue.remove(7));
        assertTrue(queue.isEmpty());
        assertFalse(queue.remove(7));
    }

    @Test
    void shouldSiftUpReplacementWhenNeeded() {
        // Куча [1, 10, 2, 11, 12, 3, 4]: при удалении 11 на его место встаёт 4,
        // который меньше нового родителя 10 и должен подняться вверх
        PriorityQueueCustom<Integer> queue = of(1, 10, 2, 11, 12, 3, 4);
        assertTrue(queue.remove(11));
        assertArrayEquals(new int[] {1, 2, 3, 4, 10, 12}, drain(queue));
    }

    @Test
    void shouldRemoveFromMaxHeap() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(Comparator.reverseOrder());
        for (int x : new int[] {4, 9, 1, 7}) {
            queue.add(x);
        }
        assertTrue(queue.remove(9));
        assertEquals(7, queue.poll());
        assertEquals(4, queue.poll());
        assertEquals(1, queue.poll());
    }

    @Test
    void shouldKeepHeapValidAfterManyRandomRemovals() {
        Random random = new Random(3);
        int[] data = random.ints(2_000, 0, 500).toArray();
        PriorityQueueCustom<Integer> queue = of(data);
        // Удаляем каждый второй элемент исходного массива
        int[] rest = new int[data.length / 2];
        for (int i = 0; i < data.length; i++) {
            if (i % 2 == 0) {
                assertTrue(queue.remove(data[i]));
            } else {
                rest[i / 2] = data[i];
            }
        }
        Arrays.sort(rest);
        assertArrayEquals(rest, drain(queue));
    }
}
