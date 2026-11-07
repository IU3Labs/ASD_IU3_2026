package ru.bmstu.lab1.task_8;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

/** Проверки задачи 8: обычные данные и граничные случаи. */
class MainTest {

    private static String run(String input) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(buffer, true, StandardCharsets.UTF_8);
        Main.solve(new Scanner(input), output);
        // строки не обрезаем: пустая первая строка - часть ответа
        return buffer.toString(StandardCharsets.UTF_8).replace(System.lineSeparator(), "\n");
    }

    @Test
    void shouldSplitMixedElementsKeepingOriginalOrder() {
        assertEquals("3 1 5\n-2 -4\n", run("5\n3 -2 1 -4 5"));
    }

    @Test
    void shouldSkipZeros() {
        assertEquals("1 2\n-3\n", run("6\n0 1 0 -3 2 0"));
    }

    @Test
    void shouldPrintEmptyNegativeLineWhenAllPositive() {
        assertEquals("1 2 3\n\n", run("3\n1 2 3"));
    }

    @Test
    void shouldPrintEmptyPositiveLineWhenAllNegative() {
        assertEquals("\n-1 -2 -3\n", run("3\n-1 -2 -3"));
    }

    @Test
    void shouldPrintTwoEmptyLinesWhenAllZeros() {
        assertEquals("\n\n", run("3\n0 0 0"));
    }

    @Test
    void shouldKeepDuplicates() {
        assertEquals("2 2\n-1 -1\n", run("4\n2 -1 2 -1"));
    }

    @Test
    void shouldHandleIntegerExtremes() {
        assertEquals("2147483647\n-2147483648\n", run("2\n2147483647 -2147483648"));
    }

    @Test
    void shouldAcceptNumbersSplitAcrossLines() {
        assertEquals("3 5\n-2\n", run("3\n3\n-2\n5"));
    }

    @Test
    void shouldHandleMaximumInputSize() {
        int size = 99_999;
        StringBuilder input = new StringBuilder().append(size).append('\n');
        StringBuilder expectedPositives = new StringBuilder();
        // чётные индексы - положительные числа, нечётные - нули
        for (int i = 0; i < size; i++) {
            if (i % 2 == 0) {
                input.append(i + 1).append(' ');
                expectedPositives.append(expectedPositives.length() > 0 ? " " : "").append(i + 1);
            } else {
                input.append("0 ");
            }
        }
        assertEquals(expectedPositives + "\n\n", run(input.toString()));
    }
}
