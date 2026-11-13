package ru.bmstu.lab1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class Task27SumOfSquaresTest {

    @Test
    void should_return_correct_sum_of_squares_for_standard_array() {
        int[] array = {1, 2, 3}; // 1^2 + 2^2 + 3^2 = 1 + 4 + 9 = 14
        long result = Task27SumOfSquares.sumOfSquares(array);
        assertEquals(14, result);
    }

    @Test
    void should_handle_negative_numbers() {
        int[] array = {-2, 3, -4}; // (-2)^2 + 3^2 + (-4)^2 = 4 + 9 + 16 = 29
        long result = Task27SumOfSquares.sumOfSquares(array);
        assertEquals(29, result);
    }

    @Test
    void should_return_zero_for_empty_array() {
        int[] array = {};
        long result = Task27SumOfSquares.sumOfSquares(array);
        assertEquals(0, result);
    }

    @Test
    void should_handle_single_element_array() {
        int[] array = {5}; // 5^2 = 25
        long result = Task27SumOfSquares.sumOfSquares(array);
        assertEquals(25, result);
    }
}