package ru.bmstu.lab1.task_47;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/**
 * Тесты задачи 47: программа запускается с подставленным вводом, проверяется её вывод.
 */
class RotateMatrixTest {

    /**
     * Запускает RotateMatrix с заданным вводом и возвращает всё, что программа напечатала.
     */
    private String run(String input) {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            RotateMatrix.main(new String[0]);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
        return output.toString(StandardCharsets.UTF_8).replace("\r\n", "\n").strip();
    }

    @Test
    void shouldRotateSquareMatrix() {
        assertEquals("7 4 1\n8 5 2\n9 6 3", run("3 3\n1 2 3\n4 5 6\n7 8 9\n"));
    }

    @Test
    void shouldRotateWideMatrix() {
        assertEquals("4 1\n5 2\n6 3", run("2 3\n1 2 3\n4 5 6\n"));
    }

    @Test
    void shouldRotateTallMatrix() {
        assertEquals("5 3 1\n6 4 2", run("3 2\n1 2\n3 4\n5 6\n"));
    }

    @Test
    void shouldTurnRowIntoColumn() {
        assertEquals("1\n2\n3", run("1 3\n1 2 3\n"));
    }

    @Test
    void shouldTurnColumnIntoReversedRow() {
        assertEquals("3 2 1", run("3 1\n1\n2\n3\n"));
    }

    @Test
    void shouldKeepSingleElement() {
        assertEquals("42", run("1 1\n42\n"));
    }

    @Test
    void shouldHandleNegativeNumbers() {
        assertEquals("-3 -1\n-4 -2", run("2 2\n-1 -2\n-3 -4\n"));
    }

    @Test
    void shouldReportErrorWhenRowsIsNotNumber() {
        assertTrue(run("abc\n").contains("должно быть целое число"));
    }

    @Test
    void shouldReportErrorWhenColumnsIsNotNumber() {
        assertTrue(run("3 x\n").contains("должно быть целое число"));
    }

    @Test
    void shouldReportErrorWhenSizeIsTooSmall() {
        assertTrue(run("0 5\n").contains("некорректный размер"));
    }

    @Test
    void shouldReportErrorWhenSizeIsTooLarge() {
        assertTrue(run("2 1001\n").contains("некорректный размер"));
    }

    @Test
    void shouldReportErrorWhenElementIsNotNumber() {
        assertTrue(run("2 2\n1 x\n").contains("должно быть целое число"));
    }

    @Test
    void shouldReportErrorWhenNotEnoughElements() {
        assertTrue(run("2 2\n1 2 3\n").contains("должно быть целое число"));
    }
}
