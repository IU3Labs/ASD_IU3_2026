package ru.bmstu.lab01.task_55;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class AntiDiagonalSumTest {

    @Test
    void shouldSumAntiDiagonal() {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        assertEquals(15L, AntiDiagonalSum.compute(matrix)); // 3 + 5 + 7
    }

    @Test
    void shouldHandleSingleElement() {
        assertEquals(42L, AntiDiagonalSum.compute(new int[][] {{42}}));
    }

    @Test
    void shouldHandleNegativeValues() {
        int[][] matrix = {{-1, -2}, {-3, -4}};
        // побочная: matrix[0][1] + matrix[1][0] = -2 + -3 = -5
        assertEquals(-5L, AntiDiagonalSum.compute(matrix));
    }

    @Test
    void shouldHandleAllEqual() {
        int[][] matrix = {{7, 0, 7}, {0, 7, 0}, {7, 0, 7}};
        // побочная: 7 + 7 + 7 = 21
        assertEquals(21L, AntiDiagonalSum.compute(matrix));
    }

    @Test
    void shouldNotOverflowInt() {
        int[][] matrix = {{0, Integer.MAX_VALUE}, {Integer.MAX_VALUE, 0}};
        assertEquals(2L * Integer.MAX_VALUE, AntiDiagonalSum.compute(matrix));
    }

    @Test
    void shouldRejectNull() {
        assertThrows(IllegalArgumentException.class, () -> AntiDiagonalSum.compute(null));
    }

    @Test
    void shouldRejectEmptyMatrix() {
        assertThrows(IllegalArgumentException.class, () -> AntiDiagonalSum.compute(new int[0][]));
    }

    @Test
    void shouldRejectNonSquare() {
        assertThrows(IllegalArgumentException.class,
                () -> AntiDiagonalSum.compute(new int[][] {{1, 2, 3}, {4, 5, 6}}));
    }
}