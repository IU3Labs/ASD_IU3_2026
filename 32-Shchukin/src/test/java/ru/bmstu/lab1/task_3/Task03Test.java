package ru.bmstu.lab1.task_3;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/** Проверяет решение задачи №3, включая граничные случаи. */
class Task03Test {
    @Test
    void shouldCountSeveralMatches() {
        assertEquals(3, Task03.countOccurrences(new int[]{2, 1, 2, 2}, 2));
    }

    @Test
    void shouldCountNone() {
        assertEquals(0, Task03.countOccurrences(new int[]{1, 3}, 2));
    }

    @Test
    void shouldCountAll() {
        assertEquals(3, Task03.countOccurrences(new int[]{-1, -1, -1}, -1));
    }

    @Test
    void shouldHandleZero() {
        assertEquals(2, Task03.countOccurrences(new int[]{0, 1, 0}, 0));
    }
}
