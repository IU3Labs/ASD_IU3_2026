package ru.bmstu.lab1.task_11;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/** Проверяет решение задачи №11, включая граничные случаи. */
class Task11Test {
    @Test
    void shouldChooseClosestToFractionalMean() {
        assertEquals(3, Task11.closestToAverage(new int[]{1, 3, 4}));
    }

    @Test
    void shouldChooseFirstOnTie() {
        assertEquals(0, Task11.closestToAverage(new int[]{0, 10}));
    }

    @Test
    void shouldHandleNegativeValues() {
        assertEquals(-3, Task11.closestToAverage(new int[]{-5, -3, 2}));
    }

    @Test
    void shouldAvoidIntegerOverflow() {
        assertEquals(Integer.MAX_VALUE, Task11.closestToAverage(
                        new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE}));
    }
}
