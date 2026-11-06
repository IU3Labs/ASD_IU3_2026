package ru.bmstu.lab1.task_6;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

class Task6Test {

    @Test
    void shouldFindLastMatchingIndex() {
        int[] arr = {4, 9, 7, 12, 5, 8};
        int k = 3;
        int expected = 3;
        int actual = Task6.findLastMultipleIndex(arr, k);

        assertEquals(expected, actual);
    }

    @Test
    void shouldFindMatchAtLastIndex() {
        int[] arr = {5, 7, 9, 12};
        int k = 4;
        int expected = 3;
        int actual = Task6.findLastMultipleIndex(arr, k);

        assertEquals(expected, actual);
    }

    @Test
    void shouldFindMatchAtFirstIndex() {
        int[] arr = {12, 5, 7, 11};
        int k = 3;
        int expected = 0;
        int actual = Task6.findLastMultipleIndex(arr, k);

        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnMinusOneWhenNoMatchExists() {
        int[] arr = {1, 5, 7, 11};
        int k = 3;
        int expected = -1;
        int actual = Task6.findLastMultipleIndex(arr, k);

        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnLastIndexWhenAllElementsMatch() {
        int[] arr = {3, 6, 9, 12};
        int k = 3;
        int expected = 3;
        int actual = Task6.findLastMultipleIndex(arr, k);

        assertEquals(expected, actual);
    }

    @Test
    void shouldHandleNegativeNumbers() {
        int[] arr = {-6, -5, -12, -7};
        int k = 3;
        int expected = 2;
        int actual = Task6.findLastMultipleIndex(arr, k);

        assertEquals(expected, actual);
    }

    @Test
    void shouldHandleNegativeDivisor() {
        int[] arr = {6, 5, -12, 7};
        int k = -3;
        int expected = 2;
        int actual = Task6.findLastMultipleIndex(arr, k);

        assertEquals(expected, actual);
    }

    @Test
    void shouldTreatZeroAsMultiple() {
        int[] arr = {5, 0, 7};
        int k = 3;
        int expected = 1;
        int actual = Task6.findLastMultipleIndex(arr, k);

        assertEquals(expected, actual);
    }

    @Test
    void shouldHandleTwoNumbers() {
        int[] arr = {8, 5};
        int k = 2;
        int expected = 0;
        int actual = Task6.findLastMultipleIndex(arr, k);

        assertEquals(expected, actual);
    }

    @Test
    void shouldHandleDivisorOne() {
        int[] arr = {Integer.MIN_VALUE, 0, Integer.MAX_VALUE};
        int expected = 2;
        int actual = Task6.findLastMultipleIndex(arr, 1);

        assertEquals(expected, actual);
    }

    @Test
    void shouldHandleIntegerMinimumWithDivisorMinusOne() {
        int[] arr = {5, Integer.MIN_VALUE};
        int expected = 1;
        int actual = Task6.findLastMultipleIndex(arr, -1);

        assertEquals(expected, actual);
    }

    @Test
    void shouldHandleIntegerMinimumAsDivisor() {
        int[] arr = {Integer.MIN_VALUE, Integer.MAX_VALUE};
        int expected = 0;
        int actual = Task6.findLastMultipleIndex(arr, Integer.MIN_VALUE);

        assertEquals(expected, actual);
    }

    @Test
    void shouldHandleLargestAllowedArray() {
        // Единственное совпадение в начале требует просмотра всего массива.
        int[] arr = new int[99_999];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1;
        }
        arr[0] = 6;
        int expected = 0;
        int actual = Task6.findLastMultipleIndex(arr, 3);

        assertEquals(expected, actual);
    }

    @Test
    void shouldKeepOriginalArrayUnchanged() {
        int[] arr = {6, 5, 12, 7};
        int[] expected = arr.clone();

        Task6.findLastMultipleIndex(arr, 3);

        assertArrayEquals(expected, arr);
    }

    @Test
    void shouldRejectZeroDivisor() {
        int[] arr = {5, 10};

        assertThrows(
                IllegalArgumentException.class,
                () -> Task6.findLastMultipleIndex(arr, 0));
    }

    @Test
    void shouldRejectEmptyArray() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Task6.findLastMultipleIndex(new int[0], 3));
    }

    @Test
    void shouldRejectSingleElementArray() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Task6.findLastMultipleIndex(new int[] {6}, 3));
    }

    @Test
    void shouldRejectTooLargeArray() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Task6.findLastMultipleIndex(new int[100_000], 3));
    }

    @Test
    void shouldReadInputAndPrintLastIndex() {
        String input = "6\n4 9 7 12 5 8\n3\n";
        String expected = "3" + System.lineSeparator();

        String actual = runMain(input);

        assertEquals(expected, actual);
    }

    @Test
    void shouldPrintMinusOneWhenNoMatchExists() {
        String input = "4\n1 5 7 11\n3\n";
        String expected = "-1" + System.lineSeparator();

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

            Task6.main(new String[0]);

            return output.toString(StandardCharsets.UTF_8);
        }
        finally {
            System.setIn(originalInput);
            System.setOut(originalOutput);
        }
    }
}