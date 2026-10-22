package ru.bmstu.lab01.task_57;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ru.bmstu.lab01.common.ConsoleRunner;

class SpiralTraversalTest {

    @Test
    void shouldTraverseSquareMatrix() {
        int[][] matrix = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9},
        };
        int[] expected = {1, 2, 3, 6, 9, 8, 7, 4, 5};

        assertArrayEquals(expected, SpiralTraversal.traverse(matrix));
    }

    @Test
    void shouldTraverseWideMatrix() {
        int[][] matrix = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
        };
        int[] expected = {1, 2, 3, 4, 8, 12, 11, 10, 9, 5, 6, 7};

        assertArrayEquals(expected, SpiralTraversal.traverse(matrix));
    }

    @Test
    void shouldTraverseTallMatrix() {
        int[][] matrix = {
            {1, 2},
            {3, 4},
            {5, 6},
            {7, 8},
        };
        int[] expected = {1, 2, 4, 6, 8, 7, 5, 3};

        assertArrayEquals(expected, SpiralTraversal.traverse(matrix));
    }

    @Test
    void shouldNotRepeatElementsOfSingleRow() {
        assertArrayEquals(new int[] {1, 2, 3}, SpiralTraversal.traverse(new int[][] {{1, 2, 3}}));
    }

    @Test
    void shouldNotRepeatElementsOfSingleColumn() {
        assertArrayEquals(new int[] {1, 2, 3}, SpiralTraversal.traverse(new int[][] {{1}, {2}, {3}}));
    }

    @Test
    void shouldReturnEmptyArrayForEmptyMatrix() {
        assertEquals(0, SpiralTraversal.traverse(new int[0][]).length);
    }

    @Test
    void shouldReadMatrixAndPrintSpiral() {
        assertEquals("1 2 4 3", ConsoleRunner.run(SpiralTraversal::run, "2 2\n1 2\n3 4\n"));
    }
}
