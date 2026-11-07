package ru.bmstu.lab1.task_3;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Тесты подсчёта вхождений заданного числа.
 */
class MainTest {

    @Test //больше одного вхождения
    void shouldCountSeveralOccurrences() {
        assertResult("6\n1 3 5 3 7 3\n3\n", "3");
    }

    @Test //ноль вхождений
    void shouldReturnZeroWhenAbsent() {
        assertResult("5\n1 2 3 4 5\n9\n", "0");
    }

    @Test //все элементы массива равны x
    void shouldCountAllWhenAllEqual() {
        assertResult("4\n7 7 7 7\n7\n", "4");
    }

    @Test //одно вхождение
    void shouldCountSingleOccurrence() {
        assertResult("5\n10 20 30 40 50\n30\n", "1");
    }

    @Test //одно вхождение
    void shouldHandleSingleElementArray() {
        assertResult("1\n42\n42\n", "1");
    }

    @Test //без совпадений
    void shouldHandleSingleElementNoMatch() {
        assertResult("1\n42\n7\n", "0");
    }

    @Test //для отр. чисел
    void shouldHandleNegativeNumbers() {
        assertResult("6\n-5 -5 0 3 -5 8\n-5\n", "3");
    }

    @Test //для границ int
    void shouldHandleIntegerLimits() {
        assertResult(
                "3\n-2147483648 0 2147483647\n-2147483648\n",
                "1"
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
            System.setIn(new ByteArrayInputStream("5\n3 -1 2 0 3\n3\n".getBytes(StandardCharsets.UTF_8)));
            System.setOut(output);
            Main.main(new String[0]);
        } finally {
            // Потоки возвращаются даже при ошибке, чтобы не влиять на другие тесты.
            System.setIn(savedInput);
            System.setOut(savedOutput);
        }
        assertEquals("2\n", buffer.toString(StandardCharsets.UTF_8).replace("\r\n", "\n"));
    }
}
