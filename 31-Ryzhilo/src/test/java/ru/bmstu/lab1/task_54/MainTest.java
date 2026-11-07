package ru.bmstu.lab1.task_54;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

/** Граничные и функциональные тесты задачи 54. */
class MainTest {

    @Test
    void shouldSumDiagonalOfThreeByThreeMatrix() {
        assertEquals("15\n", run("3\n1 2 3\n4 5 6\n7 8 9\n"));
    }

    @Test
    void shouldHandleSingleCell() {
        assertEquals("9\n", run("1\n9\n"));
    }

    @Test
    void shouldSumNegativeDiagonal() {
        assertEquals("-7\n", run("2\n-3 -2\n-1 -4\n"));
    }

    @Test
    void shouldSumDiagonalOfEqualValues() {
        assertEquals("14\n", run("2\n7 7\n7 7\n"));
    }

    @Test
    void shouldHandleIntegerLimits() {
        assertEquals("-1\n", run("2\n2147483647 -2147483648\n2147483647 -2147483648\n"));
    }

    @Test
    void shouldSelectCorrectDiagonal() {
        assertEquals("3\n", run("2\n0 1\n2 3\n"));
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
            System.setIn(new ByteArrayInputStream("3\n1 2 3\n4 5 6\n7 8 9\n".getBytes(StandardCharsets.UTF_8)));
            System.setOut(output);
            Main.main(new String[0]);
        } finally {
            // Потоки возвращаются даже при ошибке, чтобы не влиять на другие тесты.
            System.setIn(savedInput);
            System.setOut(savedOutput);
        }
        assertEquals("15\n", buffer.toString(StandardCharsets.UTF_8).replace("\r\n", "\n"));
    }
}
