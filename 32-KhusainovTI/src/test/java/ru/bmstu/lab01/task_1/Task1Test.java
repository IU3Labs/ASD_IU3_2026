package ru.bmstu.lab01.task_1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class Task1Test {

    @Test
    void shouldFindMinimumAndMaximum() {
        int[] numbers = {4, 2, 9, 1, 6};
        int[] actualResult = Task1.findMinAndMax(numbers);
        assertArrayEquals(new int[]{1, 9}, actualResult);
    }

    @Test
    void shouldWorkWithNegativeNumbers() {
        int[] numbers = {-8, -3, -10, -5};
        int[] actualResult = Task1.findMinAndMax(numbers);
        assertArrayEquals(new int[]{-10, -3}, actualResult);
    }

    @Test
    void shouldWorkWhenNumbersAreEqual() {
        int[] numbers = {7, 7, 7};
        int[] actualResult = Task1.findMinAndMax(numbers);
        assertArrayEquals(new int[]{7, 7}, actualResult);
    }
}
