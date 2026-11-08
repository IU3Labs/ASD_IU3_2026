package ru.bmstu.lab1.task_1;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.ConsoleTestSupport;

/** Проверяет поиск крайних значений, сохранность массива и консольный формат задачи 1. */
class MainTest {

    @Test
    void shouldFindExtremesAmongMixedNumbers() {
        int[] numbers = {8, -3, 12, 0, 5, -9, 12};
        int[] expected = {-9, 12};

        int[] actual = Main.findMinMax(numbers);

        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldFindMinimumInPositiveArray() {
        int[] numbers = {7, 3, 11, 6};
        int[] expected = {3, 11};

        int[] actual = Main.findMinMax(numbers);

        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldFindMaximumInNegativeArray() {
        int[] numbers = {-7, -3, -11, -6};
        int[] expected = {-11, -3};

        int[] actual = Main.findMinMax(numbers);

        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldReturnSameExtremesForEqualNumbers() {
        int[] numbers = {4, 4, 4};
        int[] expected = {4, 4};

        int[] actual = Main.findMinMax(numbers);

        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldHandleOnlyZeros() {
        int[] numbers = {0, 0};
        int[] expected = {0, 0};

        int[] actual = Main.findMinMax(numbers);

        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldFindExtremesAtEndsOfIncreasingArray() {
        int[] numbers = {-5, 0, 4, 9};
        int[] expected = {-5, 9};

        int[] actual = Main.findMinMax(numbers);

        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldFindExtremesAtEndsOfDecreasingArray() {
        int[] numbers = {9, 4, 0, -5};
        int[] expected = {-5, 9};

        int[] actual = Main.findMinMax(numbers);

        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldHandleIntegerLimits() {
        int[] numbers = {0, Integer.MAX_VALUE, Integer.MIN_VALUE};
        int[] expected = {Integer.MIN_VALUE, Integer.MAX_VALUE};

        int[] actual = Main.findMinMax(numbers);

        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldHandleSmallestAllowedArray() {
        int[] numbers = {12, -8};
        int[] expected = {-8, 12};

        int[] actual = Main.findMinMax(numbers);

        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldHandleLargestAllowedArray() {
        // Верхняя граница n < 100000 строгая, поэтому допустимый максимум равен 99999.
        int[] numbers = new int[99_999];
        for (int index = 0; index < numbers.length; index++) {
            numbers[index] = index - numbers.length / 2;
        }
        int[] expected = {-49_999, 49_999};

        int[] actual = Main.findMinMax(numbers);

        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldLeaveOriginalArrayUnchanged() {
        int[] numbers = {6, 2, 9, -1};
        int[] expectedNumbers = numbers.clone();
        int[] expected = {-1, 9};

        int[] actual = Main.findMinMax(numbers);

        assertArrayEquals(expected, actual);
        assertArrayEquals(expectedNumbers, numbers);
    }

    @Test
    void shouldReadWhitespaceSeparatedInputAndPrintOnlyAnswer() {
        String inputText = "6\n8 -3\t12\n0 -9 5\n";
        String expected = "-9 12" + System.lineSeparator();

        String actual = ConsoleTestSupport.runProgram(Main::solve, inputText);

        assertEquals(expected, actual);
    }
}
