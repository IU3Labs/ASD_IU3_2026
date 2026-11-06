package ru.bmstu.lab1.task_10;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

class Task10Test {

    @Test
    void shouldSortMixedNumbersAndCountSwaps() {
        int[] arr = {8, -3, 12, 0, 7};
        int[] expected = {-3, 0, 7, 8, 12};
        long res = Task10.bubbleSort(arr);

        assertArrayEquals(expected, arr);
        assertEquals(5L, res);
    }

    @Test
    void shouldSortThreeNumbersWithTwoSwaps() {
        int[] arr = {3, 1, 2};
        int[] expected = {1, 2, 3};
        long res = Task10.bubbleSort(arr);

        assertArrayEquals(expected, arr);
        assertEquals(2L, res);
    }

    @Test
    void shouldPerformNoSwapsForSortedArray() {
        int[] arr = {-5, 0, 3, 10};
        int[] expected = {-5, 0, 3, 10};
        long res = Task10.bubbleSort(arr);

        assertArrayEquals(expected, arr);
        assertEquals(0L, res);
    }

    @Test
    void shouldSortDecreasingArray() {
        int[] arr = {5, 4, 3, 2, 1};
        int[] expected = {1, 2, 3, 4, 5};
        long res = Task10.bubbleSort(arr);

        assertArrayEquals(expected, arr);
        assertEquals(10L, res);
    }

    @Test
    void shouldSortNegativeNumbers() {
        int[] arr = {-2, -8, -4};
        int[] expected = {-8, -4, -2};
        long res = Task10.bubbleSort(arr);

        assertArrayEquals(expected, arr);
        assertEquals(2L, res);
    }

    @Test
    void shouldHandleRepeatedNumbers() {
        int[] arr = {2, 1, 2, 1};
        int[] expected = {1, 1, 2, 2};
        long res = Task10.bubbleSort(arr);

        assertArrayEquals(expected, arr);
        assertEquals(3L, res);
    }

    @Test
    void shouldPerformNoSwapsForEqualNumbers() {
        int[] arr = {7, 7, 7};
        int[] expected = {7, 7, 7};
        long res = Task10.bubbleSort(arr);

        assertArrayEquals(expected, arr);
        assertEquals(0L, res);
    }

    @Test
    void shouldSortTwoNumbersWithOneSwap() {
        int[] arr = {8, 2};
        int[] expected = {2, 8};
        long res = Task10.bubbleSort(arr);

        assertArrayEquals(expected, arr);
        assertEquals(1L, res);
    }

    @Test
    void shouldHandleIntegerLimits() {
        int[] arr = {Integer.MAX_VALUE, 0, Integer.MIN_VALUE};
        int[] expected = {Integer.MIN_VALUE, 0, Integer.MAX_VALUE};
        long res = Task10.bubbleSort(arr);

        assertArrayEquals(expected, arr);
        assertEquals(3L, res);
    }

    @Test
    void shouldMoveSmallestElementFromEndToBeginning() {
        int[] arr = {2, 3, 4, 5, 1};
        int[] expected = {1, 2, 3, 4, 5};
        long res = Task10.bubbleSort(arr);

        assertArrayEquals(expected, arr);
        assertEquals(4L, res);
    }

    @Test
    void shouldSortLargerDecreasingArray() {
        // Умеренный размер для проверки худшего случая сортировки.
        int n = 1000;
        int[] arr = new int[n];
        int[] expected = new int[n];

        for (int i = 0; i < n; i++) {
            arr[i] = n - i;
            expected[i] = i + 1;
        }

        long res = Task10.bubbleSort(arr);

        assertArrayEquals(expected, arr);
        assertEquals((long) n * (n - 1) / 2, res);
    }

    @Test
    void shouldHandleLargestAllowedSortedArray() {
        // Досрочный выход позволяет быстро обработать отсортированный массив.
        int[] arr = new int[99_999];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
        int[] expected = arr.clone();

        long res = Task10.bubbleSort(arr);

        assertArrayEquals(expected, arr);
        assertEquals(0L, res);
    }

    @Test
    void shouldRejectEmptyArray() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Task10.bubbleSort(new int[0]));
    }

    @Test
    void shouldRejectSingleElementArray() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Task10.bubbleSort(new int[] {5}));
    }

    @Test
    void shouldRejectTooLargeArray() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Task10.bubbleSort(new int[100_000]));
    }

    @Test
    void shouldReadInputAndPrintSortedArrayAndSwapCount() {
        InputStream originalInput = System.in;
        PrintStream originalOutput = System.out;
        byte[] input = "3\n3 1 2\n".getBytes(StandardCharsets.UTF_8);
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try (PrintStream testOutput = new PrintStream(output, true, StandardCharsets.UTF_8)) {
            System.setIn(new ByteArrayInputStream(input));
            System.setOut(testOutput);

            Task10.main(new String[0]);

            String expected = "1 2 3" + System.lineSeparator()
                    + "2" + System.lineSeparator();
            String actual = output.toString(StandardCharsets.UTF_8);
            assertEquals(expected, actual);
        }
        finally {
            System.setIn(originalInput);
            System.setOut(originalOutput);
        }
    }
}