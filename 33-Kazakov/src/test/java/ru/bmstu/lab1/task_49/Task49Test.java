package ru.bmstu.lab1.task_49;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

/**
 * Проверяет вычисление сумм строк и ввод-вывод задачи 49
 */
class Task49Test {

    @Test
    void shouldSumRowsOfRectangularMatrix() {
        int[][] matrix = {{1, 2, 3}, {-1, 0, 4}};

        long[] actual = Task49.sumRows(matrix);

        assertArrayEquals(new long[] {6, 3}, actual);
    }

    @Test
    void shouldSumNegativeValues() {
        int[][] matrix = {{-5, -2}, {-1, -9}};

        assertArrayEquals(new long[] {-7, -10}, Task49.sumRows(matrix));
    }

    @Test
    void shouldHandleSingleColumnAndZero() {
        int[][] matrix = {{4}, {0}, {-2}};

        assertArrayEquals(new long[] {4, 0, -2}, Task49.sumRows(matrix));
    }

    @Test
    void shouldSumSingleRowWithoutIntOverflow() {
        int[][] matrix = {{Integer.MAX_VALUE, Integer.MAX_VALUE}};

        assertArrayEquals(new long[] {4294967294L}, Task49.sumRows(matrix));
    }

    @Test
    void shouldReadMatrixAndPrintRowSums() {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        try (Scanner input = new Scanner("2 3\n1 2 3\n-1 0 4\n");
                PrintStream output = new PrintStream(buffer)) {
            Task49.solve(input, output);
        }

        String expected = "6" + System.lineSeparator()
                + "3" + System.lineSeparator();

        assertEquals(expected, buffer.toString());
    }
}