package ru.bmstu.lab02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;

/** Задача 1C: удаление первого вхождения по значению с восстановлением свойства кучи. */
class PriorityQueueRemoveTest {

    @Test
    void shouldRemoveRoot() {
        PriorityQueueCustom<Integer> queue = queueOf(5, 1, 8, 3, 9);

        assertTrue(queue.remove(1));
        assertEquals(3, queue.peek());
        assertEquals(List.of(3, 5, 8, 9), QueueTestUtils.drain(queue));
    }

    @Test
    void shouldRemoveMiddleElement() {
        PriorityQueueCustom<Integer> queue = queueOf(5, 1, 8, 3, 9, 4, 7);

        assertTrue(queue.remove(4));
        assertTrue(queue.isValidHeap());
        assertEquals(List.of(1, 3, 5, 7, 8, 9), QueueTestUtils.drain(queue));
    }

    @Test
    void shouldReturnFalseForNonexistentElement() {
        PriorityQueueCustom<Integer> queue = queueOf(5, 1, 8);

        assertFalse(queue.remove(42));
        assertEquals(3, queue.size());
        assertEquals(List.of(1, 5, 8), QueueTestUtils.drain(queue));
    }

    @Test
    void shouldReturnFalseWhenRemovingFromEmptyQueue() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();

        assertFalse(queue.remove(1));
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldRemoveLastElementOfArray() {
        PriorityQueueCustom<Integer> queue = queueOf(1, 2, 3);

        assertTrue(queue.remove(3));
        assertEquals(List.of(1, 2), QueueTestUtils.drain(queue));
    }

    @Test
    void shouldRemoveOnlyElement() {
        PriorityQueueCustom<Integer> queue = queueOf(7);

        assertTrue(queue.remove(7));
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldRemoveOnlyFirstOccurrenceOfDuplicate() {
        PriorityQueueCustom<Integer> queue = queueOf(2, 2, 2, 1);

        assertTrue(queue.remove(2));
        assertEquals(List.of(1, 2, 2), QueueTestUtils.drain(queue));
    }

    @Test
    void shouldSiftUpWhenMovedElementIsSmallerThanNewParent() {
        // Корректная куча:            0
        //                         10     1
        //                       11  12  2  3
        // При удалении 11 на её место встаёт последний элемент 3 — он меньше нового родителя 10,
        // поэтому его нужно поднять (siftUp), а не опустить.
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(new Integer[] {0, 10, 1, 11, 12, 2, 3});

        assertTrue(queue.remove(11));
        assertTrue(queue.isValidHeap());
        assertEquals(List.of(0, 1, 2, 3, 10, 12), QueueTestUtils.drain(queue));
    }

    @Test
    void shouldReturnFalseForNull() {
        PriorityQueueCustom<Integer> queue = queueOf(1, 2);

        assertFalse(queue.remove(null));
        assertEquals(2, queue.size());
    }

    @Test
    void shouldRemoveFromMaxHeap() {
        PriorityQueueCustom<Integer> maxHeap = new PriorityQueueCustom<>(Comparator.reverseOrder());
        for (int value : new int[] {4, 9, 1, 7, 3}) {
            maxHeap.add(value);
        }

        assertTrue(maxHeap.remove(9));
        assertEquals(List.of(7, 4, 3, 1), QueueTestUtils.drain(maxHeap));
    }

    @Test
    void shouldKeepHeapValidAfterRandomRemovals() {
        Random random = new Random(QueueTestUtils.SEED);
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        List<Integer> expected = new ArrayList<>();
        for (int i = 0; i < 2_000; i++) {
            int value = random.nextInt(500);
            queue.add(value);
            expected.add(value);
        }
        for (int i = 0; i < 1_000; i++) {
            Integer value = random.nextInt(600); // часть значений гарантированно отсутствует
            assertEquals(expected.remove(value), queue.remove(value));
            assertTrue(queue.isValidHeap());
        }
        Collections.sort(expected);

        assertEquals(expected, QueueTestUtils.drain(queue));
    }

    private static PriorityQueueCustom<Integer> queueOf(int... values) {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (int value : values) {
            queue.add(value);
        }
        return queue;
    }
}
