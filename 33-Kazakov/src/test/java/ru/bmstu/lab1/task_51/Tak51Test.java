package ru.bmstu.lab1.task_51;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

/**
 * Проверяет максимумы строк и ввод-вывод задачи 51
 */
class Task51Test {

    @Test
    void shouldFindMaximumsInRectangularMatrix() {
        int[][] matrix = {{1, 9, 3}, {8, 2, 4}};

        int[] actual = Task51.maxRows(matrix);

        assertArrayEquals(new int[] {9, 8}, actual);
    }

    @Test
    void shouldFindMaximumsAmongNegativeValues() {
        int[][] matrix = {{-5, -2, -9}, {-1, -7, -3}};

        assertArrayEquals(new int[] {-2, -1}, Task51.maxRows(matrix));
    }

    @Test
    void shouldHandleSingleColumnAndZero() {
        int[][] matrix = {{4}, {0}, {-2}};

        assertArrayEquals(new int[] {4, 0, -2}, Task51.maxRows(matrix));
    }

    @Test
    void shouldHandleSingleRowWithMinimumIntValues() {
        int[][] matrix = {{Integer.MIN_VALUE, Integer.MIN_VALUE}};

        assertArrayEquals(
                new int[] {Integer.MIN_VALUE},
                Task51.maxRows(matrix));
    }

    @Test
    void shouldReadMatrixAndPrintRowMaximums() {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        try (Scanner input = new Scanner("2 3\n1 9 3\n8 2 4\n");
                PrintStream output = new PrintStream(buffer)) {
            Task51.solve(input, output);
        }

        String expected = "9" + System.lineSeparator()
                + "8" + System.lineSeparator();

        assertEquals(expected, buffer.toString());
    }
}