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

/** Проверки очереди с приоритетом. */
class PriorityQueueCustomTest {

    private final PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();

    @Test
    void shouldStartEmpty() {
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
        assertNull(queue.peek());
    }

    @Test
    void shouldThrowWhenPollingEmptyQueue() {
        assertThrows(NoSuchElementException.class, queue::poll);
    }

    @Test
    void shouldPeekWithoutRemoving() {
        queue.add(8);
        queue.add(3);
        assertEquals(3, queue.peek());
        assertEquals(2, queue.size());
        assertFalse(queue.isEmpty());
    }

    @Test
    void shouldBecomeEmptyWhenLastElementIsRemoved() {
        queue.add(42);
        assertEquals(42, queue.poll());
        assertTrue(queue.isEmpty());
        assertNull(queue.peek());
        queue.add(10);
        assertEquals(10, queue.poll());
    }

    @Test
    void shouldPreserveDuplicatesAndNegativeValues() {
        queue.add(2);
        queue.add(-7);
        queue.add(2);
        queue.add(0);
        assertEquals(-7, queue.poll());
        assertEquals(0, queue.poll());
        assertEquals(2, queue.poll());
        assertEquals(2, queue.poll());
    }

    @Test
    void shouldGrowPastInitialCapacity() {
        for (int value = 128; value >= 0; value--) {
            queue.add(value);
        }
        assertEquals(129, queue.size());
        for (int value = 0; value <= 128; value++) {
            assertEquals(value, queue.poll());
        }
    }

    @Test
    void shouldSupportComparableStrings() {
        PriorityQueueCustom<String> words = new PriorityQueueCustom<>();
        words.add("бета");
        words.add("альфа");
        assertEquals("альфа", words.poll());
        assertEquals("бета", words.poll());
    }

    @Test
    void shouldMatchStandardQueueOnMixedOperations() {
        Random random = new Random(42);
        PriorityQueue<Integer> expected = new PriorityQueue<>();
        for (int operation = 0; operation < 5_000; operation++) {
            if (expected.isEmpty() || random.nextInt(3) != 0) {
                int value = random.nextInt(500);
                expected.add(value);
                queue.add(value);
            } else {
                assertEquals(expected.poll(), queue.poll());
            }
            assertEquals(expected.peek(), queue.peek());
            assertEquals(expected.size(), queue.size());
        }
    }

    @Test
    void shouldHeapifyAnEmptyArray() {
        PriorityQueueCustom<Integer> heap = new PriorityQueueCustom<>(new Integer[0]);
        assertTrue(heap.isEmpty());
        heap.add(7);
        assertEquals(7, heap.poll());
    }

    @Test
    void shouldHeapifyASingleElement() {
        PriorityQueueCustom<Integer> heap = new PriorityQueueCustom<>(new Integer[] {7});
        assertEquals(7, heap.poll());
        assertTrue(heap.isEmpty());
    }

    @Test
    void shouldHeapifyArrayAndKeepCopy() {
        Integer[] input = {9, 4, 2, 4, -6, 0};
        PriorityQueueCustom<Integer> heap = new PriorityQueueCustom<>(input);
        assertEquals(9, input[0]);
        input[4] = 100;
        assertEquals(-6, heap.poll());
        assertEquals(0, heap.poll());
        assertEquals(2, heap.poll());
        assertEquals(4, heap.poll());
        assertEquals(4, heap.poll());
        assertEquals(9, heap.poll());
    }

    @Test
    void shouldAllowAddingAfterHeapify() {
        PriorityQueueCustom<Integer> heap = new PriorityQueueCustom<>(new Integer[] {3, 1, 2});
        heap.add(0);
        assertEquals(0, heap.poll());
        assertEquals(1, heap.poll());
    }

    @Test
    void shouldRejectNullElements() {
        assertThrows(NullPointerException.class, () -> queue.add(null));
        assertThrows(NullPointerException.class, () -> new PriorityQueueCustom<>(new Integer[] {1, null}));
        assertThrows(NullPointerException.class, () -> new PriorityQueueCustom<Integer>(null));
        assertTrue(queue.isEmpty());
    }
}
