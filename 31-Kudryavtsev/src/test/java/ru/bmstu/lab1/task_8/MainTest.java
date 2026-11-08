package ru.bmstu.lab1.task_8;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.ConsoleTestSupport;

/** Проверяет порядок чисел, исключение нулей и вывод пустых групп в задаче 8. */
class MainTest {

    @Test
    void shouldPreserveOrderAndSkipZerosWithoutChangingArray() {
        int[] numbers = {3, -2, 0, 1, -5, 4};
        int[] expectedNumbers = numbers.clone();

        int[][] actual = Main.filterBySign(numbers);

        assertArrayEquals(new int[] {3, 1, 4}, actual[0]);
        assertArrayEquals(new int[] {-2, -5}, actual[1]);
        assertArrayEquals(expectedNumbers, numbers);
    }

    @Test
    void shouldKeepRepeatedPositiveNumbers() {
        int[] numbers = {2, 2, 1};

        int[][] actual = Main.filterBySign(numbers);

        assertArrayEquals(new int[] {2, 2, 1}, actual[0]);
        assertArrayEquals(new int[0], actual[1]);
    }

    @Test
    void shouldKeepOnlyNegativeNumbersInSecondGroup() {
        int[] numbers = {-1, -3, -2};

        int[][] actual = Main.filterBySign(numbers);

        assertArrayEquals(new int[0], actual[0]);
        assertArrayEquals(new int[] {-1, -3, -2}, actual[1]);
    }

    @Test
    void shouldReturnTwoEmptyGroupsForZeros() {
        int[] numbers = {0, 0};

        int[][] actual = Main.filterBySign(numbers);

        assertEquals(2, actual.length);
        assertArrayEquals(new int[0], actual[0]);
        assertArrayEquals(new int[0], actual[1]);
    }

    @Test
    void shouldSeparateIntegerLimits() {
        int[] numbers = {Integer.MIN_VALUE, Integer.MAX_VALUE};

        int[][] actual = Main.filterBySign(numbers);

        assertArrayEquals(new int[] {Integer.MAX_VALUE}, actual[0]);
        assertArrayEquals(new int[] {Integer.MIN_VALUE}, actual[1]);
    }

    @Test
    void shouldHandleLargestAllowedArrayWithBothSigns() {
        int[] numbers = new int[99_999];
        for (int index = 0; index < numbers.length; index++) {
            numbers[index] = index % 2 == 0 ? index + 1 : -index;
        }

        int[][] actual = Main.filterBySign(numbers);

        assertEquals(50_000, actual[0].length);
        assertEquals(49_999, actual[1].length);
        for (int index = 0; index < actual[0].length; index++) {
            assertEquals(2 * index + 1, actual[0][index]);
        }
        for (int index = 0; index < actual[1].length; index++) {
            assertEquals(-(2 * index + 1), actual[1][index]);
        }
    }

    @Test
    void shouldPrintPositiveGroupBeforeNegativeGroup() {
        String expected = "3 1 4" + System.lineSeparator() + "-2 -5" + System.lineSeparator();

        String actual = ConsoleTestSupport.runProgram(Main::solve, "6\n3 -2 0 1 -5 4\n");

        assertEquals(expected, actual);
    }

    @Test
    void shouldPrintEmptyFirstLineWhenPositiveGroupIsEmpty() {
        String expected = System.lineSeparator() + "-1 -3 -2" + System.lineSeparator();

        String actual = ConsoleTestSupport.runProgram(Main::solve, "3\n-1 -3 -2\n");

        assertEquals(expected, actual);
    }

    @Test
    void shouldPrintTwoEmptyLinesWhenAllNumbersAreZero() {
        String expected = System.lineSeparator() + System.lineSeparator();

        String actual = ConsoleTestSupport.runProgram(Main::solve, "2\n0 0\n");

        assertEquals(expected, actual);
    }
}
