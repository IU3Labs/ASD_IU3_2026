package ru.bmstu.lab1.task_1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/** Проверяет решение задачи №1, включая граничные случаи. */
class Task01Test {
    @Test
    void shouldFindMinAndMax() {
        assertArrayEquals(new int[]{-8, 9}, Task01.findMinMax(new int[]{2, -8, 9, 1}));
    }

    @Test
    void shouldHandleEqualValues() {
        assertArrayEquals(new int[]{7, 7}, Task01.findMinMax(new int[]{7, 7, 7}));
    }

    @Test
    void shouldHandleAllNegativeValues() {
        assertArrayEquals(new int[]{-10, -1}, Task01.findMinMax(new int[]{-10, -3, -1}));
    }

    @Test
    void shouldHandleIntExtremes() {
        assertArrayEquals(new int[]{Integer.MIN_VALUE, Integer.MAX_VALUE},
                        Task01.findMinMax(new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE}));
    }
}
