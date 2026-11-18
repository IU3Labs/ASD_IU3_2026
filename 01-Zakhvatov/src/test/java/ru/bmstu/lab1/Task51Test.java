package ru.bmstu.lab1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Task51Test {

    @Test
    void shouldFindMaxInEachRow() {
        int[][] matrix = {
            {1, 9, 3},
            {7, 2, 5}
        };
        int[] exp = {9, 7};
        Assertions.assertArrayEquals(exp, Task51.rowMaxes(matrix));
    }

    @Test
    void shouldFindMaxWithAllNegativeNumbers() {
        int[][] matrix = {
            {-10, -5, -20},
            {-3, -8, -1}
        };
        int[] exp = {-5, -1};
        Assertions.assertArrayEquals(exp, Task51.rowMaxes(matrix));
    }

    @Test
    void shouldWorkForSquareMatrix() {
        int[][] matrix = {
            {4, 4},
            {2, 8}
        };
        int[] exp = {4, 8};
        Assertions.assertArrayEquals(exp, Task51.rowMaxes(matrix));
    }
}
