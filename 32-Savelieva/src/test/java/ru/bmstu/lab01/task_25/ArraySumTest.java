package ru.bmstu.lab01.task_25;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ArraySumTest {

    @Test
    void shouldSumPositiveNumbers() {
        assertEquals(15L, ArraySum.sum(new int[] {1, 2, 3, 4, 5}));
    }

    @Test
    void shouldSumNegativeNumbers() {
        assertEquals(-12L, ArraySum.sum(new int[] {-3, -4, -5}));
    }

    @Test
    void shouldSumMixedSigns() {
        assertEquals(3L, ArraySum.sum(new int[] {-7, 5, 5, 0}));
    }

    @Test
    void shouldReturnZeroForSingleZero() {
        assertEquals(0L, ArraySum.sum(new int[] {0}));
    }

    @Test
    void shouldReturnElementForSingleElement() {
        assertEquals(42L, ArraySum.sum(new int[] {42}));
    }

    @Test
    void shouldNotOverflowIntRange() {
        // сумма трёх MAX_VALUE не помещается в int
        long expected = 3L * Integer.MAX_VALUE;
        assertEquals(expected, ArraySum.sum(new int[] {
                Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE}));
    }

    @Test
    void shouldHandleExtremeIntValues() {
        long expected = (long) Integer.MIN_VALUE + Integer.MAX_VALUE;
        assertEquals(expected, ArraySum.sum(new int[] {Integer.MIN_VALUE, Integer.MAX_VALUE}));
    }

    @Test
    void shouldRejectNull() {
        assertThrows(IllegalArgumentException.class, () -> ArraySum.sum(null));
    }

    @Test
    void shouldRejectEmptyArray() {
        assertThrows(IllegalArgumentException.class, () -> ArraySum.sum(new int[0]));
    }
}