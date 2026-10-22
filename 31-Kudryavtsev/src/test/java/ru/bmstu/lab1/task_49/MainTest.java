package ru.bmstu.lab1.task_49;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.ConsoleTestSupport;

/** Проверяет суммы строк прямоугольных матриц, включая суммы за пределами int. */
class MainTest {

    @Test
    void shouldSumRowsOfRectangularMatrix() {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}};
        long[] expected = {6, 15};

        long[] actual = Main.sumRows(matrix);

        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldSumSingleRow() {
        int[][] matrix = {{3, -2, 4}};
        long[] expected = {5};

        long[] actual = Main.sumRows(matrix);

        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldSumSingleColumn() {
        int[][] matrix = {{3}, {-2}, {0}};
        long[] expected = {3, -2, 0};

        long[] actual = Main.sumRows(matrix);

        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldReturnZerosForZeroMatrix() {
        int[][] matrix = {{0, 0, 0}, {0, 0, 0}};
        long[] expected = {0, 0};

        long[] actual = Main.sumRows(matrix);

        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldHandlePositiveAndNegativeRowSums() {
        int[][] matrix = {{-3, 0, 7}, {-4, -2, -1}};
        long[] expected = {4, -7};

        long[] actual = Main.sumRows(matrix);

        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldAvoidIntegerOverflowInBothDirections() {
        int[][] matrix = {
            {Integer.MAX_VALUE, Integer.MAX_VALUE},
            {Integer.MIN_VALUE, Integer.MIN_VALUE}
        };
        long[] expected = {4_294_967_294L, -4_294_967_296L};

        long[] actual = Main.sumRows(matrix);

        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldLeaveMatrixUnchanged() {
        int[][] matrix = {{2, 3}, {-1, 8}};
        int[][] expected = {{2, 3}, {-1, 8}};

        Main.sumRows(matrix);

        assertArrayEquals(expected[0], matrix[0]);
        assertArrayEquals(expected[1], matrix[1]);
    }

    @Test
    void shouldReadDimensionsAndPrintRowSums() {
        String expected = "6 15" + System.lineSeparator();

        String actual = ConsoleTestSupport.runProgram(Main::solve, "2 3\n1 2 3\n4 5 6\n");

        assertEquals(expected, actual);
    }

    @Test
    void shouldPrintSingleRowSumWithoutSpaces() {
        String expected = "5" + System.lineSeparator();

        String actual = ConsoleTestSupport.runProgram(Main::solve, "1 3\n3 -2 4\n");

        assertEquals(expected, actual);
    }
}
