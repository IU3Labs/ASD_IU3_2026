package ru.bmstu.lab1.task_33;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/**
 * Тесты задачи 33: программа запускается с подставленным вводом, проверяется её вывод.
 */
class InversionsTest {

    /**
     * Запускает Inversions с заданным вводом и возвращает всё, что программа напечатала.
     */
    private String run(String input) {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            Inversions.main(new String[0]);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
        return output.toString(StandardCharsets.UTF_8).strip();
    }

    @Test
    void shouldCountInversions() {
        assertEquals("3", run("5\n2 4 1 3 5\n"));
    }

    @Test
    void shouldReturnZeroForSortedArray() {
        assertEquals("0", run("4\n1 2 3 4\n"));
    }

    @Test
    void shouldReturnMaximumForReversedArray() {
        assertEquals("10", run("5\n5 4 3 2 1\n"));
    }

    @Test
    void shouldNotCountEqualElements() {
        assertEquals("0", run("3\n7 7 7\n"));
    }

    @Test
    void shouldHandleDuplicates() {
        assertEquals("4", run("4\n2 2 1 1\n"));
    }

    @Test
    void shouldHandleNegativeNumbers() {
        assertEquals("3", run("3\n0 -1 -2\n"));
    }

    @Test
    void shouldHandleMinimalLength() {
        assertEquals("1", run("2\n9 1\n"));
    }

    @Test
    void shouldHandleExtremeValues() {
        assertEquals("1", run("2\n2147483647 -2147483648\n"));
    }

    @Test
    void shouldNotOverflowIntOnManyInversions() {
        // 70000 элементов по убыванию: 70000 · 69999 / 2 = 2449965000 > Integer.MAX_VALUE
        StringBuilder input = new StringBuilder("70000\n");
        for (int i = 70_000; i > 0; i--) {
            input.append(i).append(' ');
        }
        assertEquals("2449965000", run(input.toString()));
    }

    @Test
    void shouldReportErrorWhenLengthIsNotNumber() {
        assertTrue(run("abc\n").contains("должно быть целое число"));
    }

    @Test
    void shouldReportErrorWhenLengthIsTooSmall() {
        assertTrue(run("1\n5\n").contains("некорректная длина"));
    }

    @Test
    void shouldReportErrorWhenLengthIsTooLarge() {
        assertTrue(run("100000\n").contains("некорректная длина"));
    }

    @Test
    void shouldReportErrorWhenElementIsNotNumber() {
        assertTrue(run("3\n1 x 2\n").contains("должно быть целое число"));
    }
}
