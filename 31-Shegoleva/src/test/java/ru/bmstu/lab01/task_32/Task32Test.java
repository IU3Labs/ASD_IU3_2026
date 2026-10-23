package ru.bmstu.lab01.task_32;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

class Task32Test {

    @Test
    void shouldReturnSumOfPairProductsForSimpleArray() {
        int[] array = {1, 2, 3};
        long expected = 11;
        long actual = Task32.calculateSumOfPairProduct(array);
        assertEquals(expected, actual);
    }

    @Test
    void shouldHandleNegativeNumbers() {
        int[] array = {-1, 2, 3};
        long expected = 1;
        long actual = Task32.calculateSumOfPairProduct(array);
        assertEquals(expected, actual);
    }

    @Test
    void shouldIgnorePairsWithZero() {
        int[] array = {0, 5, 7};
        long expected = 35;
        long actual = Task32.calculateSumOfPairProduct(array);
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnZeroWhenArrayHasOneElement() {
        int[] array = {42};
        long expected = 0;
        long actual = Task32.calculateSumOfPairProduct(array);
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnZeroWhenArrayIsEmpty() {
        int[] array = {};
        long expected = 0;
        long actual = Task32.calculateSumOfPairProduct(array);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotOverflowForLargeValues() {
        int[] array = {100_000, 100_000, 100_000};
        long expected = 30_000_000_000L;
        long actual = Task32.calculateSumOfPairProduct(array);
        assertEquals(expected, actual);
    }

    @Test
    void shouldMatchBruteForceSolution() {
        int[] array = {3, -7, 12, 0, 5, -1, 8, -4};
        long expected = bruteForce(array);
        long actual = Task32.calculateSumOfPairProduct(array);
        assertEquals(expected, actual);
    }

    @Test
    void shouldPrintResultWhenInputIsGiven() {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream("3\n1 2 3".getBytes()));
            System.setOut(new PrintStream(output));

            Task32.main(new String[0]);

            assertEquals("11", output.toString().trim());
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
    }


    private static long bruteForce(int[] array) {
        long sum = 0;
        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                sum += (long) array[i] * array[j];
            }
        }
        return sum;
    }
}