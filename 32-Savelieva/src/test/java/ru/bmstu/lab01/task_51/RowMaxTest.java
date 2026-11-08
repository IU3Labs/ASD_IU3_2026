package ru.bmstu.lab01.task_51;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class RowMaxTest {

    @Test
    void shouldFindMaxOfEachRow() {
        int[][] matrix = {{1, 5, 3}, {4, 2, 6}};
        assertArrayEquals(new int[] {5, 6}, RowMax.compute(matrix));
    }

    @Test
    void shouldHandleAllNegative() {
        int[][] matrix = {{-5, -1, -3}, {-2, -4, -6}};
        assertArrayEquals(new int[] {-1, -2}, RowMax.compute(matrix));
    }

    @Test
    void shouldHandleSingleRow() {
        assertArrayEquals(new int[] {9}, RowMax.compute(new int[][] {{1, 9, 3}}));
    }

    @Test
    void shouldHandleSingleColumn() {
        assertArrayEquals(new int[] {1, 2, 3}, RowMax.compute(new int[][] {{1}, {2}, {3}}));
    }

    @Test
    void shouldHandleAllEqual() {
        assertArrayEquals(new int[] {7, 7}, RowMax.compute(new int[][] {{7, 7}, {7, 7}}));
    }

    @Test
    void shouldHandleExtremeIntValues() {
        int[][] matrix = {{Integer.MIN_VALUE, Integer.MAX_VALUE}};
        assertArrayEquals(new int[] {Integer.MAX_VALUE}, RowMax.compute(matrix));
    }

    @Test
    void shouldRejectNull() {
        assertThrows(IllegalArgumentException.class, () -> RowMax.compute(null));
    }

    @Test
    void shouldRejectEmptyMatrix() {
        assertThrows(IllegalArgumentException.class, () -> RowMax.compute(new int[0][]));
    }
}