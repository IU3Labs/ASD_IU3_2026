package ru.bmstu.lab02.task_1d;

import org.junit.jupiter.api.Test;
import java.util.Comparator;
import static org.junit.jupiter.api.Assertions.*;

class PriorityQueueCustomTest {

    @Test
    void testConstructorBuildsMinHeap() {
        Integer[] arr = {5, 3, 8, 1, 4};
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>(arr);
        assertEquals(5, pq.size());
        assertEquals(1, pq.poll());
        assertEquals(3, pq.poll());
        assertEquals(4, pq.poll());
        assertEquals(5, pq.poll());
        assertEquals(8, pq.poll());
    }

    @Test
    void testConstructorBuildsMaxHeap() {
        Integer[] arr = {5, 3, 8, 1, 4};
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>(arr, Comparator.reverseOrder());
        assertEquals(8, pq.poll());
        assertEquals(5, pq.poll());
        assertEquals(4, pq.poll());
        assertEquals(3, pq.poll());
        assertEquals(1, pq.poll());
    }

    @Test
    void testConstructorEmptyArray() {
        Integer[] arr = {};
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>(arr);
        assertTrue(pq.isEmpty());
    }

    @Test
    void testConstructorSingleElement() {
        Integer[] arr = {42};
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>(arr);
        assertEquals(42, pq.poll());
        assertTrue(pq.isEmpty());
    }

    @Test
    void testConstructorAlreadyHeap() {
        Integer[] arr = {1, 3, 5, 4, 8};
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>(arr);
        assertEquals(1, pq.poll());
        assertEquals(3, pq.poll());
        assertEquals(4, pq.poll());
        assertEquals(5, pq.poll());
        assertEquals(8, pq.poll());
    }

    @Test
    void testConstructorReverseSortedArray() {
        Integer[] arr = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>(arr);
        for (int i = 1; i <= 10; i++) {
            assertEquals(i, pq.poll());
        }
    }

    @Test
    void testConstructorWithDuplicates() {
        Integer[] arr = {2, 2, 1, 1, 3};
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>(arr);
        assertEquals(1, pq.poll());
        assertEquals(1, pq.poll());
        assertEquals(2, pq.poll());
        assertEquals(2, pq.poll());
        assertEquals(3, pq.poll());
    }

    @Test
    void testAddAfterConstructor() {
        Integer[] arr = {5, 3, 8};
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>(arr);
        pq.add(1);
        assertEquals(1, pq.poll());
        assertEquals(3, pq.poll());
    }
}