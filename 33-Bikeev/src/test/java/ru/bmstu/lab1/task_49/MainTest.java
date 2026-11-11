package ru.bmstu.lab1.task_49;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Проверки решения задачи 49.
 */
public class MainTest {

    @Test
    void sumsOrdinaryRows() {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6}
        };

        assertArrayEquals(
                new long[]{6, 15},
                Main.calculateRowSums(matrix)
        );
    }

    @Test
    void sumsNegativeAndMixedNumbers() {
        int[][] matrix = {
                {-1, -2, -3},
                {-5, 0, 5}
        };

        assertArrayEquals(
                new long[]{-6, 0},
                Main.calculateRowSums(matrix)
        );
    }

    @Test
    void sumsZeros() {
        int[][] matrix = {
                {0, 0},
                {0, 0}
        };

        assertArrayEquals(
                new long[]{0, 0},
                Main.calculateRowSums(matrix)
        );
    }

    @Test
    void handlesSingleElement() {
        int[][] matrix = {{7}};

        assertArrayEquals(
                new long[]{7},
                Main.calculateRowSums(matrix)
        );
    }

    @Test
    void handlesSingleColumn() {
        int[][] matrix = {{3}, {-2}, {0}};

        assertArrayEquals(
                new long[]{3, -2, 0},
                Main.calculateRowSums(matrix)
        );
    }

    @Test
    void handlesLargeSum() {
        int[][] matrix = {
                {Integer.MAX_VALUE, Integer.MAX_VALUE}
        };

        assertArrayEquals(
                new long[]{4294967294L},
                Main.calculateRowSums(matrix)
        );
    }

    @Test
    void readsMatrixAndPrintsSums() {
        InputStream originalInput = System.in;
        PrintStream originalOutput = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try {
            byte[] input = "2 3\n1 2 3\n4 5 6\n".getBytes(
                    java.nio.charset.StandardCharsets.UTF_8
            );
            System.setIn(new ByteArrayInputStream(input));
            System.setOut(new PrintStream(output));

            Main.main(new String[0]);

            assertEquals(
                    "6 15" + System.lineSeparator(),
                    output.toString(java.nio.charset.StandardCharsets.UTF_8)
            );
        } finally {
            System.setIn(originalInput);
            System.setOut(originalOutput);
        }
    }
}
