package ru.bmstu.lab1.task_5;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

class Task5Test {

    @Test
    void shouldCountElementsAboveAverage() {
        int[] arr = {1, 2, 3, 4, 5};
        int expected = 2;
        int actual = Task5.countAboveAverage(arr);

        assertEquals(expected, actual);
    }

    @Test
    void shouldHandleNegativeNumbers() {
        int[] arr = {-10, -8, -6, -4};
        // Среднее равно -7; больше него элементы -6 и -4.
        int expected = 2;
        int actual = Task5.countAboveAverage(arr);

        assertEquals(expected, actual);
    }

    @Test
    void shouldHandleMixedSigns() {
        int[] arr = {-6, -2, 0, 3, 5};
        int expected = 2;
        int actual = Task5.countAboveAverage(arr);

        assertEquals(expected, actual);
    }

    @Test
    void shouldExcludeElementsEqualToAverage() {
        int[] arr = {1, 3, 3, 3, 5};
        int expected = 1;
        int actual = Task5.countAboveAverage(arr);

        assertEquals(expected, actual);
    }

    @Test
    void shouldCountRepeatedElementsSeparately() {
        int[] arr = {0, 0, 4, 4};
        int expected = 2;
        int actual = Task5.countAboveAverage(arr);

        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnZeroForEqualNumbers() {
        int[] arr = {7, 7, 7};
        int expected = 0;
        int actual = Task5.countAboveAverage(arr);

        assertEquals(expected, actual);
    }

    @Test
    void shouldHandleTwoNumbers() {
        int[] arr = {2, 8};
        int expected = 1;
        int actual = Task5.countAboveAverage(arr);

        assertEquals(expected, actual);
    }

    @Test
    void shouldHandleSumAboveIntegerMaximum() {
        int[] arr = {Integer.MAX_VALUE, Integer.MAX_VALUE - 2};
        // Сумма превышает диапазон int, среднее равно 2147483646.
        int expected = 1;
        int actual = Task5.countAboveAverage(arr);

        assertEquals(expected, actual);
    }

    @Test
    void shouldHandleSumBelowIntegerMinimum() {
        int[] arr = {Integer.MIN_VALUE, Integer.MIN_VALUE + 2};
        // Сумма меньше нижней границы int, среднее равно -2147483647.
        int expected = 1;
        int actual = Task5.countAboveAverage(arr);

        assertEquals(expected, actual);
    }

    @Test
    void shouldHandleLargestAllowedArray() {
        // Сумма равна 99999, поэтому среднее равно 1.
        int[] arr = new int[99_999];
        arr[arr.length - 1] = 99_999;
        int expected = 1;
        int actual = Task5.countAboveAverage(arr);

        assertEquals(expected, actual);
    }

    @Test
    void shouldKeepOriginalArrayUnchanged() {
        int[] arr = {1, 2, 3, 4, 5};
        int[] expected = arr.clone();

        Task5.countAboveAverage(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    void shouldRejectEmptyArray() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Task5.countAboveAverage(new int[0]));
    }

    @Test
    void shouldRejectSingleElementArray() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Task5.countAboveAverage(new int[] {5}));
    }

    @Test
    void shouldRejectTooLargeArray() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Task5.countAboveAverage(new int[100_000]));
    }

    @Test
    void shouldReadInputAndPrintCount() {
        InputStream originalInput = System.in;
        PrintStream originalOutput = System.out;
        byte[] input = "5\n1 2 3 4 5\n".getBytes(StandardCharsets.UTF_8);
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try (PrintStream testOutput = new PrintStream(output, true, StandardCharsets.UTF_8)) {
            System.setIn(new ByteArrayInputStream(input));
            System.setOut(testOutput);

            Task5.main(new String[0]);

            String expected = "2" + System.lineSeparator();
            String actual = output.toString(StandardCharsets.UTF_8);
            assertEquals(expected, actual);
        }
        finally {
            System.setIn(originalInput);
            System.setOut(originalOutput);
        }
    }
}