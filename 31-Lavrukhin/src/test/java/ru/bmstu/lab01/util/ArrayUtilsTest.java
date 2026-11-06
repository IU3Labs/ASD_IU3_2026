package ru.bmstu.lab01.util;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Scanner;

import org.junit.jupiter.api.Test;

class ArrayUtilsTest {

    @Test
    void shouldReadArrayWithLength() {
        Scanner scanner = new Scanner("3\n10 -20 30");

        assertArrayEquals(new int[] {10, -20, 30}, ArrayUtils.readArray(scanner));
    }

    @Test
    void shouldReadEmptyArray() {
        assertEquals(0, ArrayUtils.readArray(new Scanner("0")).length);
    }

    @Test
    void shouldThrowWhenSizeIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> ArrayUtils.readSize(new Scanner("-2")));
    }

    @Test
    void shouldReadMatrixByRows() {
        int[][] matrix = ArrayUtils.readMatrix(new Scanner("1 2 3\n4 5 6"), 2, 3);

        assertArrayEquals(new int[] {1, 2, 3}, matrix[0]);
        assertArrayEquals(new int[] {4, 5, 6}, matrix[1]);
    }

    @Test
    void shouldJoinArrayWithSpaces() {
        assertEquals("5 -6 7", ArrayUtils.toLine(new int[] {5, -6, 7}));
        assertEquals("", ArrayUtils.toLine(new int[0]));
    }

    @Test
    void shouldAcceptRectangularMatrix() {
        ArrayUtils.checkMatrix(new int[][] {{1, 2}, {3, 4}, {5, 6}});
    }

    @Test
    void shouldRejectBadMatrices() {
        assertThrows(IllegalArgumentException.class, () -> ArrayUtils.checkMatrix(null));
        assertThrows(IllegalArgumentException.class, () -> ArrayUtils.checkMatrix(new int[0][0]));
        assertThrows(IllegalArgumentException.class, () -> ArrayUtils.checkMatrix(new int[][] {{}}));
        assertThrows(IllegalArgumentException.class, () -> ArrayUtils.checkMatrix(new int[][] {{1, 2}, {3}}));
        assertThrows(IllegalArgumentException.class, () -> ArrayUtils.checkMatrix(new int[][] {{1}, null}));
    }

    @Test
    void shouldRejectNotSquareMatrix() {
        assertThrows(IllegalArgumentException.class, () -> ArrayUtils.checkSquare(new int[][] {{1, 2}}));
    }
}
