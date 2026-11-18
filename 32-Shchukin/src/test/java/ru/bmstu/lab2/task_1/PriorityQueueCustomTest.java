package ru.bmstu.lab2.task_1;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Random;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Проверки задач 1A–1D: операции кучи, компаратор, удаление и построение из массива. */
class PriorityQueueCustomTest {

    @Test
    void shouldStartEmpty() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
        assertNull(queue.peek());
    }

    @Test
    void shouldPollElementsInAscendingOrder() {
        PriorityQueueCustom<Integer> queue = queueOf(8, 2, 7, 1, 5);
        assertEquals(Integer.valueOf(1), queue.poll());
        assertEquals(Integer.valueOf(2), queue.poll());
        assertEquals(Integer.valueOf(5), queue.poll());
        assertEquals(Integer.valueOf(7), queue.poll());
        assertEquals(Integer.valueOf(8), queue.poll());
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldNotRemoveElementOnPeek() {
        PriorityQueueCustom<Integer> queue = queueOf(3, 1, 2);
        assertEquals(Integer.valueOf(1), queue.peek());
        assertEquals(Integer.valueOf(1), queue.peek());
        assertEquals(3, queue.size());
    }

    @Test
    void shouldThrowWhenPollingEmptyQueue() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        assertThrows(NoSuchElementException.class, queue::poll);
    }

    @Test
    void shouldAllowReuseAfterPollingLastElement() {
        PriorityQueueCustom<Integer> queue = queueOf(7);
        assertEquals(Integer.valueOf(7), queue.poll());
        assertNull(queue.peek());
        queue.add(4);
        assertEquals(Integer.valueOf(4), queue.poll());
    }

    @Test
    void shouldHandleDuplicates() {
        PriorityQueueCustom<Integer> queue = queueOf(2, 1, 2, 1);
        assertEquals(Integer.valueOf(1), queue.poll());
        assertEquals(Integer.valueOf(1), queue.poll());
        assertEquals(Integer.valueOf(2), queue.poll());
        assertEquals(Integer.valueOf(2), queue.poll());
    }

    @Test
    void shouldHandleNegativeValues() {
        PriorityQueueCustom<Integer> queue = queueOf(0, -2, -10, 3);
        assertEquals(Integer.valueOf(-10), queue.poll());
        assertEquals(Integer.valueOf(-2), queue.poll());
        assertEquals(Integer.valueOf(0), queue.poll());
        assertEquals(Integer.valueOf(3), queue.poll());
    }

    @Test
    void shouldGrowBeyondInitialCapacity() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (int value = 999; value >= 0; value--) {
            queue.add(value);
        }
        assertEquals(1_000, queue.size());
        for (int value = 0; value < 1_000; value++) {
            assertEquals(Integer.valueOf(value), queue.poll());
        }
    }

    @Test
    void shouldRejectNullOnAdd() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        assertThrows(NullPointerException.class, () -> queue.add(null));
    }

    @Test
    void shouldUseNaturalOrderForStrings() {
        PriorityQueueCustom<String> queue = new PriorityQueueCustom<>();
        queue.add("beta");
        queue.add("alpha");
        assertEquals("alpha", queue.poll());
        assertEquals("beta", queue.poll());
    }

    @Test
    void shouldWorkAsMaxHeapWithReverseComparator() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(Comparator.reverseOrder());
        for (int value : new int[]{1, 4, 2, 5, 3}) {
            queue.add(value);
        }
        for (int value = 5; value >= 1; value--) {
            assertEquals(Integer.valueOf(value), queue.poll());
        }
    }

    @Test
    void shouldUseCustomComparatorForStrings() {
        PriorityQueueCustom<String> queue = new PriorityQueueCustom<>(Comparator.comparingInt(String::length));
        queue.add("a long string");
        queue.add("xy");
        queue.add("abcd");
        assertEquals("xy", queue.poll());
        assertEquals("abcd", queue.poll());
        assertEquals("a long string", queue.poll());
    }

    @Test
    void shouldRemoveRootAndRestoreHeap() {
        PriorityQueueCustom<Integer> queue = queueOf(5, 1, 4, 3, 2);
        assertTrue(queue.remove(1));
        assertEquals(Integer.valueOf(2), queue.poll());
        assertEquals(Integer.valueOf(3), queue.poll());
        assertEquals(Integer.valueOf(4), queue.poll());
        assertEquals(Integer.valueOf(5), queue.poll());
    }

    @Test
    void shouldRemoveMiddleElementAndRestoreHeap() {
        PriorityQueueCustom<Integer> queue = queueOf(1, 5, 2, 8, 6, 3, 4);
        assertTrue(queue.remove(8));
        assertEquals(Integer.valueOf(1), queue.poll());
        assertEquals(Integer.valueOf(2), queue.poll());
        assertEquals(Integer.valueOf(3), queue.poll());
        assertEquals(Integer.valueOf(4), queue.poll());
        assertEquals(Integer.valueOf(5), queue.poll());
        assertEquals(Integer.valueOf(6), queue.poll());
    }

    @Test
    void shouldReturnFalseWhenRemovingMissingElement() {
        PriorityQueueCustom<Integer> queue = queueOf(2, 7, 4);
        assertFalse(queue.remove(9));
        assertEquals(3, queue.size());
        assertEquals(Integer.valueOf(2), queue.peek());
    }

    @Test
    void shouldReturnFalseWhenRemovingFromEmptyQueue() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        assertFalse(queue.remove(1));
    }

    @Test
    void shouldRemoveOnlyOneDuplicate() {
        PriorityQueueCustom<Integer> queue = queueOf(2, 2, 2);
        assertTrue(queue.remove(2));
        assertEquals(2, queue.size());
        assertEquals(Integer.valueOf(2), queue.poll());
        assertEquals(Integer.valueOf(2), queue.poll());
    }

    @Test
    void shouldRemoveLastHeapElement() {
        PriorityQueueCustom<Integer> queue = queueOf(1, 2, 3);
        assertTrue(queue.remove(3));
        assertEquals(Integer.valueOf(1), queue.poll());
        assertEquals(Integer.valueOf(2), queue.poll());
    }

    @Test
    void shouldRestoreMaxHeapAfterRemove() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(Comparator.reverseOrder());
        for (int value : new int[]{1, 5, 3, 2, 4}) {
            queue.add(value);
        }
        assertTrue(queue.remove(3));
        assertEquals(Integer.valueOf(5), queue.poll());
        assertEquals(Integer.valueOf(4), queue.poll());
        assertEquals(Integer.valueOf(2), queue.poll());
        assertEquals(Integer.valueOf(1), queue.poll());
    }

    @Test
    void shouldBuildHeapFromUnsortedArray() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(new Integer[]{7, 2, 8, 1, 3});
        assertEquals(Integer.valueOf(1), queue.poll());
        assertEquals(Integer.valueOf(2), queue.poll());
        assertEquals(Integer.valueOf(3), queue.poll());
        assertEquals(Integer.valueOf(7), queue.poll());
        assertEquals(Integer.valueOf(8), queue.poll());
    }

    @Test
    void shouldNotChangeSourceArrayOnBuild() {
        Integer[] array = {4, 1, 3};
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(array);
        array[0] = -100;
        assertEquals(Integer.valueOf(1), queue.poll());
        assertEquals(Integer.valueOf(3), queue.poll());
        assertEquals(Integer.valueOf(4), queue.poll());
    }

    @Test
    void shouldSupportEmptyArrayConstructor() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(new Integer[0]);
        assertTrue(queue.isEmpty());
        queue.add(1);
        assertEquals(Integer.valueOf(1), queue.poll());
    }

    @Test
    void shouldRejectNullElementInArrayConstructor() {
        Integer[] values = {1, null, 2};
        assertThrows(NullPointerException.class, () -> new PriorityQueueCustom<>(values));
    }

    @Test
    void shouldMatchStandardPriorityQueueOnRandomInput() {
        PriorityQueueCustom<Integer> actual = new PriorityQueueCustom<>();
        PriorityQueue<Integer> expected = new PriorityQueue<>();
        Random random = new Random(42);
        for (int i = 0; i < 3_000; i++) {
            int value = random.nextInt(2_000) - 1_000;
            actual.add(value);
            expected.add(value);
        }
        for (int i = 0; i < 300; i++) {
            int value = random.nextInt(2_000) - 1_000;
            assertEquals(expected.remove(value), actual.remove(value));
        }
        while (!expected.isEmpty()) {
            assertEquals(expected.poll(), actual.poll());
        }
        assertTrue(actual.isEmpty());
    }

    private PriorityQueueCustom<Integer> queueOf(int... values) {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (int value : values) {
            queue.add(value);
        }
        return queue;
    }
}
