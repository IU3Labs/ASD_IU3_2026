package ru.bmstu.lab1.task_28;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

/** Проверки задачи 28: основной результат и граничные случаи. */
class MainTest {

    @Test
    void shouldFindMiddleForOddLength() {
        double actual = Double.parseDouble(run("5\n9 1 5 2 8\n").trim());
        assertEquals(5.0, actual, 1e-9);
    }

    @Test
    void shouldAverageMiddleForEvenLength() {
        double actual = Double.parseDouble(run("4\n8 1 4 2\n").trim());
        assertEquals(3.0, actual, 1e-9);
    }

    @Test
    void shouldKeepFractionalMedian() {
        double actual = Double.parseDouble(run("2\n2 9\n").trim());
        assertEquals(5.5, actual, 1e-9);
    }

    @Test
    void shouldHandleNegativeValues() {
        double actual = Double.parseDouble(run("4\n-5 -2 -4 -3\n").trim());
        assertEquals(-3.5, actual, 1e-9);
    }

    @Test
    void shouldHandleEqualValues() {
        double actual = Double.parseDouble(run("4\n7 7 7 7\n").trim());
        assertEquals(7.0, actual, 1e-9);
    }

    @Test
    void shouldHandleZeros() {
        double actual = Double.parseDouble(run("3\n0 0 0\n").trim());
        assertEquals(0.0, actual, 1e-9);
    }

    @Test
    void shouldAvoidOverflowForMaximumValues() {
        double actual = Double.parseDouble(run("2\n2147483647 2147483647\n").trim());
        assertEquals(2147483647.0, actual, 1e-9);
    }

    @Test
    void shouldAvoidOverflowForMinimumValues() {
        double actual = Double.parseDouble(run("2\n-2147483648 -2147483648\n").trim());
        assertEquals(-2147483648.0, actual, 1e-9);
    }

    @Test
    void shouldAverageOppositeIntegerLimits() {
        double actual = Double.parseDouble(run("2\n-2147483648 2147483647\n").trim());
        assertEquals(-0.5, actual, 1e-9);
    }

    @Test
    void shouldReadAndWriteThroughMain() {
        String inputText = "5\n9 1 5 2 8\n";
        String expected = run(inputText);
        InputStream originalInput = System.in;
        PrintStream originalOutput = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        // Возвращаем стандартные потоки даже при ошибке, чтобы не повлиять на другие тесты.
        try (PrintStream output = new PrintStream(buffer, true, StandardCharsets.UTF_8)) {
            System.setIn(new ByteArrayInputStream(inputText.getBytes(StandardCharsets.UTF_8)));
            System.setOut(output);
            Main.main(new String[0]);
        } finally {
            System.setIn(originalInput);
            System.setOut(originalOutput);
        }

        assertEquals(expected, buffer.toString(StandardCharsets.UTF_8).replace("\r\n", "\n"));
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
