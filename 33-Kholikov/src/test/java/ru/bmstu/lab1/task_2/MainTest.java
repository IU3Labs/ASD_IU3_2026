package ru.bmstu.lab1.task_2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.bmstu.lab1.ConsoleTestSupport.run;

import org.junit.jupiter.api.Test;

/** Проверки алгоритма и консольного интерфейса задачи 2. */
class MainTest {
    @Test
    void shouldReturnFirstIndexAmongDuplicates() {
        assertEquals(1, Main.firstIndex(new int[]{4, 7, 2, 7, 7}, 7));
        assertEquals(0, Main.firstIndex(new int[]{7, 7}, 7));
    }

    @Test
    void shouldReturnMinusOneWhenTargetIsAbsent() {
        assertEquals(-1, Main.firstIndex(new int[]{-3, 0, 5}, 4));
    }

    @Test
    void shouldHandleLastPositionAndExtremeValues() {
        int[] numbers = {Integer.MIN_VALUE, 0, Integer.MAX_VALUE};
        assertEquals(0, Main.firstIndex(numbers, Integer.MIN_VALUE));
        assertEquals(1, Main.firstIndex(numbers, 0));
        assertEquals(2, Main.firstIndex(numbers, Integer.MAX_VALUE));
    }

    @Test
    void shouldReadTargetAndPrintIndexOrMinusOne() {
        assertEquals("1\n", run("5\n4 7 2 7 7\n7\n", () -> Main.main(new String[0])));
        assertEquals("-1\n", run("2\n1 2\n3\n", () -> Main.main(new String[0])));
    }

    @Test
    void shouldSearchMaximumAllowedArray() {
        int[] numbers = new int[99_999];
        numbers[numbers.length - 1] = 8;
        assertEquals(99_998, Main.firstIndex(numbers, 8));
        assertEquals(-1, Main.firstIndex(numbers, -1));
    }
}
