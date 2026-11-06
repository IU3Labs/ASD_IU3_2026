package ru.bmstu.lab1.task_2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/** Проверяет решение задачи №2, включая граничные случаи. */
class Task02Test {
    @Test
    void shouldReturnFirstIndex() {
        assertEquals(1, Task02.firstIndex(new int[]{3, 7, 7, 2}, 7));
    }

    @Test
    void shouldReturnMinusOneWhenMissing() {
        assertEquals(-1, Task02.firstIndex(new int[]{1, 2, 3}, 5));
    }

    @Test
    void shouldFindAtBeginning() {
        assertEquals(0, Task02.firstIndex(new int[]{-2, 5, -2}, -2));
    }

    @Test
    void shouldFindAtEnd() {
        assertEquals(2, Task02.firstIndex(new int[]{1, 4, 8}, 8));
    }
}
