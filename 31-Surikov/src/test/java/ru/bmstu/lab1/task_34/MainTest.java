package ru.bmstu.lab1.task_34;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.Random;
import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.common.ConsoleSupport;

/** граничные случаи и независимая проверка алгоритма задачи 34 */
class MainTest {
    @Test
    void shouldFindNonemptyMaximumForNegativeAndMixedValues() {
        assertEquals(-2, Main.maximumSubarraySum(new int[] {-5, -2, -7}));
        assertEquals(6, Main.maximumSubarraySum(new int[] {-2, 1, -3, 4, -1, 2, 1, -5, 4}));
        assertEquals(0, Main.maximumSubarraySum(new int[] {0, 0}));
        assertEquals(Integer.MIN_VALUE, Main.maximumSubarraySum(new int[] {Integer.MIN_VALUE}));
        assertThrows(IllegalArgumentException.class, () -> Main.maximumSubarraySum(new int[0]));
    }

    @Test
    void shouldAvoidOverflowOnLargeInput() {
        int[] values = new int[99_999];
        Arrays.fill(values, Integer.MAX_VALUE);
        assertEquals(99_999L * Integer.MAX_VALUE, Main.maximumSubarraySum(values));
    }

    @Test
    void shouldMatchBruteForceOnRandomArrays() {
        Random random = new Random(34);
        for (int attempt = 0; attempt < 200; attempt++) {
            int[] values = random.ints(random.nextInt(30) + 1, -20, 21).toArray();
            assertEquals(bruteForceMaximum(values), Main.maximumSubarraySum(values));
        }
    }

    private long bruteForceMaximum(int[] values) {
        long maximum = Long.MIN_VALUE;
        for (int left = 0; left < values.length; left++) {
            long sum = 0;
            for (int right = left; right < values.length; right++) {
                sum += values[right];
                maximum = Math.max(maximum, sum);
            }
        }
        return maximum;
    }

    @Test
    void shouldReadAndPrintAnswer() throws Exception {
        assertEquals("6\n", ConsoleSupport.runMain(34, "9 -2 1 -3 4 -1 2 1 -5 4"));
    }
}
