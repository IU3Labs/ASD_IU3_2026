package ru.bmstu.lab1.task_7;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/**
 * Тесты задачи 7: программа запускается с подставленным вводом, проверяется её вывод.
 */
class SecondMaxTest {

    /**
     * Запускает SecondMax с заданным вводом и возвращает всё, что программа напечатала.
     */
    private String run(String input) {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            SecondMax.main(new String[0]);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
        return output.toString(StandardCharsets.UTF_8).strip();
    }

    @Test
    void shouldFindSecondMaxInUnsortedArray() {
        assertEquals("7", run("5\n3 9 1 7 4\n"));
    }

    @Test
    void shouldFindSecondMaxWhenMaxIsFirst() {
        assertEquals("2", run("3\n9 1 2\n"));
    }

    @Test
    void shouldFindSecondMaxInAscendingArray() {
        assertEquals("2", run("3\n1 2 3\n"));
    }

    @Test
    void shouldFindSecondMaxInDescendingArray() {
        assertEquals("6", run("4\n8 6 4 2\n"));
    }

    @Test
    void shouldHandleTwoElements() {
        assertEquals("3", run("2\n5 3\n"));
    }

    @Test
    void shouldHandleNegativeNumbers() {
        assertEquals("-5", run("3\n-5 -1 -9\n"));
    }

    @Test
    void shouldReportErrorWhenLengthIsNotNumber() {
        assertTrue(run("abc\n").contains("должно быть целое число"));
    }

    @Test
    void shouldReportErrorWhenLengthIsNegative() {
        assertTrue(run("-3\n").contains("некорректная длина"));
    }

    @Test
    void shouldReportErrorWhenLengthIsTooLarge() {
        assertTrue(run("100000\n").contains("некорректная длина"));
    }

    @Test
    void shouldReportErrorWhenFirstElementIsNotNumber() {
        assertTrue(run("3\nx\n").contains("должно быть целое число"));
    }

    @Test
    void shouldReportErrorWhenLaterElementIsNotNumber() {
        assertTrue(run("3\n1 x\n").contains("должно быть целое число"));
    }
}