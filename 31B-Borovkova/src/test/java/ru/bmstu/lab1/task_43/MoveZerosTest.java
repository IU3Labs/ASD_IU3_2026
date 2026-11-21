package ru.bmstu.lab1.task_43;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/**
 * Тесты задачи 43: программа запускается с подставленным вводом, проверяется её вывод.
 */
class MoveZerosTest {

    /**
     * Запускает MoveZeros с заданным вводом и возвращает всё, что программа напечатала.
     */
    private String run(String input) {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            MoveZeros.main(new String[0]);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
        return output.toString(StandardCharsets.UTF_8).strip();
    }

    @Test
    void shouldMoveZerosToEnd() {
        assertEquals("1 3 12 0 0", run("5\n0 1 0 3 12\n"));
    }

    @Test
    void shouldKeepArrayWithoutZeros() {
        assertEquals("4 2 7", run("3\n4 2 7\n"));
    }

    @Test
    void shouldKeepArrayOfZerosOnly() {
        assertEquals("0 0 0", run("3\n0 0 0\n"));
    }

    @Test
    void shouldKeepZerosAlreadyAtEnd() {
        assertEquals("5 6 0 0", run("4\n5 6 0 0\n"));
    }

    @Test
    void shouldKeepOrderOfNegativeAndPositive() {
        assertEquals("-1 2 -3 0 0", run("5\n0 -1 2 0 -3\n"));
    }

    @Test
    void shouldHandleMinimalLength() {
        assertEquals("8 0", run("2\n0 8\n"));
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
