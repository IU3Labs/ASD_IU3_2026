package ru.bmstu.lab01.task_54;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

class Task54Test {

    @Test
    void shouldReturnDiagonalSumForSquareMatrix() {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        long expected = 15; // 1 + 5 + 9
        long actual = Task54.mainDiagonalSum(matrix);
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnElementWhenMatrixIsOneByOne() {
        int[][] matrix = {{42}};
        long expected = 42;
        long actual = Task54.mainDiagonalSum(matrix);
        assertEquals(expected, actual);
    }

    @Test
    void shouldIgnoreElementsOutsideDiagonal() {
        int[][] matrix = {
                {0, 100, 100},
                {-100, 0, 100},
                {100, -100, 0}
        };
        long expected = 0;
        long actual = Task54.mainDiagonalSum(matrix);
        assertEquals(expected, actual);
    }

    @Test
    void shouldHandleNegativeNumbers() {
        int[][] matrix = {
                {-1, 2},
                {3, -4}
        };
        long expected = -5;
        long actual = Task54.mainDiagonalSum(matrix);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotOverflowForLargeValues() {
        int[][] matrix = {
                {Integer.MAX_VALUE, 0},
                {0, Integer.MAX_VALUE}
        };
        long expected = 2L * Integer.MAX_VALUE;
        long actual = Task54.mainDiagonalSum(matrix);
        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowExceptionWhenMatrixIsNotSquare() {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6}
        };
        assertThrows(IllegalArgumentException.class, () -> Task54.mainDiagonalSum(matrix));
    }

    @Test
    void shouldPrintResultWhenInputIsGiven() {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream("3\n1 2 3\n4 5 6\n7 8 9".getBytes()));
            System.setOut(new PrintStream(output));

            Task54.main(new String[0]);

            assertEquals("15", output.toString().trim());
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
    }
}