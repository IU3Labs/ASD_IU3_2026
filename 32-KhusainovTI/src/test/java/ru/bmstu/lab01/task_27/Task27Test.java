package ru.bmstu.lab01.task_27;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Task27Test {

    @Test
    void shouldCalculateSumOfSquares() {
        int[] numbers = {1, 2, 3};
        long actualSum = Task27.calculateSumOfSquares(numbers);
        assertEquals(14L, actualSum);
    }

    @Test
    void shouldWorkWithNegativeNumbers() {
        int[] numbers = {-2, -3};
        long actualSum = Task27.calculateSumOfSquares(numbers);
        assertEquals(13L, actualSum);
    }

    @Test
    void shouldWorkWithZeros() {
        int[] numbers = {0, 0, 0};
        long actualSum = Task27.calculateSumOfSquares(numbers);
        assertEquals(0L, actualSum);
    }
}
