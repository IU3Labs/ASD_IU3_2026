package ru.bmstu.lab1.task_3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

class Task3Test {

    @Test
    void shouldFindAllMatchingIndices() {
        int[] arr = {8, -3, 12, -3, 7, -3};
        int[] expected = {1, 3, 5};
        int[] actual = Task3.findAllIndices(arr, -3);

        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldFindSingleMatch() {
        int[] arr = {8, 3, 12, 7};
        int[] expected = {2};
        int[] actual = Task3.findAllIndices(arr, 12);

        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldFindFirstAndLastIndices() {
        int[] arr = {5, 2, 9, 5};
        int[] expected = {0, 3};
        int[] actual = Task3.findAllIndices(arr, 5);

        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldFindEveryIndexForEqualNumbers() {
        int[] arr = {7, 7, 7};
        int[] expected = {0, 1, 2};
        int[] actual = Task3.findAllIndices(arr, 7);

        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldReturnEmptyArrayWhenNumberIsAbsent() {
        int[] arr = {8, -3, 12, 7};
        int[] expected = new int[0];
        int[] actual = Task3.findAllIndices(arr, 5);

        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldHandleTwoNumbersAndIntegerLimits() {
        int[] arr = {Integer.MIN_VALUE, Integer.MAX_VALUE};

        assertArrayEquals(
                new int[] {0},
                Task3.findAllIndices(arr, Integer.MIN_VALUE));
        assertArrayEquals(
                new int[] {1},
                Task3.findAllIndices(arr, Integer.MAX_VALUE));
    }

    @Test
    void shouldHandleLargestAllowedArrayWithAllMatches() {
        int[] arr = new int[99_999];
        int[] expected = new int[arr.length];
        for (int i = 0; i < expected.length; i++) {
            expected[i] = i;
        }

        int[] actual = Task3.findAllIndices(arr, 0);

        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldKeepOriginalArrayUnchanged() {
        int[] arr = {5, -2, 5, 1};
        int[] expected = arr.clone();

        Task3.findAllIndices(arr, 5);

        assertArrayEquals(expected, arr);
    }

    @Test
    void shouldRejectEmptyArray() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Task3.findAllIndices(new int[0], 5));
    }

    @Test
    void shouldRejectSingleElementArray() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Task3.findAllIndices(new int[] {5}, 5));
    }

    @Test
    void shouldRejectTooLargeArray() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Task3.findAllIndices(new int[100_000], 5));
    }

    @Test
    void shouldReadInputAndPrintAllIndices() {
        String input = "6\n8 -3 12 -3 7 -3\n-3\n";
        String expected = "1 3 5" + System.lineSeparator();

        String actual = runMain(input);

        assertEquals(expected, actual);
    }

    @Test
    void shouldPrintEmptyLineWhenNumberIsAbsent() {
        String input = "4\n8 -3 12 7\n5\n";
        String expected = System.lineSeparator();

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

            Task3.main(new String[0]);

            return output.toString(StandardCharsets.UTF_8);
        }
        finally {
            System.setIn(originalInput);
            System.setOut(originalOutput);
        }
    }
}