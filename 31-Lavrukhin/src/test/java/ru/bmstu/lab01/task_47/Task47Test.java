package ru.bmstu.lab01.task_47;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import ru.bmstu.lab01.util.ConsoleHelper;

class Task47Test {

    @Test
    void shouldRotateSquareMatrix() {
        int[][] matrix = {{1, 2}, {3, 4}};
        int[][] expected = {{3, 1}, {4, 2}};

        assertArrayEquals(expected, Task47.rotate(matrix));
    }

    @Test
    void shouldRotateRectangularMatrix() {
        // матрица 2 x 3 превращается в 3 x 2
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}};
        int[][] expected = {{4, 1}, {5, 2}, {6, 3}};

        assertArrayEquals(expected, Task47.rotate(matrix));
    }

    @Test
    void shouldTurnRowIntoColumn() {
        int[][] expected = {{7}, {8}, {9}};

        assertArrayEquals(expected, Task47.rotate(new int[][] {{7, 8, 9}}));
    }

    @Test
    void shouldReturnSameMatrixAfterFourRotations() {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}};

        int[][] result = matrix;
        for (int k = 0; k < 4; k++) {
            result = Task47.rotate(result);
        }

        assertArrayEquals(matrix, result);
    }

    @Test
    void shouldThrowForEmptyMatrix() {
        assertThrows(IllegalArgumentException.class, () -> Task47.rotate(new int[0][0]));
    }

    @Test
    void shouldReadInputAndPrintRotatedMatrix() {
        String output = ConsoleHelper.run(() -> Task47.main(new String[0]), "2 3\n1 2 3\n4 5 6\n");

        assertEquals("4 1\n5 2\n6 3", output);
    }
}
