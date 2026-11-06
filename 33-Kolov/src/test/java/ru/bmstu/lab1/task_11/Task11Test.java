package ru.bmstu.lab1.task_11;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

class Task11Test {

    @Test
    void shouldSortMixedNumbersByAbsoluteValue() {
        int[] arr = {3, -1, -3, 2, 0, -2, 1};
        int[] expected = {0, -1, 1, -2, 2, -3, 3};

        Task11.sortByAbsoluteValue(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    void shouldSortPositiveNumbers() {
        int[] arr = {8, 2, 5, 1};
        int[] expected = {1, 2, 5, 8};

        Task11.sortByAbsoluteValue(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    void shouldSortNegativeNumbers() {
        int[] arr = {-8, -2, -5, -1};
        int[] expected = {-1, -2, -5, -8};

        Task11.sortByAbsoluteValue(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    void shouldPlaceNegativeNumberBeforePositiveWithSameAbsoluteValue() {
        int[] arr = {5, -5};
        int[] expected = {-5, 5};

        Task11.sortByAbsoluteValue(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    void shouldCompareAbsoluteValueBeforeSign() {
        int[] arr = {-10, 2, -3, 1};
        int[] expected = {1, 2, -3, -10};

        Task11.sortByAbsoluteValue(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    void shouldHandleRepeatedNumbers() {
        int[] arr = {2, -2, 2, -2, 1};
        int[] expected = {1, -2, -2, 2, 2};

        Task11.sortByAbsoluteValue(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    void shouldHandleEqualNumbers() {
        int[] arr = {-7, -7, -7};
        int[] expected = {-7, -7, -7};

        Task11.sortByAbsoluteValue(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    void shouldPlaceZerosFirst() {
        int[] arr = {3, 0, -1, 0};
        int[] expected = {0, 0, -1, 3};

        Task11.sortByAbsoluteValue(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    void shouldKeepSortedArrayUnchanged() {
        int[] arr = {0, -1, 1, -3, 3, 8};
        int[] expected = arr.clone();

        Task11.sortByAbsoluteValue(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    void shouldHandleIntegerLimits() {
        int[] arr = {
                Integer.MIN_VALUE,
                Integer.MAX_VALUE,
                -Integer.MAX_VALUE,
                0
        };
        int[] expected = {
                0,
                -Integer.MAX_VALUE,
                Integer.MAX_VALUE,
                Integer.MIN_VALUE
        };

        Task11.sortByAbsoluteValue(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    void shouldHandleLargestAllowedArray() {
        int n = 99_999;
        int[] arr = new int[n];
        int[] expected = new int[n];

        for (int i = 0; i < n; i++) {
            arr[i] = -(n - i);
            expected[i] = -(i + 1);
        }

        Task11.sortByAbsoluteValue(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    void shouldRejectEmptyArray() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Task11.sortByAbsoluteValue(new int[0]));
    }

    @Test
    void shouldRejectSingleElementArray() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Task11.sortByAbsoluteValue(new int[] {5}));
    }

    @Test
    void shouldRejectTooLargeArray() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Task11.sortByAbsoluteValue(new int[100_000]));
    }

    @Test
    void shouldReadInputAndPrintSortedArray() {
        InputStream originalInput = System.in;
        PrintStream originalOutput = System.out;
        byte[] input = "7\n3 -1 -3 2 0 -2 1\n".getBytes(StandardCharsets.UTF_8);
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try (PrintStream testOutput = new PrintStream(output, true, StandardCharsets.UTF_8)) {
            System.setIn(new ByteArrayInputStream(input));
            System.setOut(testOutput);

            Task11.main(new String[0]);

            String expected = "0 -1 1 -2 2 -3 3" + System.lineSeparator();
            String actual = output.toString(StandardCharsets.UTF_8);
            assertEquals(expected, actual);
        }
        finally {
            System.setIn(originalInput);
            System.setOut(originalOutput);
        }
    }
}