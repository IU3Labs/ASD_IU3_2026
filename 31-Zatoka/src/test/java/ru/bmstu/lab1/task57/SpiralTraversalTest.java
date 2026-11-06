package ru.bmstu.lab1.task57;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

class SpiralTraversalTest {

    @Test
    void shouldTraverseSquareMatrix() {
        int[][] matrix = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        int[] expected = {1, 2, 3, 6, 9, 8, 7, 4, 5};
        assertArrayEquals(expected, SpiralTraversal.traverse(matrix));
    }

    @Test
    void shouldTraverseWideMatrix() {
        int[][] matrix = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12}
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
            {7, 8}
        };
        int[] expected = {1, 2, 4, 6, 8, 7, 5, 3};
        assertArrayEquals(expected, SpiralTraversal.traverse(matrix));
    }

    @Test
    void shouldTraverseSingleRow() {
        int[][] matrix = {{1, 2, 3}};
        int[] expected = {1, 2, 3};
        assertArrayEquals(expected, SpiralTraversal.traverse(matrix));
    }

    @Test
    void shouldTraverseSingleColumn() {
        int[][] matrix = {{1}, {2}, {3}};
        int[] expected = {1, 2, 3};
        assertArrayEquals(expected, SpiralTraversal.traverse(matrix));
    }

    @Test
    void shouldTraverseSingleElement() {
        int[][] matrix = {{42}};
        int[] expected = {42};
        assertArrayEquals(expected, SpiralTraversal.traverse(matrix));
    }

    @Test
    void shouldReturnEmptyArrayForEmptyMatrix() {
        int[][] matrix = {};
        int[] expected = {};
        assertArrayEquals(expected, SpiralTraversal.traverse(matrix));
    }
}
