package ru.bmstu.lab1.task_54;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/** Проверяет решение задачи №54, включая граничные случаи. */
class Task54Test {
    @Test
    void shouldSumMainDiagonal() {
        assertEquals(15L, Task54.diagonalSum(new int[][]{
                        {1, 2, 3}, {4, 5, 6}, {7, 8, 9}}));
    }

    @Test
    void shouldIgnoreOffDiagonal() {
        assertEquals(0L, Task54.diagonalSum(new int[][]{
                        {0, 5}, {5, 0}}));
    }

    @Test
    void shouldHandleNegativeEntries() {
        assertEquals(-4L, Task54.diagonalSum(new int[][]{
                        {-1, 100}, {200, -3}}));
    }

    @Test
    void shouldHandleLargeElements() {
        assertEquals(4294967294L, Task54.diagonalSum(new int[][]{
                        {Integer.MAX_VALUE, 0}, {0, Integer.MAX_VALUE}}));
    }
}
