package ru.bmstu.lab1.task34;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;

class MaxSubarraySumTest {

    @Test
    void shouldFindClassicExample() {
        int[] arr = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        assertEquals(6L, MaxSubarraySum.find(arr));
    }

    @Test
    void shouldTakeWholeArrayWhenAllPositive() {
        int[] arr = {1, 2, 3};
        assertEquals(6L, MaxSubarraySum.find(arr));
    }

    @Test
    void shouldReturnLargestElementWhenAllNegative() {
        int[] arr = {-8, -3, -6, -2, -5};
        assertEquals(-2L, MaxSubarraySum.find(arr));
    }

    @Test
    void shouldWorkWithSingleElement() {
        int[] arr = {-7};
        assertEquals(-7L, MaxSubarraySum.find(arr));
    }

    @Test
    void shouldNotOverflowOnLargeNumbers() {
        int[] arr = {Integer.MAX_VALUE, Integer.MAX_VALUE};
        assertEquals(2L * Integer.MAX_VALUE, MaxSubarraySum.find(arr));
    }

    @Test
    void shouldThrowWhenArrayIsEmpty() {
        int[] arr = {};
        assertThrows(IllegalArgumentException.class, () -> MaxSubarraySum.find(arr));
    }

    @Test
    void shouldMatchBruteForceOnRandomArrays() {
        Random random = new Random(34);
        for (int test = 0; test < 500; test++) {
            int[] arr = new int[1 + random.nextInt(25)];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = random.nextInt(21) - 10;
            }
            assertEquals(bruteForce(arr), MaxSubarraySum.find(arr));
        }
    }

    /** Перебор всех подмассивов за O(n^2), чтобы проверить алгоритм Кадане. */
    private static long bruteForce(int[] arr) {
        long best = arr[0];
        for (int from = 0; from < arr.length; from++) {
            long sum = 0;
            for (int to = from; to < arr.length; to++) {
                sum += arr[to];
                best = Math.max(best, sum);
            }
        }
        return best;
    }
}
