package ru.bmstu.lab1.task19;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

class SortedMergerTest {

    @Test
    void shouldMergeInterleavedArrays() {
        int[] first = {1, 4, 7};
        int[] second = {2, 3, 8, 9};
        int[] expected = {1, 2, 3, 4, 7, 8, 9};
        assertArrayEquals(expected, SortedMerger.merge(first, second));
    }

    @Test
    void shouldMergeWhenFirstArrayIsSmaller() {
        int[] first = {1, 2};
        int[] second = {5, 6, 7};
        int[] expected = {1, 2, 5, 6, 7};
        assertArrayEquals(expected, SortedMerger.merge(first, second));
    }

    @Test
    void shouldMergeWhenSecondArrayIsSmaller() {
        int[] first = {5, 6, 7};
        int[] second = {1, 2};
        int[] expected = {1, 2, 5, 6, 7};
        assertArrayEquals(expected, SortedMerger.merge(first, second));
    }

    @Test
    void shouldKeepDuplicates() {
        int[] first = {1, 3, 3};
        int[] second = {3, 3, 4};
        int[] expected = {1, 3, 3, 3, 3, 4};
        assertArrayEquals(expected, SortedMerger.merge(first, second));
    }

    @Test
    void shouldWorkWithNegativeNumbers() {
        int[] first = {-5, 0};
        int[] second = {-7, -1, 2};
        int[] expected = {-7, -5, -1, 0, 2};
        assertArrayEquals(expected, SortedMerger.merge(first, second));
    }

    @Test
    void shouldReturnOtherArrayWhenOneIsEmpty() {
        int[] first = {};
        int[] second = {1, 2, 3};
        int[] expected = {1, 2, 3};
        assertArrayEquals(expected, SortedMerger.merge(first, second));
        assertArrayEquals(expected, SortedMerger.merge(second, first));
    }
}
