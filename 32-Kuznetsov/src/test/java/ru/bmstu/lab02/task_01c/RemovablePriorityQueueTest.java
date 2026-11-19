package ru.bmstu.lab02.task_01c;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.bmstu.lab02.QueueTestHelper.drain;

class RemovablePriorityQueueTest {

    private static final long SEED = 25;

    @Test
    @DisplayName("Удаление корня")
    void shouldRemoveRoot() {
        RemovablePriorityQueue<Integer> queue = new RemovablePriorityQueue<>();
        queue.add(5);
        queue.add(3);
        queue.add(8);
        queue.add(1);

        assertTrue(queue.remove(1));
        assertEquals(3, queue.peek());
        assertEquals(List.of(3, 5, 8), drain(queue));
    }

    @Test
    @DisplayName("Удаление из середины, после которого нужен siftUp")
    void shouldSiftUpAfterRemovingFromMiddle() {
        RemovablePriorityQueue<Integer> queue = new RemovablePriorityQueue<>();
        queue.add(1);
        queue.add(50);
        queue.add(5);
        queue.add(60);
        queue.add(70);
        queue.add(8);
        queue.add(6);

        assertTrue(queue.remove(60));
        assertEquals(List.of(1, 5, 6, 8, 50, 70), drain(queue));
    }

    @Test
    @DisplayName("Удаление из середины, после которого нужен siftDown")
    void shouldSiftDownAfterRemovingFromMiddle() {
        RemovablePriorityQueue<Integer> queue = new RemovablePriorityQueue<>();
        for (int i = 1; i <= 7; i++) {
            queue.add(i);
        }

        assertTrue(queue.remove(2));
        assertEquals(6, queue.size());
        assertEquals(List.of(1, 3, 4, 5, 6, 7), drain(queue));
    }

    @Test
    @DisplayName("Удаление последнего элемента массива")
    void shouldRemoveLastElementOfArray() {
        RemovablePriorityQueue<Integer> queue = new RemovablePriorityQueue<>();
        queue.add(1);
        queue.add(2);
        queue.add(3);

        assertTrue(queue.remove(3));
        assertEquals(List.of(1, 2), drain(queue));
    }

    @Test
    @DisplayName("Удаление несуществующего элемента возвращает false")
    void shouldReturnFalseWhenRemovingMissingElement() {
        RemovablePriorityQueue<Integer> queue = new RemovablePriorityQueue<>();
        queue.add(1);
        queue.add(2);
        queue.add(3);

        assertFalse(queue.remove(25));
        assertEquals(3, queue.size());
    }

    @Test
    @DisplayName("Удаление из пустой очереди возвращает false")
    void shouldReturnFalseWhenRemovingFromEmptyQueue() {
        RemovablePriorityQueue<Integer> queue = new RemovablePriorityQueue<>();

        assertFalse(queue.remove(1));
        assertTrue(queue.isEmpty());
    }

    @Test
    @DisplayName("Удаление null возвращает false")
    void shouldReturnFalseWhenRemovingNull() {
        RemovablePriorityQueue<Integer> queue = new RemovablePriorityQueue<>();
        queue.add(1);

        assertFalse(queue.remove(null));
        assertEquals(1, queue.size());
    }

    @Test
    @DisplayName("Удаляется только первое вхождение")
    void shouldRemoveOnlyFirstOccurrence() {
        RemovablePriorityQueue<Integer> queue = new RemovablePriorityQueue<>();
        queue.add(4);
        queue.add(4);
        queue.add(4);

        assertTrue(queue.remove(4));
        assertEquals(List.of(4, 4), drain(queue));
    }

    @Test
    @DisplayName("Удаление всех элементов по одному опустошает очередь")
    void shouldBecomeEmptyAfterRemovingEverything() {
        RemovablePriorityQueue<Integer> queue = new RemovablePriorityQueue<>();
        for (int i = 0; i < 10; i++) {
            queue.add(i);
        }
        for (int i = 9; i >= 0; i--) {
            assertTrue(queue.remove(i));
        }

        assertTrue(queue.isEmpty());
        assertThrows(NoSuchElementException.class, () -> queue.poll());
    }

    @Test
    @DisplayName("Удаление из max-кучи")
    void shouldRemoveFromMaxHeap() {
        RemovablePriorityQueue<Integer> queue = new RemovablePriorityQueue<>(Comparator.reverseOrder());
        for (int i = 1; i <= 7; i++) {
            queue.add(i);
        }

        assertTrue(queue.remove(7));
        assertTrue(queue.remove(3));
        assertEquals(6, queue.peek());
        assertEquals(List.of(6, 5, 4, 2, 1), drain(queue));
    }

    @Test
    @DisplayName("200 случайных удалений сохраняют порядок кучи")
    void shouldKeepOrderAfterRandomRemovals() {
        Random random = new Random(SEED);
        RemovablePriorityQueue<Integer> queue = new RemovablePriorityQueue<>();
        List<Integer> expected = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            int value = random.nextInt(300);
            queue.add(value);
            expected.add(value);
        }
        for (int i = 0; i < 200; i++) {
            Integer value = random.nextInt(400);
            assertEquals(expected.remove(value), queue.remove(value));
        }
        Collections.sort(expected);

        assertEquals(expected, drain(queue));
    }
}
