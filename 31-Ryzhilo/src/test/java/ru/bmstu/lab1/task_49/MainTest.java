package ru.bmstu.lab1.task_49;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

/** Граничные и функциональные тесты задачи 49. */
class MainTest {

    @Test
    void shouldHandleRectangularMatrix() {
        assertEquals("6 15\n", run("2 3\n1 2 3\n4 5 6\n"));
    }

    @Test
    void shouldHandleSingleCell() {
        assertEquals("9\n", run("1 1\n9\n"));
    }

    @Test
    void shouldHandleNegativeValues() {
        assertEquals("-5 -5\n", run("2 2\n-3 -2\n-1 -4\n"));
    }

    @Test
    void shouldHandleEqualValues() {
        assertEquals("14 14\n", run("2 2\n7 7\n7 7\n"));
    }

    @Test
    void shouldHandleIntegerLimits() {
        assertEquals("-1 -1\n", run("2 2\n2147483647 -2147483648\n2147483647 -2147483648\n"));
    }

    @Test
    void shouldHandleSingleColumn() {
        assertEquals("1 2 3\n", run("3 1\n1\n2\n3\n"));
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
    void shouldReadAndWriteThroughMain() {
        InputStream savedInput = System.in;
        PrintStream savedOutput = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        try (PrintStream output = new PrintStream(buffer, true, StandardCharsets.UTF_8)) {
            System.setIn(new ByteArrayInputStream("2 3\n1 2 3\n4 5 6\n".getBytes(StandardCharsets.UTF_8)));
            System.setOut(output);
            Main.main(new String[0]);
        } finally {
            // Потоки возвращаются даже при ошибке, чтобы не влиять на другие тесты.
            System.setIn(savedInput);
            System.setOut(savedOutput);
        }
        assertEquals("6 15\n", buffer.toString(StandardCharsets.UTF_8).replace("\r\n", "\n"));
    }
}
