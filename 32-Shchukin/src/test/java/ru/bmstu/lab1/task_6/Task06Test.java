package ru.bmstu.lab1.task_6;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/** Проверяет решение задачи №6, включая граничные случаи. */
class Task06Test {
    @Test
    void shouldCountAboveIntegerMean() {
        assertEquals(1, Task06.countAboveAverage(new int[]{1, 2, 3, 6}));
    }

    @Test
    void shouldReturnZeroWhenEqual() {
        assertEquals(0, Task06.countAboveAverage(new int[]{5, 5, 5}));
    }

    @Test
    void shouldHandleNegativeMean() {
        assertEquals(2, Task06.countAboveAverage(new int[]{-6, -3, -3}));
    }

    @Test
    void shouldHandleLargeSum() {
        assertEquals(0, Task06.countAboveAverage(
                        new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE}));
    }
}
