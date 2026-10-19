package ru.bmstu.lab01.task_48;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab01.common.TestIo;

/**
 * Тесты задачи 48: транспонирование квадратной матрицы на месте.
 */
class Task48Test {

    private static final int SIZE = 4;

    @Test
    void shouldTransposeTwoByTwoMatrix() {
        int[][] matrix = {{1, 2}, {3, 4}};

        Task48.transposeInPlace(matrix);

        assertArrayEquals(new int[][] {{1, 3}, {2, 4}}, matrix);
    }

    @Test
    void shouldTransposeThreeByThreeMatrix() {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        int[][] expected = {{1, 4, 7}, {2, 5, 8}, {3, 6, 9}};

        Task48.transposeInPlace(matrix);

        assertArrayEquals(expected, matrix);
    }

    @Test
    void shouldKeepSingleCellMatrixUnchanged() {
        int[][] matrix = {{5}};

        Task48.transposeInPlace(matrix);

        assertArrayEquals(new int[][] {{5}}, matrix);
    }

    @Test
    void shouldKeepSymmetricMatrixUnchanged() {
        int[][] matrix = {{1, 2}, {2, 1}};

        Task48.transposeInPlace(matrix);

        assertArrayEquals(new int[][] {{1, 2}, {2, 1}}, matrix);
    }

    @Test
    void shouldRestoreOriginalAfterTransposingTwice() {
        int[][] matrix = new int[SIZE][SIZE];
        int[][] expected = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                matrix[i][j] = i * SIZE + j;
                expected[i][j] = i * SIZE + j;
            }
        }

        Task48.transposeInPlace(matrix);
        Task48.transposeInPlace(matrix);

        assertArrayEquals(expected, matrix);
    }

    @Test
    void shouldModifyTheSameArraysWithoutAllocatingNewOnes() {
        int[][] matrix = {{1, 2}, {3, 4}};
        int[] firstRow = matrix[0];
        int[] secondRow = matrix[1];

        Task48.transposeInPlace(matrix);

        assertSame(firstRow, matrix[0]);
        assertSame(secondRow, matrix[1]);
    }

    @Test
    void shouldRejectNonSquareMatrix() {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}};

        assertThrows(IllegalArgumentException.class, () -> Task48.transposeInPlace(matrix));
    }

    @Test
    void shouldPrintTransposedMatrixRowByRow() {
        assertEquals("1 3\n2 4\n", TestIo.run(Task48::run, "2\n1 2\n3 4\n"));
    }
}
