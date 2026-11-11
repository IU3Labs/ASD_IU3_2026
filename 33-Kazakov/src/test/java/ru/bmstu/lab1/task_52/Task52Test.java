package ru.bmstu.lab1.task_52;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

/**
 * Проверяет минимумы столбцов и ввод-вывод задачи 52
 */
class Task52Test {

    @Test
    void shouldFindMinimumsInPositiveRectangularMatrix() {
        int[][] matrix = {{1, 9, 3}, {8, 2, 4}};

        int[] actual = Task52.minColumns(matrix);

        assertArrayEquals(new int[] {1, 2, 3}, actual);
    }

    @Test
    void shouldFindMinimumsAmongNegativeValues() {
        int[][] matrix = {{-5, -2, -9}, {-1, -7, -3}};

        assertArrayEquals(new int[] {-5, -7, -9}, Task52.minColumns(matrix));
    }

    @Test
    void shouldHandleSingleRowWithIntBoundaries() {
        int[][] matrix = {{Integer.MIN_VALUE, 0, Integer.MAX_VALUE}};

        assertArrayEquals(
                new int[] {Integer.MIN_VALUE, 0, Integer.MAX_VALUE},
                Task52.minColumns(matrix));
    }

    @Test
    void shouldHandleSingleColumn() {
        int[][] matrix = {{4}, {0}, {-2}};

        assertArrayEquals(new int[] {-2}, Task52.minColumns(matrix));
    }

    @Test
    void shouldReadMatrixAndPrintColumnMinimums() {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        try (Scanner input = new Scanner("2 3\n1 9 3\n8 2 4\n");
                PrintStream output = new PrintStream(buffer)) {
            Task52.solve(input, output);
        }

        String expected = "1" + System.lineSeparator()
                + "2" + System.lineSeparator()
                + "3" + System.lineSeparator();

        assertEquals(expected, buffer.toString());
    }
}