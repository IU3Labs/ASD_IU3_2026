package ru.bmstu.lab1.task_41;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/**
 * Тесты задачи 41: программа запускается с подставленным вводом, проверяется её вывод.
 */
class RemoveElementTest {

    /**
     * Запускает RemoveElement с заданным вводом и возвращает всё, что программа напечатала.
     */
    private String run(String input) {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            RemoveElement.main(new String[0]);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
        return output.toString(StandardCharsets.UTF_8).strip();
    }

    @Test
    void shouldRemoveAllOccurrences() {
        assertEquals("1 3 4", run("6\n2 1 2 3 2 4\n2\n"));
    }

    @Test
    void shouldKeepArrayWhenXIsAbsent() {
        assertEquals("1 2 3", run("3\n1 2 3\n9\n"));
    }

    @Test
    void shouldReturnEmptyWhenAllElementsAreX() {
        assertEquals("", run("3\n7 7 7\n7\n"));
    }

    @Test
    void shouldRemoveFirstAndLastElements() {
        assertEquals("1 2", run("4\n5 1 2 5\n5\n"));
    }

    @Test
    void shouldHandleNegativeX() {
        assertEquals("0 3", run("4\n-1 0 -1 3\n-1\n"));
    }

    @Test
    void shouldHandleMinimalLength() {
        assertEquals("4", run("2\n3 4\n3\n"));
    }

    @Test
    void shouldReportErrorWhenXIsNotNumber() {
        assertTrue(run("3\n1 2 3\nx\n").contains("должно быть целое число"));
    }

    @Test
    void shouldReportErrorWhenLengthIsNotNumber() {
        assertTrue(run("abc\n").contains("должно быть целое число"));
    }

    @Test
    void shouldReportErrorWhenLengthIsTooSmall() {
        assertTrue(run("1\n5\n5\n").contains("некорректная длина"));
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
