package ru.bmstu.lab1.task_21;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/**
 * Тесты задачи 21: программа запускается с подставленным вводом, проверяется её вывод.
 */
class KthSmallestTest {

    /**
     * Запускает KthSmallest с заданным вводом и возвращает всё, что программа напечатала.
     */
    private String run(String input) {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            KthSmallest.main(new String[0]);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
        return output.toString(StandardCharsets.UTF_8).strip();
    }

    @Test
    void shouldReturnKthSmallest() {
        assertEquals("3", run("5\n5 3 8 1 2\n3\n"));
    }

    @Test
    void shouldReturnMinimumWhenKIsOne() {
        assertEquals("1", run("5\n5 3 8 1 2\n1\n"));
    }

    @Test
    void shouldReturnMaximumWhenKIsN() {
        assertEquals("8", run("5\n5 3 8 1 2\n5\n"));
    }

    @Test
    void shouldCountDuplicatesSeparately() {
        assertEquals("2", run("4\n2 3 1 2\n3\n"));
    }

    @Test
    void shouldHandleNegativeNumbers() {
        assertEquals("-2", run("4\n0 -2 4 -7\n2\n"));
    }

    @Test
    void shouldReportErrorWhenKIsZero() {
        assertTrue(run("3\n1 2 3\n0\n").contains("k должно быть от 1 до n"));
    }

    @Test
    void shouldReportErrorWhenKIsGreaterThanN() {
        assertTrue(run("3\n1 2 3\n4\n").contains("k должно быть от 1 до n"));
    }

    @Test
    void shouldReportErrorWhenKIsNotNumber() {
        assertTrue(run("3\n1 2 3\ny\n").contains("должно быть целое число"));
    }

    @Test
    void shouldReportErrorWhenLengthIsNotNumber() {
        assertTrue(run("abc\n").contains("должно быть целое число"));
    }

    @Test
    void shouldReportErrorWhenLengthIsTooSmall() {
        assertTrue(run("1\n5\n1\n").contains("некорректная длина"));
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
