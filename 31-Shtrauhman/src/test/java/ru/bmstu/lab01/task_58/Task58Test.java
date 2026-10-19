package ru.bmstu.lab01.task_58;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab01.common.TestIo;

/**
 * Тесты задачи 58: матрица, заполненная змейкой.
 */
class Task58Test {

    /** Размер, при котором rows * cols уже не помещается в int. */
    private static final int OVERFLOWING_SIZE = 100_000;

    @Test
    void shouldFillRowsInAlternatingDirections() {
        int[][] expected = {
            {1, 2, 3, 4},
            {8, 7, 6, 5},
            {9, 10, 11, 12}
        };

        assertArrayEquals(expected, Task58.snake(3, 4));
    }

    @Test
    void shouldHandleSingleCell() {
        assertArrayEquals(new int[][] {{1}}, Task58.snake(1, 1));
    }

    @Test
    void shouldHandleSingleRow() {
        assertArrayEquals(new int[][] {{1, 2, 3, 4, 5}}, Task58.snake(1, 5));
    }

    @Test
    void shouldHandleSingleColumn() {
        assertArrayEquals(new int[][] {{1}, {2}, {3}}, Task58.snake(3, 1));
    }

    @Test
    void shouldContainEveryNumberExactlyOnce() {
        int rows = 7;
        int cols = 9;
        boolean[] seen = new boolean[rows * cols + 1];

        for (int[] row : Task58.snake(rows, cols)) {
            for (int value : row) {
                assertEquals(false, seen[value], "duplicate " + value);
                seen[value] = true;
            }
        }

        for (int value = 1; value <= rows * cols; value++) {
            assertEquals(true, seen[value], "missing " + value);
        }
    }

    @Test
    void shouldRejectSizeThatOverflowsInt() {
        assertThrows(ArithmeticException.class, () -> Task58.snake(OVERFLOWING_SIZE, OVERFLOWING_SIZE));
    }

    @Test
    void shouldPrintMatrixRowByRow() {
        assertEquals("1 2 3\n6 5 4\n", TestIo.run(Task58::run, "2 3\n"));
    }
}
