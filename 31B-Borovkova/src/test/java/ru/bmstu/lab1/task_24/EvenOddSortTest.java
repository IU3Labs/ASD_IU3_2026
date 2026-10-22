package ru.bmstu.lab1.task_24;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/**
 * Тесты задачи 24: программа запускается с подставленным вводом, проверяется её вывод.
 */
class EvenOddSortTest {

    /**
     * Запускает EvenOddSort с заданным вводом и возвращает всё, что программа напечатала.
     */
    private String run(String input) {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            EvenOddSort.main(new String[0]);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
        return output.toString(StandardCharsets.UTF_8).strip();
    }

    @Test
    void shouldPutSortedEvenFirstAndOddDescendingAfter() {
        assertEquals("2 4 8 7 5 3 1", run("7\n5 8 3 2 7 4 1\n"));
    }

    @Test
    void shouldHandleOnlyEvenElements() {
        assertEquals("0 2 6", run("3\n6 0 2\n"));
    }

    @Test
    void shouldHandleOnlyOddElements() {
        assertEquals("9 5 1", run("3\n1 9 5\n"));
    }

    @Test
    void shouldHandleNegativeNumbers() {
        assertEquals("-4 0 2 3 -1 -5", run("6\n-1 2 -4 3 0 -5\n"));
    }

    @Test
    void shouldHandleDuplicates() {
        assertEquals("2 2 3 3", run("4\n3 2 3 2\n"));
    }

    @Test
    void shouldHandleMinimalLength() {
        assertEquals("2 1", run("2\n1 2\n"));
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
