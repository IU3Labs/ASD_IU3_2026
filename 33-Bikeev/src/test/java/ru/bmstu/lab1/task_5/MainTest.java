package ru.bmstu.lab1.task_5;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Проверки решения задачи 5.
 */
public class MainTest {

    @Test
    void sumsEvenIndicesForOddLength() {
        int[] numbers = {10, 20, 30, 40, 50};

        assertEquals(90L, Main.calculateEvenIndexSum(numbers));
    }

    @Test
    void sumsEvenIndicesForEvenLength() {
        int[] numbers = {3, 100, 7, 200};

        assertEquals(10L, Main.calculateEvenIndexSum(numbers));
    }

    @Test
    void handlesTwoElements() {
        int[] numbers = {8, 99};

        assertEquals(8L, Main.calculateEvenIndexSum(numbers));
    }

    @Test
    void sumsNegativeNumbers() {
        int[] numbers = {-3, 10, -7};

        assertEquals(-10L, Main.calculateEvenIndexSum(numbers));
    }

    @Test
    void sumsZeros() {
        int[] numbers = {0, 0, 0, 0};

        assertEquals(0L, Main.calculateEvenIndexSum(numbers));
    }

    @Test
    void handlesLargeSum() {
        int[] numbers = {Integer.MAX_VALUE, 0, Integer.MAX_VALUE};

        assertEquals(4294967294L, Main.calculateEvenIndexSum(numbers));
    }

    @Test
    void readsArrayAndPrintsSum() {
        String input = "5\n10 20 30 40 50\n";
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));

            Main.main(new String[0]);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }

        assertEquals("90" + System.lineSeparator(), output.toString(StandardCharsets.UTF_8));
    }
}