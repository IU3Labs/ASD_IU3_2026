package ru.bmstu.lab1;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Random;
import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.common.ConsoleIo;
import ru.bmstu.lab1.task_25.ArraySum;
import ru.bmstu.lab1.task_26.ModularProduct;
import ru.bmstu.lab1.task_36.RunningSum;

/** Проверки разрядности сумм и произведения независимым вычислением через BigInteger. */
class ArithmeticTest {
    private static final int MAX_VALID_SIZE = 99_999;
    private static final int RANDOM_CASES = 200;
    private static final int MAX_RANDOM_LENGTH = 20;
    private static final long MODULUS = 1_000_000_007L;
    private static final long SEED = 20261022L;

    @Test
    void shouldSumLargestAllowedArrayWithoutOverflow() {
        int[] values = new int[MAX_VALID_SIZE];
        Arrays.fill(values, Integer.MAX_VALUE);
        assertEquals((long) MAX_VALID_SIZE * Integer.MAX_VALUE, ArraySum.solve(values));
    }

    @Test
    void shouldMatchExactProductOnRandomArrays() {
        Random random = new Random(SEED);
        BigInteger modulus = BigInteger.valueOf(MODULUS);
        for (int example = 0; example < RANDOM_CASES; example++) {
            int[] values = new int[2 + random.nextInt(MAX_RANDOM_LENGTH - 1)];
            BigInteger exactProduct = BigInteger.ONE;
            for (int index = 0; index < values.length; index++) {
                values[index] = random.nextInt();
                exactProduct = exactProduct.multiply(BigInteger.valueOf(values[index]));
            }
            assertEquals(exactProduct.mod(modulus).longValueExact(), ModularProduct.solve(values));
        }
    }

    @Test
    void shouldMultiplyLargestAllowedArrayWithRepeatedRemainders() {
        int[] values = new int[MAX_VALID_SIZE];
        Arrays.fill(values, (int) MODULUS - 1);
        // Нечётная степень -1 равна -1 по модулю MODULUS.
        assertEquals(MODULUS - 1, ModularProduct.solve(values));
    }

    @Test
    void shouldComputeEveryPrefixWithoutChangingInput() {
        int[] values = new int[MAX_VALID_SIZE];
        Arrays.fill(values, Integer.MIN_VALUE);
        int[] original = values.clone();
        long[] expected = new long[MAX_VALID_SIZE];
        for (int index = 0; index < expected.length; index++) {
            expected[index] = (index + 1L) * Integer.MIN_VALUE;
        }
        assertArrayEquals(expected, RunningSum.solve(values));
        assertArrayEquals(original, values);
    }

    @Test
    void shouldFormatEmptyLongArray() {
        assertEquals("", ConsoleIo.format(new long[0]));
    }
}
