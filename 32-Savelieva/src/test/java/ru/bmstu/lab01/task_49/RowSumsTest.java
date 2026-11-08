package ru.bmstu.lab01.task_49;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class RowSumsTest {

    @Test
    void shouldSumEachRow() {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}};
        assertArrayEquals(new long[] {6, 15}, RowSums.compute(matrix));
    }

    @Test
    void shouldHandleNegativeValues() {
        int[][] matrix = {{-1, -2, -3}, {0, 5, -5}};
        assertArrayEquals(new long[] {-6, 0}, RowSums.compute(matrix));
    }

    @Test
    void shouldHandleSingleRow() {
        int[][] matrix = {{1, 2, 3, 4}};
        assertArrayEquals(new long[] {10}, RowSums.compute(matrix));
    }

    @Test
    void shouldHandleSingleColumn() {
        int[][] matrix = {{1}, {2}, {3}};
        assertArrayEquals(new long[] {1, 2, 3}, RowSums.compute(matrix));
    }

    @Test
    void shouldHandleSingleCell() {
        int[][] matrix = {{7}};
        assertArrayEquals(new long[] {7}, RowSums.compute(matrix));
    }

    @Test
    void shouldNotOverflowInt() {
        int[][] matrix = {{Integer.MAX_VALUE, Integer.MAX_VALUE}};
        assertArrayEquals(new long[] {2L * Integer.MAX_VALUE}, RowSums.compute(matrix));
    }

    @Test
    void shouldRejectNull() {
        assertThrows(IllegalArgumentException.class, () -> RowSums.compute(null));
    }

    @Test
    void shouldRejectEmptyMatrix() {
        assertThrows(IllegalArgumentException.class, () -> RowSums.compute(new int[0][]));
    }

    @Test
    void shouldRejectNullRow() {
        assertThrows(IllegalArgumentException.class,
                () -> RowSums.compute(new int[][] {{1, 2}, null}));
    }
}