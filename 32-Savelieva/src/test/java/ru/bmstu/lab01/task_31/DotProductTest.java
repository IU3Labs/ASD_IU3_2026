package ru.bmstu.lab01.task_31;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigInteger;
import org.junit.jupiter.api.Test;

class DotProductTest {

    @Test
    void shouldComputeSimpleDotProduct() {
        assertEquals(BigInteger.valueOf(32),
                DotProduct.dotProduct(new int[] {1, 2, 3}, new int[] {4, 5, 6}));
    }

    @Test
    void shouldHandleNegativeValues() {
        // -1*3 + 2*(-4) = -3 - 8 = -11
        assertEquals(BigInteger.valueOf(-11),
                DotProduct.dotProduct(new int[] {-1, 2}, new int[] {3, -4}));
    }

    @Test
    void shouldReturnZeroForZeroVector() {
        assertEquals(BigInteger.ZERO,
                DotProduct.dotProduct(new int[] {0, 0, 0}, new int[] {5, 6, 7}));
    }

    @Test
    void shouldHandleSingleElement() {
        assertEquals(BigInteger.valueOf(20),
                DotProduct.dotProduct(new int[] {4}, new int[] {5}));
    }

    @Test
    void shouldHandleBothNegative() {
        // (-3)*(-4) = 12
        assertEquals(BigInteger.valueOf(12),
                DotProduct.dotProduct(new int[] {-3}, new int[] {-4}));
    }

    @Test
    void shouldHandleIntegerMaxValuesWithoutOverflow() {
        BigInteger expected = BigInteger.valueOf(Integer.MAX_VALUE)
                .multiply(BigInteger.valueOf(Integer.MAX_VALUE));
        assertEquals(expected, DotProduct.dotProduct(
                new int[] {Integer.MAX_VALUE}, new int[] {Integer.MAX_VALUE}));
    }

    @Test
    void shouldExceedLongForManyMaxValues() {
        BigInteger max = BigInteger.valueOf(Integer.MAX_VALUE);
        BigInteger expected = max.multiply(max).multiply(BigInteger.valueOf(3));
        assertEquals(expected, DotProduct.dotProduct(
                new int[] {Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE},
                new int[] {Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE}));
    }

    @Test
    void shouldRejectDifferentLengths() {
        assertThrows(IllegalArgumentException.class,
                () -> DotProduct.dotProduct(new int[] {1, 2}, new int[] {3}));
    }

    @Test
    void shouldRejectNull() {
        assertThrows(IllegalArgumentException.class,
                () -> DotProduct.dotProduct(null, new int[] {1}));
    }

    @Test
    void shouldRejectEmptyArrays() {
        assertThrows(IllegalArgumentException.class,
                () -> DotProduct.dotProduct(new int[0], new int[0]));
    }
}