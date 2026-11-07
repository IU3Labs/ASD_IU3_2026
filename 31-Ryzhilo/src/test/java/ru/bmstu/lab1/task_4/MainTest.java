package ru.bmstu.lab1.task_4;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

/** Проверки задачи 4: обычные данные и граничные случаи. */
class MainTest {

    private static String run(String input) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(buffer, true, StandardCharsets.UTF_8);
        Main.solve(new Scanner(input), output);
        return buffer.toString(StandardCharsets.UTF_8).strip();
    }

    @Test
    void shouldPrintAllIndicesInAscendingOrder() {
        assertEquals("0 2 4", run("5\n5 1 5 2 5\n5"));
    }

    @Test
    void shouldPrintSingleIndexWhenValueOccursOnce() {
        assertEquals("2", run("6\n4 8 15 16 23 42\n15"));
    }

    @Test
    void shouldPrintEmptyLineWhenValueIsAbsent() {
        assertEquals("", run("3\n1 2 3\n7"));
    }

    @Test
    void shouldPrintAllIndicesWhenAllElementsAreEqual() {
        assertEquals("0 1 2 3", run("4\n9 9 9 9\n9"));
    }

    @Test
    void shouldFindValueOnBothBoundaries() {
        assertEquals("0 3", run("4\n3 1 2 3\n3"));
    }

    @Test
    void shouldHandleNegativeNumbersAndZero() {
        assertEquals("0 2", run("5\n-1 0 -1 1 0\n-1"));
        assertEquals("1 4", run("5\n-1 0 -1 1 0\n0"));
    }

    @Test
    void shouldHandleIntegerExtremes() {
        assertEquals("0 2", run("3\n2147483647 -2147483648 2147483647\n2147483647"));
        assertEquals("1", run("3\n2147483647 -2147483648 2147483647\n-2147483648"));
    }

    @Test
    void shouldAcceptNumbersSplitAcrossLines() {
        assertEquals("1 3", run("4\n2\n8\n2\n8\n8"));
    }

    @Test
    void shouldHandleMaximumInputSize() {
        int size = 99_999;
        StringBuilder input = new StringBuilder().append(size).append('\n');
        StringBuilder expected = new StringBuilder();
        for (int i = 0; i < size; i++) {
            input.append("5 ");
            expected.append(i).append(i < size - 1 ? " " : "");
        }
        input.append("\n5");

        assertEquals(expected.toString(), run(input.toString()));
    }
}
