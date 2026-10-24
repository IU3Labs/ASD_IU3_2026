package ru.bmstu.lab1.task_26;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.bmstu.lab1.ConsoleTestSupport.run;

import java.math.BigInteger;
import org.junit.jupiter.api.Test;

/** Проверки алгоритма и консольного интерфейса задачи 26. */
class MainTest {
    @Test
    void shouldNormalizeNegativeFactors() {
        assertEquals(1_000_000_001L, Main.productModulo(new int[]{-2, 3}));
        assertEquals(6L, Main.productModulo(new int[]{-2, -3}));
    }

    @Test
    void shouldHandleZeroAndMultiplesOfModulus() {
        assertEquals(0L, Main.productModulo(new int[]{3, 0, -5}));
        assertEquals(0L, Main.productModulo(new int[]{1_000_000_007, 2}));
    }

    @Test
    void shouldMatchExactProductForExtremeValues() {
        int[] numbers = {Integer.MIN_VALUE, Integer.MAX_VALUE, -1, Integer.MAX_VALUE};
        BigInteger exact = BigInteger.ONE;
        for (int number : numbers) {
            exact = exact.multiply(BigInteger.valueOf(number));
        }
        long expected = exact.mod(BigInteger.valueOf(1_000_000_007L)).longValueExact();
        assertEquals(expected, Main.productModulo(numbers));
    }

    @Test
    void shouldReadArrayAndPrintProduct() {
        assertEquals("24\n", run("3\n2 3 4\n", () -> Main.main(new String[0])));
    }
}
