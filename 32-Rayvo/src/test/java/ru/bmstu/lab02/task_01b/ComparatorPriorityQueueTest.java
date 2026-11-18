package ru.bmstu.lab02.task_01b;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;

import org.junit.jupiter.api.Test;

/** Проверяет естественный порядок, min-heap, max-heap и объекты с компаратором. */
class ComparatorPriorityQueueTest {
    @Test
    void shouldUseNaturalOrderByDefault() {
        ComparatorPriorityQueue<Integer> queue = new ComparatorPriorityQueue<>();
        queue.add(9);
        queue.add(1);
        queue.add(5);
        assertPollOrder(queue, 1, 5, 9);
    }

    @Test
    void shouldUseNaturalOrderForNullComparator() {
        ComparatorPriorityQueue<Integer> queue = new ComparatorPriorityQueue<>(null);
        queue.add(8);
        queue.add(-3);
        assertPollOrder(queue, -3, 8);
    }

    @Test
    void shouldSupportExplicitMinHeap() {
        ComparatorPriorityQueue<Integer> queue = new ComparatorPriorityQueue<>(Comparator.naturalOrder());
        queue.add(6);
        queue.add(2);
        queue.add(4);
        assertPollOrder(queue, 2, 4, 6);
    }

    @Test
    void shouldSupportMaxHeapAndPreservePeekedElement() {
        ComparatorPriorityQueue<Integer> queue = new ComparatorPriorityQueue<>(Comparator.reverseOrder());
        queue.add(2);
        queue.add(9);
        queue.add(4);
        assertEquals(9, queue.peek());
        assertEquals(3, queue.size());
        assertPollOrder(queue, 9, 4, 2);
    }

    @Test
    void shouldOrderObjectsWithoutComparable() {
        record Job(String name, int priority) { }
        ComparatorPriorityQueue<Job> queue = new ComparatorPriorityQueue<>(Comparator.comparingInt(Job::priority));
        Job first = new Job("first", 1);
        Job second = new Job("second", 2);
        queue.add(second);
        queue.add(first);
        assertEquals(first, queue.poll());
        assertEquals(second, queue.poll());
    }

    @Test
    void shouldAcceptComparatorOfSupertype() {
        Comparator<Number> comparator = Comparator.comparingDouble(Number::doubleValue);
        ComparatorPriorityQueue<Integer> queue = new ComparatorPriorityQueue<>(comparator);
        queue.add(7);
        queue.add(-2);
        assertPollOrder(queue, -2, 7);
    }

    @Test
    void shouldKeepAllValuesWithEqualPriorities() {
        ComparatorPriorityQueue<String> queue = new ComparatorPriorityQueue<>(Comparator.comparingInt(String::length));
        queue.add("aa");
        queue.add("bb");
        queue.add("x");
        assertEquals("x", queue.poll());
        assertEquals(Set.of("aa", "bb"), Set.of(queue.poll(), queue.poll()));
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldCompareExtremeValuesInMaxHeapWithoutOverflow() {
        ComparatorPriorityQueue<Integer> queue = new ComparatorPriorityQueue<>(Comparator.reverseOrder());
        queue.add(Integer.MIN_VALUE);
        queue.add(0);
        queue.add(Integer.MAX_VALUE);
        assertPollOrder(queue, Integer.MAX_VALUE, 0, Integer.MIN_VALUE);
    }

    @Test
    void shouldMatchStandardMaxHeapAfterArrayGrowth() {
        Comparator<Integer> comparator = Comparator.reverseOrder();
        ComparatorPriorityQueue<Integer> actual = new ComparatorPriorityQueue<>(comparator);
        PriorityQueue<Integer> expected = new PriorityQueue<>(comparator);
        Random random = new Random(102);
        for (int index = 0; index < 5000; index++) {
            int value = random.nextInt();
            actual.add(value);
            expected.add(value);
        }
        while (!expected.isEmpty()) {
            assertEquals(expected.poll(), actual.poll());
        }
        assertTrue(actual.isEmpty());
    }

    @Test
    void shouldProduceOppositeOrdersForSameRandomInput() {
        ComparatorPriorityQueue<Integer> minHeap = new ComparatorPriorityQueue<>(Comparator.naturalOrder());
        ComparatorPriorityQueue<Integer> maxHeap = new ComparatorPriorityQueue<>(Comparator.reverseOrder());
        Integer[] sorted = new Random(103).ints(1000, -100, 101).boxed().toArray(Integer[]::new);
        for (Integer value : sorted) {
            minHeap.add(value);
            maxHeap.add(value);
        }
        Arrays.sort(sorted);
        for (int index = 0; index < sorted.length; index++) {
            assertEquals(sorted[index], minHeap.poll());
            assertEquals(sorted[sorted.length - 1 - index], maxHeap.poll());
        }
    }

    private void assertPollOrder(ComparatorPriorityQueue<Integer> queue, Integer... expected) {
        for (Integer value : expected) {
            assertEquals(value, queue.poll());
        }
        assertTrue(queue.isEmpty());
    }
}
