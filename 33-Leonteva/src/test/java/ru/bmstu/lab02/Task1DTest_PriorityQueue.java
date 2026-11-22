package ru.bmstu.lab02;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Task1DTest_PriorityQueue {
    @Test void shouldStartEmpty() {
        var queue = new Task1D_PriorityQueue<Integer>();
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
    }

    @Test void shouldReturnSmallestElement() {
        var queue = new Task1D_PriorityQueue<Integer>();
        queue.add(8); queue.add(2); queue.add(5);
        assertEquals(2, queue.peek().intValue());
    }

    @Test void shouldExtractInAscendingOrder() {
        var queue = new Task1D_PriorityQueue<Integer>();
        for (int value : new int[]{5, 1, 3, 2, 4}) queue.add(value);
        for (int i = 1; i <= 5; i++) assertEquals(i, queue.poll().intValue());
    }

    @Test void shouldKeepDuplicates() {
        var queue = new Task1D_PriorityQueue<Integer>();
        queue.add(3); queue.add(3); queue.add(1);
        assertEquals(1, queue.poll().intValue());
        assertEquals(3, queue.poll().intValue());
        assertEquals(3, queue.poll().intValue());
    }

    @Test void shouldHandleNegativeValues() {
        var queue = new Task1D_PriorityQueue<Integer>();
        queue.add(-1); queue.add(-5); queue.add(0);
        assertEquals(-5, queue.poll().intValue());
    }

    @Test void shouldHandleIntegerLimits() {
        var queue = new Task1D_PriorityQueue<Integer>();
        queue.add(Integer.MAX_VALUE); queue.add(Integer.MIN_VALUE);
        assertEquals(Integer.MIN_VALUE, queue.poll().intValue());
    }

    @Test void shouldResize() {
        var queue = new Task1D_PriorityQueue<Integer>();
        for (int i = 100; i >= 0; i--) queue.add(i);
        assertEquals(101, queue.size());
        for (int i = 0; i <= 100; i++) assertEquals(i, queue.poll().intValue());
    }

    @Test void shouldNotRemoveOnPeek() {
        var queue = new Task1D_PriorityQueue<Integer>();
        queue.add(7);
        assertEquals(7, queue.peek().intValue());
        assertEquals(1, queue.size());
    }

    @Test void shouldThrowOnEmptyPoll() {
        var queue = new Task1D_PriorityQueue<Integer>();
        assertThrows(java.util.NoSuchElementException.class, queue::poll);
    }

    @Test void shouldThrowOnEmptyPeek() {
        var queue = new Task1D_PriorityQueue<Integer>();
        assertThrows(java.util.NoSuchElementException.class, queue::peek);
    }

    @Test void shouldRejectNull() {
        var queue = new Task1D_PriorityQueue<Integer>();
        assertThrows(NullPointerException.class, () -> queue.add(null));
    }

    @Test void shouldBeEmptyAfterPollingEverything() {
        var queue = new Task1D_PriorityQueue<Integer>();
        queue.add(1); queue.poll();
        assertTrue(queue.isEmpty());
    }
    @Test void shouldBuildHeapFromArray() {
        var queue = new Task1D_PriorityQueue<>(new Integer[]{8, 3, 7, 1, 6});
        for (int value : new int[]{1, 3, 6, 7, 8}) assertEquals(value, queue.poll().intValue());
    }

    @Test void shouldBuildEmptyHeap() {
        var queue = new Task1D_PriorityQueue<>(new Integer[]{});
        assertTrue(queue.isEmpty());
    }

    @Test void shouldKeepSourceArrayUnchanged() {
        Integer[] values = {3, 2, 1};
        var queue = new Task1D_PriorityQueue<>(values);
        queue.poll();
        assertArrayEquals(new Integer[]{3, 2, 1}, values);
    }

    @Test void shouldBuildMaxHeap() {
        var queue = new Task1D_PriorityQueue<>(new Integer[]{1, 7, 3}, java.util.Comparator.reverseOrder());
        assertEquals(7, queue.poll().intValue());
    }

    @Test void shouldRejectNullArray() {
        assertThrows(NullPointerException.class, () -> new Task1D_PriorityQueue<Integer>((Integer[]) null));
    }

    @Test void shouldRejectNullArrayItem() {
        assertThrows(NullPointerException.class, () -> new Task1D_PriorityQueue<>(new Integer[]{1, null}));
    }

}
