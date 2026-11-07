package ru.bmstu.lab1.task_10;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

/** Проверки задачи 10: обычные данные и граничные случаи. */
class MainTest {

    private static String run(String input) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(buffer, true, StandardCharsets.UTF_8);
        Main.solve(new Scanner(input), output);
        return buffer.toString(StandardCharsets.UTF_8).strip();
    }

    @Test
    void shouldReturnIndexOfLastMultiple() {
        assertEquals("4", run("6\n3 5 6 7 9 10\n3"));
    }

    @Test
    void shouldReturnLastIndexAmongSeveralMultiples() {
        assertEquals("3", run("5\n2 4 6 8 5\n2"));
    }

    @Test
    void shouldReturnMinusOneWhenNoMultiples() {
        assertEquals("-1", run("4\n1 2 3 4\n7"));
    }

    @Test
    void shouldReturnLastIndexWhenLastElementIsMultiple() {
        assertEquals("3", run("4\n1 2 3 9\n3"));
    }

    @Test
    void shouldReturnZeroWhenOnlyFirstElementIsMultiple() {
        assertEquals("0", run("4\n10 1 3 7\n5"));
    }

    @Test
    void shouldTreatZeroAsMultipleOfAnyNumber() {
        assertEquals("2", run("4\n5 7 0 9\n4"));
    }

    @Test
    void shouldHandleNegativeElements() {
        assertEquals("3", run("4\n6 1 5 -9\n3"));
    }

    @Test
    void shouldHandleNegativeDivisor() {
        assertEquals("2", run("4\n1 5 -6 7\n-3"));
    }

    @Test
    void shouldReturnLastIndexWhenKIsOne() {
        assertEquals("3", run("4\n5 -2 7 11\n1"));
    }

    @Test
    void shouldHandleIntegerExtremes() {
        assertEquals("1", run("2\n5 -2147483648\n-1"));
        assertEquals("-1", run("2\n2147483647 5\n2"));
    }

    @Test
    void shouldAcceptNumbersSplitAcrossLines() {
        assertEquals("2", run("4\n1\n4\n6\n3\n2"));
    }

    @Test
    void shouldHandleMaximumInputSize() {
        int size = 99_999;
        StringBuilder noMultiples = new StringBuilder().append(size).append('\n');
        StringBuilder multipleAtStart = new StringBuilder().append(size).append('\n');
        for (int i = 0; i < size; i++) {
            noMultiples.append("1 ");
            multipleAtStart.append(i == 0 ? "2 " : "1 ");
        }
        // худший случай для поиска с конца: подходящий элемент только в начале или его нет совсем
        assertEquals("-1", run(noMultiples.append("\n2").toString()));
        assertEquals("0", run(multipleAtStart.append("\n2").toString()));
    }
}
