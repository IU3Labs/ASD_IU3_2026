package ru.bmstu.lab1.task_50;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/**
 * Тесты задачи 50: программа запускается с подставленным вводом, проверяется её вывод.
 */
class ColumnSumsTest {

    /**
     * Запускает ColumnSums с заданным вводом и возвращает всё, что программа напечатала.
     */
    private String run(String input) {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            ColumnSums.main(new String[0]);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
        return output.toString(StandardCharsets.UTF_8).strip();
    }

    @Test
    void shouldSumEachColumn() {
        assertEquals("5 7 9", run("2 3\n1 2 3\n4 5 6\n"));
    }

    @Test
    void shouldHandleNegativeNumbers() {
        assertEquals("6 -6", run("3 2\n1 -1\n2 -2\n3 -3\n"));
    }

    @Test
    void shouldReturnZeroWhenColumnElementsCancelOut() {
        assertEquals("0 0", run("2 2\n5 -3\n-5 3\n"));
    }

    @Test
    void shouldReturnRowItselfForSingleRowMatrix() {
        assertEquals("4 2 7", run("1 3\n4 2 7\n"));
    }

    @Test
    void shouldReturnTotalSumForSingleColumnMatrix() {
        assertEquals("15", run("3 1\n4\n9\n2\n"));
    }

    @Test
    void shouldNotOverflowOnLargeValues() {
        assertEquals("4294967294", run("2 1\n2147483647\n2147483647\n"));
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