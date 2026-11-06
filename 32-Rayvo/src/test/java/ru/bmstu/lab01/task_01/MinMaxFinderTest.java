package ru.bmstu.lab01.task_01;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MinMaxFinderTest {

    @Test
    void shouldFindMinAndMaxInUnsortedArray() {
        int[] array = {7, 3, 12, 1, 9};
        MinMaxFinder.MinMax result = MinMaxFinder.findMinMax(array);

        assertEquals(1, result.min());
        assertEquals(12, result.max());
    }

    @Test
    void shouldReturnSameValueWhenArrayHasOneElement() {
        int[] array = {17};
        MinMaxFinder.MinMax result = MinMaxFinder.findMinMax(array);

        assertEquals(17, result.min());
        assertEquals(17, result.max());
    }

    @Test
    void shouldHandleAllEqualElements() {
        int[] array = {6, 6, 6, 6};
        MinMaxFinder.MinMax result = MinMaxFinder.findMinMax(array);

        assertEquals(6, result.min());
        assertEquals(6, result.max());
    }

    @Test
    void shouldFindMinAndMaxInSortedAscendingArray() {
        int[] array = {2, 4, 6, 8, 10};
        MinMaxFinder.MinMax result = MinMaxFinder.findMinMax(array);

        assertEquals(2, result.min());
        assertEquals(10, result.max());
    }

    @Test
    void shouldFindMinAndMaxInSortedDescendingArray() {
        int[] array = {10, 8, 6, 4, 2};
        MinMaxFinder.MinMax result = MinMaxFinder.findMinMax(array);

        assertEquals(2, result.min());
        assertEquals(10, result.max());
    }

    @Test
    void shouldHandleNegativeNumbers() {
        int[] array = {-5, -20, -3, -12};
        MinMaxFinder.MinMax result = MinMaxFinder.findMinMax(array);

        assertEquals(-20, result.min());
        assertEquals(-3, result.max());
    }

    @Test
    void shouldHandleMixedPositiveAndNegativeNumbers() {
        int[] array = {-4, 7, 0, 12, -9};
        MinMaxFinder.MinMax result = MinMaxFinder.findMinMax(array);

        assertEquals(-9, result.min());
        assertEquals(12, result.max());
    }

    @Test
    void shouldNotModifyOriginalArray() {
        int[] original = {7, 3, 12, 1, 9};
        int[] copy = original.clone();

        MinMaxFinder.findMinMax(original);

        for (int i = 0; i < original.length; i++) {
            assertEquals(copy[i], original[i]);
        }
    }
}