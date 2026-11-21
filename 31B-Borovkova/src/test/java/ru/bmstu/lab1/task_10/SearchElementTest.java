package ru.bmstu.lab1.task_10;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/**
 * Тесты задачи 10: программа запускается с подставленным вводом, проверяется её вывод.
 */
class SearchElementTest {

    /**
     * Запускает SearchElement с заданным вводом и возвращает всё, что программа напечатала.
     */
    private String run(String input) {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            SearchElement.main(new String[0]);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
        return output.toString(StandardCharsets.UTF_8).strip();
    }

    @Test
    void shouldReturnIndexOfLastMultiple() {
        assertEquals("4", run("5\n3 4 6 7 9\n3\n"));
    }

    @Test
    void shouldReturnLastMultipleWhenItIsInMiddle() {
        assertEquals("2", run("5\n6 1 9 2 5\n3\n"));
    }

    @Test
    void shouldReturnZeroWhenOnlyFirstElementIsMultiple() {
        assertEquals("0", run("3\n10 3 7\n5\n"));
    }

    @Test
    void shouldReturnMinusOneWhenThereAreNoMultiples() {
        assertEquals("-1", run("3\n1 2 4\n3\n"));
    }

    @Test
    void shouldTreatZeroAsMultipleOfAnyNumber() {
        assertEquals("1", run("3\n1 0 5\n7\n"));
    }

    @Test
    void shouldHandleNegativeElements() {
        assertEquals("2", run("3\n-6 5 -9\n3\n"));
    }

    @Test
    void shouldHandleNegativeK() {
        assertEquals("1", run("3\n4 6 7\n-2\n"));
    }

    @Test
    void shouldReportErrorWhenKIsZero() {
        assertTrue(run("2\n1 2\n0\n").contains("не должно быть равно нулю"));
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
    void shouldReportErrorWhenElementIsNotNumber() {
        assertTrue(run("3\n1 x\n").contains("должно быть целое число"));
    }

    @Test
    void shouldReportErrorWhenKIsNotNumber() {
        assertTrue(run("2\n1 2\ny\n").contains("должно быть целое число"));
    }
}
