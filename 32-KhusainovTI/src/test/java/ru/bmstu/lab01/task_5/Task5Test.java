package ru.bmstu.lab01.task_5;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Task5Test {

    @Test
    void shouldCalculateSumAtEvenIndexes() {
        int[] numbers = {5, 10, 3, 8, 2};
        long actualSum = Task5.calculateSumAtEvenIndexes(numbers);
        assertEquals(10L, actualSum);
    }

    @Test
    void shouldWorkWithNegativeNumbers() {
        int[] numbers = {-2, 7, -3, 4};
        long actualSum = Task5.calculateSumAtEvenIndexes(numbers);
        assertEquals(-5L, actualSum);
    }

    @Test
    void shouldIgnoreNumbersAtOddIndexes() {
        int[] numbers = {0, 100, 0, 200};
        long actualSum = Task5.calculateSumAtEvenIndexes(numbers);
        assertEquals(0L, actualSum);
    }
}
