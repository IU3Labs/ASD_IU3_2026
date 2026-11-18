package ru.bmstu.lab1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Task49Test {

    @Test
    void shouldCalculateRowSumsForRegularMatrix() {
        int[][] matrix = {
            {1, 2, 3},
            {4, 5, 6}
        };
        int[] exp = {6, 15};
        Assertions.assertArrayEquals(exp, Task49.rowSums(matrix));
    }

    @Test
    void shouldCalculateRowSumsWithNegatives() {
        int[][] matrix = {
            {-1, 1},
            {5, -5}
        };
        int[] exp = {0, 0};
        Assertions.assertArrayEquals(exp, Task49.rowSums(matrix));
    }

    @Test
    void shouldCalculateRowSumsForSquareMatrix() {
        int[][] matrix = {
            {2, 3},
            {4, 1}
        };
        int[] exp = {5, 5};
        Assertions.assertArrayEquals(exp, Task49.rowSums(matrix));
    }
}
