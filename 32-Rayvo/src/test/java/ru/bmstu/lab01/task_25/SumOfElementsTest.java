package ru.bmstu.lab01.task_25;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SumOfElementsTest {

    @Test
    void shouldSumPositiveNumbers() {
        int[] array = {1, 2, 3, 4, 5};
        assertEquals(15L, SumOfElements.sum(array));
    }

    @Test
    void shouldSumNegativeNumbers() {
        int[] array = {-3, -7, -1, -5};
        assertEquals(-16L, SumOfElements.sum(array));
    }

    @Test
    void shouldSumMixedSigns() {
        int[] array = {5, -3, 2, -8, 4};
        assertEquals(0L, SumOfElements.sum(array));
    }

    @Test
    void shouldReturnZeroWhenAllElementsAreZero() {
        assertEquals(0L, SumOfElements.sum(new int[]{0, 0, 0, 0}));
    }

    @Test
    void shouldHandleIntegerExtremes() {
        int[] array = {Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE};
        // MAX + MAX + MIN = MAX - 1 (в long, без переполнения)
        assertEquals(2L * Integer.MAX_VALUE + Integer.MIN_VALUE, SumOfElements.sum(array));
    }

    @Test
    void shouldNotOverflowOnLargeValues() {
        int[] array = {Integer.MAX_VALUE, Integer.MAX_VALUE};
        // 2 * MAX > int, но в long помещается
        assertEquals(2L * Integer.MAX_VALUE, SumOfElements.sum(array));
    }

    @Test
    void shouldNotModifyOriginalArray() {
        int[] original = {1, 2, 3, 4, 5};
        int[] copy = original.clone();

        SumOfElements.sum(original);

        for (int i = 0; i < original.length; i++) {
            assertEquals(copy[i], original[i]);
        }
    }
}