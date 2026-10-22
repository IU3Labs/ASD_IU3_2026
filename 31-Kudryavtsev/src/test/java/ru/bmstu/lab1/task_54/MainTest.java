package ru.bmstu.lab1.task_54;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.ConsoleTestSupport;

/** Проверяет главную диагональ, сохранность матрицы и большие суммы задачи 54. */
class MainTest {

    @Test
    void shouldSumMainDiagonalOfThreeByThreeMatrix() {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};

        long actual = Main.sumMainDiagonal(matrix);

        assertEquals(15L, actual);
    }

    @Test
    void shouldIgnoreNumbersOutsideMainDiagonal() {
        int[][] matrix = {{4, 99}, {-8, 6}};

        long actual = Main.sumMainDiagonal(matrix);

        assertEquals(10L, actual);
    }

    @Test
    void shouldHandleSingleElementMatrix() {
        int[][] matrix = {{-7}};

        long actual = Main.sumMainDiagonal(matrix);

        assertEquals(-7L, actual);
    }

    @Test
    void shouldReturnZeroForZeroDiagonal() {
        int[][] matrix = {{0, 5}, {-4, 0}};

        long actual = Main.sumMainDiagonal(matrix);

        assertEquals(0L, actual);
    }

    @Test
    void shouldAddNegativeDiagonalElements() {
        int[][] matrix = {{-3, 0}, {0, -2}};

        long actual = Main.sumMainDiagonal(matrix);

        assertEquals(-5L, actual);
    }

    @Test
    void shouldAvoidPositiveIntegerOverflow() {
        int[][] matrix = {{Integer.MAX_VALUE, 0}, {0, Integer.MAX_VALUE}};

        long actual = Main.sumMainDiagonal(matrix);

        assertEquals(4_294_967_294L, actual);
    }

    @Test
    void shouldAvoidNegativeIntegerOverflow() {
        int[][] matrix = {{Integer.MIN_VALUE, 0}, {0, Integer.MIN_VALUE}};

        long actual = Main.sumMainDiagonal(matrix);

        assertEquals(-4_294_967_296L, actual);
    }

    @Test
    void shouldLeaveMatrixUnchanged() {
        int[][] matrix = {{2, 3}, {-1, 8}};
        int[][] expected = {{2, 3}, {-1, 8}};

        Main.sumMainDiagonal(matrix);

        assertArrayEquals(expected[0], matrix[0]);
        assertArrayEquals(expected[1], matrix[1]);
    }

    @Test
    void shouldVisitEveryDiagonalPositionInLargeMatrix() {
        int[][] matrix = new int[500][500];
        for (int index = 0; index < matrix.length; index++) {
            matrix[index][index] = 1;
        }

        long actual = Main.sumMainDiagonal(matrix);

        assertEquals(500L, actual);
    }

    @Test
    void shouldReadSquareMatrixAndPrintDiagonalSum() {
        String expected = "15" + System.lineSeparator();

        String actual = ConsoleTestSupport.runProgram(Main::solve, "3\n1 2 3\n4 5 6\n7 8 9\n");

        assertEquals(expected, actual);
    }
}
