package ru.bmstu.lab1.task_25;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.bmstu.lab1.ConsoleTestSupport.run;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

/** Проверки алгоритма и консольного интерфейса задачи 25. */
class MainTest {
    @Test
    void shouldSumMixedNumbers() {
        assertEquals(0L, Main.sum(new int[]{-5, 2, 3}));
        assertEquals(-60L, Main.sum(new int[]{-10, -20, -30}));
        assertEquals(0L, Main.sum(new int[]{0, 0}));
    }

    @Test
    void shouldAvoidIntOverflow() {
        assertEquals(4_294_967_294L, Main.sum(new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE}));
        assertEquals(-4_294_967_296L, Main.sum(new int[]{Integer.MIN_VALUE, Integer.MIN_VALUE}));
    }

    @Test
    void shouldReadArrayAndPrintSum() {
        assertEquals("150\n", run("5\n10 20 30 40 50\n", () -> Main.main(new String[0])));
    }

    @Test
    void shouldHandleMaximumAllowedSize() {
        int[] numbers = new int[99_999];
        Arrays.fill(numbers, Integer.MAX_VALUE);
        assertEquals(214_746_217_216_353L, Main.sum(numbers));
    }
}
