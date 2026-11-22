package ru.bmstu.lab2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Comparator;
import org.junit.jupiter.api.Test;

/** Проверки естественного порядка и произвольного компаратора. */
class PriorityQueueComparatorTest {
    @Test
    void shouldUseNaturalOrderByDefault() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(3);
        queue.add(1);
        assertEquals(1, queue.poll());
        assertEquals(3, queue.poll());
    }

    @Test
    void shouldUseReverseOrderForMaxHeap() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(Comparator.reverseOrder());
        for (int value : new int[] {3, 1, 4, 2}) {
            queue.add(value);
        }
        for (int expected : new int[] {4, 3, 2, 1}) {
            assertEquals(expected, queue.poll());
        }
    }

    @Test
    void shouldSupportCustomObjectsWithComparator() {
        record Box(int priority, String label) { }
        PriorityQueueCustom<Box> queue = new PriorityQueueCustom<>(Comparator.comparingInt(Box::priority));
        queue.add(new Box(9, "later"));
        queue.add(new Box(2, "first"));
        assertEquals("first", queue.poll().label());
    }

    @Test
    void shouldAcceptNullComparatorAsNaturalOrder() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>((Comparator<Integer>) null);
        queue.add(9);
        queue.add(-1);
        assertEquals(-1, queue.poll());
    }

    @Test
    void shouldKeepDuplicatesInMaxHeap() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(Comparator.reverseOrder());
        queue.add(2);
        queue.add(5);
        queue.add(5);
        assertEquals(5, queue.poll());
        assertEquals(5, queue.poll());
        assertEquals(2, queue.poll());
        assertNull(queue.peek());
    }
}
