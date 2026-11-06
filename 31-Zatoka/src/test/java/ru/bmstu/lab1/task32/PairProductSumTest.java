package ru.bmstu.lab1.task32;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;
import java.util.Random;
import org.junit.jupiter.api.Test;

class PairProductSumTest {

    @Test
    void shouldSumProductsOfAllPairs() {
        int[] arr = {1, 2, 3};
        assertEquals(BigInteger.valueOf(11), PairProductSum.sum(arr));
    }

    @Test
    void shouldWorkWithNegativeNumbers() {
        // (-2) * 3 + (-2) * 4 + 3 * 4 = -6 - 8 + 12
        int[] arr = {-2, 3, 4};
        assertEquals(BigInteger.valueOf(-2), PairProductSum.sum(arr));
    }

    @Test
    void shouldReturnZeroWhenThereAreNoPairs() {
        int[] single = {5};
        int[] empty = {};
        assertEquals(BigInteger.ZERO, PairProductSum.sum(single));
        assertEquals(BigInteger.ZERO, PairProductSum.sum(empty));
    }

    @Test
    void shouldNotOverflowOnLargeNumbers() {
        // три пары, каждая MAX * MAX: результат больше, чем вмещает long
        int[] arr = {Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE};
        BigInteger max = BigInteger.valueOf(Integer.MAX_VALUE);
        BigInteger expected = max.multiply(max).multiply(BigInteger.valueOf(3));
        assertEquals(expected, PairProductSum.sum(arr));
    }

    @Test
    void shouldMatchBruteForceOnRandomArrays() {
        Random random = new Random(32);
        for (int test = 0; test < 300; test++) {
            int[] arr = new int[2 + random.nextInt(25)];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = random.nextInt();
            }
            assertEquals(bruteForce(arr), PairProductSum.sum(arr));
        }
    }

    /** Перебор всех пар за O(n^2), чтобы проверить быстрое решение. */
    private static BigInteger bruteForce(int[] arr) {
        BigInteger result = BigInteger.ZERO;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                result = result.add(BigInteger.valueOf((long) arr[i] * arr[j]));
            }
        }
        return result;
    }
}
