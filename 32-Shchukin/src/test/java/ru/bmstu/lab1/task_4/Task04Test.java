package ru.bmstu.lab1.task_4;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/** Проверяет решение задачи №4, включая граничные случаи. */
class Task04Test {
    @Test
    void shouldReturnOrderedIndices() {
        assertArrayEquals(new int[]{0, 2, 4}, Task04.allIndices(new int[]{4, 1, 4, 2, 4}, 4));
    }

    @Test
    void shouldReturnEmptyWhenMissing() {
        assertArrayEquals(new int[]{}, Task04.allIndices(new int[]{1, 2}, 3));
    }

    @Test
    void shouldReturnAllPositions() {
        assertArrayEquals(new int[]{0, 1, 2}, Task04.allIndices(new int[]{7, 7, 7}, 7));
    }

    @Test
    void shouldFindSingleMatch() {
        assertArrayEquals(new int[]{1}, Task04.allIndices(new int[]{1, -4, 3}, -4));
    }
}
