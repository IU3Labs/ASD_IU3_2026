package ru.bmstu.lab01.task_25;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Task25Test {

    @Test
    void shouldCalculateSumOfPositiveNumbers() {
        int[] numbers = {1, 2, 3, 4};
        long actualSum = Task25.calculateSum(numbers);
        assertEquals(10L, actualSum);
    }

    @Test
    void shouldCalculateSumOfPositiveAndNegativeNumbers() {
        int[] numbers = {-5, 10, -3, 2};
        long actualSum = Task25.calculateSum(numbers);
        assertEquals(4L, actualSum);
    }

    @Test
    void shouldCalculateZeroForZeroElements() {
        int[] numbers = {0, 0, 0};
        long actualSum = Task25.calculateSum(numbers);
        assertEquals(0L, actualSum);
    }

    @Test
    void shouldCalculateSumLargerThanIntMaximum() {
        int[] numbers = {2_000_000_000, 2_000_000_000};
        long actualSum = Task25.calculateSum(numbers);
        assertEquals(4_000_000_000L, actualSum);
    }
}
