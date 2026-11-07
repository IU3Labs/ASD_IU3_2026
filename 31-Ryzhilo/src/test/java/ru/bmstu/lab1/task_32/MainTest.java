package ru.bmstu.lab1.task_32;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

/** Граничные и функциональные тесты задачи 32. */
class MainTest {

    @Test
    void shouldHandleMixed() {
        assertEquals("13\n", run("5\n3 -1 2 0 3\n"));
    }

    @Test
    void shouldHandleNegative() {
        assertEquals("71\n", run("4\n-5 -4 -3 -2\n"));
    }

    @Test
    void shouldHandleEqual() {
        assertEquals("294\n", run("4\n7 7 7 7\n"));
    }

    @Test
    void shouldHandleZeros() {
        assertEquals("0\n", run("3\n0 0 0\n"));
    }

    @Test
    void shouldHandleLimits() {
        assertEquals("-4611686014132420608\n", run("4\n-2147483648 2147483647 0 -2147483648\n"));
    }

    @Test
    void shouldHandleTwoElements() {
        assertEquals("18\n", run("2\n9 2\n"));
    }

    @Test
    void shouldHandlePairSumExceedsLong() {
        assertEquals("27670116084794523654\n", run("4\n2147483647 2147483647 2147483647 2147483647\n"));
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
            System.setIn(new ByteArrayInputStream("5\n3 -1 2 0 3\n".getBytes(StandardCharsets.UTF_8)));
            System.setOut(output);
            Main.main(new String[0]);
        } finally {
            // Потоки возвращаются даже при ошибке, чтобы не влиять на другие тесты.
            System.setIn(savedInput);
            System.setOut(savedOutput);
        }
        assertEquals("13\n", buffer.toString(StandardCharsets.UTF_8).replace("\r\n", "\n"));
    }
}
