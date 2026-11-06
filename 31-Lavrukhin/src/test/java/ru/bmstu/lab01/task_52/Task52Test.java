package ru.bmstu.lab01.task_52;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import ru.bmstu.lab01.util.ConsoleHelper;

class Task52Test {

    @Test
    void shouldFindMinimumOfEachColumn() {
        int[][] matrix = {
            {5, 1, 9},
            {2, 8, 7},
            {6, 3, 4},
        };

        assertArrayEquals(new int[] {2, 1, 4}, Task52.columnMinimums(matrix));
    }

    @Test
    void shouldWorkWithNegativeNumbers() {
        int[][] matrix = {{-1, -10}, {-5, 3}};

        assertArrayEquals(new int[] {-5, -10}, Task52.columnMinimums(matrix));
    }

    @Test
    void shouldReturnRowItselfForOneRow() {
        assertArrayEquals(new int[] {4, 0, -2}, Task52.columnMinimums(new int[][] {{4, 0, -2}}));
    }

    @Test
    void shouldWorkForOneColumn() {
        assertArrayEquals(new int[] {-3}, Task52.columnMinimums(new int[][] {{2}, {-3}, {8}}));
    }

    @Test
    void shouldWorkWithExtremeValues() {
        int[][] matrix = {{Integer.MAX_VALUE, 0}, {Integer.MIN_VALUE, Integer.MAX_VALUE}};

        assertArrayEquals(new int[] {Integer.MIN_VALUE, 0}, Task52.columnMinimums(matrix));
    }

    @Test
    void shouldThrowForMatrixWithDifferentRowLengths() {
        assertThrows(IllegalArgumentException.class, () -> Task52.columnMinimums(new int[][] {{1, 2}, {3}}));
    }

    @Test
    void shouldReadInputAndPrintMinimums() {
        String output = ConsoleHelper.run(() -> Task52.main(new String[0]), "2 3\n5 1 9\n2 8 7\n");

        assertEquals("2 1 7", output);
    }
}
