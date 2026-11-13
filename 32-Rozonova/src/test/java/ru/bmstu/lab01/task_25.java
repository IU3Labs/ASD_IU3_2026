package ru.bmstu.lab1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class Task25SumOfElementsTest {

    @Test
    void should_return_correct_sum_for_standard_array() {
        int[] array = {1, 2, 3, 4, 5};
        long result = Task25SumOfElements.sumElements(array);
        assertEquals(15, result);
    }

    @Test
    void should_handle_negative_numbers() {
        int[] array = {-1, -2, 5, 10};
        long result = Task25SumOfElements.sumElements(array);
        assertEquals(12, result);
    }

    @Test
    void should_return_zero_for_empty_array() {
        int[] array = {};
        long result = Task25SumOfElements.sumElements(array);
        assertEquals(0, result);
    }

    @Test
    void should_handle_single_element_array() {
        int[] array = {42};
        long result = Task25SumOfElements.sumElements(array);
        assertEquals(42, result);
    }
}