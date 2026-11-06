package ru.bmstu.lab1.task_12;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

class Task12Test {

    @Test
    void shouldSortByDigitSum() {
        int[] arr = {21, 5, 12, 100, 3};
        int[] expected = {100, 21, 12, 3, 5};

        Task12.sortByDigitSum(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    void shouldSortSingleDigitNumbers() {
        int[] arr = {8, 2, 5, 1};
        int[] expected = {1, 2, 5, 8};

        Task12.sortByDigitSum(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    void shouldCompareDigitSumsInsteadOfNumbers() {
        int[] arr = {9, 100, 20};
        int[] expected = {100, 20, 9};

        Task12.sortByDigitSum(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    void shouldKeepOriginalOrderForEqualDigitSums() {
        int[] arr = {30, 12, 21, 3};
        int[] expected = {30, 12, 21, 3};

        Task12.sortByDigitSum(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    void shouldPreserveOrderWithinDifferentGroups() {
        int[] arr = {22, 30, 13, 12, 4, 21};
        int[] expected = {30, 12, 21, 22, 13, 4};

        Task12.sortByDigitSum(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    void shouldPlaceZerosFirst() {
        int[] arr = {12, 0, 10, 0, 2};
        int[] expected = {0, 0, 10, 2, 12};

        Task12.sortByDigitSum(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    void shouldHandleOnlyZeros() {
        int[] arr = {0, 0};
        int[] expected = {0, 0};

        Task12.sortByDigitSum(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    void shouldHandleRepeatedNumbers() {
        int[] arr = {12, 5, 12, 100, 5};
        int[] expected = {100, 12, 12, 5, 5};

        Task12.sortByDigitSum(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    void shouldSortTwoNumbers() {
        int[] arr = {99, 10};
        int[] expected = {10, 99};

        Task12.sortByDigitSum(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    void shouldKeepSortedArrayUnchanged() {
        int[] arr = {0, 100, 20, 3, 99};
        int[] expected = arr.clone();

        Task12.sortByDigitSum(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    void shouldHandleIntegerMaximum() {
        // Сумма цифр Integer.MAX_VALUE равна 46.
        int[] arr = {Integer.MAX_VALUE, 99, 1_000_000_000};
        int[] expected = {1_000_000_000, 99, Integer.MAX_VALUE};

        Task12.sortByDigitSum(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    void shouldHandleLargestAllowedArray() {
        int n = 99_999;
        int[] arr = new int[n];
        int[] expected = new int[n];

        for (int i = 0; i < n - 1; i++) {
            arr[i] = 99;
            expected[i + 1] = 99;
        }
        // Последний элемент arr равен 0 и должен переместиться в начало.
        Task12.sortByDigitSum(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    void shouldRejectNegativeNumber() {
        int[] arr = {12, -3, 5};

        assertThrows(
                IllegalArgumentException.class,
                () -> Task12.sortByDigitSum(arr));
    }

    @Test
    void shouldRejectEmptyArray() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Task12.sortByDigitSum(new int[0]));
    }

    @Test
    void shouldRejectSingleElementArray() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Task12.sortByDigitSum(new int[] {5}));
    }

    @Test
    void shouldRejectTooLargeArray() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Task12.sortByDigitSum(new int[100_000]));
    }

    @Test
    void shouldReadInputAndPrintSortedArray() {
        InputStream originalInput = System.in;
        PrintStream originalOutput = System.out;
        byte[] input = "5\n21 5 12 100 3\n".getBytes(StandardCharsets.UTF_8);
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try (PrintStream testOutput =new PrintStream(output, true, StandardCharsets.UTF_8)) {
            System.setIn(new ByteArrayInputStream(input));
            System.setOut(testOutput);

            Task12.main(new String[0]);

            String expected = "100 21 12 3 5" + System.lineSeparator();
            String actual = output.toString(StandardCharsets.UTF_8);
            assertEquals(expected, actual);
        }
        finally {
            System.setIn(originalInput);
            System.setOut(originalOutput);
        }
    }
}