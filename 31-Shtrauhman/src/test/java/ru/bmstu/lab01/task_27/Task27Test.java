package ru.bmstu.lab01.task_27;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigInteger;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import ru.bmstu.lab01.common.TestIo;

/**
 * Тесты задачи 27: сумма квадратов.
 */
class Task27Test {

    /** Наибольший размер массива по условию (n меньше 10^5). */
    private static final int MAX_SIZE = 99_999;

    @Test
    void shouldSumSquaresOfPositiveNumbers() {
        // 1 + 4 + 9 = 14
        assertEquals(BigInteger.valueOf(14), Task27.sumOfSquares(new int[] {1, 2, 3}));
    }

    @Test
    void shouldTreatNegativeNumbersAsPositive() {
        assertEquals(BigInteger.valueOf(25), Task27.sumOfSquares(new int[] {-3, 4}));
    }

    @Test
    void shouldReturnZeroForZeros() {
        assertEquals(BigInteger.ZERO, Task27.sumOfSquares(new int[] {0, 0, 0}));
    }

    @Test
    void shouldHandleSingleElement() {
        assertEquals(BigInteger.valueOf(49), Task27.sumOfSquares(new int[] {-7}));
    }

    @Test
    void shouldNotOverflowLongOnSingleExtremeValue() {
        BigInteger expected = BigInteger.TWO.pow(62);

        assertEquals(expected, Task27.sumOfSquares(new int[] {Integer.MIN_VALUE}));
    }

    @Test
    void shouldNotOverflowOnMaximumSizeInput() {
        int[] array = new int[MAX_SIZE];
        Arrays.fill(array, Integer.MIN_VALUE);
        BigInteger expected = BigInteger.TWO.pow(62).multiply(BigInteger.valueOf(MAX_SIZE));

        assertEquals(expected, Task27.sumOfSquares(array));
    }

    @Test
    void shouldPrintSumForStandardInput() {
        assertEquals("14\n", TestIo.run(Task27::run, "3\n1 2 3\n"));
    }

    @Test
    void shouldRejectNonPositiveSize() {
        assertThrows(IllegalArgumentException.class, () -> TestIo.run(Task27::run, "0\n"));
    }
}
