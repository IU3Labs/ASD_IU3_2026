package ru.bmstu.lab02;

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

/** Проверки порядка, изменения размера и восстановления кучи. */
class PriorityQueueCustomTest {
    private final PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();

    @Test
    void shouldRejectReadingEmptyQueue() {
        assertTrue(queue.isEmpty());
        assertThrows(NoSuchElementException.class, queue::poll);
        assertThrows(NoSuchElementException.class, queue::peek);
    }

    @Test
    void shouldPeekWithoutRemoving() {
        queue.add(3);
        queue.add(1);
        assertEquals(1, queue.peek());
        assertEquals(2, queue.size());
        assertEquals(1, queue.poll());
    }

    @Test
    void shouldGrowAndPollInOrder() {
        for (int i = 99; i >= 0; i--) {
            queue.add(i);
        }
        for (int i = 0; i < 100; i++) {
            assertEquals(i, queue.poll());
        }
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldUseReverseComparator() {
        var maxHeap = new PriorityQueueCustom<Integer>(Comparator.reverseOrder());
        maxHeap.add(Integer.MIN_VALUE);
        maxHeap.add(0);
        maxHeap.add(Integer.MAX_VALUE);
        assertArrayEquals(new int[]{Integer.MAX_VALUE, 0, Integer.MIN_VALUE}, drain(maxHeap));
    }

    @Test
    void shouldRemoveRoot() {
        var heap = new PriorityQueueCustom<>(new Integer[]{1, 2, 3, 4});
        assertTrue(heap.remove(1));
        assertArrayEquals(new int[]{2, 3, 4}, drain(heap));
    }

    @Test
    void shouldMoveReplacementUpAfterInteriorRemoval() {
        var heap = new PriorityQueueCustom<>(new Integer[]{1, 8, 2, 9, 10, 3, 4});
        assertTrue(heap.remove(9));
        assertArrayEquals(new int[]{1, 2, 3, 4, 8, 10}, drain(heap));
    }

    @Test
    void shouldRemoveOnlyOneEqualValue() {
        var heap = new PriorityQueueCustom<>(new Integer[]{2, 1, 2, 2});
        assertTrue(heap.remove(2));
        assertArrayEquals(new int[]{1, 2, 2}, drain(heap));
    }

    @Test
    void shouldLeaveQueueIntactWhenMissing() {
        queue.add(8);
        assertFalse(queue.remove(1));
        assertEquals(8, queue.poll());
    }

    @Test
    void shouldReturnFalseForRemovalFromEmptyQueue() {
        assertFalse(queue.remove(1));
    }

    @Test
    void shouldBuildFromIndependentArrayCopy() {
        Integer[] values = {5, 4, 3, 2, 1};
        Integer[] original = values.clone();
        var heap = new PriorityQueueCustom<>(values);
        assertArrayEquals(original, values);
        Arrays.fill(values, 0);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, drain(heap));
    }

    @Test
    void shouldGrowFromEmptyArray() {
        var heap = new PriorityQueueCustom<>(new Integer[0]);
        heap.add(7);
        assertTrue(heap.remove(7));
        assertTrue(heap.isEmpty());
    }

    @Test
    void shouldRejectNullElements() {
        assertThrows(NullPointerException.class, () -> queue.add(null));
        assertThrows(NullPointerException.class, () -> new PriorityQueueCustom<>(new Integer[]{1, null}));
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldMatchStandardQueueOnMixedOperations() {
        Random random = new Random(31);
        PriorityQueue<Integer> expected = new PriorityQueue<>();
        for (int i = 0; i < 5000; i++) {
            int value = random.nextInt(30);
            switch (random.nextInt(3)) {
                case 0 -> {
                    expected.add(value);
                    queue.add(value);
                }
                case 1 -> assertEquals(expected.remove(value), queue.remove(value));
                default -> {
                    if (!expected.isEmpty()) {
                        assertEquals(expected.poll(), queue.poll());
                    }
                }
            }
            assertEquals(expected.size(), queue.size());
        }
        while (!expected.isEmpty()) {
            assertEquals(expected.poll(), queue.poll());
        }
    }

    private static int[] drain(PriorityQueueCustom<Integer> heap) {
        int[] values = new int[heap.size()];
        for (int i = 0; i < values.length; i++) {
            values[i] = heap.poll();
        }
        return values;
    }
}
