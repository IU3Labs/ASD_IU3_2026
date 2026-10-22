package ru.bmstu.lab1.task_8;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/**
 * Тесты задачи 8: программа запускается с подставленным вводом, проверяется её вывод.
 */
class FiltrationTest {

    /**
     * Запускает Filtration с заданным вводом и возвращает всё, что программа напечатала.
     * Переводы строк Windows (\r\n) заменяются на \n, чтобы сравнение не зависело от системы.
     */
    private String run(String input) {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            Filtration.main(new String[0]);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
        return output.toString(StandardCharsets.UTF_8).replace("\r\n", "\n").strip();
    }

    @Test
    void shouldSplitBySignKeepingOriginalOrder() {
        assertEquals("[1, 4, 4]\n[-3, -8]", run("5\n1 4 -3 -8 4\n"));
    }

    @Test
    void shouldSkipZeros() {
        assertEquals("[5]\n[-2]", run("4\n0 5 0 -2\n"));
    }

    @Test
    void shouldPrintEmptyNegativeListWhenAllPositive() {
        assertEquals("[1, 2, 3]\n[]", run("3\n1 2 3\n"));
    }

    @Test
    void shouldPrintEmptyPositiveListWhenAllNegative() {
        assertEquals("[]\n[-1, -2]", run("2\n-1 -2\n"));
    }

    @Test
    void shouldPrintTwoEmptyListsWhenAllZeros() {
        assertEquals("[]\n[]", run("3\n0 0 0\n"));
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
    void shouldReportErrorWhenFirstElementIsNotNumber() {
        assertTrue(run("3\nx\n").contains("должно быть целое число"));
    }

    @Test
    void shouldReportErrorWhenLaterElementIsNotNumber() {
        assertTrue(run("3\n1 x\n").contains("должно быть целое число"));
    }
}