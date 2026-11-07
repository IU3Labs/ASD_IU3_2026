package ru.bmstu.lab1.task_57;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

/** Граничные и функциональные тесты задачи 57. */
class MainTest {

    @Test
    void shouldHandleRectangularMatrix() {
        assertEquals("1 2 3 6 5 4\n", run("2 3\n1 2 3\n4 5 6\n"));
    }

    @Test
    void shouldHandleSingleCell() {
        assertEquals("9\n", run("1 1\n9\n"));
    }

    @Test
    void shouldHandleNegativeValues() {
        assertEquals("-3 -2 -4 -1\n", run("2 2\n-3 -2\n-1 -4\n"));
    }

    @Test
    void shouldHandleEqualValues() {
        assertEquals("7 7 7 7\n", run("2 2\n7 7\n7 7\n"));
    }

    @Test
    void shouldHandleIntegerLimits() {
        assertEquals("2147483647 -2147483648 -2147483648 2147483647\n",
                run("2 2\n2147483647 -2147483648\n2147483647 -2147483648\n"));
    }

    @Test
    void shouldHandleSingleColumn() {
        assertEquals("1 2 3\n", run("3 1\n1\n2\n3\n"));
    }

    @Test
    void shouldHandleSingleRow() {
        assertEquals("1 2 3 4 5\n", run("1 5\n1 2 3 4 5\n"));
    }

    @Test
    void shouldHandleOddSquare() {
        assertEquals("1 2 3 6 9 8 7 4 5\n", run("3 3\n1 2 3\n4 5 6\n7 8 9\n"));
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
