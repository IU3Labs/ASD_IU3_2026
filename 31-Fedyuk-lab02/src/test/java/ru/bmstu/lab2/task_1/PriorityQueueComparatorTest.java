package ru.bmstu.lab2.task_1;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.bmstu.lab2.task_1.QueueTestSupport.drain;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;
import org.junit.jupiter.api.Test;

/** Проверки очереди: PriorityQueueComparatorTest. */
class PriorityQueueComparatorTest {
    private static final int RANDOM_OPERATIONS = 10_000;
    private static final int VALUE_BOUND = 200;
    private static final long SEED = 20261120L;

    @Test
    void shouldExtractMaximumWithReverseComparator() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(Comparator.reverseOrder());
        queue.add(1);
        queue.add(8);
        queue.add(-2);
        assertArrayEquals(new int[] {8, 1, -2}, drain(queue));
    }

    @Test
    void shouldUseNaturalOrderWithNullComparator() {
        PriorityQueueCustom<String> queue = new PriorityQueueCustom<>((Comparator<String>) null);
        queue.add("pear");
        queue.add("apple");
        assertEquals("apple", queue.poll());
        assertEquals("pear", queue.poll());
    }

    @Test
    void shouldSupportNonComparableObjectsWithComparator() {
        PriorityQueueCustom<int[]> queue =
                new PriorityQueueCustom<>(Comparator.comparingInt(a -> a[0]));
        queue.add(new int[] {9});
        queue.add(new int[] {2});
        assertArrayEquals(new int[] {2}, queue.poll());
    }

    @Test
    void shouldMatchStandardQueueDuringRandomOperations() {
        compareRandomOperations(Comparator.naturalOrder());
        compareRandomOperations(Comparator.reverseOrder());
    }

    private static void compareRandomOperations(Comparator<Integer> comparator) {
        Random random = new Random(SEED);
        PriorityQueue<Integer> expected = new PriorityQueue<>(comparator);
        PriorityQueueCustom<Integer> actual = new PriorityQueueCustom<>(comparator);
        for (int step = 0; step < RANDOM_OPERATIONS; step++) {
            int value = random.nextInt(VALUE_BOUND);
            int operation = random.nextInt(3);
            if (operation == 0 || expected.isEmpty()) {
                expected.add(value);
                actual.add(value);
            } else if (operation == 1) {
                assertEquals(expected.poll(), actual.poll());
            } else {
                assertEquals(expected.remove(value), actual.remove(value));
            }
            assertEquals(expected.size(), actual.size());
            if (!expected.isEmpty()) {
                assertEquals(expected.peek(), actual.peek());
            }
        }
    }
}
