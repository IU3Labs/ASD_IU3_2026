package ru.bmstu.lab01.task_50;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class ColumnSumsTest {

    @Test
    void shouldSumEachColumn() {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}};
        assertArrayEquals(new long[] {5, 7, 9}, ColumnSums.compute(matrix));
    }

    @Test
    void shouldHandleNegativeValues() {
        int[][] matrix = {{-1, 2}, {-3, -4}};
        assertArrayEquals(new long[] {-4, -2}, ColumnSums.compute(matrix));
    }

    @Test
    void shouldHandleSingleRow() {
        int[][] matrix = {{1, 2, 3}};
        assertArrayEquals(new long[] {1, 2, 3}, ColumnSums.compute(matrix));
    }

    @Test
    void shouldHandleSingleColumn() {
        int[][] matrix = {{1}, {2}, {3}};
        assertArrayEquals(new long[] {6}, ColumnSums.compute(matrix));
    }

    @Test
    void shouldHandleSingleCell() {
        assertArrayEquals(new long[] {7}, ColumnSums.compute(new int[][] {{7}}));
    }

    @Test
    void shouldNotOverflowInt() {
        int[][] matrix = {{Integer.MAX_VALUE}, {Integer.MAX_VALUE}};
        assertArrayEquals(new long[] {2L * Integer.MAX_VALUE}, ColumnSums.compute(matrix));
    }

    @Test
    void shouldRejectNull() {
        assertThrows(IllegalArgumentException.class, () -> ColumnSums.compute(null));
    }

    @Test
    void shouldRejectEmptyMatrix() {
        assertThrows(IllegalArgumentException.class, () -> ColumnSums.compute(new int[0][]));
    }

    @Test
    void shouldRejectRaggedMatrix() {
        assertThrows(IllegalArgumentException.class,
                () -> ColumnSums.compute(new int[][] {{1, 2}, {3}}));
    }
}