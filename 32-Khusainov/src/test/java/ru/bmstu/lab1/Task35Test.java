package ru.bmstu.lab1;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import org.junit.jupiter.api.Test;

class Task35Test {

    @Test
    void shouldReturnCorrectSumsForQueries() {
        int[] array = {1, 2, 3, 4, 5};
        int[][] queries = {
            {0, 2}, // сумма 1+2+3 = 6
            {1, 3}, // сумма 2+3+4 = 9
            {2, 4}  // сумма 3+4+5 = 12
        };
        long[] expected = {6L, 9L, 12L};
        
        assertArrayEquals(expected, Task35.rangeSumQueries(array, queries));
    }

    @Test
    void shouldWorkWithNegativeNumbersAndSingleElements() {
        int[] array = {-1, -2, -3};
        int[][] queries = {
            {0, 0}, // -1
            {1, 1}, // -2
            {0, 2}  // -6
        };
        long[] expected = {-1L, -2L, -6L};
        
        assertArrayEquals(expected, Task35.rangeSumQueries(array, queries));
    }

    @Test
    void shouldPreventOverflowOnMaximumAllowedLength() {
        int[] array = new int[99_999];
        for (int i = 0; i < array.length; i++) {
            array[i] = Integer.MAX_VALUE;
        }
        int[][] queries = {{0, array.length - 1}};
        
        long expectedSum = 99_999L * Integer.MAX_VALUE;
        long[] expected = {expectedSum};
        
        assertArrayEquals(expected, Task35.rangeSumQueries(array, queries));
    }
}