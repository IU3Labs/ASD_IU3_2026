package ru.bmstu.lab01.task_34;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.Random;
import org.junit.jupiter.api.Test;
import ru.bmstu.lab01.common.TestIo;

/**
 * Тесты задачи 34: максимальная сумма подмассива (Кадане), сверка с перебором O(n^2).
 */
class Task34Test {

    /** Наибольший размер массива по условию (n меньше 10^5). */
    private static final int MAX_SIZE = 99_999;

    /** Фиксированное зерно, чтобы случайные тесты воспроизводились. */
    private static final long RANDOM_SEED = 2026;
    private static final int RANDOM_RUNS = 300;
    private static final int RANDOM_MAX_LENGTH = 30;
    private static final int RANDOM_BOUND = 50;

    private static long bruteForce(int[] array) {
        long best = Long.MIN_VALUE;
        for (int from = 0; from < array.length; from++) {
            long sum = 0;
            for (int to = from; to < array.length; to++) {
                sum += array[to];
                best = Math.max(best, sum);
            }
        }
        return best;
    }

    @Test
    void shouldFindBestSubarrayInClassicExample() {
        // подмассив [4, -1, 2, 1] даёт 6
        int[] array = {-2, 1, -3, 4, -1, 2, 1, -5, 4};

        assertEquals(6, Task34.maxSubarraySum(array));
    }

    @Test
    void shouldReturnLargestElementWhenAllNegative() {
        assertEquals(-1, Task34.maxSubarraySum(new int[] {-3, -1, -2}));
    }

    @Test
    void shouldReturnElementForSingleElementArray() {
        assertEquals(-7, Task34.maxSubarraySum(new int[] {-7}));
        assertEquals(7, Task34.maxSubarraySum(new int[] {7}));
    }

    @Test
    void shouldSumWholeArrayWhenAllPositive() {
        assertEquals(15, Task34.maxSubarraySum(new int[] {1, 2, 3, 4, 5}));
    }

    @Test
    void shouldFindSubarrayAtTheStart() {
        assertEquals(10, Task34.maxSubarraySum(new int[] {4, 6, -20, 3}));
    }

    @Test
    void shouldFindSubarrayAtTheEnd() {
        assertEquals(10, Task34.maxSubarraySum(new int[] {3, -20, 4, 6}));
    }

    @Test
    void shouldBridgeNegativeGapWhenItPaysOff() {
        // 5 + (-1) + 5 = 9 лучше, чем 5 по отдельности
        assertEquals(9, Task34.maxSubarraySum(new int[] {5, -1, 5}));
    }

    @Test
    void shouldReturnZeroForZeros() {
        assertEquals(0, Task34.maxSubarraySum(new int[] {0, 0, 0}));
    }

    @Test
    void shouldMatchBruteForceOnRandomArrays() {
        Random random = new Random(RANDOM_SEED);
        for (int attempt = 0; attempt < RANDOM_RUNS; attempt++) {
            int length = 1 + random.nextInt(RANDOM_MAX_LENGTH);
            int[] array = random.ints(length, -RANDOM_BOUND, RANDOM_BOUND).toArray();

            assertEquals(bruteForce(array), Task34.maxSubarraySum(array), Arrays.toString(array));
        }
    }

    @Test
    void shouldNotOverflowIntRange() {
        long expected = 2L * Integer.MAX_VALUE;

        assertEquals(expected, Task34.maxSubarraySum(new int[] {Integer.MAX_VALUE, Integer.MAX_VALUE}));
    }

    @Test
    void shouldHandleMaximumSizeInput() {
        int[] array = new int[MAX_SIZE];
        Arrays.fill(array, Integer.MAX_VALUE);
        long expected = (long) MAX_SIZE * Integer.MAX_VALUE;

        assertEquals(expected, Task34.maxSubarraySum(array));
    }

    @Test
    void shouldRejectEmptyArray() {
        assertThrows(IllegalArgumentException.class, () -> Task34.maxSubarraySum(new int[0]));
    }

    @Test
    void shouldPrintResultForStandardInput() {
        assertEquals("6\n", TestIo.run(Task34::run, "9\n-2 1 -3 4 -1 2 1 -5 4\n"));
    }

    @Test
    void shouldRejectNonPositiveSize() {
        assertThrows(IllegalArgumentException.class, () -> TestIo.run(Task34::run, "0\n"));
    }
}
