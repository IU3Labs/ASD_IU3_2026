package ru.bmstu.lab2.task_1;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.bmstu.lab2.task_1.QueueTestSupport.drain;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;

/** Проверки очереди: PriorityQueueConstructionTest. */
class PriorityQueueConstructionTest {
    private static final int LARGE_SIZE = 100_000;
    private static final long SEED = 20261120L;

    @Test
    void shouldBuildHeapWithoutChangingOrRetainingInputArray() {
        Integer[] values = {4, 1, 3};
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(values);
        assertArrayEquals(new Integer[] {4, 1, 3}, values);
        values[1] = 100;
        assertArrayEquals(new int[] {1, 3, 4}, drain(queue));
    }

    @Test
    void shouldBuildMaxHeapFromArray() {
        PriorityQueueCustom<Integer> queue =
                new PriorityQueueCustom<>(new Integer[] {2, 7, 1, 7}, Comparator.reverseOrder());
        assertArrayEquals(new int[] {7, 7, 2, 1}, drain(queue));
    }

    @Test
    void shouldAcceptEmptyArrayAndAllowFurtherInsertion() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(new Integer[0]);
        assertTrue(queue.isEmpty());
        queue.add(8);
        assertEquals(8, queue.poll());
    }

    @Test
    void shouldRejectNullArrayAndNullArrayElement() {
        assertThrows(NullPointerException.class, () -> new PriorityQueueCustom<>((Integer[]) null));
        assertThrows(
                NullPointerException.class,
                () -> new PriorityQueueCustom<>(new Integer[] {1, null}));
    }

    @Test
    void shouldBuildHeapWithLinearNumberOfComparisons() {
        Integer[] values = new Random(SEED).ints(LARGE_SIZE).boxed().toArray(Integer[]::new);
        AtomicInteger comparisons = new AtomicInteger();
        Comparator<Integer> counting =
                (first, second) -> {
                    comparisons.incrementAndGet();
                    return Integer.compare(first, second);
                };
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(values, counting);
        assertTrue(comparisons.get() < 2 * LARGE_SIZE);
        Arrays.sort(values);
        for (int expected : values) {
            assertEquals(expected, queue.poll());
        }
    }
}
