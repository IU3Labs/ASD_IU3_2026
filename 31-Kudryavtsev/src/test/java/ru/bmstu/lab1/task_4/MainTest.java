package ru.bmstu.lab1.task_4;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.ConsoleTestSupport;

/** Проверяет порядок индексов, отсутствие совпадений и формат ответа задачи 4. */
class MainTest {

    @Test
    void shouldFindAllIndicesWithoutChangingArray() {
        int[] numbers = {7, 2, 7, 3, 7};
        int[] expectedNumbers = numbers.clone();
        int[] expected = {0, 2, 4};

        int[] actual = Main.findAllIndices(numbers, 7);

        assertArrayEquals(expected, actual);
        assertArrayEquals(expectedNumbers, numbers);
    }

    @Test
    void shouldReturnEmptyArrayWhenNumberIsAbsent() {
        int[] numbers = {1, 2};

        int[] actual = Main.findAllIndices(numbers, 7);

        assertArrayEquals(new int[0], actual);
    }

    @Test
    void shouldReturnEveryIndexWhenAllNumbersMatch() {
        int[] numbers = {4, 4, 4};
        int[] expected = {0, 1, 2};

        int[] actual = Main.findAllIndices(numbers, 4);

        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldFindSingleZeroAtLastPosition() {
        int[] numbers = {-2, 3, 0};
        int[] expected = {2};

        int[] actual = Main.findAllIndices(numbers, 0);

        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldFindNegativeNumbers() {
        int[] numbers = {-1, 2, -1, -1};
        int[] expected = {0, 2, 3};

        int[] actual = Main.findAllIndices(numbers, -1);

        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldHandleIntegerLimitsInSmallestArray() {
        int[] numbers = {Integer.MIN_VALUE, Integer.MAX_VALUE};
        int[] expected = {1};

        int[] actual = Main.findAllIndices(numbers, Integer.MAX_VALUE);

        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldFindEveryIndexInLargestAllowedArray() {
        int[] numbers = new int[99_999];
        int[] expected = new int[numbers.length];
        for (int index = 0; index < expected.length; index++) {
            expected[index] = index;
        }

        int[] actual = Main.findAllIndices(numbers, 0);

        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldPrintIndicesWithoutExtraSpaces() {
        String expected = "0 2 4" + System.lineSeparator();

        String actual = ConsoleTestSupport.runProgram(Main::solve, "5\n7 2 7 3 7\n7\n");

        assertEquals(expected, actual);
    }

    @Test
    void shouldPrintEmptyLineWhenNumberIsAbsent() {
        String expected = System.lineSeparator();

        String actual = ConsoleTestSupport.runProgram(Main::solve, "2\n1 2\n7\n");

        assertEquals(expected, actual);
    }
}
