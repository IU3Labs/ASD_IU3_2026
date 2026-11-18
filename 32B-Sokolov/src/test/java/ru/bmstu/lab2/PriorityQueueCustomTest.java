package ru.bmstu.lab2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import org.junit.jupiter.api.Test;

/** Проверки порядка, роста массива и восстановления кучи. */
class PriorityQueueCustomTest {
    private final PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();

    private static <T> List<T> drain(PriorityQueueCustom<T> heap) {
        List<T> items = new ArrayList<>();
        while (!heap.isEmpty()) {
            items.add(heap.poll());
        }
        return items;
    }

    @Test
    void shouldStartEmpty() {
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
        assertNull(queue.peek());
    }

    @Test
    void shouldThrowOnEmptyPoll() {
        assertThrows(NoSuchElementException.class, queue::poll);
    }

    @Test
    void shouldPeekWithoutRemoving() {
        queue.add(8);
        queue.add(2);
        assertEquals(2, queue.peek());
        assertEquals(2, queue.size());
        assertEquals(2, queue.poll());
    }

    @Test
    void shouldGrowAndKeepOrder() {
        for (int i = 100; i >= 0; i--) {
            queue.add(i);
        }
        for (int i = 0; i <= 100; i++) {
            assertEquals(i, queue.poll());
        }
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldUseReverseOrder() {
        PriorityQueueCustom<Integer> max = new PriorityQueueCustom<>(Comparator.reverseOrder());
        List.of(3, 1, 5, 3).forEach(max::add);
        assertEquals(List.of(5, 3, 3, 1), drain(max));
    }

    @Test
    void shouldRemoveRoot() {
        List.of(4, 1, 7, 3).forEach(queue::add);
        assertTrue(queue.remove(1));
        assertEquals(List.of(3, 4, 7), drain(queue));
    }

    @Test
    void shouldMoveReplacementUp() {
        List.of(1, 10, 2, 11, 12, 3, 4).forEach(queue::add);
        assertTrue(queue.remove(11));
        assertEquals(List.of(1, 2, 3, 4, 10, 12), drain(queue));
    }

    @Test
    void shouldMoveReplacementDown() {
        List.of(1, 2, 3, 4, 5, 6, 7).forEach(queue::add);
        assertTrue(queue.remove(2));
        assertEquals(List.of(1, 3, 4, 5, 6, 7), drain(queue));
    }

    @Test
    void shouldKeepOtherDuplicates() {
        List.of(2, 1, 2).forEach(queue::add);
        assertTrue(queue.remove(2));
        assertEquals(List.of(1, 2), drain(queue));
    }

    @Test
    void shouldIgnoreMissingValue() {
        queue.add(3);
        assertFalse(queue.remove(4));
        assertEquals(3, queue.peek());
    }

    @Test
    void shouldHandleRemovalFromEmptyQueue() {
        assertFalse(queue.remove(0));
    }

    @Test
    void shouldBuildFromIndependentCopy() {
        Integer[] input = {8, 2, 7, 2, -4};
        PriorityQueueCustom<Integer> heap = new PriorityQueueCustom<>(input);
        assertEquals(List.of(8, 2, 7, 2, -4), List.of(input));
        input[0] = 100;
        assertEquals(List.of(-4, 2, 2, 7, 8), drain(heap));
    }

    @Test
    void shouldGrowFromEmptyArray() {
        PriorityQueueCustom<String> heap = new PriorityQueueCustom<>(new String[0]);
        heap.add("abc");
        assertEquals("abc", heap.poll());
    }

    @Test
    void shouldRejectNullElements() {
        assertThrows(NullPointerException.class, () -> queue.add(null));
        assertThrows(NullPointerException.class, () -> new PriorityQueueCustom<>(new Integer[] {1, null}));
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldMatchSortingForRandomInput() {
        List<Integer> input = new Random(42).ints(5000, -100, 100).boxed().toList();
        input.forEach(queue::add);
        assertEquals(input.stream().sorted().toList(), drain(queue));
    }
}
