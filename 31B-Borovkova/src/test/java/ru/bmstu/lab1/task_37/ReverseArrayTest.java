package ru.bmstu.lab1.task_37;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/**
 * Тесты задачи 37: программа запускается с подставленным вводом, проверяется её вывод.
 */
class ReverseArrayTest {

    /**
     * Запускает ReverseArray с заданным вводом и возвращает всё, что программа напечатала.
     */
    private String run(String input) {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            ReverseArray.main(new String[0]);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
        return output.toString(StandardCharsets.UTF_8).strip();
    }

    @Test
    void shouldReverseOddLengthArray() {
        assertEquals("5 4 3 2 1", run("5\n1 2 3 4 5\n"));
    }

    @Test
    void shouldReverseEvenLengthArray() {
        assertEquals("4 3 2 1", run("4\n1 2 3 4\n"));
    }

    @Test
    void shouldReverseMinimalLengthArray() {
        assertEquals("9 1", run("2\n1 9\n"));
    }

    @Test
    void shouldKeepPalindrome() {
        assertEquals("1 2 3 2 1", run("5\n1 2 3 2 1\n"));
    }

    @Test
    void shouldHandleNegativeNumbers() {
        assertEquals("-3 0 -1", run("3\n-1 0 -3\n"));
    }

    @Test
    void shouldHandleExtremeValues() {
        assertEquals("2147483647 -2147483648", run("2\n-2147483648 2147483647\n"));
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
