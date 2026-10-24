package ru.bmstu.lab1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Task50Test {

    @Test
    void shouldCalculateColSumsForRegularMatrix() {
        int[][] matrix = {
            {1, 2, 3},
            {4, 5, 6}
        };
        int[] exp = {5, 7, 9};
        Assertions.assertArrayEquals(exp, Task50.colSums(matrix));
    }

    @Test
    void shouldCalculateColSumsWithNegatives() {
        int[][] matrix = {
            {-2, 10},
            {2, -5}
        };
        int[] exp = {0, 5};
        Assertions.assertArrayEquals(exp, Task50.colSums(matrix));
    }

    @Test
    void shouldCalculateColSumsForSquareMatrix() {
        int[][] matrix = {
            {1, 4},
            {2, 5}
        };
        int[] exp = {3, 9};
        Assertions.assertArrayEquals(exp, Task50.colSums(matrix));
    }
}
