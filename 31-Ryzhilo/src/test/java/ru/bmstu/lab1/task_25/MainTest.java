package ru.bmstu.lab1.task_25;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

/** Тесты вычисления суммы элементов массива. */
class MainTest {

    @Test
    void shouldSumPositiveNumbers() {
        assertEquals("15\n", run("5\n1 2 3 4 5\n"));
    }

    @Test
    void shouldSumNegativeNumbers() {
        assertEquals("-10\n", run("4\n-1 -2 -3 -4\n"));
    }

    @Test
    void shouldHandleMixedSigns() {
        assertEquals("3\n", run("5\n-5 8 0 -2 2\n"));
    }

    @Test
    void shouldHandleZeros() {
        assertEquals("0\n", run("3\n0 0 0\n"));
    }

    @Test
    void shouldHandleSumAboveIntegerMaximum() {
        assertEquals("4294967294\n", run("2\n2147483647 2147483647\n"));
    }

    @Test
    void shouldHandleSumBelowIntegerMinimum() {
        assertEquals("-4294967296\n", run("2\n-2147483648 -2147483648\n"));
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
        assertEquals("7\n", buffer.toString(StandardCharsets.UTF_8).replace("\r\n", "\n"));
    }
}
