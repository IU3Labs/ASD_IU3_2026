package ru.bmstu.lab1.task_3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.bmstu.lab1.ConsoleTestSupport.run;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

/** Проверки алгоритма и консольного интерфейса задачи 3. */
class MainTest {
    @Test
    void shouldCountAllOccurrences() {
        assertEquals(3, Main.countOccurrences(new int[]{7, 2, 7, 4, 7}, 7));
    }

    @Test
    void shouldReturnZeroWhenTargetIsAbsent() {
        assertEquals(0, Main.countOccurrences(new int[]{-3, 0, 5}, 4));
    }

    @Test
    void shouldCountZeroNegativeAndExtremeValues() {
        assertEquals(2, Main.countOccurrences(new int[]{0, -2, 0, -2}, 0));
        assertEquals(2, Main.countOccurrences(new int[]{0, -2, 0, -2}, -2));
        int[] numbers = {Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE};
        assertEquals(2, Main.countOccurrences(numbers, Integer.MIN_VALUE));
        assertEquals(1, Main.countOccurrences(numbers, Integer.MAX_VALUE));
    }

    @Test
    void shouldReadTargetAndPrintCount() {
        assertEquals("3\n", run("5\n7 2 7 4 7\n7\n", () -> Main.main(new String[0])));
        assertEquals("0\n", run("2\n1 2\n3\n", () -> Main.main(new String[0])));
    }

    @Test
    void shouldCountEveryElementOfMaximumAllowedArray() {
        int[] numbers = new int[99_999];
        Arrays.fill(numbers, -7);
        assertEquals(99_999, Main.countOccurrences(numbers, -7));
    }
}
