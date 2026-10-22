package ru.bmstu.lab1.task_53;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/**
 * Тесты задачи 53: программа запускается с подставленным вводом, проверяется её вывод.
 */
class SaddlePointsTest {

    /**
     * Запускает SaddlePoints с заданным вводом и возвращает всё, что программа напечатала.
     * Переводы строк Windows (\r\n) заменяются на \n, чтобы сравнение не зависело от системы.
     */
    private String run(String input) {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            SaddlePoints.main(new String[0]);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
        return output.toString(StandardCharsets.UTF_8).replace("\r\n", "\n").strip();
    }

    @Test
    void shouldFindSingleSaddlePoint() {
        assertEquals("(2, 0, 7)", run("3 3\n1 2 3\n4 5 6\n7 8 9\n"));
    }

    @Test
    void shouldFindSaddlePointWithNegativeValues() {
        assertEquals("(0, 1, -2)", run("2 2\n-1 -2\n-4 -3\n"));
    }

    @Test
    void shouldFindAllSaddlePointsWhenValuesAreEqual() {
        assertEquals("(0, 0, 5)\n(0, 1, 5)\n(1, 0, 5)\n(1, 1, 5)", run("2 2\n5 5\n5 5\n"));
    }

    @Test
    void shouldFindRowMinimumInSingleRowMatrix() {
        assertEquals("(0, 1, 2)", run("1 3\n4 2 7\n"));
    }

    @Test
    void shouldFindColumnMaximumInSingleColumnMatrix() {
        assertEquals("(1, 0, 9)", run("3 1\n4\n9\n2\n"));
    }

    @Test
    void shouldPrintMinusOneWhenThereAreNoSaddlePoints() {
        assertEquals("-1", run("2 2\n1 2\n2 1\n"));
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