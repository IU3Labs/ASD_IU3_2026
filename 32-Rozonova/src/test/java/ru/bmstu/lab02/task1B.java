package ru.bmstu.lab2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Comparator;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for PriorityQueueCustomComparator in Min-Heap and Max-Heap modes.
 */
class PriorityQueueCustomComparatorTest {

    @Test
    void should_work_as_min_heap_by_default() {
        PriorityQueueCustomComparator<Integer> pq = new PriorityQueueCustomComparator<>();
        pq.add(10);
        pq.add(5);
        pq.add(20);
        pq.add(1);

        assertEquals(1, pq.poll());
        assertEquals(5, pq.poll());
        assertEquals(10, pq.poll());
        assertEquals(20, pq.poll());
        assertTrue(pq.isEmpty());
    }

    @Test
    void should_work_as_max_heap_with_reverse_comparator() {
        PriorityQueueCustomComparator<Integer> pq = new PriorityQueueCustomComparator<>(Comparator.reverseOrder());
        pq.add(10);
        pq.add(5);
        pq.add(20);
        pq.add(1);

        assertEquals(20, pq.poll());
        assertEquals(10, pq.poll());
        assertEquals(5, pq.poll());
        assertEquals(1, pq.poll());
        assertTrue(pq.isEmpty());
    }

    @Test
    void should_work_with_custom_object_comparator() {
        Comparator<String> lengthComparator = Comparator.comparingInt(String::length);
        PriorityQueueCustomComparator<String> pq = new PriorityQueueCustomComparator<>(lengthComparator);

        pq.add("apple");
        pq.add("a");
        pq.add("banana");

        assertEquals("a", pq.poll());      // Длина 1
        assertEquals("apple", pq.poll());  // Длина 5
        assertEquals("banana", pq.poll()); // Длина 6
    }

    @Test
    void should_handle_empty_and_single_element_cases() {
        PriorityQueueCustomComparator<Integer> pq = new PriorityQueueCustomComparator<>(Comparator.naturalOrder());
        assertTrue(pq.isEmpty());
        pq.add(42);
        assertFalse(pq.isEmpty());
        assertEquals(42, pq.peek());
        assertEquals(42, pq.poll());
        assertTrue(pq.isEmpty());
    }
}