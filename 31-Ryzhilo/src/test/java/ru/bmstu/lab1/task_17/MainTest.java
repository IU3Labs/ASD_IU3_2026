package ru.bmstu.lab1.task_17;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

/** Граничные и функциональные тесты задачи 17. */
class MainTest {

    @Test
    void shouldHandleMixed() {
        assertEquals("0 1 2 3 3\n", run("5\n3 1 2 0 3\n"));
    }

    @Test
    void shouldSortDescendingValues() {
        assertEquals("2 3 4 5\n", run("4\n5 4 3 2\n"));
    }

    @Test
    void shouldHandleEqual() {
        assertEquals("7 7 7 7\n", run("4\n7 7 7 7\n"));
    }

    @Test
    void shouldHandleZeros() {
        assertEquals("0 0 0\n", run("3\n0 0 0\n"));
    }

    @Test
    void shouldHandleLimits() {
        assertEquals("0 2147483647 2147483647 2147483647\n", run("4\n2147483647 2147483647 0 2147483647\n"));
    }

    @Test
    void shouldHandleTwoElements() {
        assertEquals("2 9\n", run("2\n9 2\n"));
    }

    @Test
    void shouldHandleStableDigitSums() {
        assertEquals("0 21 12 3 30 4 40\n", run("7\n21 12 3 30 4 40 0\n"));
    }

    private static String run(String inputText) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try (Scanner input = new Scanner(inputText);
                PrintStream output = new PrintStream(buffer, true, StandardCharsets.UTF_8)) {
            Main.solve(input, output);
        }
        return buffer.toString(StandardCharsets.UTF_8).replace("\r\n", "\n");
    }
}
