package ru.bmstu.lab01.task_2;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** Проверяет поиск первого вхождения числа. */
class MainTest {

    @Test
    void shouldReturnFirstMatchingIndex() {
        assertEquals("1", run("5\n7 3 10 3 4\n3\n"));
    }

    @Test
    void shouldReturnMinusOneWhenNumberIsAbsent() {
        assertEquals("-1", run("3\n2 4 6\n5\n"));
    }

    @Test
    void shouldFindNumberAtBeginning() {
        assertEquals("0", run("3\n8 2 5\n8\n"));
    }

    @Test
    void shouldFindNumberAtEnd() {
        assertEquals("2", run("3\n8 2 5\n5\n"));
    }

    @Test
    void shouldHandleNegativeNumbersAndMinimumSize() {
        assertEquals("1", run("2\n-7 -3\n-3\n"));
    }

    private String run(String data) {
        InputStream originalInput = System.in;
        PrintStream originalOutput = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try (PrintStream testOutput =
                new PrintStream(output, true, StandardCharsets.UTF_8)) {
            System.setIn(new ByteArrayInputStream(
                    data.getBytes(StandardCharsets.UTF_8)));
            System.setOut(testOutput);

            Main.main();

            return output.toString(StandardCharsets.UTF_8).strip();
        } finally {
            System.setIn(originalInput);
            System.setOut(originalOutput);
        }
    }
}