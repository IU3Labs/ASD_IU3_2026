package ru.bmstu.lab1.task25;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ArraySumTest {

    @Test
    void shouldSumPositiveNumbers() {
        int[] arr = {1, 2, 3, 4, 5};
        assertEquals(15L, ArraySum.sum(arr));
    }

    @Test
    void shouldSumNegativeAndPositiveNumbers() {
        int[] arr = {-5, 10, -3};
        assertEquals(2L, ArraySum.sum(arr));
    }

    @Test
    void shouldReturnZeroForEmptyArray() {
        int[] arr = {};
        assertEquals(0L, ArraySum.sum(arr));
    }

    @Test
    void shouldNotOverflowOnLargeNumbers() {
        int[] arr = {Integer.MAX_VALUE, Integer.MAX_VALUE};
        long expected = 2L * Integer.MAX_VALUE;
        assertEquals(expected, ArraySum.sum(arr));
    }
}
