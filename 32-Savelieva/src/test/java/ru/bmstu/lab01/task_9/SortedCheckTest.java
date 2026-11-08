package ru.bmstu.lab01.task_9;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class SortedCheckTest {

    @Test
    void shouldReturnTrueForStrictlyIncreasingArray() {
        assertTrue(SortedCheck.isNonDecreasing(new int[] {1, 2, 3, 4, 5}));
    }

    @Test
    void shouldReturnTrueWhenEqualNeighboursPresent() {
        assertTrue(SortedCheck.isNonDecreasing(new int[] {1, 2, 2, 3, 5}));
    }

    @Test
    void shouldReturnFalseWhenOrderBrokenInMiddle() {
        assertFalse(SortedCheck.isNonDecreasing(new int[] {1, 3, 2, 4, 5}));
    }

    @Test
    void shouldReturnFalseForStrictlyDecreasingArray() {
        assertFalse(SortedCheck.isNonDecreasing(new int[] {5, 4, 3, 2, 1}));
    }

    @Test
    void shouldReturnTrueForSingleElement() {
        assertTrue(SortedCheck.isNonDecreasing(new int[] {7}));
    }

    @Test
    void shouldReturnTrueWhenAllElementsEqual() {
        assertTrue(SortedCheck.isNonDecreasing(new int[] {0, 0, 0}));
    }

    @Test
    void shouldReturnTrueForPairInOrder() {
        assertTrue(SortedCheck.isNonDecreasing(new int[] {-5, -1}));
    }

    @Test
    void shouldReturnFalseForPairOutOfOrder() {
        assertFalse(SortedCheck.isNonDecreasing(new int[] {0, -1}));
    }

    @Test
    void shouldReturnFalseWhenOnlyLastBreaksOrder() {
        assertFalse(SortedCheck.isNonDecreasing(new int[] {1, 2, 3, 4, 0}));
    }

    @Test
    void shouldRejectNull() {
        assertThrows(IllegalArgumentException.class, () -> SortedCheck.isNonDecreasing(null));
    }

    @Test
    void shouldRejectEmptyArray() {
        assertThrows(IllegalArgumentException.class,
                () -> SortedCheck.isNonDecreasing(new int[0]));
    }
}