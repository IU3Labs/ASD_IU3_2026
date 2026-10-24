package ru.bmstu.lab01.task_36;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class Task36Test {

    @Test
    void shouldCalculateRunningSum() {
        int[] numbers = {1, 2, 3, 4};
        long[] actualResult = Task36.calculateRunningSum(numbers);
        assertArrayEquals(new long[]{1, 3, 6, 10}, actualResult);
    }

    @Test
    void shouldWorkWithNegativeNumbers() {
        int[] numbers = {5, -2, -4, 3};
        long[] actualResult = Task36.calculateRunningSum(numbers);
        assertArrayEquals(new long[]{5, 3, -1, 2}, actualResult);
    }

    @Test
    void shouldWorkWithZeros() {
        int[] numbers = {0, 0, 0};
        long[] actualResult = Task36.calculateRunningSum(numbers);
        assertArrayEquals(new long[]{0, 0, 0}, actualResult);
    }
}
