package ru.bmstu.lab1.task_51;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/**
 * Тесты задачи 51: программа запускается с подставленным вводом, проверяется её вывод.
 */
class RowMaxTest {

    /**
     * Запускает RowMax с заданным вводом и возвращает всё, что программа напечатала.
     */
    private String run(String input) {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            RowMax.main(new String[0]);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
        return output.toString(StandardCharsets.UTF_8).strip();
    }

    @Test
    void shouldFindMaximumOfEachRow() {
        assertEquals("8 9 7", run("3 3\n4 2 8\n1 5 9\n7 3 6\n"));
    }

    @Test
    void shouldHandleRectangularMatrix() {
        assertEquals("4 8", run("2 4\n1 2 3 4\n0 5 -1 8\n"));
    }

    @Test
    void shouldFindMaximumWhenItIsFirstInRow() {
        assertEquals("9 8", run("2 3\n9 1 2\n8 0 3\n"));
    }

    @Test
    void shouldReturnSingleMaximumForSingleRowMatrix() {
        assertEquals("7", run("1 3\n4 2 7\n"));
    }

    @Test
    void shouldReturnColumnItselfForSingleColumnMatrix() {
        assertEquals("4 9 2", run("3 1\n4\n9\n2\n"));
    }

    @Test
    void shouldHandleNegativeNumbersOnly() {
        assertEquals("-1 -2", run("2 2\n-1 -5\n-3 -2\n"));
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
        assertTrue(run("1001 1\n").contains("некорректный размер"));
    }

    @Test
    void shouldReportErrorWhenElementIsNotNumber() {
        assertTrue(run("2 2\n1 x\n").contains("должно быть целое число"));
    }
}