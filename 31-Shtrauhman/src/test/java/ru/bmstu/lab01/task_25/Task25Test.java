package ru.bmstu.lab01.task_25;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import ru.bmstu.lab01.common.TestIo;

/**
 * Тесты задачи 25: сумма элементов.
 */
class Task25Test {

    /** Наибольший размер массива по условию (n меньше 10^5). */
    private static final int MAX_SIZE = 99_999;

    @Test
    void shouldSumPositiveNumbers() {
        assertEquals(15, Task25.sum(new int[] {1, 2, 3, 4, 5}));
    }

    @Test
    void shouldSumMixedSigns() {
        assertEquals(-3, Task25.sum(new int[] {-10, 4, 3}));
    }

    @Test
    void shouldReturnZeroWhenAllCancelOut() {
        assertEquals(0, Task25.sum(new int[] {5, -5, 7, -7}));
    }

    @Test
    void shouldNotOverflowIntRange() {
        long expected = 2L * Integer.MAX_VALUE;

        assertEquals(expected, Task25.sum(new int[] {Integer.MAX_VALUE, Integer.MAX_VALUE}));
    }

    @Test
    void shouldHandleMaximumSizeInput() {
        int[] array = new int[MAX_SIZE];
        Arrays.fill(array, Integer.MIN_VALUE);
        long expected = (long) MAX_SIZE * Integer.MIN_VALUE;

        assertEquals(expected, Task25.sum(array));
    }

    @Test
    void shouldPrintSumForStandardInput() {
        assertEquals("6\n", TestIo.run(Task25::run, "3\n1 2 3\n"));
    }

    @Test
    void shouldAcceptNumbersOnSeparateLines() {
        assertEquals("10\n", TestIo.run(Task25::run, "4\n1\n2\n3\n4\n"));
    }

    @Test
    void shouldRejectNonPositiveSize() {
        assertThrows(IllegalArgumentException.class, () -> TestIo.run(Task25::run, "0\n"));
    }
}
