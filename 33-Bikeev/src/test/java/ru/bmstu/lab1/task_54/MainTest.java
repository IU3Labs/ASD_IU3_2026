package ru.bmstu.lab1.task_54;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Проверки решения задачи 54.
 */
public class MainTest {

    @Test
    void sumsMainDiagonal() {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 10}
        };

        assertEquals(16L, Main.calculateMainDiagonalSum(matrix));
    }

    @Test
    void ignoresElementsOutsideDiagonal() {
        int[][] matrix = {
                {2, 100, 100},
                {100, 3, 100},
                {100, 100, 4}
        };

        assertEquals(9L, Main.calculateMainDiagonalSum(matrix));
    }

    @Test
    void sumsNegativeNumbers() {
        int[][] matrix = {
                {-2, 7},
                {8, -5}
        };

        assertEquals(-7L, Main.calculateMainDiagonalSum(matrix));
    }

    @Test
    void sumsZeros() {
        int[][] matrix = {
                {0, 0},
                {0, 0}
        };

        assertEquals(0L, Main.calculateMainDiagonalSum(matrix));
    }

    @Test
    void handlesSingleElement() {
        int[][] matrix = {{7}};

        assertEquals(7L, Main.calculateMainDiagonalSum(matrix));
    }

    @Test
    void handlesLargeSum() {
        int[][] matrix = {
                {Integer.MAX_VALUE, 0},
                {0, Integer.MAX_VALUE}
        };

        assertEquals(4294967294L, Main.calculateMainDiagonalSum(matrix));
    }

    @Test
    void readsMatrixAndPrintsSum() {
        String input = "3\n1 2 3\n4 5 6\n7 8 10\n";
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

        assertEquals("16" + System.lineSeparator(), output.toString(StandardCharsets.UTF_8));
    }
}