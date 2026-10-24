package ru.bmstu.lab1.task_34;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.bmstu.lab1.ConsoleTestSupport.run;

import java.util.Random;
import org.junit.jupiter.api.Test;

/** Проверки алгоритма и консольного интерфейса задачи 34. */
class MainTest {
    @Test
    void shouldKeepNegativeMaximumForNonemptySubarray() {
        assertEquals(-2L, Main.maxSubarraySum(new int[]{-8, -3, -6, -2, -5}));
    }

    @Test
    void shouldRestartAndExtendSubarray() {
        assertEquals(6L, Main.maxSubarraySum(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4}));
        assertEquals(0L, Main.maxSubarraySum(new int[]{0, 0, 0}));
        assertEquals(4_294_967_294L, Main.maxSubarraySum(new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE}));
    }

    @Test
    void shouldMatchEnumerationOnDeterministicRandomArrays() {
        Random random = new Random(34);
        for (int sample = 0; sample < 200; sample++) {
            int[] numbers = random.ints(2 + random.nextInt(20), -50, 51).toArray();
            long expected = Long.MIN_VALUE;
            for (int left = 0; left < numbers.length; left++) {
                long sum = 0;
                for (int right = left; right < numbers.length; right++) {
                    sum += numbers[right];
                    expected = Math.max(expected, sum);
                }
            }
            assertEquals(expected, Main.maxSubarraySum(numbers));
        }
    }

    @Test
    void shouldReadArrayAndPrintMaximumSum() {
        assertEquals("6\n", run("9\n-2 1 -3 4 -1 2 1 -5 4\n", () -> Main.main(new String[0])));
    }
}
