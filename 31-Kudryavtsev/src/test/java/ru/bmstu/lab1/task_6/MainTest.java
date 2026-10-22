package ru.bmstu.lab1.task_6;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.ConsoleTestSupport;

/** Проверяет строгое сравнение со средним, большие суммы и ввод-вывод задачи 6. */
class MainTest {

    @Test
    void shouldExcludeElementEqualToAverageWithoutChangingArray() {
        int[] numbers = {2, 5, 8};
        int[] expectedNumbers = numbers.clone();

        int actual = Main.countAboveAverage(numbers);

        assertEquals(1, actual);
        assertArrayEquals(expectedNumbers, numbers);
    }

    @Test
    void shouldHandleNegativeAverage() {
        int[] numbers = {-9, -6, -3};

        int actual = Main.countAboveAverage(numbers);

        assertEquals(1, actual);
    }

    @Test
    void shouldCountOnlyPositiveElementsWhenAverageIsZero() {
        int[] numbers = {-6, -3, 0, 9};

        int actual = Main.countAboveAverage(numbers);

        assertEquals(1, actual);
    }

    @Test
    void shouldReturnZeroWhenAllElementsAreEqual() {
        int[] numbers = {4, 4, 4, 4};

        int actual = Main.countAboveAverage(numbers);

        assertEquals(0, actual);
    }

    @Test
    void shouldHandleTwoDifferentElements() {
        int[] numbers = {2, 8};

        int actual = Main.countAboveAverage(numbers);

        assertEquals(1, actual);
    }

    @Test
    void shouldAvoidPositiveOverflowWhileFindingAverage() {
        int[] numbers = {Integer.MAX_VALUE, Integer.MAX_VALUE - 2};

        int actual = Main.countAboveAverage(numbers);

        assertEquals(1, actual);
    }

    @Test
    void shouldAvoidNegativeOverflowWhileFindingAverage() {
        int[] numbers = {Integer.MIN_VALUE, Integer.MIN_VALUE + 2};

        int actual = Main.countAboveAverage(numbers);

        assertEquals(1, actual);
    }

    @Test
    void shouldHandleLargestAllowedArray() {
        int[] numbers = new int[99_999];
        Arrays.fill(numbers, 0, 33_333, 3);

        int actual = Main.countAboveAverage(numbers);

        assertEquals(33_333, actual);
    }

    @Test
    void shouldReadArrayAndPrintCount() {
        String expected = "1" + System.lineSeparator();

        String actual = ConsoleTestSupport.runProgram(Main::solve, "3\n2 5 8\n");

        assertEquals(expected, actual);
    }
}
