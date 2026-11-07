package ru.bmstu.lab1.task_30;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

/** Граничные и функциональные тесты задачи 30. */
class MainTest {

    @Test
    void shouldHandleMixed() {
        double expected = 3.3;
        double actual = Double.parseDouble(run("5\n3 -1 2 0 3\n").trim());
        assertEquals(expected, actual, Math.max(1.0, Math.abs(expected)) * 1e-7);
    }

    @Test
    void shouldHandleNegative() {
        double expected = 1.6666666666666667;
        double actual = Double.parseDouble(run("4\n-5 -4 -3 -2\n").trim());
        assertEquals(expected, actual, Math.max(1.0, Math.abs(expected)) * 1e-7);
    }

    @Test
    void shouldHandleEqual() {
        double expected = 0.0;
        double actual = Double.parseDouble(run("4\n7 7 7 7\n").trim());
        assertEquals(expected, actual, Math.max(1.0, Math.abs(expected)) * 1e-7);
    }

    @Test
    void shouldHandleZeros() {
        double expected = 0.0;
        double actual = Double.parseDouble(run("3\n0 0 0\n").trim());
        assertEquals(expected, actual, Math.max(1.0, Math.abs(expected)) * 1e-7);
    }

    @Test
    void shouldHandleLimits() {
        double expected = 4.227378848435536e+18;
        double actual = Double.parseDouble(run("4\n-2147483648 2147483647 0 -2147483648\n").trim());
        assertEquals(expected, actual, Math.max(1.0, Math.abs(expected)) * 1e-7);
    }

    @Test
    void shouldHandleTwoElements() {
        double expected = 24.5;
        double actual = Double.parseDouble(run("2\n9 2\n").trim());
        assertEquals(expected, actual, Math.max(1.0, Math.abs(expected)) * 1e-7);
    }

    @Test
    void shouldHandleNearLargeMean() {
        double expected = 1.0;
        double actual = Double.parseDouble(run("3\n2147483647 2147483646 2147483645\n").trim());
        assertEquals(expected, actual, Math.max(1.0, Math.abs(expected)) * 1e-7);
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
        double actual = Double.parseDouble(buffer.toString(StandardCharsets.UTF_8).trim());
        assertEquals(3.3, actual, 1e-9);
    }
}
