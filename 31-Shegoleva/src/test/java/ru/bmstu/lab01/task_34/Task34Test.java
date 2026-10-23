package ru.bmstu.lab01.task_34;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

class Task34Test {

    @Test
    void shouldReturnMaxSumForMixedNumbers() {
        int[] array = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        long expected = 6; // подмассив {4, -1, 2, 1}
        long actual = Task34.maxSubarraySum(array);
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnLargestElementWhenAllNegative() {
        int[] array = {-3, -1, -2};
        long expected = -1;
        long actual = Task34.maxSubarraySum(array);
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnElementWhenArrayHasOne() {
        int[] array = {7};
        long expected = 7;
        long actual = Task34.maxSubarraySum(array);
        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowExceptionWhenArrayIsEmpty() {
        int[] array = {};
        assertThrows(IllegalArgumentException.class, () -> Task34.maxSubarraySum(array));
    }

    @Test
    void shouldNotOverflowForLargeValues() {
        int[] array = {Integer.MAX_VALUE, Integer.MAX_VALUE};
        long expected = 2L * Integer.MAX_VALUE;
        long actual = Task34.maxSubarraySum(array);
        assertEquals(expected, actual);
    }

    @Test
    void shouldMatchBruteForceSolution() {
        int[] array = {3, -7, 12, 0, 5, -1, -20, 8, 4, -4};
        long expected = bruteForce(array);
        long actual = Task34.maxSubarraySum(array);
        assertEquals(expected, actual);
    }

    @Test
    void shouldPrintResultWhenInputIsGiven() {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream("9\n-2 1 -3 4 -1 2 1 -5 4".getBytes()));
            System.setOut(new PrintStream(output));

            Task34.main(new String[0]);

            assertEquals("6", output.toString().trim());
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
    }

    /**
     * Медленное решение O(n^2): перебирает все отрезки [i, j] и запоминает наибольшую сумму.
     */
    private static long bruteForce(int[] array) {
        long best = Long.MIN_VALUE;
        for (int i = 0; i < array.length; i++) {
            long sum = 0;
            for (int j = i; j < array.length; j++) {
                sum += array[j];
                best = Math.max(best, sum);
            }
        }
        return best;
    }
}