package ru.bmstu.lab1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Task25Test {

    @Test
    void shouldReturnSumForPositiveNumbers() {
        assertEquals(10, Task25.sum(new int[]{2, 3, 5}));
    }

    @Test
    void shouldReturnZeroForEmptyArray() {
        assertEquals(0, Task25.sum(new int[]{}));
    }

    @Test
    void shouldHandleNegativeNumbers() {
        assertEquals(2, Task25.sum(new int[]{-2, 5, -1}));
    }
}
