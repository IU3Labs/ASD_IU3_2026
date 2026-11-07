package ru.bmstu.lab1.task_6;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

/** Проверки задачи 6: обычные данные и граничные случаи. */
class MainTest {

    private static String run(String input) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(buffer, true, StandardCharsets.UTF_8);
        Main.solve(new Scanner(input), output);
        return buffer.toString(StandardCharsets.UTF_8).strip();
    }

    @Test
    void shouldCountElementsGreaterThanAverage() {
        assertEquals("2", run("5\n1 2 3 4 5"));
    }

    @Test
    void shouldNotCountElementsEqualToAverage() {
        assertEquals("0", run("4\n7 7 7 7"));
    }

    @Test
    void shouldCountSingleElementAboveAverage() {
        assertEquals("1", run("3\n1 1 4"));
    }

    @Test
    void shouldCountAllElementsExceptEqualOnes() {
        assertEquals("2", run("4\n0 0 3 3"));
    }

    @Test
    void shouldHandleNegativeNumbers() {
        assertEquals("1", run("3\n-3 -1 -2"));
    }

    @Test
    void shouldHandleNegativeAverageWithPositiveElements() {
        assertEquals("2", run("4\n-10 1 2 -5"));
    }

    @Test
    void shouldHandleZeroAverage() {
        assertEquals("2", run("4\n-2 -1 1 2"));
    }

    @Test
    void shouldNotOverflowIntRangeWhenSummingElements() {
        assertEquals("1", run("2\n2147483647 2147483645"));
    }

    @Test
    void shouldAcceptNumbersSplitAcrossLines() {
        assertEquals("2", run("5\n1\n2\n3\n4\n5"));
    }

    @Test
    void shouldHandleMaximumInputSize() {
        int size = 99_999;
        int pairs = (size - 1) / 2;
        StringBuilder input = new StringBuilder().append(size).append('\n').append("1 ");
        // среднее равно 1: pairs двоек, pairs нулей и одна единица, сумма = size
        for (int i = 0; i < pairs; i++) {
            input.append("2 0 ");
        }
        assertEquals(String.valueOf(pairs), run(input.toString()));
    }
}
