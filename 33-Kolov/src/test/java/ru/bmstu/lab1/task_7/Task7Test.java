package ru.bmstu.lab1.task_7;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

class Task7Test {

    @Test
    void shouldFindElementEqualToAverage() {
        int[] arr = {1, 3, 5};
        int expected = 3;
        int actual = Task7.findClosestToAverage(arr);

        assertEquals(expected, actual);
    }

    @Test
    void shouldHandleFractionalAverage() {
        int[] arr = {1, 2, 7};
        // Среднее равно 10/3, ближайший элемент — 2.
        int expected = 2;
        int actual = Task7.findClosestToAverage(arr);

        assertEquals(expected, actual);
    }

    @Test
    void shouldChooseFirstElementWhenDistancesAreEqual() {
        int[] arr = {1, 4, 6, 9};
        // Среднее равно 5; из элементов 4 и 6 первым встречается 4.
        int expected = 4;
        int actual = Task7.findClosestToAverage(arr);

        assertEquals(expected, actual);
    }

    @Test
    void shouldHandleNegativeFractionalAverage() {
        int[] arr = {-1, -2, -7};
        // Среднее равно -10/3, ближайший элемент — -2.
        int expected = -2;
        int actual = Task7.findClosestToAverage(arr);

        assertEquals(expected, actual);
    }

    @Test
    void shouldHandleMixedSigns() {
        int[] arr = {-10, -2, 3, 9};
        // Среднее равно 0, ближайший элемент — -2.
        int expected = -2;
        int actual = Task7.findClosestToAverage(arr);

        assertEquals(expected, actual);
    }

    @Test
    void shouldHandleEqualNumbers() {
        int[] arr = {7, 7, 7};
        int expected = 7;
        int actual = Task7.findClosestToAverage(arr);

        assertEquals(expected, actual);
    }

    @Test
    void shouldChooseFirstOfTwoDifferentNumbers() {
        int[] arr = {8, 2};
        // Для двух чисел расстояния до их среднего всегда одинаковы.
        int expected = 8;
        int actual = Task7.findClosestToAverage(arr);

        assertEquals(expected, actual);
    }

    @Test
    void shouldFindClosestElementAtFirstIndex() {
        int[] arr = {4, 1, 8};
        int expected = 4;
        int actual = Task7.findClosestToAverage(arr);

        assertEquals(expected, actual);
    }

    @Test
    void shouldFindClosestElementAtLastIndex() {
        int[] arr = {1, 8, 4};
        int expected = 4;
        int actual = Task7.findClosestToAverage(arr);

        assertEquals(expected, actual);
    }

    @Test
    void shouldHandleIntegerLimits() {
        int[] arr = {Integer.MAX_VALUE, Integer.MIN_VALUE};
        // Среднее равно -0.5; расстояния равны, выбирается первый элемент.
        int expected = Integer.MAX_VALUE;
        int actual = Task7.findClosestToAverage(arr);

        assertEquals(expected, actual);
    }

    @Test
    void shouldHandleSumAboveIntegerMaximum() {
        int[] arr = {
                Integer.MAX_VALUE,
                Integer.MAX_VALUE - 2,
                Integer.MAX_VALUE - 4
        };
        int expected = Integer.MAX_VALUE - 2;
        int actual = Task7.findClosestToAverage(arr);

        assertEquals(expected, actual);
    }

    @Test
    void shouldHandleSumBelowIntegerMinimum() {
        int[] arr = {
                Integer.MIN_VALUE,
                Integer.MIN_VALUE + 2,
                Integer.MIN_VALUE + 4
        };
        int expected = Integer.MIN_VALUE + 2;
        int actual = Task7.findClosestToAverage(arr);

        assertEquals(expected, actual);
    }

    @Test
    void shouldHandleLargestAllowedArray() {
        int[] arr = new int[99_999];
        arr[0] = Integer.MAX_VALUE;
        int expected = 0;
        int actual = Task7.findClosestToAverage(arr);

        assertEquals(expected, actual);
    }

    @Test
    void shouldKeepOriginalArrayUnchanged() {
        int[] arr = {9, 6, 4, 1};
        int[] expected = arr.clone();
        Task7.findClosestToAverage(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    void shouldRejectEmptyArray() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Task7.findClosestToAverage(new int[0]));
    }

    @Test
    void shouldRejectSingleElementArray() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Task7.findClosestToAverage(new int[] {5}));
    }

    @Test
    void shouldRejectTooLargeArray() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Task7.findClosestToAverage(new int[100_000]));
    }

    @Test
    void shouldReadInputAndPrintClosestElement() {
        InputStream originalInput = System.in;
        PrintStream originalOutput = System.out;
        byte[] input = "4\n1 4 6 9\n".getBytes(StandardCharsets.UTF_8);
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try (PrintStream testOutput = new PrintStream(output, true, StandardCharsets.UTF_8)) {
            System.setIn(new ByteArrayInputStream(input));
            System.setOut(testOutput);

            Task7.main(new String[0]);

            String expected = "4" + System.lineSeparator();
            String actual = output.toString(StandardCharsets.UTF_8);
            assertEquals(expected, actual);
        }
        finally {
            System.setIn(originalInput);
            System.setOut(originalOutput);
        }
    }
}