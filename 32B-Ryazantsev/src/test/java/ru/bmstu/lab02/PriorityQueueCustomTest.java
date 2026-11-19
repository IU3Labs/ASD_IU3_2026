package ru.bmstu.lab02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Random;
import org.junit.jupiter.api.Test;

/** Проверяет порядок извлечения и поведение очереди на границах. */
class PriorityQueueCustomTest {
    private final PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();

    @Test
    void shouldStartEmpty() {
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
    }

    @Test
    void shouldThrowWhenPollingEmptyQueue() {
        assertThrows(NoSuchElementException.class, queue::poll);
    }

    @Test
    void shouldPeekNullWhenEmpty() {
        assertNull(queue.peek());
    }

    @Test
    void shouldPeekWithoutRemoving() {
        queue.add(7);
        queue.add(3);
        assertEquals(3, queue.peek());
        assertEquals(3, queue.peek());
        assertEquals(2, queue.size());
    }

    @Test
    void shouldReturnToEmptyAfterOnePoll() {
        queue.add(1);
        assertFalse(queue.isEmpty());
        assertEquals(1, queue.poll());
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldReuseQueueAfterDrainingIt() {
        queue.add(10);
        queue.poll();
        queue.add(-5);
        assertEquals(-5, queue.poll());
    }

    @Test
    void shouldKeepDuplicates() {
        queue.add(4);
        queue.add(4);
        queue.add(1);
        assertEquals(1, queue.poll());
        assertEquals(4, queue.poll());
        assertEquals(4, queue.poll());
    }

    @Test
    void shouldOrderIntegerExtremes() {
        queue.add(Integer.MAX_VALUE);
        queue.add(-1);
        queue.add(Integer.MIN_VALUE);
        assertEquals(Integer.MIN_VALUE, queue.poll());
        assertEquals(-1, queue.poll());
        assertEquals(Integer.MAX_VALUE, queue.poll());
    }

    @Test
    void shouldGrowAndDrainDescendingInputInOrder() {
        for (int i = 500; i >= 1; i--) {
            queue.add(i);
        }
        assertEquals(500, queue.size());
        for (int i = 1; i <= 500; i++) {
            assertEquals(i, queue.poll());
        }
    }

    @Test
    void shouldRejectNullWithoutChangingSize() {
        queue.add(8);
        assertThrows(NullPointerException.class, () -> queue.add(null));
        assertEquals(1, queue.size());
    }

    @Test
    void shouldOrderStringsByNaturalOrder() {
        PriorityQueueCustom<String> words = new PriorityQueueCustom<>();
        words.add("gamma");
        words.add("alpha");
        words.add("beta");
        assertEquals("alpha", words.poll());
        assertEquals("beta", words.poll());
        assertEquals("gamma", words.poll());
    }

    @Test
    void shouldMatchStandardQueueDuringMixedOperations() {
        Random random = new Random(3212);
        PriorityQueue<Integer> expected = new PriorityQueue<>();
        for (int i = 0; i < 3000; i++) {
            if (expected.isEmpty() || random.nextInt(3) != 0) {
                int element = random.nextInt(40);
                expected.add(element);
                queue.add(element);
            } else {
                assertEquals(expected.remove(), queue.poll());
            }
            assertEquals(expected.peek(), queue.peek());
            assertEquals(expected.size(), queue.size());
        }
    }
}
