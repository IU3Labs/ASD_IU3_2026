package ru.bmstu.lab1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Task31Test {

    @Test
    void shouldCalculateDotProduct() {
        assertEquals(32, Task31.dotProduct(
                new int[]{1, 2, 3},
                new int[]{4, 5, 6}
        ));
    }

    @Test
    void shouldReturnZeroForEmptyArrays() {
        assertEquals(0, Task31.dotProduct(
                new int[]{},
                new int[]{}
        ));
    }

    @Test
    void shouldHandleNegativeNumbers() {
        assertEquals(-20, Task31.dotProduct(
                new int[]{-1, 2, -3},
                new int[]{4, -5, 2}
        ));
    }
}
