package ru.bmstu.lab1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for Task1MaxMin implementation.
 */
class Task1MaxMinTest {

    @Test
    void should_return_correct_min_and_max_for_standard_array() {
        int[] array = {5, 2, 9, 1, 7, 6};
        Task1MaxMin.Result result = Task1MaxMin.findMinMax(array);
        assertEquals(1, result.getMin());
        assertEquals(9, result.getMax());
    }

    @Test
    void should_handle_single_element_array() {
        int[] array = {42};
        Task1MaxMin.Result result = Task1MaxMin.findMinMax(array);
        assertEquals(42, result.getMin());
        assertEquals(42, result.getMax());
    }

    @Test
    void should_throw_exception_when_array_is_empty() {
        int[] array = {};
        assertThrows(IllegalArgumentException.class, () -> Task1MaxMin.findMinMax(array));
    }
}