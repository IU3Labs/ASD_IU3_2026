package ru.bmstu.lab1.task_14;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

class Task14Test {

    @Test
    void shouldSumSquaresOfMixedNumbers() {
        int[] arr = {2, -3, 0, 4};
        BigInteger expected = BigInteger.valueOf(29);

        BigInteger actual = Task14.sumSquares(arr);

        assertEquals(expected, actual);
    }

    @Test
    void shouldSumSquaresOfPositiveNumbers() {
        int[] arr = {1, 2, 3, 4};
        BigInteger expected = BigInteger.valueOf(30);

        BigInteger actual = Task14.sumSquares(arr);

        assertEquals(expected, actual);
    }

    @Test
    void shouldSumSquaresOfNegativeNumbers() {
        int[] arr = {-2, -3, -4};
        BigInteger expected = BigInteger.valueOf(29);

        BigInteger actual = Task14.sumSquares(arr);

        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnZeroForZeros() {
        int[] arr = {0, 0, 0};
        BigInteger expected = BigInteger.ZERO;

        BigInteger actual = Task14.sumSquares(arr);

        assertEquals(expected, actual);
    }

    @Test
    void shouldCountEveryRepeatedNumber() {
        int[] arr = {5, 5, 5};
        BigInteger expected = BigInteger.valueOf(75);

        BigInteger actual = Task14.sumSquares(arr);

        assertEquals(expected, actual);
    }

    @Test
    void shouldAddSquaresOfOppositeNumbers() {
        int[] arr = {-7, 7};
        BigInteger expected = BigInteger.valueOf(98);

        BigInteger actual = Task14.sumSquares(arr);

        assertEquals(expected, actual);
    }

    @Test
    void shouldHandleSquareAboveIntegerMaximum() {
        int[] arr = {50_000, 0};
        BigInteger expected = new BigInteger("2500000000");

        BigInteger actual = Task14.sumSquares(arr);

        assertEquals(expected, actual);
    }

    @Test
    void shouldHandleIntegerMaximum() {
        int[] arr = {Integer.MAX_VALUE, 0};
        BigInteger expected = new BigInteger("4611686014132420609");

        BigInteger actual = Task14.sumSquares(arr);

        assertEquals(expected, actual);
    }

    @Test
    void shouldHandleIntegerMinimum() {
        int[] arr = {Integer.MIN_VALUE, 0};
        BigInteger expected = new BigInteger("4611686018427387904");

        BigInteger actual = Task14.sumSquares(arr);

        assertEquals(expected, actual);
    }

    @Test
    void shouldHandleSumAboveLongMaximum() {
        int[] arr = {Integer.MIN_VALUE, Integer.MIN_VALUE};
        BigInteger expected = new BigInteger("9223372036854775808");

        BigInteger actual = Task14.sumSquares(arr);

        assertEquals(expected, actual);
    }

    @Test
    void shouldHandleLargestAllowedArray() {
        int[] arr = new int[99_999];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = Integer.MIN_VALUE;
        }
        BigInteger square = new BigInteger("4611686018427387904");
        BigInteger expected = square.multiply(BigInteger.valueOf(arr.length));

        BigInteger actual = Task14.sumSquares(arr);

        assertEquals(expected, actual);
    }

    @Test
    void shouldKeepOriginalArrayUnchanged() {
        int[] arr = {5, -2, 9, 1};
        int[] expected = arr.clone();

        Task14.sumSquares(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    void shouldRejectEmptyArray() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Task14.sumSquares(new int[0]));
    }

    @Test
    void shouldRejectSingleElementArray() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Task14.sumSquares(new int[] {5}));
    }

    @Test
    void shouldRejectTooLargeArray() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Task14.sumSquares(new int[100_000]));
    }

    @Test
    void shouldReadInputAndPrintSumOfSquares() {
        String input = "4\n2 -3 0 4\n";
        String expected = "29" + System.lineSeparator();

        String actual = runMain(input);

        assertEquals(expected, actual);
    }

    @Test
    void shouldPrintSumAboveLongMaximum() {
        String input = "2\n-2147483648 -2147483648\n";
        String expected = "9223372036854775808" + System.lineSeparator();

        String actual = runMain(input);

        assertEquals(expected, actual);
    }

    private String runMain(String input) {
        InputStream originalInput = System.in;
        PrintStream originalOutput = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try (PrintStream testOutput =
                     new PrintStream(output, true, StandardCharsets.UTF_8)) {
            byte[] bytes = input.getBytes(StandardCharsets.UTF_8);
            System.setIn(new ByteArrayInputStream(bytes));
            System.setOut(testOutput);

            Task14.main(new String[0]);

            return output.toString(StandardCharsets.UTF_8);
        } finally {
            System.setIn(originalInput);
            System.setOut(originalOutput);
        }
    }
}