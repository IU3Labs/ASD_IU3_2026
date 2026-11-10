
package ru.bmstu.lab1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Task27Test {

    @Test
    void shouldReturnSumOfSquares() {
        assertEquals(26, Task27.sumOfSquares(new int[]{3, 4, 1}));
    }

    @Test
    void shouldReturnZeroForEmptyArray() {
        assertEquals(0, Task27.sumOfSquares(new int[]{}));
    }

    @Test
    void shouldHandleNegativeNumbers() {
        assertEquals(14, Task27.sumOfSquares(new int[]{-1, 2, -3}));
    }
}
