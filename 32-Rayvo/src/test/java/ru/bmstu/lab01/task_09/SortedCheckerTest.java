package ru.bmstu.lab01.task_09;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SortedCheckerTest {

    @Test
    void shouldReturnTrueForStrictlyIncreasingArray() {
        int[] array = {1, 3, 5, 7, 9};
        assertTrue(SortedChecker.isNonDecreasing(array));
    }

    @Test
    void shouldReturnTrueForNonDecreasingArrayWithDuplicates() {
        int[] array = {1, 2, 2, 5, 5, 9};
        assertTrue(SortedChecker.isNonDecreasing(array));
    }

    @Test
    void shouldReturnTrueForAllEqualElements() {
        int[] array = {4, 4, 4, 4, 4};
        assertTrue(SortedChecker.isNonDecreasing(array));
    }

    @Test
    void shouldReturnTrueForArrayWithOneElement() {
        assertTrue(SortedChecker.isNonDecreasing(new int[]{42}));
    }

    @Test
    void shouldReturnFalseForStrictlyDecreasingArray() {
        int[] array = {9, 7, 5, 3, 1};
        assertFalse(SortedChecker.isNonDecreasing(array));
    }

    @Test
    void shouldReturnFalseWhenViolationIsAtStart() {
        int[] array = {5, 3, 4, 6, 7};
        assertFalse(SortedChecker.isNonDecreasing(array));
    }

    @Test
    void shouldReturnFalseWhenViolationIsAtEnd() {
        int[] array = {1, 2, 3, 4, 2};
        assertFalse(SortedChecker.isNonDecreasing(array));
    }

    @Test
    void shouldReturnFalseWhenViolationIsInMiddle() {
        int[] array = {1, 2, 9, 4, 5};
        assertFalse(SortedChecker.isNonDecreasing(array));
    }

    @Test
    void shouldReturnFalseForUnsortedArray() {
        int[] array = {3, 1, 4, 2, 5};
        assertFalse(SortedChecker.isNonDecreasing(array));
    }

    @Test
    void shouldHandleNegativeNumbersInNonDecreasingArray() {
        int[] array = {-10, -5, -5, -1, 0, 3};
        assertTrue(SortedChecker.isNonDecreasing(array));
    }

    @Test
    void shouldHandleNegativeNumbersInUnsortedArray() {
        int[] array = {-3, -7, -1, -5};
        assertFalse(SortedChecker.isNonDecreasing(array));
    }

    @Test
    void shouldHandleTwoEqualElements() {
        assertTrue(SortedChecker.isNonDecreasing(new int[]{5, 5}));
    }

    @Test
    void shouldHandleTwoElementsInWrongOrder() {
        assertFalse(SortedChecker.isNonDecreasing(new int[]{5, 3}));
    }

    @Test
    void shouldDistinguishNonDecreasingFromStrictlyIncreasing() {
        int[] withDuplicates = {1, 2, 2, 3};
        assertTrue(SortedChecker.isNonDecreasing(withDuplicates));
    }

    @Test
    void shouldNotModifyOriginalArray() {
        int[] original = {1, 3, 5, 7, 9};
        int[] copy = original.clone();

        SortedChecker.isNonDecreasing(original);

        for (int i = 0; i < original.length; i++) {
            assertTrue(copy[i] == original[i]);
        }
    }
}