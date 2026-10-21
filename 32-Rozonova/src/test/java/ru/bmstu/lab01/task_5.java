package ru.bmstu.lab1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class Task5EvenPositionSumTest {

    @Test
    void should_return_correct_sum_for_standard_array() {
        int[] array = {1, 2, 3, 4, 5}; // Even indices: 0(1), 2(3), 4(5) -> sum = 1 + 3 + 5 = 9
        long result = Task5EvenPositionSum.sumEvenPositions(array);
        assertEquals(9, result);
    }

    @Test
    void should_return_zero_for_empty_array() {
        int[] array = {};
        long result = Task5EvenPositionSum.sumEvenPositions(array);
        assertEquals(0, result);
    }

    @Test
    void should_return_single_element_for_single_element_array() {
        int[] array = {42}; // Index 0
        long result = Task5EvenPositionSum.sumEvenPositions(array);
        assertEquals(42, result);
    }

    @Test
    void should_handle_array_with_even_length() {
        int[] array = {10, 20, 30, 40}; // Even indices: 0(10), 2(30) -> sum = 40
        long result = Task5EvenPositionSum.sumEvenPositions(array);
        assertEquals(40, result);
    }
}