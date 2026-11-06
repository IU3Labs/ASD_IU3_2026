package ru.bmstu.lab1;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Random;
import org.junit.jupiter.api.Test;

/** Граничные проверки сумм, квадратов и произведения по модулю. */
class ArithmeticTest {
    @Test
    void shouldSumLargestAllowedArray() {
        int[] values = new int[99_999];
        Arrays.fill(values, Integer.MAX_VALUE);
        long expected = (long) Integer.MAX_VALUE * values.length;
        assertEquals(expected, ru.bmstu.lab1.task_25.Main.solve(values));
    }

    @Test
    void shouldMultiplyLargeValuesWithoutOverflow() {
        int[] values = new int[99_999];
        Arrays.fill(values, 1_000_000_006);
        assertEquals(1_000_000_006L, ru.bmstu.lab1.task_26.Main.solve(values));
    }

    @Test
    void shouldSumSquaresBeyondLongRange() {
        int[] values = new int[99_999];
        Arrays.fill(values, Integer.MIN_VALUE);
        BigInteger expected = BigInteger.valueOf(Integer.MIN_VALUE).pow(2)
                .multiply(BigInteger.valueOf(values.length));
        assertEquals(expected, ru.bmstu.lab1.task_27.Main.solve(values));
    }

    @Test
    void shouldMatchExactProductForRandomArrays() {
        Random random = new Random(26);
        BigInteger modulus = BigInteger.valueOf(1_000_000_007);
        for (int attempt = 0; attempt < 300; attempt++) {
            int[] values = random.ints(2 + random.nextInt(20)).toArray();
            BigInteger exactProduct = BigInteger.ONE;
            for (int number : values) {
                exactProduct = exactProduct.multiply(BigInteger.valueOf(number));
            }
            long expected = exactProduct.mod(modulus).longValueExact();
            assertEquals(expected, ru.bmstu.lab1.task_26.Main.solve(values));
        }
    }

    @Test
    void shouldLeaveArithmeticInputUnchanged() {
        int[] values = {Integer.MIN_VALUE, 0, 3, Integer.MAX_VALUE};
        int[] original = values.clone();
        ru.bmstu.lab1.task_25.Main.solve(values);
        ru.bmstu.lab1.task_26.Main.solve(values);
        ru.bmstu.lab1.task_27.Main.solve(values);
        assertArrayEquals(original, values);
    }
}
