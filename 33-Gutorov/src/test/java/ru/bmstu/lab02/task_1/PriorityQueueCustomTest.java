package ru.bmstu.lab02.task_1;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PriorityQueueCustomTest {

    @Test
    void shouldStartEmpty() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
    }

    @Test
    void shouldRejectPollOnEmptyQueue() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        assertThrows(NoSuchElementException.class, queue::poll);
    }

    @Test
    void shouldRejectPeekOnEmptyQueue() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        assertThrows(NoSuchElementException.class, queue::peek);
    }

    @Test
    void shouldRejectNull() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        assertThrows(NullPointerException.class, () -> queue.add(null));
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldPeekWithoutRemovingMinimum() {
        PriorityQueueCustom<Integer> queue = integers(5, 2, 7);
        assertEquals(2, queue.peek());
        assertEquals(2, queue.peek());
        assertEquals(3, queue.size());
    }

    @Test
    void shouldBecomeEmptyAfterPollingOnlyElement() {
        PriorityQueueCustom<Integer> queue = integers(7);
        assertFalse(queue.isEmpty());
        assertEquals(7, queue.poll());
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
    }

    @Test
    void shouldPollInAscendingOrder() {
        PriorityQueueCustom<Integer> queue = integers(7, 2, 9, 1, 4);
        for (int expected : new int[] {1, 2, 4, 7, 9}) {
            assertEquals(expected, queue.poll());
        }
    }

    @Test
    void shouldKeepDuplicatesAndNegativeValues() {
        PriorityQueueCustom<Integer> queue = integers(0, -2, 3, -2);
        for (int expected : new int[] {-2, -2, 0, 3}) {
            assertEquals(expected, queue.poll());
        }
    }

    @Test
    void shouldPreserveElementsAfterSeveralExpansions() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (int value = 63; value >= 0; value--) {
            queue.add(value);
        }
        for (int expected = 0; expected < 64; expected++) {
            assertEquals(expected, queue.poll());
        }
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldUseNaturalOrderForStrings() {
        PriorityQueueCustom<String> queue = new PriorityQueueCustom<>();
        queue.add("pear");
        queue.add("apple");
        queue.add("banana");
        assertEquals("apple", queue.poll());
        assertEquals("banana", queue.poll());
        assertEquals("pear", queue.poll());
    }

        @Test
    void shouldRemoveRootAndRestoreOrder() {
        PriorityQueueCustom<Integer> queue = integers(1, 2, 3, 4, 5);

        assertTrue(queue.remove(1));
        assertEquals(4, queue.size());
        assertEquals(2, queue.peek());
    }

    @Test
    void shouldSiftUpAfterRemovingInteriorElement() {
        PriorityQueueCustom<Integer> queue = integers(1, 5, 2, 9, 6, 3, 4);

        assertTrue(queue.remove(9));
        for (int expected : new int[] {1, 2, 3, 4, 5, 6}) {
            assertEquals(expected, queue.poll());
        }
    }

    @Test
    void shouldSiftDownAfterRemovingInteriorElement() {
        PriorityQueueCustom<Integer> queue = integers(1, 2, 3, 4, 5, 6, 7);

        assertTrue(queue.remove(2));
        for (int expected : new int[] {1, 3, 4, 5, 6, 7}) {
            assertEquals(expected, queue.poll());
        }
    }

    @Test
    void shouldRemoveLastElementInHeapArray() {
        PriorityQueueCustom<Integer> queue = integers(1, 2, 3);

        assertTrue(queue.remove(3));
        assertEquals(2, queue.size());
        assertEquals(1, queue.poll());
        assertEquals(2, queue.poll());
    }

    @Test
    void shouldRemoveOnlyOneDuplicate() {
        PriorityQueueCustom<Integer> queue = integers(2, 2, 5);

        assertTrue(queue.remove(2));
        assertEquals(2, queue.size());
        assertEquals(2, queue.poll());
        assertEquals(5, queue.poll());
    }

    @Test
    void shouldReturnFalseWhenElementIsMissing() {
        PriorityQueueCustom<Integer> queue = integers(1, 2, 3);

        assertFalse(queue.remove(9));
        assertEquals(3, queue.size());
        assertEquals(1, queue.peek());
    }

    @Test
    void shouldReturnFalseWhenRemovingFromEmptyQueue() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();

        assertFalse(queue.remove(1));
        assertTrue(queue.isEmpty());
    }
    
    private PriorityQueueCustom<Integer> integers(int... values) {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (int value : values) {
            queue.add(value);
        }
        return queue;
    }
}