package ru.bmstu.lab1.task_9;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

class Task9Test {

    @Test
    void shouldSortMixedNumbers() {
        int[] arr = {8, -3, 12, 0, 7};
        int[] expected = {12, 8, 7, 0, -3};

        Task9.insertionSort(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    void shouldSortPositiveNumbers() {
        int[] arr = {5, 2, 9, 4};
        int[] expected = {9, 5, 4, 2};

        Task9.insertionSort(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    void shouldSortNegativeNumbers() {
        int[] arr = {-8, -2, -15, -4};
        int[] expected = {-2, -4, -8, -15};

        Task9.insertionSort(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    void shouldKeepDecreasingArrayUnchanged() {
        int[] arr = {10, 3, 0, -5};
        int[] expected = {10, 3, 0, -5};

        Task9.insertionSort(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    void shouldSortIncreasingArray() {
        int[] arr = {1, 3, 5, 7, 9};
        int[] expected = {9, 7, 5, 3, 1};

        Task9.insertionSort(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    void shouldHandleRepeatedNumbers() {
        int[] arr = {5, 2, 5, 1, 2};
        int[] expected = {5, 5, 2, 2, 1};

        Task9.insertionSort(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    void shouldHandleEqualNumbers() {
        int[] arr = {7, 7, 7};
        int[] expected = {7, 7, 7};

        Task9.insertionSort(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    void shouldSortTwoNumbers() {
        int[] arr = {2, 8};
        int[] expected = {8, 2};

        Task9.insertionSort(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    void shouldHandleIntegerLimits() {
        int[] arr = {Integer.MIN_VALUE, 0, Integer.MAX_VALUE, -1};
        int[] expected = {Integer.MAX_VALUE, 0, -1, Integer.MIN_VALUE};

        Task9.insertionSort(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    void shouldSortLargerIncreasingArray() {
        // Умеренный размер для проверки случая с максимальным числом сдвигов.
        int n = 1000;
        int[] arr = new int[n];
        int[] expected = new int[n];

        for (int i = 0; i < n; i++) {
            arr[i] = i + 1;
            expected[i] = n - i;
        }

        Task9.insertionSort(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    void shouldHandleLargestAllowedSortedArray() {
        // На уже отсортированном массиве вставки работают за O(n).
        int n = 99_999;
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = n - i;
        }
        int[] expected = arr.clone();

        Task9.insertionSort(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    void shouldRejectEmptyArray() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Task9.insertionSort(new int[0]));
    }

    @Test
    void shouldRejectSingleElementArray() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Task9.insertionSort(new int[] {5}));
    }

    @Test
    void shouldRejectTooLargeArray() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Task9.insertionSort(new int[100_000]));
    }

    @Test
    void shouldReadInputAndPrintSortedArray() {
        InputStream originalInput = System.in;
        PrintStream originalOutput = System.out;
        byte[] input = "5\n8 -3 12 0 7\n".getBytes(StandardCharsets.UTF_8);
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try (PrintStream testOutput = new PrintStream(output, true, StandardCharsets.UTF_8)) {
            System.setIn(new ByteArrayInputStream(input));
            System.setOut(testOutput);

            Task9.main(new String[0]);

            String expected = "12 8 7 0 -3" + System.lineSeparator();
            String actual = output.toString(StandardCharsets.UTF_8);
            assertEquals(expected, actual);
        }
        finally {
            System.setIn(originalInput);
            System.setOut(originalOutput);
        }
    }
}