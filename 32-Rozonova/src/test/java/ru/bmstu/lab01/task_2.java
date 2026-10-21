package ru.bmstu.lab1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for Task2FirstIndex implementation.
 */
class Task2FirstIndexTest {

    @Test
    void should_return_first_index_when_element_exists_multiple_times() {
        int[] array = {10, 20, 30, 20, 40};
        int result = Task2FirstIndex.findFirstIndex(array, 20);
        assertEquals(1, result); // First occurrence of 20 is at index 1
    }

    @Test
    void should_return_minus_one_when_element_not_found() {
        int[] array = {1, 2, 3, 4, 5};
        int result = Task2FirstIndex.findFirstIndex(array, 99);
        assertEquals(-1, result);
    }

    @Test
    void should_return_zero_when_element_is_at_first_position() {
        int[] array = {7, 8, 9};
        int result = Task2FirstIndex.findFirstIndex(array, 7);
        assertEquals(0, result);
    }

    @Test
    void should_return_minus_one_for_empty_array() {
        int[] array = {};
        int result = Task2FirstIndex.findFirstIndex(array, 5);
        assertEquals(-1, result);
    }
}