package ru.bmstu.lab1.task_35;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

/** Граничные и функциональные тесты задачи 35. */
class MainTest {

    @Test
    void shouldHandleMixed() {
        assertEquals("7\n3\n3\n", run("5\n3 -1 2 0 3\n3\n0 4\n0 0\n4 4\n"));
    }

    @Test
    void shouldHandleNegative() {
        assertEquals("-14\n-5\n-2\n", run("4\n-5 -4 -3 -2\n3\n0 3\n0 0\n3 3\n"));
    }

    @Test
    void shouldHandleEqual() {
        assertEquals("28\n7\n7\n", run("4\n7 7 7 7\n3\n0 3\n0 0\n3 3\n"));
    }

    @Test
    void shouldHandleZeros() {
        assertEquals("", run("3\n0 0 0\n0\n"));
    }

    @Test
    void shouldHandleLimits() {
        assertEquals("-2147483649\n-2147483648\n-2147483648\n",
                run("4\n-2147483648 2147483647 0 -2147483648\n3\n0 3\n0 0\n3 3\n"));
    }

    @Test
    void shouldHandleTwoElements() {
        assertEquals("11\n9\n2\n", run("2\n9 2\n3\n0 1\n0 0\n1 1\n"));
    }

    @Test
    void shouldHandleSumAboveInt() {
        assertEquals("4294967294\n2147483647\n", run("2\n2147483647 2147483647\n2\n0 1\n1 1\n"));
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
            System.setIn(new ByteArrayInputStream(
                    "5\n3 -1 2 0 3\n3\n0 4\n0 0\n4 4\n".getBytes(StandardCharsets.UTF_8)));
            System.setOut(output);
            Main.main(new String[0]);
        } finally {
            // Потоки возвращаются даже при ошибке, чтобы не влиять на другие тесты.
            System.setIn(savedInput);
            System.setOut(savedOutput);
        }
        assertEquals("7\n3\n3\n", buffer.toString(StandardCharsets.UTF_8).replace("\r\n", "\n"));
    }
}
