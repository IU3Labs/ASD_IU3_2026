package ru.bmstu.lab1.task_1;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

class Task1Test {

    @Test
    void shouldFindMinMaxForMixedNumbers() {
        int[] arr = {8, -3, 12, 0, 7};
        int[] expected = {-3, 12};
        int[] actual = Task1.findMinMax(arr);

        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldFindMinMaxForPositiveNumbers() {
        int[] arr = {5, 2, 9, 4};
        int[] expected = {2, 9};
        int[] actual = Task1.findMinMax(arr);

        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldFindMinMaxForNegativeNumbers() {
        int[] arr = {-8, -2, -15, -4};
        int[] expected = {-15, -2};
        int[] actual = Task1.findMinMax(arr);

        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldHandleEqualNumbers() {
        int[] arr = {7, 7, 7};
        int[] expected = {7, 7};
        int[] actual = Task1.findMinMax(arr);

        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldHandleTwoNumbersAndIntegerLimits() {
        int[] arr = {Integer.MAX_VALUE, Integer.MIN_VALUE};
        int[] expected = {Integer.MIN_VALUE, Integer.MAX_VALUE};
        int[] actual = Task1.findMinMax(arr);

        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldHandleLargestAllowedArray() {
        // По условию n < 100000, поэтому максимальный размер — 99999.
        int[] arr = new int[99_999];
        arr[0] = -10;
        arr[arr.length - 1] = 20;
        int[] expected = {-10, 20};

        int[] actual = Task1.findMinMax(arr);

        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldKeepOriginalArrayUnchanged() {
        int[] arr = {5, -2, 9, 1};
        int[] expected = arr.clone();

        Task1.findMinMax(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    void shouldRejectSingleElementArray() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Task1.findMinMax(new int[] {5}));
    }

    @Test
    void shouldRejectEmptyArray() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Task1.findMinMax(new int[0]));
    }

    @Test
    void shouldRejectTooLargeArray() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Task1.findMinMax(new int[100000]));
    }

    @Test
    void shouldReadInputAndPrintMinMax() {
        InputStream originalInput = System.in;
        PrintStream originalOutput = System.out;
        byte[] input = "5\n8 -3 12 0 7\n".getBytes(StandardCharsets.UTF_8);
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try (PrintStream testOutput =
                     new PrintStream(output, true, StandardCharsets.UTF_8)) {
            System.setIn(new ByteArrayInputStream(input));
            System.setOut(testOutput);

            Task1.main(new String[0]);

            String expected = "-3 12" + System.lineSeparator();
            String actual = output.toString(StandardCharsets.UTF_8);
            assertEquals(expected, actual);
        } finally {
            System.setIn(originalInput);
            System.setOut(originalOutput);
        }
    }
}
