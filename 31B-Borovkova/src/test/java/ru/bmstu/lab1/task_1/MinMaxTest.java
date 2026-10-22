package ru.bmstu.lab1.task_1;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/**
 * Тесты задачи 1: программа запускается с подставленным вводом, проверяется её вывод.
 */
class MinMaxTest {

    /**
     * Запускает MinMax с заданным вводом и возвращает всё, что программа напечатала.
     */
    private String run(String input) {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            MinMax.main(new String[0]);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
        return output.toString(StandardCharsets.UTF_8).strip();
    }

    @Test
    void shouldPrintMinAndMaxForRegularArray() {
        assertTrue(run("5\n3 -1 7 2 0\n").endsWith("-1 7"));
    }

    @Test
    void shouldHandleAllNegativeNumbers() {
        assertTrue(run("3\n-5 -2 -9\n").endsWith("-9 -2"));
    }

    @Test
    void shouldFindMaxWhenItIsFirst() {
        assertTrue(run("4\n10 1 2 3\n").endsWith("1 10"));
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
    void shouldReportErrorWhenFirstElementIsNotNumber() {
        assertTrue(run("3\nx\n").contains("должно быть целое число"));
    }

    @Test
    void shouldReportErrorWhenLaterElementIsNotNumber() {
        assertTrue(run("3\n1 2 x\n").contains("должно быть целое число"));
    }
}