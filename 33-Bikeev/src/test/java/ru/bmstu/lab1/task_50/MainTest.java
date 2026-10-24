package ru.bmstu.lab1.task_50;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Проверки решения задачи 50.
 */
public class MainTest {

    @Test
    void sumsOrdinaryColumns() {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6}
        };

        assertArrayEquals(
                new long[]{5, 7, 9},
                Main.calculateColumnSums(matrix, 3)
        );
    }

    @Test
    void sumsNegativeAndMixedNumbers() {
        int[][] matrix = {
                {-1, 2, -3},
                {-4, -2, 5}
        };

        assertArrayEquals(
                new long[]{-5, 0, 2},
                Main.calculateColumnSums(matrix, 3)
        );
    }

    @Test
    void sumsZeros() {
        int[][] matrix = {{0, 0}, {0, 0}};

        assertArrayEquals(
                new long[]{0, 0},
                Main.calculateColumnSums(matrix, 2)
        );
    }

    @Test
    void handlesSingleRow() {
        int[][] matrix = {{3, -2, 7}};

        assertArrayEquals(
                new long[]{3, -2, 7},
                Main.calculateColumnSums(matrix, 3)
        );
    }

    @Test
    void handlesSingleColumn() {
        int[][] matrix = {{3}, {-2}, {7}};

        assertArrayEquals(
                new long[]{8},
                Main.calculateColumnSums(matrix, 1)
        );
    }

    @Test
    void handlesLargeSum() {
        int[][] matrix = {
                {Integer.MAX_VALUE},
                {Integer.MAX_VALUE}
        };

        assertArrayEquals(
                new long[]{4294967294L},
                Main.calculateColumnSums(matrix, 1)
        );
    }

    @Test
    void readsMatrixAndPrintsSums() {
        InputStream originalInput = System.in;
        PrintStream originalOutput = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try {
            byte[] input = "2 3\n1 2 3\n4 5 6\n".getBytes(
                    StandardCharsets.UTF_8
            );
            System.setIn(new ByteArrayInputStream(input));
            System.setOut(new PrintStream(output));

            Main.main(new String[0]);

            assertEquals(
                    "5 7 9" + System.lineSeparator(),
                    output.toString(StandardCharsets.UTF_8)
            );
        } finally {
            System.setIn(originalInput);
            System.setOut(originalOutput);
        }
    }
}