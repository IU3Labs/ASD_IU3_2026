package ru.bmstu.lab1.task_4;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

class Task4Test {

    @Test
    void shouldSumElementsAtEvenIndicesForOddLength() {
        int[] arr = {8, -3, 12, 7, 5};
        long expected = 25;
        long actual = Task4.sumEvenIndices(arr);

        assertEquals(expected, actual);
    }

    @Test
    void shouldSumElementsAtEvenIndicesForEvenLength() {
        int[] arr = {3, 100, 5, 200, 7, 300};
        long expected = 15;
        long actual = Task4.sumEvenIndices(arr);

        assertEquals(expected, actual);
    }

    @Test
    void shouldHandleNegativeNumbers() {
        int[] arr = {-8, -2, -15, -4, -3};
        long expected = -26;
        long actual = Task4.sumEvenIndices(arr);

        assertEquals(expected, actual);
    }

    @Test
    void shouldHandleMixedSigns() {
        int[] arr = {-5, 10, 8, 20, -3};
        long expected = 0;
        long actual = Task4.sumEvenIndices(arr);

        assertEquals(expected, actual);
    }

    @Test
    void shouldHandleZeros() {
        int[] arr = {0, 0, 0, 0};
        long expected = 0;
        long actual = Task4.sumEvenIndices(arr);

        assertEquals(expected, actual);
    }

    @Test
    void shouldUseOnlyFirstElementForTwoNumbers() {
        int[] arr = {7, 100};
        long expected = 7;
        long actual = Task4.sumEvenIndices(arr);

        assertEquals(expected, actual);
    }

    @Test
    void shouldHandleSumAboveIntegerMaximum() {
        int[] arr = {Integer.MAX_VALUE, 0, Integer.MAX_VALUE};
        long expected = 4_294_967_294L;
        long actual = Task4.sumEvenIndices(arr);

        assertEquals(expected, actual);
    }

    @Test
    void shouldHandleSumBelowIntegerMinimum() {
        int[] arr = {Integer.MIN_VALUE, 0, Integer.MIN_VALUE};
        long expected = -4_294_967_296L;
        long actual = Task4.sumEvenIndices(arr);

        assertEquals(expected, actual);
    }

    @Test
    void shouldHandleLargestAllowedArray() {
        // Максимальный размер — 99999, число чётных индексов — 50000.
        int[] arr = new int[99_999];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = Integer.MAX_VALUE;
        }
        long expected = 50_000L * Integer.MAX_VALUE;
        long actual = Task4.sumEvenIndices(arr);

        assertEquals(expected, actual);
    }

    @Test
    void shouldKeepOriginalArrayUnchanged() {
        int[] arr = {5, -2, 9, 1};
        int[] expected = arr.clone();

        Task4.sumEvenIndices(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    void shouldRejectEmptyArray() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Task4.sumEvenIndices(new int[0]));
    }

    @Test
    void shouldRejectSingleElementArray() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Task4.sumEvenIndices(new int[] {5}));
    }

    @Test
    void shouldRejectTooLargeArray() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Task4.sumEvenIndices(new int[100_000]));
    }

    @Test
    void shouldReadInputAndPrintSum() {
        InputStream originalInput = System.in;
        PrintStream originalOutput = System.out;
        byte[] input = "5\n8 -3 12 7 5\n".getBytes(StandardCharsets.UTF_8);
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try (PrintStream testOutput = new PrintStream(output, true, StandardCharsets.UTF_8)) {
            System.setIn(new ByteArrayInputStream(input));
            System.setOut(testOutput);

            Task4.main(new String[0]);

            String expected = "25" + System.lineSeparator();
            String actual = output.toString(StandardCharsets.UTF_8);
            assertEquals(expected, actual);
        }
        finally {
            System.setIn(originalInput);
            System.setOut(originalOutput);
        }
    }
}