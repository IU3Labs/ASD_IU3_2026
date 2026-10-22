package ru.bmstu.lab1.task_35;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/**
 * Тесты задачи 35: программа запускается с подставленным вводом, проверяется её вывод.
 */
class PrefixSumsTest {

    /**
     * Запускает PrefixSums с заданным вводом и возвращает всё, что программа напечатала.
     * Переводы строк приводятся к \n, чтобы тесты не зависели от ОС.
     */
    private String run(String input) {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            PrefixSums.main(new String[0]);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
        return output.toString(StandardCharsets.UTF_8).strip().replace("\r\n", "\n");
    }

    @Test
    void shouldAnswerRangeQueries() {
        assertEquals("15\n9\n3", run("5\n1 2 3 4 5\n3\n0 4\n1 3\n2 2\n"));
    }

    @Test
    void shouldHandleQueryFromStart() {
        assertEquals("3", run("3\n1 2 3\n1\n0 1\n"));
    }

    @Test
    void shouldHandleQueryToEnd() {
        assertEquals("5", run("3\n1 2 3\n1\n1 2\n"));
    }

    @Test
    void shouldHandleNegativeNumbers() {
        assertEquals("-1\n0", run("4\n-1 3 -4 2\n2\n1 2\n0 3\n"));
    }

    @Test
    void shouldHandleRepeatedQueries() {
        assertEquals("7\n7", run("2\n3 4\n2\n0 1\n0 1\n"));
    }

    @Test
    void shouldNotOverflowOnLargeValues() {
        assertEquals("4294967294", run("2\n2147483647 2147483647\n1\n0 1\n"));
    }

    @Test
    void shouldNotOverflowOnSmallValues() {
        assertEquals("-4294967296", run("2\n-2147483648 -2147483648\n1\n0 1\n"));
    }

    @Test
    void shouldReportErrorWhenLeftIsGreaterThanRight() {
        assertTrue(run("3\n1 2 3\n1\n2 1\n").contains("некорректный запрос"));
    }

    @Test
    void shouldReportErrorWhenRightIsOutOfBounds() {
        assertTrue(run("3\n1 2 3\n1\n0 3\n").contains("некорректный запрос"));
    }

    @Test
    void shouldReportErrorWhenLeftIsNegative() {
        assertTrue(run("3\n1 2 3\n1\n-1 2\n").contains("некорректный запрос"));
    }

    @Test
    void shouldNotPrintPartialAnswersOnError() {
        assertEquals("Ошибка: некорректный запрос", run("3\n1 2 3\n2\n0 1\n0 5\n"));
    }

    @Test
    void shouldReportErrorWhenQueryCountIsZero() {
        assertTrue(run("3\n1 2 3\n0\n").contains("некорректное количество запросов"));
    }

    @Test
    void shouldReportErrorWhenQueryCountIsTooLarge() {
        assertTrue(run("3\n1 2 3\n100000\n").contains("некорректное количество запросов"));
    }

    @Test
    void shouldReportErrorWhenQueryCountIsNotNumber() {
        assertTrue(run("3\n1 2 3\nq\n").contains("должно быть целое число"));
    }

    @Test
    void shouldReportErrorWhenLeftIsNotNumber() {
        assertTrue(run("3\n1 2 3\n1\nx 2\n").contains("должно быть целое число"));
    }

    @Test
    void shouldReportErrorWhenRightIsNotNumber() {
        assertTrue(run("3\n1 2 3\n1\n0 y\n").contains("должно быть целое число"));
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
