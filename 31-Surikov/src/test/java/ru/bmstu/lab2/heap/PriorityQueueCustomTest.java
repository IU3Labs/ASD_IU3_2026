package ru.bmstu.lab2.heap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Random;
import org.junit.jupiter.api.Test;

/** проверка границ, основных операций и сравнение с библиотечным аналогом */
class PriorityQueueCustomTest {
    private PriorityQueueCustom<Integer> subject = new PriorityQueueCustom<>();

    @Test
    void shouldStartEmpty() {
        assertTrue(subject.isEmpty());
        assertEquals(0, subject.size());
    }

    @Test
    void shouldPeekEmpty() {
        assertNull(subject.peek());
    }

    @Test
    void shouldRejectEmptyPoll() {
        assertThrows(NoSuchElementException.class, subject::poll);
    }

    @Test
    void shouldRejectNull() {
        assertThrows(NullPointerException.class, () -> subject.add(null));
    }

    @Test
    void shouldKeepMinimum() {
        subject.add(7);
        subject.add(-1);
        assertEquals(-1, subject.peek());
        assertEquals(2, subject.size());
    }

    @Test
    void shouldKeepDuplicates() {
        subject.add(3);
        subject.add(3);
        assertEquals(3, subject.poll());
        assertEquals(3, subject.poll());
    }

    @Test
    void shouldGrow() {
        for (int i = 100; i >= 0; i--) {
            subject.add(i);
        }
        for (int i = 0; i <= 100; i++) {
            assertEquals(i, subject.poll());
        }
    }

    @Test
    void shouldReuseEmptyHeap() {
        subject.add(4);
        subject.poll();
        subject.add(9);
        assertEquals(9, subject.peek());
    }

    @Test
    void shouldKeepExtremeValuesInOrder() {
        subject.add(Integer.MAX_VALUE);
        subject.add(Integer.MIN_VALUE);
        assertEquals(Integer.MIN_VALUE, subject.poll());
        assertEquals(Integer.MAX_VALUE, subject.poll());
    }

    @Test
    void shouldMatchReference() {
        Random random = new Random(19);
        Integer[] data = random.ints(501).boxed().toArray(Integer[]::new);
        for (Integer value : data) {
            subject.add(value);
        }
        PriorityQueue<Integer> expected = new PriorityQueue<>(java.util.List.of(data));
        while (!expected.isEmpty()) {
            assertEquals(expected.poll(), subject.poll());
        }
    }
}
