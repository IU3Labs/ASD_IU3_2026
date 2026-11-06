package ru.bmstu.lab1.task_13;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

class Task13Test {

    @Test
    void shouldSumMixedNumbers() {
        int[] arr = {8, -3, 12, 0, 7};
        long expected = 24;
        long actual = Task13.sumElements(arr);

        assertEquals(expected, actual);
    }

    @Test
    void shouldSumPositiveNumbers() {
        int[] arr = {5, 2, 9, 4};
        long expected = 20;
        long actual = Task13.sumElements(arr);

        assertEquals(expected, actual);
    }

    @Test
    void shouldSumNegativeNumbers() {
        int[] arr = {-8, -2, -15, -4};
        long expected = -29;
        long actual = Task13.sumElements(arr);

        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnZeroForOppositeNumbers() {
        int[] arr = {-5, 3, 5, -3};
        long expected = 0;
        long actual = Task13.sumElements(arr);

        assertEquals(expected, actual);
    }

    @Test
    void shouldHandleZeros() {
        int[] arr = {0, 0, 0};
        long expected = 0;
        long actual = Task13.sumElements(arr);

        assertEquals(expected, actual);
    }

    @Test
    void shouldCountEveryRepeatedNumber() {
        int[] arr = {7, 7, 7};
        long expected = 21;
        long actual = Task13.sumElements(arr);

        assertEquals(expected, actual);
    }

    @Test
    void shouldHandleTwoNumbers() {
        int[] arr = {8, -2};
        long expected = 6;
        long actual = Task13.sumElements(arr);

        assertEquals(expected, actual);
    }

    @Test
    void shouldHandleSumAboveIntegerMaximum() {
        int[] arr = {Integer.MAX_VALUE, Integer.MAX_VALUE};
        long expected = 4_294_967_294L;
        long actual = Task13.sumElements(arr);

        assertEquals(expected, actual);
    }

    @Test
    void shouldHandleSumBelowIntegerMinimum() {
        int[] arr = {Integer.MIN_VALUE, Integer.MIN_VALUE};
        long expected = -4_294_967_296L;
        long actual = Task13.sumElements(arr);

        assertEquals(expected, actual);
    }

    @Test
    void shouldHandleBothIntegerLimits() {
        int[] arr = {Integer.MIN_VALUE, Integer.MAX_VALUE};
        long expected = -1;
        long actual = Task13.sumElements(arr);

        assertEquals(expected, actual);
    }

    @Test
    void shouldHandleLargestAllowedArray() {
        int[] arr = new int[99_999];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = Integer.MAX_VALUE;
        }
        long expected = 99_999L * Integer.MAX_VALUE;

        long actual = Task13.sumElements(arr);

        assertEquals(expected, actual);
    }

    @Test
    void shouldKeepOriginalArrayUnchanged() {
        int[] arr = {5, -2, 9, 1};
        int[] expected = arr.clone();

        Task13.sumElements(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    void shouldRejectEmptyArray() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Task13.sumElements(new int[0]));
    }

    @Test
    void shouldRejectSingleElementArray() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Task13.sumElements(new int[] {5}));
    }

    @Test
    void shouldRejectTooLargeArray() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Task13.sumElements(new int[100_000]));
    }

    @Test
    void shouldReadInputAndPrintSum() {
        InputStream originalInput = System.in;
        PrintStream originalOutput = System.out;
        byte[] input = "5\n8 -3 12 0 7\n".getBytes(StandardCharsets.UTF_8);
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try (PrintStream testOutput = new PrintStream(output, true, StandardCharsets.UTF_8)) {
            System.setIn(new ByteArrayInputStream(input));
            System.setOut(testOutput);

            Task13.main(new String[0]);

            String expected = "24" + System.lineSeparator();
            String actual = output.toString(StandardCharsets.UTF_8);
            assertEquals(expected, actual);
        }
        finally {
            System.setIn(originalInput);
            System.setOut(originalOutput);
        }
    }
}