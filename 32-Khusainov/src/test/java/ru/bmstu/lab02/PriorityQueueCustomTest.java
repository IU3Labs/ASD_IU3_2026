package ru.bmstu.lab02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Random;
import org.junit.jupiter.api.Test;

/** Проверки контракта PriorityQueueCustom и граничных случаев. */
class PriorityQueueCustomTest {
    private final PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();

    @Test
    void shouldStartEmpty() {
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
    }

    @Test
    void shouldRejectEmptyPoll() {
        assertThrows(NoSuchElementException.class, queue::poll);
    }

    @Test
    void shouldRejectEmptyPeek() {
        assertThrows(NoSuchElementException.class, queue::peek);
    }

    @Test
    void shouldRetainMinimumAfterPeek() {
        queue.add(7);
        queue.add(3);
        assertEquals(3, queue.peek());
        assertEquals(2, queue.size());
    }

    @Test
    void shouldDrainSingleton() {
        queue.add(4);
        assertFalse(queue.isEmpty());
        assertEquals(4, queue.poll());
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldPreserveDuplicates() {
        queue.add(2);
        queue.add(2);
        assertEquals(2, queue.poll());
        assertEquals(2, queue.poll());
    }

    @Test
    void shouldGrowAcrossSeveralCapacities() {
        for (int i = 99; i >= 0; i--) {
            queue.add(i);
        }
        for (int i = 0; i < 100; i++) {
            assertEquals(i, queue.poll());
        }
    }

    @Test
    void shouldHandleIntegerExtremes() {
        queue.add(Integer.MAX_VALUE);
        queue.add(Integer.MIN_VALUE);
        assertEquals(Integer.MIN_VALUE, queue.poll());
        assertEquals(Integer.MAX_VALUE, queue.poll());
    }

    @Test
    void shouldCompareStringsNaturally() {
        PriorityQueueCustom<String> words = new PriorityQueueCustom<>();
        words.add("beta");
        words.add("alpha");
        assertEquals("alpha", words.poll());
    }

    @Test
    void shouldRejectNull() {
        assertThrows(NullPointerException.class, () -> queue.add(null));
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldMatchReferenceDuringMixedOperations() {
        Random random = new Random(42);
        PriorityQueue<Integer> reference = new PriorityQueue<>();
        for (int i = 0; i < 4000; i++) {
            if (reference.isEmpty() || random.nextBoolean()) {
                int value = random.nextInt();
                reference.add(value);
                queue.add(value);
            } else {
                assertEquals(reference.poll(), queue.poll());
            }
            assertEquals(reference.size(), queue.size());
        }
    }
}
