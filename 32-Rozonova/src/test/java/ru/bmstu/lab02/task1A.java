package ru.bmstu.lab2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for PriorityQueueCustom implementation.
 */
class PriorityQueueCustomTest {

    private PriorityQueueCustom<Integer> pq;

    @BeforeEach
    void setUp() {
        pq = new PriorityQueueCustom<>();
    }

    @Test
    void should_be_empty_initially() {
        assertTrue(pq.isEmpty());
        assertEquals(0, pq.size());
    }

    @Test
    void should_add_and_peek_elements() {
        pq.add(10);
        pq.add(5);
        pq.add(20);

        assertFalse(pq.isEmpty());
        assertEquals(3, pq.size());
        assertEquals(5, pq.peek()); // Minimum element
    }

    @Test
    void should_poll_elements_in_non_decreasing_order() {
        pq.add(15);
        pq.add(3);
        pq.add(10);
        pq.add(1);
        pq.add(8);

        assertEquals(5, pq.size());
        assertEquals(1, pq.poll());
        assertEquals(3, pq.poll());
        assertEquals(8, pq.poll());
        assertEquals(10, pq.poll());
        assertEquals(15, pq.poll());
        assertTrue(pq.isEmpty());
    }

    @Test
    void should_handle_duplicates() {
        pq.add(5);
        pq.add(2);
        pq.add(5);
        pq.add(1);

        assertEquals(1, pq.poll());
        assertEquals(2, pq.poll());
        assertEquals(5, pq.poll());
        assertEquals(5, pq.poll());
        assertTrue(pq.isEmpty());
    }

    @Test
    void should_handle_negative_numbers() {
        pq.add(-5);
        pq.add(0);
        pq.add(-10);
        pq.add(3);

        assertEquals(-10, pq.poll());
        assertEquals(-5, pq.poll());
        assertEquals(0, pq.poll());
        assertEquals(3, pq.poll());
    }

    @Test
    void should_throw_exception_on_peek_when_empty() {
        assertThrows(NoSuchElementException.class, () -> pq.peek());
    }

    @Test
    void should_throw_exception_on_poll_when_empty() {
        assertThrows(NoSuchElementException.class, () -> pq.poll());
    }

    @Test
    void should_throw_exception_on_add_null() {
        assertThrows(IllegalArgumentException.class, () -> pq.add(null));
    }

    @Test
    void should_resize_automatically_on_many_elements() {
        int count = 100;
        for (int i = count; i > 0; i--) {
            pq.add(i);
        }

        assertEquals(count, pq.size());
        for (int i = 1; i <= count; i++) {
            assertEquals(i, pq.poll());
        }
        assertTrue(pq.isEmpty());
    }

    @Test
    void should_handle_single_element() {
        pq.add(42);
        assertEquals(1, pq.size());
        assertEquals(42, pq.peek());
        assertEquals(42, pq.poll());
        assertTrue(pq.isEmpty());
    }

    @Test
    void should_handle_reverse_sorted_input() {
        for (int i = 50; i >= 1; i--) {
            pq.add(i);
        }
        for (int i = 1; i <= 50; i++) {
            assertEquals(i, pq.poll());
        }
    }
}