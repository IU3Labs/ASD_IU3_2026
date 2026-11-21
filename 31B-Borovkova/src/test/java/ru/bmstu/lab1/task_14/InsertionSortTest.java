package ru.bmstu.lab1.task_14;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/**
 * Тесты задачи 14: программа запускается с подставленным вводом, проверяется её вывод.
 */
class InsertionSortTest {

    /**
     * Запускает InsertionSort с заданным вводом и возвращает всё, что программа напечатала.
     */
    private String run(String input) {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            InsertionSort.main(new String[0]);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
        return output.toString(StandardCharsets.UTF_8).strip();
    }

    @Test
    void shouldSortArrayDescending() {
        assertEquals("8 5 3 2 1", run("5\n5 3 8 1 2\n"));
    }

    @Test
    void shouldKeepArraySortedDescending() {
        assertEquals("4 3 2 1", run("4\n4 3 2 1\n"));
    }

    @Test
    void shouldReverseArraySortedAscending() {
        assertEquals("5 4 3 2 1", run("5\n1 2 3 4 5\n"));
    }

    @Test
    void shouldHandleDuplicates() {
        assertEquals("3 3 2 2 1", run("5\n3 2 3 1 2\n"));
    }

    @Test
    void shouldHandleNegativeNumbers() {
        assertEquals("4 0 -2 -7", run("4\n0 -2 4 -7\n"));
    }

    @Test
    void shouldHandleMinimalLength() {
        assertEquals("9 1", run("2\n1 9\n"));
    }

    @Test
    void shouldHandleEqualElements() {
        assertEquals("7 7 7", run("3\n7 7 7\n"));
    }

    @Test
    void shouldHandleExtremeValues() {
        assertEquals("2147483647 0 -2147483648", run("3\n-2147483648 2147483647 0\n"));
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
