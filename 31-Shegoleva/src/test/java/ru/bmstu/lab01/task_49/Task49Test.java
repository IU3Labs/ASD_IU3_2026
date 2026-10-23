package ru.bmstu.lab01.task_49;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

class Task49Test {

    @Test
    void shouldReturnRowSumsForRectangularMatrix() {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6}
        };
        long[] expected = {6, 15};
        long[] actual = Task49.rowSums(matrix);
        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldReturnSingleSumWhenMatrixHasOneRow() {
        int[][] matrix = {{1, 2, 3, 4}};
        long[] expected = {10};
        long[] actual = Task49.rowSums(matrix);
        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldReturnElementsWhenMatrixHasOneColumn() {
        int[][] matrix = {{7}, {-2}, {5}};
        long[] expected = {7, -2, 5};
        long[] actual = Task49.rowSums(matrix);
        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldHandleNegativeNumbers() {
        int[][] matrix = {
                {-1, -2, 3},
                {4, -5, -6}
        };
        long[] expected = {0, -7};
        long[] actual = Task49.rowSums(matrix);
        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldReturnEmptyArrayWhenMatrixIsEmpty() {
        int[][] matrix = {};
        long[] expected = {};
        long[] actual = Task49.rowSums(matrix);
        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldNotOverflowForLargeValues() {
        int[][] matrix = {{Integer.MAX_VALUE, Integer.MAX_VALUE}};
        long[] expected = {2L * Integer.MAX_VALUE};
        long[] actual = Task49.rowSums(matrix);
        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldPrintResultWhenInputIsGiven() {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream("2 3\n1 2 3\n4 5 6".getBytes()));
            System.setOut(new PrintStream(output));

            Task49.main(new String[0]);

            assertEquals("[6, 15]", output.toString().trim());
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
    }
}