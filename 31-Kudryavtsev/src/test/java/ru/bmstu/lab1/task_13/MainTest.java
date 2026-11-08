package ru.bmstu.lab1.task_13;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.ConsoleTestSupport;

/** Проверяет сортировку выбором на разных порядках и значениях элементов. */
class MainTest {

    @Test
    void shouldSortMixedNumbers() {
        int[] numbers = {4, -2, 0, 7, -5};
        int[] expected = {-5, -2, 0, 4, 7};

        Main.selectionSort(numbers);

        assertArrayEquals(expected, numbers);
    }

    @Test
    void shouldKeepAlreadySortedArray() {
        int[] numbers = {-3, 0, 2, 9};
        int[] expected = numbers.clone();

        Main.selectionSort(numbers);

        assertArrayEquals(expected, numbers);
    }

    @Test
    void shouldSortDecreasingArray() {
        int[] numbers = {9, 5, 2, -1};
        int[] expected = {-1, 2, 5, 9};

        Main.selectionSort(numbers);

        assertArrayEquals(expected, numbers);
    }

    @Test
    void shouldPreserveRepeatedValues() {
        int[] numbers = {3, 1, 3, 1, 2};
        int[] expected = {1, 1, 2, 3, 3};

        Main.selectionSort(numbers);

        assertArrayEquals(expected, numbers);
    }

    @Test
    void shouldHandleEqualValues() {
        int[] numbers = {6, 6, 6};
        int[] expected = {6, 6, 6};

        Main.selectionSort(numbers);

        assertArrayEquals(expected, numbers);
    }

    @Test
    void shouldSortSmallestAllowedArray() {
        int[] numbers = {8, -4};
        int[] expected = {-4, 8};

        Main.selectionSort(numbers);

        assertArrayEquals(expected, numbers);
    }

    @Test
    void shouldHandleIntegerLimits() {
        int[] numbers = {Integer.MAX_VALUE, 0, Integer.MIN_VALUE};
        int[] expected = {Integer.MIN_VALUE, 0, Integer.MAX_VALUE};

        Main.selectionSort(numbers);

        assertArrayEquals(expected, numbers);
    }

    @Test
    void shouldSortTwoThousandElementsInReverseOrder() {
        int[] numbers = new int[2_000];
        int[] expected = new int[numbers.length];
        for (int index = 0; index < numbers.length; index++) {
            numbers[index] = numbers.length - index;
            expected[index] = index + 1;
        }

        Main.selectionSort(numbers);

        assertArrayEquals(expected, numbers);
    }

    @Test
    void shouldPrintSortedNumbersWithoutExtraSpaces() {
        String expected = "-2 0 4 7" + System.lineSeparator();

        String actual = ConsoleTestSupport.runProgram(Main::solve, "4\n4 -2 0 7\n");

        assertEquals(expected, actual);
    }
}
