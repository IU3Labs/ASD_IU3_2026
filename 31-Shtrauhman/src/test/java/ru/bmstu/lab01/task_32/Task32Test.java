package ru.bmstu.lab01.task_32;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Random;
import org.junit.jupiter.api.Test;
import ru.bmstu.lab01.common.TestIo;

/**
 * Тесты задачи 32: сумма произведений пар за O(n), сверка с перебором O(n^2).
 */
class Task32Test {

    /** Наибольший размер массива по условию (n меньше 10^5). */
    private static final int MAX_SIZE = 99_999;

    /** Фиксированное зерно, чтобы случайные тесты воспроизводились. */
    private static final long RANDOM_SEED = 2026;

    private static final int RANDOM_RUNS = 200;
    private static final int RANDOM_MAX_LENGTH = 30;
    private static final int RANDOM_BOUND = 1000;

    private static BigInteger bruteForce(int[] array) {
        BigInteger total = BigInteger.ZERO;
        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                total = total.add(BigInteger.valueOf((long) array[i] * array[j]));
            }
        }
        return total;
    }

    @Test
    void shouldSumProductsOfAllPairs() {
        // 1*2 + 1*3 + 2*3 = 11
        assertEquals(BigInteger.valueOf(11), Task32.pairProductsSum(new int[] {1, 2, 3}));
    }

    @Test
    void shouldReturnZeroForSingleElement() {
        assertEquals(BigInteger.ZERO, Task32.pairProductsSum(new int[] {5}));
    }

    @Test
    void shouldHandleNegativeResult() {
        assertEquals(BigInteger.valueOf(-6), Task32.pairProductsSum(new int[] {2, -3}));
    }

    @Test
    void shouldHandleZeros() {
        assertEquals(BigInteger.ZERO, Task32.pairProductsSum(new int[] {0, 0, 0}));
    }

    @Test
    void shouldMatchBruteForceOnRandomArrays() {
        Random random = new Random(RANDOM_SEED);
        for (int attempt = 0; attempt < RANDOM_RUNS; attempt++) {
            int length = 1 + random.nextInt(RANDOM_MAX_LENGTH);
            int[] array = random.ints(length, -RANDOM_BOUND, RANDOM_BOUND).toArray();

            assertEquals(bruteForce(array), Task32.pairProductsSum(array), Arrays.toString(array));
        }
    }

    @Test
    void shouldMatchBruteForceOnExtremeValues() {
        int[] array = {Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE, -1};

        assertEquals(bruteForce(array), Task32.pairProductsSum(array));
    }

    @Test
    void shouldNotOverflowOnMaximumSizeInput() {
        int[] array = new int[MAX_SIZE];
        Arrays.fill(array, Integer.MAX_VALUE);
        BigInteger value = BigInteger.valueOf(Integer.MAX_VALUE);
        BigInteger pairs = BigInteger.valueOf((long) MAX_SIZE * (MAX_SIZE - 1) / 2);
        BigInteger expected = value.multiply(value).multiply(pairs);

        assertEquals(expected, Task32.pairProductsSum(array));
    }

    @Test
    void shouldPrintResultForStandardInput() {
        assertEquals("11\n", TestIo.run(Task32::run, "3\n1 2 3\n"));
    }
}
