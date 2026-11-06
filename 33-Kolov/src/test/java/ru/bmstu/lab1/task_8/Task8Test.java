package ru.bmstu.lab1.task_8;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

class Task8Test {

    @Test
    void shouldSortMixedNumbers() {
        int[] arr = {8, -3, 12, 0, 7};
        int[] expected = {-3, 0, 7, 8, 12};

        Task8.selectionSort(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    void shouldSortPositiveNumbers() {
        int[] arr = {5, 2, 9, 4};
        int[] expected = {2, 4, 5, 9};

        Task8.selectionSort(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    void shouldSortNegativeNumbers() {
        int[] arr = {-8, -2, -15, -4};
        int[] expected = {-15, -8, -4, -2};

        Task8.selectionSort(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    void shouldKeepSortedArrayUnchanged() {
        int[] arr = {-5, 0, 3, 10};
        int[] expected = {-5, 0, 3, 10};

        Task8.selectionSort(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    void shouldSortDecreasingArray() {
        int[] arr = {9, 7, 5, 3, 1};
        int[] expected = {1, 3, 5, 7, 9};

        Task8.selectionSort(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    void shouldHandleRepeatedNumbers() {
        int[] arr = {5, 2, 5, 1, 2};
        int[] expected = {1, 2, 2, 5, 5};

        Task8.selectionSort(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    void shouldHandleEqualNumbers() {
        int[] arr = {7, 7, 7};
        int[] expected = {7, 7, 7};

        Task8.selectionSort(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    void shouldSortTwoNumbers() {
        int[] arr = {8, 2};
        int[] expected = {2, 8};

        Task8.selectionSort(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    void shouldHandleIntegerLimits() {
        int[] arr = {Integer.MAX_VALUE, 0, Integer.MIN_VALUE, -1};
        int[] expected = {Integer.MIN_VALUE, -1, 0, Integer.MAX_VALUE};

        Task8.selectionSort(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    void shouldSortLargerArray() {
        // Умеренный размер выбран с учётом квадратичной сложности сортировки.
        int n = 1000;
        int[] arr = new int[n];
        int[] expected = new int[n];

        for (int i = 0; i < n; i++) {
            arr[i] = n - i;
            expected[i] = i + 1;
        }

        Task8.selectionSort(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    void shouldRejectEmptyArray() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Task8.selectionSort(new int[0]));
    }

    @Test
    void shouldRejectSingleElementArray() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Task8.selectionSort(new int[] {5}));
    }

    @Test
    void shouldRejectTooLargeArray() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Task8.selectionSort(new int[100_000]));
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

            Task8.main(new String[0]);

            String expected = "-3 0 7 8 12" + System.lineSeparator();
            String actual = output.toString(StandardCharsets.UTF_8);
            assertEquals(expected, actual);
        }
        finally {
            System.setIn(originalInput);
            System.setOut(originalOutput);
        }
    }
}