package ru.bmstu.lab2.task_1a_1b_1d;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Random;
import org.junit.jupiter.api.Test;

/** Проверки 1A, 1B, 1D, включая сравнение с библиотечной очередью и оценку heapify. */
class PriorityQueueCustomTest {
    @Test
    void shouldStartEmpty() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        assertEquals(0, queue.size());
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldRejectPeekOnEmptyQueue() {
        assertThrows(NoSuchElementException.class, () -> new PriorityQueueCustom<Integer>().peek());
    }

    @Test
    void shouldRejectPollOnEmptyQueue() {
        assertThrows(NoSuchElementException.class, () -> new PriorityQueueCustom<Integer>().poll());
    }

    @Test
    void shouldPeekWithoutRemovingElement() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(7);
        assertEquals(7, queue.peek());
        assertEquals(7, queue.peek());
        assertEquals(1, queue.size());
        assertFalse(queue.isEmpty());
    }

    @Test
    void shouldPollSingleElementAndAllowReuse() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(7);
        assertEquals(7, queue.poll());
        assertTrue(queue.isEmpty());
        queue.add(-3);
        assertEquals(-3, queue.poll());
    }

    @Test
    void shouldPreserveDuplicates() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(new Integer[]{2, 1, 2, 1});
        assertArrayEquals(new Integer[]{1, 1, 2, 2}, drain(queue));
    }

    @Test
    void shouldHandleNegativeAndExtremeIntegers() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(
                new Integer[]{Integer.MAX_VALUE, 0, -1, Integer.MIN_VALUE});
        assertArrayEquals(new Integer[]{Integer.MIN_VALUE, -1, 0, Integer.MAX_VALUE}, drain(queue));
    }

    @Test
    void shouldResizeAndSortDescendingInput() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (int value = 2_000; value >= 0; value--) {
            queue.add(value);
        }
        for (int expected = 0; expected <= 2_000; expected++) {
            assertEquals(expected, queue.poll());
        }
    }

    @Test
    void shouldSupportMaxHeapComparator() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(Comparator.reverseOrder());
        queue.add(1);
        queue.add(9);
        queue.add(3);
        assertArrayEquals(new Integer[]{9, 3, 1}, drain(queue));
    }

    @Test
    void shouldUseNaturalOrderForNullComparator() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>((Comparator<Integer>) null);
        queue.add(2);
        queue.add(1);
        assertEquals(1, queue.poll());
    }

    @Test
    void shouldOrderNonComparableObjectsWithComparator() {
        record Job(int priority) { }
        PriorityQueueCustom<Job> queue = new PriorityQueueCustom<>(Comparator.comparingInt(Job::priority));
        queue.add(new Job(4));
        queue.add(new Job(1));
        assertEquals(new Job(1), queue.poll());
    }

    @Test
    void shouldSortStringsNaturally() {
        PriorityQueueCustom<String> queue = new PriorityQueueCustom<>(new String[]{"c", "a", "b"});
        assertEquals("a", queue.poll());
        assertEquals("b", queue.poll());
        assertEquals("c", queue.poll());
    }

    @Test
    void shouldRejectNullElementWithoutChangingQueue() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(1);
        assertThrows(NullPointerException.class, () -> queue.add(null));
        assertEquals(1, queue.size());
        assertEquals(1, queue.poll());
    }

    @Test
    void shouldRejectNullArray() {
        assertThrows(NullPointerException.class, () -> new PriorityQueueCustom<>((Integer[]) null));
    }

    @Test
    void shouldRejectNullInsideArray() {
        assertThrows(NullPointerException.class, () -> new PriorityQueueCustom<>(new Integer[]{1, null}));
    }

    @Test
    void shouldBuildEmptyHeapAndAllowInsertion() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(new Integer[0]);
        assertTrue(queue.isEmpty());
        queue.add(5);
        assertEquals(5, queue.poll());
    }

    @Test
    void shouldCopyConstructorArray() {
        Integer[] input = {3, 1, 2};
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(input);
        assertArrayEquals(new Integer[]{3, 1, 2}, input);
        input[1] = 100;
        assertArrayEquals(new Integer[]{1, 2, 3}, drain(queue));
    }

    @Test
    void shouldBuildMaxHeapFromArray() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(
                new Integer[]{4, 7, 1, 9, 3}, Comparator.reverseOrder());
        assertArrayEquals(new Integer[]{9, 7, 4, 3, 1}, drain(queue));
    }

    @Test
    void shouldMatchStandardQueueDuringMixedOperations() {
        Random random = new Random(20261121);
        for (Comparator<Integer> comparator : Arrays.<Comparator<Integer>>asList(null, Comparator.reverseOrder())) {
            PriorityQueueCustom<Integer> actual = new PriorityQueueCustom<>(comparator);
            PriorityQueue<Integer> expected = new PriorityQueue<>(11, comparator);
            for (int operation = 0; operation < 10_000; operation++) {
                if (expected.isEmpty() || random.nextBoolean()) {
                    int value = random.nextInt();
                    expected.add(value);
                    actual.add(value);
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
        }
    }

    @Test
    void shouldHeapifyWithLinearNumberOfComparisons() {
        Integer[] input = new Integer[100_000];
        Arrays.setAll(input, index -> input.length - index);
        int[] comparisons = {0};
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(input, (first, second) -> {
            comparisons[0]++;
            return Integer.compare(first, second);
        });
        assertTrue(comparisons[0] < 2 * input.length, "Heapify должен использовать менее 2n сравнений");
        for (int expected = 1; expected <= input.length; expected++) {
            assertEquals(expected, queue.poll());
        }
    }

    private Integer[] drain(PriorityQueueCustom<Integer> queue) {
        Integer[] result = new Integer[queue.size()];
        Arrays.setAll(result, index -> queue.poll());
        return result;
    }
}
