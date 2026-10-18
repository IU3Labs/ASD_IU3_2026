package ru.bmstu.lab1.task_35;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.bmstu.lab1.ConsoleTestSupport.run;

import org.junit.jupiter.api.Test;

/** Проверки алгоритма и консольного интерфейса задачи 35. */
class MainTest {
    @Test
    void shouldIncludeZeroPrefixAndUseLongSums() {
        int[] numbers = {Integer.MAX_VALUE, Integer.MAX_VALUE};
        assertArrayEquals(new long[]{0, 2_147_483_647L, 4_294_967_294L}, Main.prefixSums(numbers));
    }

    @Test
    void shouldAnswerAllRangesIncludingBoundaryIndices() {
        int[] numbers = {-5, 2, 3, 7, -1};
        long[] prefix = Main.prefixSums(numbers);
        for (int left = 0; left < numbers.length; left++) {
            long expected = 0;
            for (int right = left; right < numbers.length; right++) {
                expected += numbers[right];
                assertEquals(expected, Main.rangeSum(prefix, left, right));
            }
        }
    }

    @Test
    void shouldReadQueriesAndPrintEachAnswer() {
        String input = "5\n-5 2 3 7 -1\n4\n0 4\n0 0\n1 3\n4 4\n";
        assertEquals("6\n-5\n12\n-1\n", run(input, () -> Main.main(new String[0])));
    }

    @Test
    void shouldAllowNoQueries() {
        assertEquals("", run("2\n1 2\n0\n", () -> Main.main(new String[0])));
    }
}
