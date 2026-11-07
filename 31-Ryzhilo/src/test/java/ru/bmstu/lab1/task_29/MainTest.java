package ru.bmstu.lab1.task_29;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

/** Тесты поиска моды массива с выбором меньшего значения при равной частоте. */
class MainTest {

    @Test
    void shouldFindMostFrequentValue() {
        assertEquals("3\n", run("7\n3 1 3 2 3 2 4\n"));
    }

    @Test
    void shouldChooseSmallerValueWhenFrequenciesAreEqual() {
        assertEquals("2\n", run("6\n5 2 5 2 9 9\n"));
    }

    @Test
    void shouldChooseMinimumWhenAllValuesAreDistinct() {
        assertEquals("-4\n", run("5\n8 3 -4 7 0\n"));
    }

    @Test
    void shouldHandleEqualElements() {
        assertEquals("7\n", run("4\n7 7 7 7\n"));
    }

    @Test
    void shouldHandleNegativeNumbers() {
        assertEquals("-3\n", run("6\n-3 -1 -3 -2 -3 -1\n"));
    }

    @Test
    void shouldChooseSmallerNegativeValueWhenFrequenciesAreEqual() {
        assertEquals("-5\n", run("4\n-2 -5 -2 -5\n"));
    }

    @Test
    void shouldFindZeroAsMode() {
        assertEquals("0\n", run("6\n0 -1 0 2 0 2\n"));
    }

    @Test
    void shouldFindModeAtBeginningOfSortedArray() {
        assertEquals("1\n", run("6\n4 1 3 1 2 1\n"));
    }

    @Test
    void shouldFindModeAtEndOfSortedArray() {
        assertEquals("9\n", run("6\n9 1 9 2 9 3\n"));
    }

    @Test
    void shouldHandleSingleElement() {
        // В задаче 29 допускается n = 1.
        assertEquals("42\n", run("1\n42\n"));
    }

    @Test
    void shouldChooseSmallerOfTwoDistinctElements() {
        assertEquals("3\n", run("2\n8 3\n"));
    }

    @Test
    void shouldHandleIntegerLimits() {
        assertEquals(
                "-2147483648\n",
                run("4\n2147483647 -2147483648 2147483647 -2147483648\n")
        );
    }

    @Test
    void shouldAcceptNumbersOnSeparateLines() {
        assertEquals("4\n", run("5\n4\n2\n4\n3\n4\n"));
    }

    @Test
    void shouldHandleMaximumAllowedSize() {
        int size = 10_000;
        StringBuilder input = new StringBuilder();
        input.append(size).append('\n');

        for (int i = 0; i < size; i++) {
            input.append(i % 2 == 0 ? "5 " : "2 ");
        }

        // Оба значения встречаются по 5000 раз, выбираем меньшее.
        assertEquals("2\n", run(input.toString()));
    }

    private static String run(String inputText) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        try (Scanner input = new Scanner(inputText);
                PrintStream output = new PrintStream(buffer, true, StandardCharsets.UTF_8)) {
            Main.solve(input, output);
        }

        return buffer.toString(StandardCharsets.UTF_8).replace("\r\n", "\n");
    }
}
