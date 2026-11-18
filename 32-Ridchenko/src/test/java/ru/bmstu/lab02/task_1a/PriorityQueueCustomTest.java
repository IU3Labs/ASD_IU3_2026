package ru.bmstu.lab02.task_1a;

import org.junit.jupiter.api.Test;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;

class PriorityQueueCustomTest {

    @Test
    void testAddAndPollOrder() {
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>();
        pq.add(5);
        pq.add(3);
        pq.add(8);
        pq.add(1);
        pq.add(4);
        assertEquals(1, pq.poll());
        assertEquals(3, pq.poll());
        assertEquals(4, pq.poll());
        assertEquals(5, pq.poll());
        assertEquals(8, pq.poll());
    }

    @Test
    void testPeek() {
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>();
        pq.add(10);
        pq.add(2);
        assertEquals(2, pq.peek());
        assertEquals(2, pq.size());
    }

    @Test
    void testSizeAndIsEmpty() {
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>();
        assertTrue(pq.isEmpty());
        assertEquals(0, pq.size());
        pq.add(1);
        assertFalse(pq.isEmpty());
        assertEquals(1, pq.size());
    }

    @Test
    void testPollFromEmptyThrows() {
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>();
        assertThrows(NoSuchElementException.class, pq::poll);
    }

    @Test
    void testPeekFromEmptyThrows() {
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>();
        assertThrows(NoSuchElementException.class, pq::peek);
    }

    @Test
    void testGrowBeyondDefaultCapacity() {
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>();
        for (int i = 100; i > 0; i--) pq.add(i);
        assertEquals(100, pq.size());
        for (int i = 1; i <= 100; i++) assertEquals(i, pq.poll());
    }

    @Test
    void testSingleElement() {
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>();
        pq.add(42);
        assertEquals(42, pq.peek());
        assertEquals(42, pq.poll());
        assertTrue(pq.isEmpty());
    }

    @Test
    void testDuplicateElements() {
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>();
        pq.add(2);
        pq.add(2);
        pq.add(2);
        assertEquals(2, pq.poll());
        assertEquals(2, pq.poll());
        assertEquals(2, pq.poll());
    }

    @Test
    void testNegativeNumbers() {
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>();
        pq.add(-5);
        pq.add(0);
        pq.add(-10);
        assertEquals(-10, pq.poll());
        assertEquals(-5, pq.poll());
        assertEquals(0, pq.poll());
    }

    @Test
    void testStringsNaturalOrder() {
        PriorityQueueCustom<String> pq = new PriorityQueueCustom<>();
        pq.add("banana");
        pq.add("apple");
        pq.add("cherry");
        assertEquals("apple", pq.poll());
        assertEquals("banana", pq.poll());
        assertEquals("cherry", pq.poll());
    }

    @Test
    void testNullAddThrows() {
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>();
        assertThrows(NullPointerException.class, () -> pq.add(null));
    }

    @Test
    void testManyElementsStress() {
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>();
        int n = 10_000;
        for (int i = n; i > 0; i--) pq.add(i);
        for (int i = 1; i <= n; i++) assertEquals(i, pq.poll());
    }
}