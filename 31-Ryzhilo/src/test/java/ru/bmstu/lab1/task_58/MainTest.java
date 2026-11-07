package ru.bmstu.lab1.task_58;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

/** Граничные и функциональные тесты задачи 58. */
class MainTest {

    @Test
    void shouldFillTwoRowsAsSnake() {
        assertEquals("1 2 3\n6 5 4\n", run("2 3\n"));
    }

    @Test
    void shouldFillSingleCell() {
        assertEquals("1\n", run("1 1\n"));
    }

    @Test
    void shouldReverseEverySecondRow() {
        assertEquals("1 2\n4 3\n5 6\n", run("3 2\n"));
    }

    @Test
    void shouldFillSingleRow() {
        assertEquals("1 2 3 4\n", run("1 4\n"));
    }

    @Test
    void shouldFillSingleColumn() {
        assertEquals("1\n2\n3\n4\n", run("4 1\n"));
    }

    @Test
    void shouldFillOddSquare() {
        assertEquals("1 2 3\n6 5 4\n7 8 9\n", run("3 3\n"));
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
            System.setIn(new ByteArrayInputStream("2 3\n".getBytes(StandardCharsets.UTF_8)));
            System.setOut(output);
            Main.main(new String[0]);
        } finally {
            // Потоки возвращаются даже при ошибке, чтобы не влиять на другие тесты.
            System.setIn(savedInput);
            System.setOut(savedOutput);
        }
        assertEquals("1 2 3\n6 5 4\n", buffer.toString(StandardCharsets.UTF_8).replace("\r\n", "\n"));
    }
}
