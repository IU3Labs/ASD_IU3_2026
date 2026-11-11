package ru.bmstu.lab1.task_55;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Проверки решения задачи 55.
 */
public class MainTest {

    @Test
    void sumsSecondaryDiagonal() {
        int[][] matrix = {
                {1, 2, 4},
                {5, 6, 7},
                {8, 9, 10}
        };

        assertEquals(18L, Main.calculateSecondaryDiagonalSum(matrix));
    }

    @Test
    void ignoresElementsOutsideDiagonal() {
        int[][] matrix = {
                {100, 100, 2},
                {100, 3, 100},
                {4, 100, 100}
        };

        assertEquals(9L, Main.calculateSecondaryDiagonalSum(matrix));
    }

    @Test
    void sumsNegativeNumbers() {
        int[][] matrix = {
                {7, -2},
                {-5, 8}
        };

        assertEquals(-7L, Main.calculateSecondaryDiagonalSum(matrix));
    }

    @Test
    void sumsZeros() {
        int[][] matrix = {
                {0, 0},
                {0, 0}
        };

        assertEquals(0L, Main.calculateSecondaryDiagonalSum(matrix));
    }

    @Test
    void handlesSingleElement() {
        int[][] matrix = {{7}};

        assertEquals(7L, Main.calculateSecondaryDiagonalSum(matrix));
    }

    @Test
    void handlesLargeSum() {
        int[][] matrix = {
                {0, Integer.MAX_VALUE},
                {Integer.MAX_VALUE, 0}
        };

        assertEquals(4294967294L, Main.calculateSecondaryDiagonalSum(matrix));
    }

    @Test
    void readsMatrixAndPrintsSum() {
        String input = "3\n1 2 4\n5 6 7\n8 9 10\n";
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));

            Main.main(new String[0]);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }

        assertEquals("18" + System.lineSeparator(), output.toString(StandardCharsets.UTF_8));
    }
}