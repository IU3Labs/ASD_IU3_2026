package ru.bmstu.lab1.task_24;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

/** Тесты чётно-нечётной сортировки. */
class MainTest {

    @Test
    void shouldSortMixedValues() {
        assertEquals("2 4 6 5 3 1\n", run("6\n5 2 3 4 1 6\n"));
    }

    @Test
    void shouldSortOnlyEvenValuesAscending() {
        assertEquals("-2 0 4 8\n", run("4\n8 0 -2 4\n"));
    }

    @Test
    void shouldSortOnlyOddValuesDescending() {
        assertEquals("9 5 1 -3\n", run("4\n1 -3 9 5\n"));
    }

    @Test
    void shouldHandleNegativeValuesAndZero() {
        assertEquals("-4 -2 0 3 -1 -5\n", run("6\n-5 -2 3 0 -4 -1\n"));
    }

    @Test
    void shouldPreserveAllRepeatedValues() {
        assertEquals("2 2 4 3 3 1\n", run("6\n3 2 1 3 4 2\n"));
    }

    @Test
    void shouldHandleIntegerLimits() {
        assertEquals(
                "-2147483648 0 2147483647 -1\n",
                run("4\n2147483647 -1 0 -2147483648\n")
        );
    }

    private static String run(String inputText) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        try (Scanner input = new Scanner(inputText);
                PrintStream output = new PrintStream(buffer, true, StandardCharsets.UTF_8)) {
            Main.solve(input, output);
        }

        return buffer.toString(StandardCharsets.UTF_8).replace("\r\n", "\n");
    }

    @Test
    void shouldRunMainWithStandardStreams() {
        InputStream savedInput = System.in;
        PrintStream savedOutput = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        try (PrintStream output = new PrintStream(buffer, true, StandardCharsets.UTF_8)) {
            System.setIn(new ByteArrayInputStream("6\n5 2 3 4 1 6\n".getBytes(StandardCharsets.UTF_8)));
            System.setOut(output);
            Main.main(new String[0]);
        } finally {
            System.setIn(savedInput);
            System.setOut(savedOutput);
        }

        assertEquals("2 4 6 5 3 1\n", buffer.toString(StandardCharsets.UTF_8).replace("\r\n", "\n"));
    }
}
