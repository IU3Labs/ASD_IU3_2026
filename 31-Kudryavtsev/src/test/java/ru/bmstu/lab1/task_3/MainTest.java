package ru.bmstu.lab1.task_3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.ConsoleTestSupport;

/** Проверяет количество совпадений и ввод-вывод задачи 3. */
class MainTest {

    @Test
    void shouldCountSeparatedOccurrencesWithoutChangingArray() {
        int[] numbers = {2, 5, 2, 2};
        int[] expectedNumbers = numbers.clone();

        int actual = Main.countOccurrences(numbers, 2);

        assertEquals(3, actual);
        assertArrayEquals(expectedNumbers, numbers);
    }

    @Test
    void shouldReturnZeroWhenNumberIsAbsent() {
        int[] numbers = {2, 5};

        int actual = Main.countOccurrences(numbers, 7);

        assertEquals(0, actual);
    }

    @Test
    void shouldCountEveryElementWhenAllNumbersMatch() {
        int[] numbers = {4, 4, 4};

        int actual = Main.countOccurrences(numbers, 4);

        assertEquals(3, actual);
    }

    @Test
    void shouldCountZeros() {
        int[] numbers = {0, -1, 0, 2};

        int actual = Main.countOccurrences(numbers, 0);

        assertEquals(2, actual);
    }

    @Test
    void shouldCountNegativeNumbers() {
        int[] numbers = {-3, 1, -3, -2};

        int actual = Main.countOccurrences(numbers, -3);

        assertEquals(2, actual);
    }

    @Test
    void shouldHandleIntegerLimits() {
        int[] numbers = {Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE};

        int actual = Main.countOccurrences(numbers, Integer.MIN_VALUE);

        assertEquals(2, actual);
    }

    @Test
    void shouldCountAllElementsInLargestAllowedArray() {
        int[] numbers = new int[99_999];

        int actual = Main.countOccurrences(numbers, 0);

        assertEquals(99_999, actual);
    }

    @Test
    void shouldReadArrayBeforeTargetAndPrintCount() {
        String expected = "3" + System.lineSeparator();

        String actual = ConsoleTestSupport.runProgram(Main::solve, "5\n4 -2 4 0 4\n4\n");

        assertEquals(expected, actual);
    }
}
