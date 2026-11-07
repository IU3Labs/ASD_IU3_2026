package ru.bmstu.lab1.task_5;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

/** Проверки задачи 5: обычные данные и граничные случаи. */
class MainTest {

    private static String run(String input) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(buffer, true, StandardCharsets.UTF_8);
        Main.solve(new Scanner(input), output);
        return buffer.toString(StandardCharsets.UTF_8).strip();
    }

    @Test
    void shouldSumElementsAtEvenIndicesForOddLength() {
        assertEquals("9", run("5\n1 2 3 4 5"));
    }

    @Test
    void shouldSumElementsAtEvenIndicesForEvenLength() {
        assertEquals("4", run("4\n1 10 3 20"));
    }

    @Test
    void shouldReturnFirstElementForTwoElements() {
        assertEquals("7", run("2\n7 100"));
    }

    @Test
    void shouldIgnoreElementsAtOddIndices() {
        assertEquals("0", run("4\n0 5 0 5"));
    }

    @Test
    void shouldHandleNegativeNumbers() {
        assertEquals("-6", run("5\n-1 8 -2 8 -3"));
    }

    @Test
    void shouldHandleZeros() {
        assertEquals("0", run("3\n0 0 0"));
    }

    @Test
    void shouldNotOverflowIntRange() {
        assertEquals("4294967294", run("3\n2147483647 1 2147483647"));
    }

    @Test
    void shouldAcceptNumbersSplitAcrossLines() {
        assertEquals("9", run("5\n1\n2\n3\n4\n5"));
    }

    @Test
    void shouldHandleMaximumInputSize() {
        int size = 99_999;
        StringBuilder input = new StringBuilder().append(size).append('\n');
        for (int i = 0; i < size; i++) {
            input.append("1 ");
        }
        // чётных индексов среди 0..99998 ровно 50 000
        assertEquals("50000", run(input.toString()));
    }

    @Test
    void shouldRunMainWithStandardStreams() {
        InputStream savedInput = System.in;
        PrintStream savedOutput = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        try (PrintStream output = new PrintStream(buffer, true, StandardCharsets.UTF_8)) {
            System.setIn(new ByteArrayInputStream("5\n1 2 3 4 5\n".getBytes(StandardCharsets.UTF_8)));
            System.setOut(output);
            Main.main(new String[0]);
        } finally {
            // Восстанавливаем потоки, чтобы этот тест не влиял на остальные.
            System.setIn(savedInput);
            System.setOut(savedOutput);
        }

        assertEquals("9\n", buffer.toString(StandardCharsets.UTF_8).replace("\r\n", "\n"));
    }
}
