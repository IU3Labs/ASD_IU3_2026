package ru.bmstu.lab2.queue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Random;
import org.junit.jupiter.api.Test;

/** Проверяет естественный порядок, компараторы и совпадение со стандартной очередью. */
class PriorityQueueCustomTest {
    @Test
    void shouldStartEmpty() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        assertEquals(0, queue.size());
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldRejectPollFromEmptyQueue() {
        assertThrows(NoSuchElementException.class, () -> new PriorityQueueCustom<Integer>().poll());
    }

    @Test
    void shouldRejectPeekFromEmptyQueue() {
        assertThrows(NoSuchElementException.class, () -> new PriorityQueueCustom<Integer>().peek());
    }

    @Test
    void shouldAddAndPollSingleElement() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(42);
        assertFalse(queue.isEmpty());
        assertEquals(42, queue.poll());
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
    }

    @Test
    void shouldPeekWithoutRemoving() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(5);
        queue.add(2);
        assertEquals(2, queue.peek());
        assertEquals(2, queue.peek());
        assertEquals(2, queue.size());
    }

    @Test
    void shouldPollUnorderedElementsInAscendingOrder() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (int value : new int[] {5, 1, 4, 2, 3}) {
            queue.add(value);
        }
        for (int expected = 1; expected <= 5; expected++) {
            assertEquals(expected, queue.poll());
        }
    }

    @Test
    void shouldKeepDuplicates() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(2);
        queue.add(2);
        queue.add(1);
        assertEquals(1, queue.poll());
        assertEquals(2, queue.poll());
        assertEquals(2, queue.poll());
    }

    @Test
    void shouldHandleExtremeAndNegativeValues() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (int value : new int[] {0, Integer.MAX_VALUE, -1, Integer.MIN_VALUE}) {
            queue.add(value);
        }
        for (int value : new int[] {Integer.MIN_VALUE, -1, 0, Integer.MAX_VALUE}) {
            assertEquals(value, queue.poll());
        }
    }

    @Test
    void shouldGrowAndHandleDescendingInput() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (int value = 10_000; value > 0; value--) {
            queue.add(value);
        }
        assertEquals(10_000, queue.size());
        for (int expected = 1; expected <= 10_000; expected++) {
            assertEquals(expected, queue.poll());
        }
    }

    @Test
    void shouldWorkAsMaxHeap() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(Comparator.reverseOrder());
        for (int value : new int[] {1, 5, 2, 4, 3}) {
            queue.add(value);
        }
        for (int expected = 5; expected > 0; expected--) {
            assertEquals(expected, queue.poll());
        }
    }

    @Test
    void shouldUseComparatorForNonComparableObjects() {
        record Item(int priority) { }
        PriorityQueueCustom<Item> queue = new PriorityQueueCustom<>(Comparator.comparingInt(Item::priority));
        queue.add(new Item(9));
        queue.add(new Item(1));
        assertEquals(1, queue.poll().priority());
        assertEquals(9, queue.poll().priority());
    }

    @Test
    void shouldUseNaturalOrderForNullComparator() {
        PriorityQueueCustom<String> queue = new PriorityQueueCustom<>(null);
        queue.add("z");
        queue.add("a");
        assertEquals("a", queue.poll());
    }

    @Test
    void shouldRejectNullWithoutChangingSize() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(1);
        assertThrows(NullPointerException.class, () -> queue.add(null));
        assertEquals(1, queue.size());
        assertEquals(1, queue.poll());
    }

    @Test
    void shouldAllowReuseAfterEmptying() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(2);
        queue.poll();
        queue.add(3);
        queue.add(1);
        assertEquals(1, queue.poll());
        assertEquals(3, queue.poll());
    }

    @Test
    void shouldMatchStandardQueueForRandomOperationsInBothModes() {
        checkAgainstStandard(Comparator.naturalOrder());
        checkAgainstStandard(Comparator.reverseOrder());
    }

    private void checkAgainstStandard(Comparator<Integer> comparator) {
        PriorityQueueCustom<Integer> actual = new PriorityQueueCustom<>(comparator);
        PriorityQueue<Integer> expected = new PriorityQueue<>(comparator);
        Random random = new Random(2026);
        for (int step = 0; step < 20_000; step++) {
            if (expected.isEmpty() || random.nextBoolean()) {
                int value = random.nextInt();
                actual.add(value);
                expected.add(value);
            } else {
                assertEquals(expected.poll(), actual.poll());
            }
            assertEquals(expected.size(), actual.size());
            if (!expected.isEmpty()) {
                assertEquals(expected.peek(), actual.peek());
            }
        }
        while (!expected.isEmpty()) {
            assertEquals(expected.poll(), actual.poll());
        }
        assertTrue(actual.isEmpty());
    }
}
