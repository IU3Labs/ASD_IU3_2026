package ru.bmstu.lab01.task_56;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import ru.bmstu.lab01.common.ConsoleRunner;

class MatrixMultiplierTest {

    @Test
    void shouldMultiplyRectangularMatrices() {
        int[][] left = {
            {1, 2, 3},
            {4, 5, 6},
        };
        int[][] right = {
            {7, 8},
            {9, 10},
            {11, 12},
        };
        long[][] expected = {
            {58, 64},
            {139, 154},
        };

        assertArrayEquals(expected, MatrixMultiplier.multiply(left, right));
    }

    @Test
    void shouldReturnSameMatrixWhenMultipliedByIdentity() {
        int[][] matrix = {{2, -3}, {5, 7}};
        int[][] identity = {{1, 0}, {0, 1}};
        long[][] expected = {{2, -3}, {5, 7}};

        assertArrayEquals(expected, MatrixMultiplier.multiply(matrix, identity));
    }

    @Test
    void shouldNotOverflowOnLargeProducts() {
        int[][] left = {{Integer.MAX_VALUE, Integer.MAX_VALUE}};
        int[][] right = {{Integer.MAX_VALUE}, {Integer.MAX_VALUE}};
        long[][] expected = {{2L * Integer.MAX_VALUE * Integer.MAX_VALUE}};

        assertArrayEquals(expected, MatrixMultiplier.multiply(left, right));
    }

    @Test
    void shouldRejectIncompatibleDimensions() {
        int[][] left = {{1, 2}};
        int[][] right = {{1, 2}};

        assertThrows(IllegalArgumentException.class, () -> MatrixMultiplier.multiply(left, right));
    }

    @Test
    void shouldReturnEmptyMatrixWhenLeftHasNoRows() {
        assertEquals(0, MatrixMultiplier.multiply(new int[0][], new int[][] {{1}}).length);
    }

    @Test
    void shouldReadMatricesAndPrintProduct() {
        String input = "2 2 1\n1 2\n3 4\n5\n6\n";

        assertEquals("17\n39", ConsoleRunner.run(MatrixMultiplier::run, input));
    }
}
