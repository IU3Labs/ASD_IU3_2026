package ru.bmstu.lab1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Task59Test {

    @Test
    void shouldCalculateDeterminantForSimpleMatrix() {
        int[][] matrix = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        assertEquals(0L, Task59.determinant3x3(matrix));
    }

    @Test
    void shouldCalculateDeterminantWithNegativeNumbers() {
        int[][] matrix = {
            {-1, 0, 2},
            { 3, 1, 0},
            { 0, 4, 5}
        };
        assertEquals(19L, Task59.determinant3x3(matrix));
    }

    @Test
    void shouldPreventIntegerOverflow() {
        int[][] matrix = {
            {Integer.MAX_VALUE, 0, 0},
            {0, Integer.MAX_VALUE, 0},
            {0, 0, Integer.MAX_VALUE}
        };
        long expected = (long) Integer.MAX_VALUE * Integer.MAX_VALUE * Integer.MAX_VALUE;
        assertEquals(expected, Task59.determinant3x3(matrix));
    }
}