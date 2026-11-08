package ru.bmstu.lab01.task_52;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ColumnMinTest {

    @Test
    void shouldFindMinOfEachColumn() {
        int[][] matrix = {{1, 5, 3}, {4, 2, 6}};
        assertArrayEquals(new int[] {1, 2, 3}, ColumnMin.compute(matrix));
    }

    @Test
    void shouldHandleAllNegative() {
        int[][] matrix = {{-1, -5, -3}, {-4, -2, -6}};
        assertArrayEquals(new int[] {-4, -5, -6}, ColumnMin.compute(matrix));
    }

    @Test
    void shouldHandleSingleRow() {
        assertArrayEquals(new int[] {1, 2, 3}, ColumnMin.compute(new int[][] {{1, 2, 3}}));
    }

    @Test
    void shouldHandleSingleColumn() {
        assertArrayEquals(new int[] {1}, ColumnMin.compute(new int[][] {{1}, {2}, {3}}));
    }

    @Test
    void shouldHandleAllEqual() {
        assertArrayEquals(new int[] {7, 7}, ColumnMin.compute(new int[][] {{7, 7}, {7, 7}}));
    }

    @Test
    void shouldHandleExtremeIntValues() {
        int[][] matrix = {{Integer.MAX_VALUE, Integer.MIN_VALUE}};
        assertArrayEquals(new int[] {Integer.MAX_VALUE, Integer.MIN_VALUE},
                ColumnMin.compute(matrix));
    }

    @Test
    void shouldRejectNull() {
        assertThrows(IllegalArgumentException.class, () -> ColumnMin.compute(null));
    }

    @Test
    void shouldRejectRaggedMatrix() {
        assertThrows(IllegalArgumentException.class,
                () -> ColumnMin.compute(new int[][] {{1, 2}, {3}}));
    }
}