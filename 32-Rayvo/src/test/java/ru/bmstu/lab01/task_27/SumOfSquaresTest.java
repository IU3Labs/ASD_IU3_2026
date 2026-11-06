package ru.bmstu.lab01.task_27;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SumOfSquaresTest {

    @Test
    void shouldSumSquaresOfPositiveNumbers() {
        int[] array = {1, 2, 3, 4};
        // 1 + 4 + 9 + 16 = 30
        assertEquals(30L, SumOfSquares.sumOfSquares(array));
    }

    @Test
    void shouldSumSquaresOfNegativeNumbers() {
        int[] array = {-3, -4};
        // 9 + 16 = 25 — знак не важен, квадрат всегда неотрицателен
        assertEquals(25L, SumOfSquares.sumOfSquares(array));
    }

    @Test
    void shouldSumSquaresOfMixedSigns() {
        int[] array = {-2, 3, -1, 4};
        // 4 + 9 + 1 + 16 = 30
        assertEquals(30L, SumOfSquares.sumOfSquares(array));
    }

    @Test
    void shouldReturnZeroWhenAllElementsAreZero() {
        int[] array = {0, 0, 0, 0};
        assertEquals(0L, SumOfSquares.sumOfSquares(array));
    }

    @Test
    void shouldHandleSquareThatOverflowsInt() {
        // 50000^2 = 2_500_000_000 > Integer.MAX_VALUE (2_147_483_647)
        int[] array = {50000};
        assertEquals(2_500_000_000L, SumOfSquares.sumOfSquares(array));
    }

    @Test
    void shouldHandleNegativeValueWhoseSquareOverflowsInt() {
        // (-50000)^2 = 2_500_000_000 > int
        int[] array = {-50000};
        assertEquals(2_500_000_000L, SumOfSquares.sumOfSquares(array));
    }

    @Test
    void shouldHandleIntegerMinValue() {
        // MIN_VALUE^2 = 2^62, помещается в long
        int[] array = {Integer.MIN_VALUE};
        assertEquals(1L << 62, SumOfSquares.sumOfSquares(array));
    }

    @Test
    void shouldHandleLargeSumWithoutOverflow() {
        // 3 * (10^9)^2 = 3 * 10^18 < Long.MAX_VALUE ≈ 9.22 * 10^18
        int[] array = {1_000_000_000, 1_000_000_000, 1_000_000_000};
        assertEquals(3_000_000_000_000_000_000L, SumOfSquares.sumOfSquares(array));
    }

    @Test
    void shouldNotModifyOriginalArray() {
        int[] original = {-2, 3, -1, 4};
        int[] copy = original.clone();

        SumOfSquares.sumOfSquares(original);

        for (int i = 0; i < original.length; i++) {
            assertEquals(copy[i], original[i]);
        }
    }
}