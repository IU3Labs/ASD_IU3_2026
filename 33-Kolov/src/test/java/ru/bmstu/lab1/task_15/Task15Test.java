package ru.bmstu.lab1.task_15;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

class Task15Test {

    @Test
    void shouldFindMedianForOddLength() {
        int[] arr = {8, 1, 5, 3, 9};
        double expected = 5.0;
        double actual = Task15.findMedian(arr);

        assertEquals(expected, actual);
    }

    @Test
    void shouldFindFractionalMedianForEvenLength() {
        int[] arr = {8, 1, 5, 2};
        double expected = 3.5;
        double actual = Task15.findMedian(arr);

        assertEquals(expected, actual);
    }

    @Test
    void shouldFindIntegerMedianForEvenLength() {
        int[] arr = {10, 2, 6, 4};
        double expected = 5.0;
        double actual = Task15.findMedian(arr);

        assertEquals(expected, actual);
    }

    @Test
    void shouldHandleNegativeNumbers() {
        int[] arr = {-8, -1, -5, -2};
        double expected = -3.5;
        double actual = Task15.findMedian(arr);

        assertEquals(expected, actual);
    }

    @Test
    void shouldHandleMixedSigns() {
        int[] arr = {-10, 8, -2, 3, 0};
        double expected = 0.0;
        double actual = Task15.findMedian(arr);

        assertEquals(expected, actual);
    }

    @Test
    void shouldHandleEqualNumbers() {
        int[] arr = {7, 7, 7, 7};
        double expected = 7.0;
        double actual = Task15.findMedian(arr);

        assertEquals(expected, actual);
    }

    @Test
    void shouldHandleRepeatedNumbers() {
        int[] arr = {9, 2, 2, 5, 2};
        double expected = 2.0;
        double actual = Task15.findMedian(arr);

        assertEquals(expected, actual);
    }

    @Test
    void shouldHandleTwoNumbers() {
        int[] arr = {8, 1};
        double expected = 4.5;
        double actual = Task15.findMedian(arr);

        assertEquals(expected, actual);
    }

    @Test
    void shouldHandleBothIntegerLimits() {
        int[] arr = {Integer.MIN_VALUE, Integer.MAX_VALUE};
        double expected = -0.5;
        double actual = Task15.findMedian(arr);

        assertEquals(expected, actual);
    }

    @Test
    void shouldAvoidOverflowForLargePositiveNumbers() {
        int[] arr = {Integer.MAX_VALUE, Integer.MAX_VALUE - 1};
        double expected = 2_147_483_646.5;
        double actual = Task15.findMedian(arr);

        assertEquals(expected, actual);
    }

    @Test
    void shouldAvoidOverflowForLargeNegativeNumbers() {
        int[] arr = {Integer.MIN_VALUE, Integer.MIN_VALUE + 1};
        double expected = -2_147_483_647.5;
        double actual = Task15.findMedian(arr);

        assertEquals(expected, actual);
    }

    @Test
    void shouldHandleLargestAllowedArray() {
        int n = 99_999;
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = n - i;
        }
        double expected = 50_000.0;
        double actual = Task15.findMedian(arr);

        assertEquals(expected, actual);
    }

    @Test
    void shouldKeepOriginalArrayUnchanged() {
        int[] arr = {8, 1, 5, 2};
        int[] expected = arr.clone();

        Task15.findMedian(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    void shouldRejectEmptyArray() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Task15.findMedian(new int[0]));
    }

    @Test
    void shouldRejectSingleElementArray() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Task15.findMedian(new int[] {5}));
    }

    @Test
    void shouldRejectTooLargeArray() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Task15.findMedian(new int[100_000]));
    }

    @Test
    void shouldReadInputAndPrintOddLengthMedian() {
        String input = "5\n8 1 5 3 9\n";
        String expected = "5.0" + System.lineSeparator();

        String actual = runMain(input);

        assertEquals(expected, actual);
    }

    @Test
    void shouldReadInputAndPrintEvenLengthMedian() {
        String input = "4\n8 1 5 2\n";
        String expected = "3.5" + System.lineSeparator();

        String actual = runMain(input);

        assertEquals(expected, actual);
    }

    private String runMain(String input) {
        InputStream originalInput = System.in;
        PrintStream originalOutput = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try (PrintStream testOutput = new PrintStream(output, true, StandardCharsets.UTF_8)) {
            byte[] bytes = input.getBytes(StandardCharsets.UTF_8);
            System.setIn(new ByteArrayInputStream(bytes));
            System.setOut(testOutput);

            Task15.main(new String[0]);

            return output.toString(StandardCharsets.UTF_8);
        }
        finally {
            System.setIn(originalInput);
            System.setOut(originalOutput);
        }
    }
}