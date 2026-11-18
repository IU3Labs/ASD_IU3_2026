package ru.bmstu.lab02.task_1b;

import org.junit.jupiter.api.Test;
import java.util.Comparator;
import static org.junit.jupiter.api.Assertions.*;

class PriorityQueueCustomTest {

    @Test
    void testMinHeapNaturalOrder() {
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>();
        pq.add(5);
        pq.add(1);
        pq.add(3);
        assertEquals(1, pq.poll());
        assertEquals(3, pq.poll());
        assertEquals(5, pq.poll());
    }

    @Test
    void testMaxHeapReverseOrder() {
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>(Comparator.reverseOrder());
        pq.add(5);
        pq.add(1);
        pq.add(3);
        assertEquals(5, pq.poll());
        assertEquals(3, pq.poll());
        assertEquals(1, pq.poll());
    }

    @Test
    void testStringsMinHeap() {
        PriorityQueueCustom<String> pq = new PriorityQueueCustom<>();
        pq.add("banana");
        pq.add("apple");
        pq.add("cherry");
        assertEquals("apple", pq.poll());
        assertEquals("banana", pq.poll());
        assertEquals("cherry", pq.poll());
    }

    @Test
    void testStringsMaxHeap() {
        PriorityQueueCustom<String> pq = new PriorityQueueCustom<>(Comparator.reverseOrder());
        pq.add("banana");
        pq.add("apple");
        pq.add("cherry");
        assertEquals("cherry", pq.poll());
        assertEquals("banana", pq.poll());
        assertEquals("apple", pq.poll());
    }

    @Test
    void testPeekAndSize() {
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>();
        pq.add(10);
        pq.add(20);
        assertEquals(10, pq.peek());
        assertEquals(2, pq.size());
    }

    @Test
    void testEmptyQueue() {
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>();
        assertTrue(pq.isEmpty());
        assertEquals(0, pq.size());
    }

    @Test
    void testDuplicates() {
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>();
        pq.add(2);
        pq.add(2);
        pq.add(1);
        assertEquals(1, pq.poll());
        assertEquals(2, pq.poll());
        assertEquals(2, pq.poll());
    }

    @Test
    void testCustomComparatorEvenFirst() {
        //кастомный компаратор: сначала четные, потом нечетные
        Comparator<Integer> evenFirst = (a, b) -> {
            if (a % 2 == 0 && b % 2 != 0) return -1;
            if (a % 2 != 0 && b % 2 == 0) return 1;
            return Integer.compare(a, b);
        };
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>(evenFirst);
        pq.add(3);
        pq.add(2);
        pq.add(1);
        pq.add(4);
        assertEquals(2, pq.poll());
        assertEquals(4, pq.poll());
        assertEquals(1, pq.poll());
        assertEquals(3, pq.poll());
    }
}