package ru.bmstu.lab01.task_49;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab01.common.TestIo;

/**
 * Тесты задачи 49: суммы строк матрицы.
 */
class Task49Test {

    @Test
    void shouldSumEachRow() {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}};

        assertArrayEquals(new long[] {6, 15}, Task49.rowSums(matrix));
    }

    @Test
    void shouldHandleSingleColumn() {
        int[][] matrix = {{7}, {-2}, {0}};

        assertArrayEquals(new long[] {7, -2, 0}, Task49.rowSums(matrix));
    }

    @Test
    void shouldHandleSingleRow() {
        assertArrayEquals(new long[] {10}, Task49.rowSums(new int[][] {{1, 2, 3, 4}}));
    }

    @Test
    void shouldHandleNegativeNumbers() {
        int[][] matrix = {{-1, -2}, {3, -3}};

        assertArrayEquals(new long[] {-3, 0}, Task49.rowSums(matrix));
    }

    @Test
    void shouldNotOverflowIntRange() {
        int[][] matrix = {{Integer.MAX_VALUE, Integer.MAX_VALUE}};
        long expected = 2L * Integer.MAX_VALUE;

        assertArrayEquals(new long[] {expected}, Task49.rowSums(matrix));
    }

    @Test
    void shouldReturnEmptyArrayForEmptyMatrix() {
        assertEquals(0, Task49.rowSums(new int[0][0]).length);
    }

    @Test
    void shouldPrintSumsOnOneLine() {
        assertEquals("6 15\n", TestIo.run(Task49::run, "2 3\n1 2 3\n4 5 6\n"));
    }
}
