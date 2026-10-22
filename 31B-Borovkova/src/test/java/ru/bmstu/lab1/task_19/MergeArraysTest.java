package ru.bmstu.lab1.task_19;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/**
 * Тесты задачи 19: программа запускается с подставленным вводом, проверяется её вывод.
 */
class MergeArraysTest {

    /**
     * Запускает MergeArrays с заданным вводом и возвращает всё, что программа напечатала.
     */
    private String run(String input) {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            MergeArrays.main(new String[0]);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
        return output.toString(StandardCharsets.UTF_8).strip();
    }

    @Test
    void shouldMergeInterleavedArrays() {
        assertEquals("1 2 3 4 5 6 7", run("4\n1 3 5 7\n3\n2 4 6\n"));
    }

    @Test
    void shouldMergeWhenFirstArrayIsEntirelySmaller() {
        assertEquals("1 2 3 4 5", run("2\n1 2\n3\n3 4 5\n"));
    }

    @Test
    void shouldMergeWhenSecondArrayIsEntirelySmaller() {
        assertEquals("-5 -4 10 20", run("2\n10 20\n2\n-5 -4\n"));
    }

    @Test
    void shouldHandleEqualElements() {
        assertEquals("1 2 2 2 3 3", run("3\n1 2 3\n3\n2 2 3\n"));
    }

    @Test
    void shouldHandleNegativeNumbers() {
        assertEquals("-9 -3 -1 0 0 8", run("3\n-9 -1 0\n3\n-3 0 8\n"));
    }

    @Test
    void shouldReportErrorWhenFirstArrayIsNotSorted() {
        assertTrue(run("3\n1 5 2\n2\n1 2\n").contains("отсортирован"));
    }

    @Test
    void shouldReportErrorWhenSecondArrayIsNotSorted() {
        assertTrue(run("2\n1 2\n3\n4 3 5\n").contains("отсортирован"));
    }

    @Test
    void shouldReportErrorWhenFirstLengthIsNotNumber() {
        assertTrue(run("abc\n").contains("должно быть целое число"));
    }

    @Test
    void shouldReportErrorWhenSecondLengthIsNotNumber() {
        assertTrue(run("2\n1 2\nx\n").contains("должно быть целое число"));
    }

    @Test
    void shouldReportErrorWhenFirstLengthIsTooSmall() {
        assertTrue(run("1\n5\n").contains("некорректная длина"));
    }

    @Test
    void shouldReportErrorWhenSecondLengthIsTooLarge() {
        assertTrue(run("2\n1 2\n100000\n").contains("некорректная длина"));
    }

    @Test
    void shouldReportErrorWhenElementIsNotNumber() {
        assertTrue(run("2\n1 2\n2\n3 y\n").contains("должно быть целое число"));
    }
}
