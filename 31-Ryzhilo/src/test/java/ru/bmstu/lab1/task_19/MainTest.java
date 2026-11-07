package ru.bmstu.lab1.task_19;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

/** Проверки задачи 19: обычные данные и граничные случаи. */
class MainTest {

    private static String run(String input) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(buffer, true, StandardCharsets.UTF_8);
        Main.solve(new Scanner(input), output);
        return buffer.toString(StandardCharsets.UTF_8).strip();
    }

    @Test
    void shouldMergeInterleavedArrays() {
        assertEquals("1 2 3 4 5 6", run("3\n1 3 5\n3\n2 4 6"));
    }

    @Test
    void shouldMergeArraysOfDifferentSizes() {
        assertEquals("1 2 3 4 5 6 7", run("2\n1 5\n5\n2 3 4 6 7"));
    }

    @Test
    void shouldAppendSecondArrayWhenAllItsElementsAreGreater() {
        assertEquals("1 2 3 7 8 9", run("3\n1 2 3\n3\n7 8 9"));
    }

    @Test
    void shouldPrependSecondArrayWhenAllItsElementsAreSmaller() {
        assertEquals("1 2 3 7 8 9", run("3\n7 8 9\n3\n1 2 3"));
    }

    @Test
    void shouldKeepDuplicatesFromBothArrays() {
        assertEquals("1 1 2 2 2 3 3", run("4\n1 2 2 3\n3\n1 2 3"));
    }

    @Test
    void shouldHandleNegativeNumbers() {
        assertEquals("-9 -5 -3 0 2 4", run("3\n-9 -3 2\n3\n-5 0 4"));
    }

    @Test
    void shouldReturnSecondArrayWhenFirstIsEmpty() {
        assertEquals("1 2 3", run("0\n3\n1 2 3"));
    }

    @Test
    void shouldReturnFirstArrayWhenSecondIsEmpty() {
        assertEquals("1 2 3", run("3\n1 2 3\n0"));
    }

    @Test
    void shouldPrintEmptyLineWhenBothArraysAreEmpty() {
        assertEquals("", run("0\n0"));
    }

    @Test
    void shouldMergeArraysWithSingleElements() {
        assertEquals("3 8", run("1\n8\n1\n3"));
    }

    @Test
    void shouldHandleIntegerExtremes() {
        assertEquals("-2147483648 0 2147483647 2147483647", run("2\n-2147483648 2147483647\n2\n0 2147483647"));
    }

    @Test
    void shouldAcceptNumbersSplitAcrossLines() {
        assertEquals("1 2 3 4", run("2\n1\n3\n2\n2\n4"));
    }

    @Test
    void shouldNotModifySourceArrays() {
        int[] first = {1, 4, 6};
        int[] second = {2, 3, 7};

        Main.merge(first, second);

        assertArrayEquals(new int[] {1, 4, 6}, first);
        assertArrayEquals(new int[] {2, 3, 7}, second);
    }

    @Test
    void shouldHandleMaximumInputSize() {
        int size = 100_000;
        StringBuilder input = new StringBuilder().append(size).append('\n');
        StringBuilder expected = new StringBuilder();
        // первый массив - чётные числа, второй - нечётные: результат 0, 1, 2, ..., 199999
        for (int i = 0; i < size; i++) {
            input.append(2 * i).append(' ');
        }
        input.append('\n').append(size).append('\n');
        for (int i = 0; i < size; i++) {
            input.append(2 * i + 1).append(' ');
        }
        for (int i = 0; i < 2 * size; i++) {
            expected.append(i).append(i < 2 * size - 1 ? " " : "");
        }
        assertEquals(expected.toString(), run(input.toString()));
    }
}
