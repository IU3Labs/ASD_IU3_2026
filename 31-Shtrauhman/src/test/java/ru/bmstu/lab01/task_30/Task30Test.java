package ru.bmstu.lab01.task_30;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab01.common.TestIo;

/**
 * Тесты задачи 30: дисперсия.
 */
class Task30Test {

    private static final double DELTA = 1e-9;

    /** Большое значение, на котором формула E[x^2] - E[x]^2 теряет всю точность. */
    private static final int LARGE_BASE = 1_000_000_000;

    @Test
    void shouldDivideByNNotByNMinusOne() {
        // mean = 2.5, сумма квадратов отклонений = 2.25 + 0.25 + 0.25 + 2.25 = 5, 5 / 4 = 1.25
        assertEquals(1.25, Task30.variance(new int[] {1, 2, 3, 4}), DELTA);
    }

    @Test
    void shouldReturnZeroForSingleElement() {
        assertEquals(0.0, Task30.variance(new int[] {42}), DELTA);
    }

    @Test
    void shouldReturnZeroWhenAllElementsEqual() {
        assertEquals(0.0, Task30.variance(new int[] {7, 7, 7, 7}), DELTA);
    }

    @Test
    void shouldHandleNegativeNumbers() {
        assertEquals(1.0, Task30.variance(new int[] {-1, 1}), DELTA);
    }

    @Test
    void shouldBeStableForLargeValuesWithSmallSpread() {
        int[] array = {LARGE_BASE - 1, LARGE_BASE + 1};

        assertEquals(1.0, Task30.variance(array), DELTA);
    }

    @Test
    void shouldRejectEmptyArray() {
        assertThrows(IllegalArgumentException.class, () -> Task30.variance(new int[0]));
    }

    @Test
    void shouldPrintSixDigitsWithDotSeparator() {
        assertEquals("1.250000\n", TestIo.run(Task30::run, "4\n1 2 3 4\n"));
    }

    @Test
    void shouldPrintZeroForConstantArray() {
        assertEquals("0.000000\n", TestIo.run(Task30::run, "2\n5 5\n"));
    }
}
