package ru.bmstu.lab1.task_36;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.bmstu.lab1.ConsoleTestSupport.run;

import org.junit.jupiter.api.Test;

/** Проверки алгоритма и консольного интерфейса задачи 36. */
class MainTest {
    @Test
    void shouldComputeRunningSumsForMixedSigns() {
        int[] numbers = {-5, 2, 3, 7, -1};
        assertArrayEquals(new long[]{-5, -3, 0, 7, 6}, Main.runningSums(numbers));
        assertArrayEquals(new int[]{-5, 2, 3, 7, -1}, numbers);
    }

    @Test
    void shouldUseLongForRunningSums() {
        assertArrayEquals(new long[]{2_147_483_647L, 4_294_967_294L},
            Main.runningSums(new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE}));
        assertArrayEquals(new long[]{0, 0}, Main.runningSums(new int[]{0, 0}));
    }

    @Test
    void shouldReadArrayAndPrintRunningSums() {
        assertEquals("10 30 60 100 150\n", run("5\n10 20 30 40 50\n", () -> Main.main(new String[0])));
    }
}
