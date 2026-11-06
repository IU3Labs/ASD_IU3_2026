package ru.bmstu.lab1.task_5;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/** Проверяет решение задачи №5, включая граничные случаи. */
class Task05Test {
    @Test
    void shouldSumOnlyEvenIndices() {
        assertEquals(9L, Task05.sumEvenPositions(new int[]{1, 20, 3, 30, 5}));
    }

    @Test
    void shouldHandleNegativeValues() {
        assertEquals(-5L, Task05.sumEvenPositions(new int[]{-2, 100, -3}));
    }

    @Test
    void shouldHandleZeroValues() {
        assertEquals(0L, Task05.sumEvenPositions(new int[]{0, 2, 0, 4}));
    }

    @Test
    void shouldAvoidOverflow() {
        assertEquals(4294967294L,
                        Task05.sumEvenPositions(new int[]{Integer.MAX_VALUE, 1, Integer.MAX_VALUE}));
    }
}
