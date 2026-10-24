package ru.bmstu.lab1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Task52Test {

    @Test
    void shouldFindMinInEachColumn() {
        int[][] matrix = {
            {10, 2, 8},
            {4, 5, 6}
        };
        int[] exp = {4, 2, 6};
        Assertions.assertArrayEquals(exp, Task52.colMins(matrix));
    }

    @Test
    void shouldFindMinWithNegatives() {
        int[][] matrix = {
            {-10, 5},
            {-2, -8}
        };
        int[] exp = {-10, -8};
        Assertions.assertArrayEquals(exp, Task52.colMins(matrix));
    }

    @Test
    void shouldWorkForSquareMatrix() {
        int[][] matrix = {
            {3, 7},
            {1, 9}
        };
        int[] exp = {1, 7};
        Assertions.assertArrayEquals(exp, Task52.colMins(matrix));
    }
}
