package ru.bmstu.lab2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for PriorityQueueWithRemove implementation.
 */
class PriorityQueueWithRemoveTest {

    private PriorityQueueWithRemove<Integer> pq;

    @BeforeEach
    void setUp() {
        pq = new PriorityQueueWithRemove<>();
    }

    @Test
    void should_remove_root_element() {
        pq.add(10);
        pq.add(20);
        pq.add(30);

        assertTrue(pq.remove(10)); // Удаление корня
        assertEquals(2, pq.size());
        assertEquals(20, pq.peek());
    }

    @Test
    void should_remove_middle_element_and_restore_heap() {
        pq.add(5);
        pq.add(15);
        pq.add(10);
        pq.add(25);
        pq.add(20);

        assertTrue(pq.remove(15)); // Удаление элемента из середины кучи
        assertEquals(4, pq.size());

        // Проверяем корректность восстановления кучи через последовательное извлечение
        assertEquals(5, pq.poll());
        assertEquals(10, pq.poll());
        assertEquals(20, pq.poll());
        assertEquals(25, pq.poll());
        assertTrue(pq.isEmpty());
    }

    @Test
    void should_return_false_when_removing_non_existent_element() {
        pq.add(10);
        pq.add(20);

        assertFalse(pq.remove(99)); // Удаление несуществующего элемента
        assertEquals(2, pq.size());
        assertEquals(10, pq.peek());
    }

    @Test
    void should_return_false_when_removing_from_empty_queue() {
        assertFalse(pq.remove(10)); // Удаление из пустой очереди
        assertTrue(pq.isEmpty());
    }
}