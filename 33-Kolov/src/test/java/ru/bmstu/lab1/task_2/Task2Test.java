package ru.bmstu.lab1.task_2;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

class Task2Test {

    @Test
    void shouldFindFirstElement() {
        int[] arr = {8, -3, 12, 7};
        int x = 8;
        int res = Task2.findFirstIndex(arr, x);

        assertEquals(0, res);
    }

    @Test
    void shouldFindMiddleElement() {
        int[] arr = {8, -3, 12, 7, 4};
        int x = 12;
        int res = Task2.findFirstIndex(arr, x);

        assertEquals(2, res);
    }

    @Test
    void shouldFindLastElement() {
        int[] arr = {8, -3, 12, 7};
        int x = 7;
        int res = Task2.findFirstIndex(arr, x);

        assertEquals(3, res);
    }

    @Test
    void shouldReturnFirstIndexForRepeatedNumber() {
        int[] arr = {8, -3, 12, -3, 7};
        int x = -3;
        int res = Task2.findFirstIndex(arr, x);

        assertEquals(1, res);
    }

    @Test
    void shouldReturnMinusOneWhenNumberIsAbsent() {
        int[] arr = {8, -3, 12, 7};
        int x = 5;
        int res = Task2.findFirstIndex(arr, x);

        assertEquals(-1, res);
    }

    @Test
    void shouldReturnZeroForEqualNumbers() {
        int[] arr = {7, 7, 7};
        int x = 7;
        int res = Task2.findFirstIndex(arr, x);

        assertEquals(0, res);
    }

    @Test
    void shouldHandleTwoNumbersAndIntegerLimits() {
        int[] arr = {Integer.MAX_VALUE, Integer.MIN_VALUE};

        assertEquals(0, Task2.findFirstIndex(arr, Integer.MAX_VALUE));
        assertEquals(1, Task2.findFirstIndex(arr, Integer.MIN_VALUE));
    }

    @Test
    void shouldHandleLargestAllowedArray() {
        // Максимальный допустимый размер по условию — 99999.
        int[] arr = new int[99_999];
        arr[arr.length - 1] = 5;
        int x = 5;

        int res = Task2.findFirstIndex(arr, x);

        assertEquals(arr.length - 1, res);
    }

    @Test
    void shouldKeepOriginalArrayUnchanged() {
        int[] arr = {5, -2, 9, 1};
        int[] expected = arr.clone();

        Task2.findFirstIndex(arr, 9);

        assertArrayEquals(expected, arr);
    }

    @Test
    void shouldRejectEmptyArray() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Task2.findFirstIndex(new int[0], 5));
    }

    @Test
    void shouldRejectSingleElementArray() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Task2.findFirstIndex(new int[] {5}, 5));
    }

    @Test
    void shouldRejectTooLargeArray() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Task2.findFirstIndex(new int[100_000], 5));
    }

    @Test
    void shouldReadInputAndPrintFirstIndex() {
        InputStream originalInput = System.in;
        PrintStream originalOutput = System.out;
        byte[] input = "5\n8 -3 12 -3 7\n-3\n".getBytes(StandardCharsets.UTF_8);
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try (PrintStream testOutput =
                     new PrintStream(output, true, StandardCharsets.UTF_8)) {
            System.setIn(new ByteArrayInputStream(input));
            System.setOut(testOutput);

            Task2.main(new String[0]);

            String expected = "1" + System.lineSeparator();
            String actual = output.toString(StandardCharsets.UTF_8);
            assertEquals(expected, actual);
        } finally {
            System.setIn(originalInput);
            System.setOut(originalOutput);
        }
    }
}