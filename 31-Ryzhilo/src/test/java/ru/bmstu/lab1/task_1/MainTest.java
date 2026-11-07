package ru.bmstu.lab1.task_1;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Тесты поиска минимального и максимального элементов.
 */
class MainTest {

    @Test
    void shouldHandleMixedNumbers() {
        assertResult("5\n7 -3 12 0 4\n", "-3 12");
    }

    @Test
    void shouldHandleNegativeNumbers() {
        assertResult("4\n-8 -2 -10 -5\n", "-10 -2");
    }

    @Test
    void shouldHandleEqualNumbers() {
        assertResult("3\n6 6 6\n", "6 6");
    }

    @Test
    void shouldHandleTwoNumbers() {
        assertResult("2\n9 1\n", "1 9");
    }

    @Test
    void shouldHandleIntegerLimits() {
        assertResult(
                "3\n-2147483648 0 2147483647\n",
                "-2147483648 2147483647"
        );
    }

    private void assertResult(String inputText, String expected) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        try (Scanner input = new Scanner(inputText);
                PrintStream output =
                     new PrintStream(buffer, true, StandardCharsets.UTF_8)) {
            Main.solve(input, output);
        }

        String actual = buffer.toString(StandardCharsets.UTF_8).trim();
        assertEquals(expected, actual);
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
        assertEquals("-1 3\n", buffer.toString(StandardCharsets.UTF_8).replace("\r\n", "\n"));
    }
}
