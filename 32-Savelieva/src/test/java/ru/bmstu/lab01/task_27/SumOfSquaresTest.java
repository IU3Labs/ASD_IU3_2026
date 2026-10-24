package ru.bmstu.lab01.task_27;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigInteger;
import org.junit.jupiter.api.Test;

class SumOfSquaresTest {

    @Test
    void shouldSumSquaresOfPositiveNumbers() {
        assertEquals(BigInteger.valueOf(30), SumOfSquares.sumOfSquares(new int[] {1, 2, 3, 4}));
    }

    @Test
    void shouldSumSquaresOfNegativeNumbers() {
        // (-3)^2 + (-4)^2 = 25
        assertEquals(BigInteger.valueOf(25), SumOfSquares.sumOfSquares(new int[] {-3, -4}));
    }

    @Test
    void shouldHandleZero() {
        assertEquals(BigInteger.ZERO, SumOfSquares.sumOfSquares(new int[] {0}));
    }

    @Test
    void shouldHandleSingleElement() {
        assertEquals(BigInteger.valueOf(49), SumOfSquares.sumOfSquares(new int[] {7}));
    }

    @Test
    void shouldHandleIntegerMaxValue() {
        // MAX_VALUE^2 — уже не помещается в long? Помещается (4.6·10^18 < 9.2·10^18),
        // но сумма двух таких — уже нет.
        BigInteger expected = BigInteger.valueOf(Integer.MAX_VALUE).pow(2);
        assertEquals(expected, SumOfSquares.sumOfSquares(new int[] {Integer.MAX_VALUE}));
    }

    @Test
    void shouldExceedLongRangeForMultipleMaxValues() {
        // Два MAX_VALUE в квадрате: 2 · (2^31 - 1)^2 ≈ 9.2·10^18 — близко к Long.MAX,
        // три — уже точно не помещаются в long.
        BigInteger expected = BigInteger.valueOf(Integer.MAX_VALUE).pow(2).multiply(BigInteger.valueOf(3));
        assertEquals(expected, SumOfSquares.sumOfSquares(new int[] {
                Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE}));
    }

    @Test
    void shouldHandleMinValue() {
        // |MIN_VALUE|^2 = 2^62 — помещается в long, но BigInteger надёжнее
        BigInteger expected = BigInteger.valueOf(Integer.MIN_VALUE).pow(2);
        assertEquals(expected, SumOfSquares.sumOfSquares(new int[] {Integer.MIN_VALUE}));
    }

    @Test
    void shouldRejectNull() {
        assertThrows(IllegalArgumentException.class, () -> SumOfSquares.sumOfSquares(null));
    }

    @Test
    void shouldRejectEmptyArray() {
        assertThrows(IllegalArgumentException.class, () -> SumOfSquares.sumOfSquares(new int[0]));
    }
}