package ru.bmstu.lab1.task_26;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;
import java.util.Random;
import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.common.ConsoleSupport;

/** граничные случаи и независимая проверка алгоритма задачи 26 */
class MainTest {
    @Test
    void shouldNormalizeNegativeFactorsAndHandleZero() {
        assertEquals(1_000_000_001L, Main.productModulo(new int[] {-2, 3}));
        assertEquals(6, Main.productModulo(new int[] {-2, -3}));
        assertEquals(0, Main.productModulo(new int[] {1, 0, -2}));
        assertEquals(0, Main.productModulo(new int[] {1_000_000_007, 2}));
        assertEquals(1, Main.productModulo(new int[0]));
    }

    @Test
    void shouldMatchExactProductForExtremeIntegers() {
        int[] values = {Integer.MIN_VALUE, Integer.MAX_VALUE, -1, -1_000_000_007};
        assertEquals(exactProductModulo(values), Main.productModulo(values));
        assertEquals(exactProductModulo(new int[] {Integer.MIN_VALUE, Integer.MAX_VALUE}),
                Main.productModulo(new int[] {Integer.MIN_VALUE, Integer.MAX_VALUE}));
    }

    @Test
    void shouldMatchExactProductOnRandomArrays() {
        Random random = new Random(26);
        for (int attempt = 0; attempt < 200; attempt++) {
            int[] values = random.ints(random.nextInt(30)).toArray();
            assertEquals(exactProductModulo(values), Main.productModulo(values));
        }
    }

    private long exactProductModulo(int[] values) {
        BigInteger result = BigInteger.ONE;
        for (int value : values) {
            result = result.multiply(BigInteger.valueOf(value));
        }
        return result.mod(BigInteger.valueOf(1_000_000_007L)).longValueExact();
    }

    @Test
    void shouldReadAndPrintAnswer() throws Exception {
        assertEquals("1000000001\n", ConsoleSupport.runMain(26, "2 -2 3"));
    }

    @Test
    void shouldReadMixedWhitespace() throws Exception {
        assertEquals("6\n", ConsoleSupport.runMain(26, "  3\n1\t2 3\n"));
    }
}
