package ru.bmstu.lab02;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Task1BTest_PriorityQueue {
    @Test void shouldStartEmpty() {
        var queue = new Task1B_PriorityQueue<Integer>();
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
    }

    @Test void shouldReturnSmallestElement() {
        var queue = new Task1B_PriorityQueue<Integer>();
        queue.add(8); queue.add(2); queue.add(5);
        assertEquals(2, queue.peek().intValue());
    }

    @Test void shouldExtractInAscendingOrder() {
        var queue = new Task1B_PriorityQueue<Integer>();
        for (int value : new int[]{5, 1, 3, 2, 4}) queue.add(value);
        for (int i = 1; i <= 5; i++) assertEquals(i, queue.poll().intValue());
    }

    @Test void shouldKeepDuplicates() {
        var queue = new Task1B_PriorityQueue<Integer>();
        queue.add(3); queue.add(3); queue.add(1);
        assertEquals(1, queue.poll().intValue());
        assertEquals(3, queue.poll().intValue());
        assertEquals(3, queue.poll().intValue());
    }

    @Test void shouldHandleNegativeValues() {
        var queue = new Task1B_PriorityQueue<Integer>();
        queue.add(-1); queue.add(-5); queue.add(0);
        assertEquals(-5, queue.poll().intValue());
    }

    @Test void shouldHandleIntegerLimits() {
        var queue = new Task1B_PriorityQueue<Integer>();
        queue.add(Integer.MAX_VALUE); queue.add(Integer.MIN_VALUE);
        assertEquals(Integer.MIN_VALUE, queue.poll().intValue());
    }

    @Test void shouldResize() {
        var queue = new Task1B_PriorityQueue<Integer>();
        for (int i = 100; i >= 0; i--) queue.add(i);
        assertEquals(101, queue.size());
        for (int i = 0; i <= 100; i++) assertEquals(i, queue.poll().intValue());
    }

    @Test void shouldNotRemoveOnPeek() {
        var queue = new Task1B_PriorityQueue<Integer>();
        queue.add(7);
        assertEquals(7, queue.peek().intValue());
        assertEquals(1, queue.size());
    }

    @Test void shouldThrowOnEmptyPoll() {
        var queue = new Task1B_PriorityQueue<Integer>();
        assertThrows(java.util.NoSuchElementException.class, queue::poll);
    }

    @Test void shouldThrowOnEmptyPeek() {
        var queue = new Task1B_PriorityQueue<Integer>();
        assertThrows(java.util.NoSuchElementException.class, queue::peek);
    }

    @Test void shouldRejectNull() {
        var queue = new Task1B_PriorityQueue<Integer>();
        assertThrows(NullPointerException.class, () -> queue.add(null));
    }

    @Test void shouldBeEmptyAfterPollingEverything() {
        var queue = new Task1B_PriorityQueue<Integer>();
        queue.add(1); queue.poll();
        assertTrue(queue.isEmpty());
    }
    @Test void shouldWorkAsMaxHeap() {
        var queue = new Task1B_PriorityQueue<Integer>(java.util.Comparator.reverseOrder());
        queue.add(1); queue.add(9); queue.add(4);
        assertEquals(9, queue.poll().intValue());
        assertEquals(4, queue.poll().intValue());
        assertEquals(1, queue.poll().intValue());
    }

    @Test void shouldSupportCustomComparator() {
        var queue = new Task1B_PriorityQueue<String>(java.util.Comparator.comparingInt(String::length));
        queue.add("long"); queue.add("a"); queue.add("mid");
        assertEquals("a", queue.poll());
    }

}
