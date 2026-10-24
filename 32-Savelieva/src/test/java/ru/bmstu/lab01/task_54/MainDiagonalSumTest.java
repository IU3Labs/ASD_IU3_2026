package ru.bmstu.lab01.task_54;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class MainDiagonalSumTest {

    @Test
    void shouldSumMainDiagonal() {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        assertEquals(15L, MainDiagonalSum.compute(matrix)); // 1 + 5 + 9
    }

    @Test
    void shouldHandleSingleElement() {
        assertEquals(42L, MainDiagonalSum.compute(new int[][] {{42}}));
    }

    @Test
    void shouldHandleNegativeValues() {
        assertEquals(-3L, MainDiagonalSum.compute(new int[][] {{-1, 0}, {0, -2}}));
    }

    @Test
    void shouldHandleAllEqual() {
        int[][] matrix = {{7, 0, 0}, {0, 7, 0}, {0, 0, 7}};
        assertEquals(21L, MainDiagonalSum.compute(matrix));
    }

    @Test
    void shouldNotOverflowInt() {
        int[][] matrix = {{Integer.MAX_VALUE}, {Integer.MAX_VALUE}};
        // не квадратная — исправим:
        int[][] square = {{Integer.MAX_VALUE, 0}, {0, Integer.MAX_VALUE}};
        assertEquals(2L * Integer.MAX_VALUE, MainDiagonalSum.compute(square));
    }

    @Test
    void shouldRejectNull() {
        assertThrows(IllegalArgumentException.class, () -> MainDiagonalSum.compute(null));
    }

    @Test
    void shouldRejectEmptyMatrix() {
        assertThrows(IllegalArgumentException.class, () -> MainDiagonalSum.compute(new int[0][]));
    }

    @Test
    void shouldRejectNonSquare() {
        assertThrows(IllegalArgumentException.class,
                () -> MainDiagonalSum.compute(new int[][] {{1, 2, 3}, {4, 5, 6}}));
    }
}