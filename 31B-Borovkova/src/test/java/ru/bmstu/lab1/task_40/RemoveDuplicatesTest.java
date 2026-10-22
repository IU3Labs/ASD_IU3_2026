package ru.bmstu.lab1.task_40;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/**
 * Тесты задачи 40: программа запускается с подставленным вводом, проверяется её вывод.
 */
class RemoveDuplicatesTest {

    /**
     * Запускает RemoveDuplicates с заданным вводом и возвращает всё, что программа напечатала.
     */
    private String run(String input) {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            RemoveDuplicates.main(new String[0]);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
        return output.toString(StandardCharsets.UTF_8).strip();
    }

    @Test
    void shouldRemoveDuplicates() {
        assertEquals("1 2 3 4", run("7\n1 1 2 3 3 3 4\n"));
    }

    @Test
    void shouldKeepArrayWithoutDuplicates() {
        assertEquals("1 2 3", run("3\n1 2 3\n"));
    }

    @Test
    void shouldLeaveOneElementWhenAllAreEqual() {
        assertEquals("5", run("4\n5 5 5 5\n"));
    }

    @Test
    void shouldHandleDuplicatesAtEnd() {
        assertEquals("1 2", run("4\n1 2 2 2\n"));
    }

    @Test
    void shouldHandleNegativeNumbers() {
        assertEquals("-3 -1 0", run("5\n-3 -3 -1 0 0\n"));
    }

    @Test
    void shouldHandleExtremeValues() {
        assertEquals("-2147483648 2147483647", run("4\n-2147483648 -2147483648 2147483647 2147483647\n"));
    }

    @Test
    void shouldReportErrorWhenArrayIsNotSorted() {
        assertTrue(run("3\n1 3 2\n").contains("отсортирован"));
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
