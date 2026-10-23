package ru.bmstu.lab01.task_25;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

class Task25Test {

    @Test
    void shouldReturnSumOfPositiveNumbers() {
        int[] array = {5, 10, 15, 20};
        long actual = Task25.calculateSum(array);
        assertEquals(50, actual);
    }

    @Test
    void shouldReturnSumOfNegativeNumbers() {
        int[] array = {-5, -10, -15, -20};
        long actual = Task25.calculateSum(array);
        assertEquals(-50, actual);
    }

    @Test
    void shouldReturnZeroWhenAllElementsAreZero() {
        int[] array = {0, 0, 0};
        long actual = Task25.calculateSum(array);
        assertEquals(0, actual);
    }

    @Test
    void shouldReturnElementWhenArrayHasOne() {
        int[] array = {555};
        long actual = Task25.calculateSum(array);
        assertEquals(555, actual);
    }

    @Test
    void shouldNotOverflowForLargeNumbers() {
        int[] array = {Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE};
        long actual = Task25.calculateSum(array);
        assertEquals(3L * Integer.MAX_VALUE, actual);
    }

    @Test
    void shouldPrintSumWhenInputIsGiven() {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream("3\n1 2 3".getBytes()));
            System.setOut(new PrintStream(output));

            Task25.main(new String[0]);

            assertEquals("6", output.toString().trim());
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
    }

}