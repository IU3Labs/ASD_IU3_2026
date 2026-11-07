package ru.bmstu.lab1.task_7;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

/** Проверки задачи 7: обычные данные и граничные случаи. */
class MainTest {

    private static String run(String input) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(buffer, true, StandardCharsets.UTF_8);
        Main.solve(new Scanner(input), output);
        return buffer.toString(StandardCharsets.UTF_8).strip();
    }

    @Test
    void shouldFindSecondMaxInUnsortedArray() {
        assertEquals("7", run("5\n3 9 1 7 2"));
    }

    @Test
    void shouldFindSecondMaxInAscendingArray() {
        assertEquals("4", run("5\n1 2 3 4 5"));
    }

    @Test
    void shouldFindSecondMaxInDescendingArray() {
        assertEquals("4", run("5\n5 4 3 2 1"));
    }

    @Test
    void shouldReturnSmallerOfTwoElementsWhenSizeIsTwo() {
        assertEquals("3", run("2\n8 3"));
        assertEquals("3", run("2\n3 8"));
    }

    @Test
    void shouldFindSecondMaxWhenMaxIsFirst() {
        assertEquals("8", run("4\n10 2 8 5"));
    }

    @Test
    void shouldFindSecondMaxWhenMaxIsLast() {
        assertEquals("8", run("4\n2 8 5 10"));
    }

    @Test
    void shouldFindSecondMaxWhenItComesAfterMax() {
        assertEquals("6", run("4\n9 1 6 2"));
    }

    @Test
    void shouldHandleNegativeNumbers() {
        assertEquals("-2", run("4\n-5 -2 -9 -1"));
    }

    @Test
    void shouldHandleIntegerExtremes() {
        assertEquals("-2147483648", run("2\n2147483647 -2147483648"));
        assertEquals("2147483646", run("3\n2147483647 -2147483648 2147483646"));
    }

    @Test
    void shouldAcceptNumbersSplitAcrossLines() {
        assertEquals("7", run("5\n3\n9\n1\n7\n2"));
    }

    @Test
    void shouldHandleMaximumInputSize() {
        int size = 99_999;
        StringBuilder input = new StringBuilder().append(size).append('\n');
        for (int i = 1; i <= size; i++) {
            input.append(i).append(' ');
        }
        assertEquals(String.valueOf(size - 1), run(input.toString()));
    }
}
