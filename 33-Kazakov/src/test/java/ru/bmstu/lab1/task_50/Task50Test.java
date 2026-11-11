package ru.bmstu.lab1.task_50;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

/**
 * Проверяет суммы столбцов и ввод-вывод задачи 50
 */
class Task50Test {

    @Test
    void shouldSumColumnsOfRectangularMatrix() {
        int[][] matrix = {{1, 2, 3}, {-1, 0, 4}};

        long[] actual = Task50.sumColumns(matrix);

        assertArrayEquals(new long[] {0, 2, 7}, actual);
    }

    @Test
    void shouldSumNegativeValues() {
        int[][] matrix = {{-5, -2}, {-1, -9}};

        assertArrayEquals(new long[] {-6, -11}, Task50.sumColumns(matrix));
    }

    @Test
    void shouldHandleSingleRowAndZero() {
        int[][] matrix = {{4, 0, -2}};

        assertArrayEquals(new long[] {4, 0, -2}, Task50.sumColumns(matrix));
    }

    @Test
    void shouldSumSingleColumnWithoutIntOverflow() {
        int[][] matrix = {{Integer.MAX_VALUE}, {Integer.MAX_VALUE}};

        assertArrayEquals(new long[] {4294967294L}, Task50.sumColumns(matrix));
    }

    @Test
    void shouldReadMatrixAndPrintColumnSums() {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        try (Scanner input = new Scanner("2 3\n1 2 3\n-1 0 4\n");
                PrintStream output = new PrintStream(buffer)) {
            Task50.solve(input, output);
        }

        String expected = "0" + System.lineSeparator()
                + "2" + System.lineSeparator()
                + "7" + System.lineSeparator();

        assertEquals(expected, buffer.toString());
    }
}