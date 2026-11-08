package ru.bmstu.lab01.task_32;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigInteger;
import org.junit.jupiter.api.Test;

class SumOfPairProductsTest {

    @Test
    void shouldComputeSumOfPairProducts() {
        // 1*2 + 1*3 + 2*3 = 2 + 3 + 6 = 11
        assertEquals(BigInteger.valueOf(11),
                SumOfPairProducts.sumOfPairProducts(new int[] {1, 2, 3}));
    }

    @Test
    void shouldHandleTwoElements() {
        assertEquals(BigInteger.valueOf(25),
                SumOfPairProducts.sumOfPairProducts(new int[] {5, 5}));
    }

    @Test
    void shouldReturnZeroForSingleElement() {
        assertEquals(BigInteger.ZERO,
                SumOfPairProducts.sumOfPairProducts(new int[] {5}));
    }

    @Test
    void shouldHandleNegativeValues() {
        // пары: (0,1): 1*(-1)=-1, (0,2): 1*2=2, (0,3): 1*(-2)=-2,
        //       (1,2): -1*2=-2, (1,3): -1*-2=2, (2,3): 2*-2=-4
        // сумма: -1+2-2-2+2-4 = -5
        assertEquals(BigInteger.valueOf(-5),
                SumOfPairProducts.sumOfPairProducts(new int[] {1, -1, 2, -2}));
    }

    @Test
    void shouldHandleAllZeros() {
        assertEquals(BigInteger.ZERO,
                SumOfPairProducts.sumOfPairProducts(new int[] {0, 0, 0}));
    }

    @Test
    void shouldHandleAllEqual() {
        // {3,3,3,3}: пар 6, каждая 3*3=9, итого 54
        assertEquals(BigInteger.valueOf(54),
                SumOfPairProducts.sumOfPairProducts(new int[] {3, 3, 3, 3}));
    }

    @Test
    void shouldMatchBruteForceForSmallArray() {
        int[] data = {7, -2, 5, 11, -3};
        BigInteger expected = BigInteger.ZERO;
        for (int i = 0; i < data.length; i++) {
            for (int j = i + 1; j < data.length; j++) {
                expected = expected.add(
                        BigInteger.valueOf(data[i]).multiply(BigInteger.valueOf(data[j])));
            }
        }
        assertEquals(expected, SumOfPairProducts.sumOfPairProducts(data));
    }

    @Test
    void shouldHandleLargeValuesWithoutOverflow() {
        BigInteger max = BigInteger.valueOf(Integer.MAX_VALUE);
        // 3 элемента MAX_VALUE: пар 3, каждая MAX_VALUE²
        BigInteger expected = max.multiply(max).multiply(BigInteger.valueOf(3));
        assertEquals(expected, SumOfPairProducts.sumOfPairProducts(
                new int[] {Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE}));
    }

    @Test
    void shouldRejectNull() {
        assertThrows(IllegalArgumentException.class,
                () -> SumOfPairProducts.sumOfPairProducts(null));
    }

    @Test
    void shouldRejectEmptyArray() {
        assertThrows(IllegalArgumentException.class,
                () -> SumOfPairProducts.sumOfPairProducts(new int[0]));
    }
}