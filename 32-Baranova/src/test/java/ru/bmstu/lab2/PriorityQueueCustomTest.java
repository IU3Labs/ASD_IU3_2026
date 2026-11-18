package ru.bmstu.lab2;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PriorityQueueCustomTest {

    @Test
    void shouldBeEmptyAfterCreation() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();

        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
    }

    @Test
    void shouldAddElement() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(5);

        assertEquals(1, queue.size());
        assertFalse(queue.isEmpty());
    }

    @Test
    void shouldReturnMinimum() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(7);
        queue.add(2);
        queue.add(5);

        assertEquals(2, queue.peek());
    }

    @Test
    void shouldNotRemoveElementWhenPeeking() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(3);
        queue.peek();

        assertEquals(1, queue.size());
    }

    @Test
    void shouldPollInAscendingOrder() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(5);
        queue.add(1);
        queue.add(8);
        queue.add(3);

        assertEquals(1, queue.poll());
        assertEquals(3, queue.poll());
        assertEquals(5, queue.poll());
        assertEquals(8, queue.poll());
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldDecreaseSizeAfterPoll() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(4);
        queue.add(2);
        queue.poll();

        assertEquals(1, queue.size());
    }

    @Test
    void shouldThrowWhenPollingEmptyQueue() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();

        assertThrows(NoSuchElementException.class, queue::poll);
    }

    @Test
    void shouldReturnNullWhenPeekingEmptyQueue() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();

        assertNull(queue.peek());
    }

    @Test
    void shouldHandleDuplicates() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(3);
        queue.add(3);
        queue.add(1);

        assertEquals(1, queue.poll());
        assertEquals(3, queue.poll());
        assertEquals(3, queue.poll());
    }

    @Test
    void shouldHandleNegativeNumbers() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(5);
        queue.add(-2);
        queue.add(-7);

        assertEquals(-7, queue.poll());
        assertEquals(-2, queue.poll());
        assertEquals(5, queue.poll());
    }

    @Test
    void shouldGrowAutomatically() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();

        for (int i = 99; i >= 0; i--) {
            queue.add(i);
        }

        assertEquals(100, queue.size());

        for (int i = 0; i < 100; i++) {
            assertEquals(i, queue.poll());
        }
    }

    @Test
    void shouldHandleStrings() {
        PriorityQueueCustom<String> queue = new PriorityQueueCustom<>();
        queue.add("banana");
        queue.add("apple");
        queue.add("cherry");

        assertEquals("apple", queue.poll());
        assertEquals("banana", queue.poll());
        assertEquals("cherry", queue.poll());
    }

    @Test
    void shouldRemoveRoot() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(5);
        queue.add(1);
        queue.add(3);

        assertTrue(queue.remove(1));
        assertEquals(3, queue.peek());
        assertEquals(2, queue.size());
    }

    @Test
    void shouldRemoveMiddleElement() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(1);
        queue.add(3);
        queue.add(5);
        queue.add(7);
        queue.add(9);

        assertTrue(queue.remove(3));
        assertEquals(1, queue.poll());
        assertEquals(5, queue.poll());
        assertEquals(7, queue.poll());
        assertEquals(9, queue.poll());
    }

    @Test
    void shouldNotRemoveMissingElement() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(5);

        assertFalse(queue.remove(10));
        assertEquals(1, queue.size());
    }

    @Test
    void shouldNotRemoveFromEmptyQueue() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();

        assertFalse(queue.remove(5));
    }

    @Test
    void shouldRemoveOnlyOneDuplicate() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(3);
        queue.add(3);

        assertTrue(queue.remove(3));
        assertEquals(1, queue.size());
        assertEquals(3, queue.peek());
    }

    @Test
    void shouldRemoveLastElement() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(1);
        queue.add(2);
        queue.add(3);

        assertTrue(queue.remove(3));
        assertEquals(2, queue.size());
        assertEquals(1, queue.poll());
        assertEquals(2, queue.poll());
    }

    @Test
    void shouldRemoveSingleElement() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(5);

        assertTrue(queue.remove(5));
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldSiftUpAfterRemoval() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(1);
        queue.add(5);
        queue.add(2);
        queue.add(7);
        queue.add(8);
        queue.add(3);
        queue.add(4);

        assertTrue(queue.remove(7));
        assertEquals(1, queue.poll());
        assertEquals(2, queue.poll());
        assertEquals(3, queue.poll());
        assertEquals(4, queue.poll());
        assertEquals(5, queue.poll());
        assertEquals(8, queue.poll());
    }

    @Test
    void shouldBuildHeapFromArray() {
        Integer[] array = {9, 5, 7, 1, 3};
        PriorityQueueCustom<Integer> queue =
                new PriorityQueueCustom<>(array);

        assertEquals(5, queue.size());
        assertEquals(1, queue.poll());
        assertEquals(3, queue.poll());
        assertEquals(5, queue.poll());
        assertEquals(7, queue.poll());
        assertEquals(9, queue.poll());
    }

    @Test
    void shouldBuildHeapFromEmptyArray() {
        Integer[] array = {};
        PriorityQueueCustom<Integer> queue =
                new PriorityQueueCustom<>(array);

        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
    }

    @Test
    void shouldBuildHeapFromSingleElement() {
        Integer[] array = {5};
        PriorityQueueCustom<Integer> queue =
                new PriorityQueueCustom<>(array);

        assertEquals(5, queue.poll());
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldBuildHeapWithDuplicates() {
        Integer[] array = {3, 1, 3, 1};
        PriorityQueueCustom<Integer> queue =
                new PriorityQueueCustom<>(array);

        assertEquals(1, queue.poll());
        assertEquals(1, queue.poll());
        assertEquals(3, queue.poll());
        assertEquals(3, queue.poll());
    }

    @Test
    void shouldAddAfterBuildingHeap() {
        Integer[] array = {5, 3, 7};
        PriorityQueueCustom<Integer> queue =
                new PriorityQueueCustom<>(array);

        queue.add(1);

        assertEquals(4, queue.size());
        assertEquals(1, queue.peek());
    }

    @Test
    void shouldNotModifyOriginalArray() {
        Integer[] array = {9, 3, 1};
        PriorityQueueCustom<Integer> queue =
                new PriorityQueueCustom<>(array);

        assertEquals(1, queue.peek());
        assertEquals(9, array[0]);
        assertEquals(3, array[1]);
        assertEquals(1, array[2]);
    }
}