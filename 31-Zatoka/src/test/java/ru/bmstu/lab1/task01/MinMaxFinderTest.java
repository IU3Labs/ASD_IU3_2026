package ru.bmstu.lab1.task01;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class MinMaxFinderTest {

    @Test
    void shouldFindMinAndMax() {
        int[] arr = {3, -7, 12, 0, 5};
        int[] expected = {-7, 12};
        assertArrayEquals(expected, MinMaxFinder.findMinMax(arr));
    }

    @Test
    void shouldReturnSameValueWhenAllElementsEqual() {
        int[] arr = {4, 4, 4};
        int[] expected = {4, 4};
        assertArrayEquals(expected, MinMaxFinder.findMinMax(arr));
    }

    @Test
    void shouldWorkWithOnlyNegativeNumbers() {
        int[] arr = {-5, -1, -9};
        int[] expected = {-9, -1};
        assertArrayEquals(expected, MinMaxFinder.findMinMax(arr));
    }

    @Test
    void shouldWorkWithIntLimits() {
        int[] arr = {Integer.MAX_VALUE, 0, Integer.MIN_VALUE};
        int[] expected = {Integer.MIN_VALUE, Integer.MAX_VALUE};
        assertArrayEquals(expected, MinMaxFinder.findMinMax(arr));
    }

    @Test
    void shouldThrowWhenArrayIsEmpty() {
        int[] arr = {};
        assertThrows(IllegalArgumentException.class, () -> MinMaxFinder.findMinMax(arr));
    }
}
