package ru.bmstu.lab1.task_16;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/**
 * Тесты задачи 16: программа запускается с подставленным вводом, проверяется её вывод.
 */
class AbsSortTest {

    /**
     * Запускает AbsSort с заданным вводом и возвращает всё, что программа напечатала.
     */
    private String run(String input) {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            AbsSort.main(new String[0]);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
        return output.toString(StandardCharsets.UTF_8).strip();
    }

    @Test
    void shouldSortByAbsoluteValue() {
        assertEquals("1 -2 3 -5 8", run("5\n-5 3 8 1 -2\n"));
    }

    @Test
    void shouldPutNegativeFirstWhenAbsoluteValuesAreEqual() {
        assertEquals("-3 3", run("2\n3 -3\n"));
    }

    @Test
    void shouldHandleSeveralEqualAbsoluteValues() {
        assertEquals("-1 -1 1 -2 2 2", run("6\n2 1 -2 -1 2 -1\n"));
    }

    @Test
    void shouldPutZeroFirst() {
        assertEquals("0 -1 4", run("3\n4 -1 0\n"));
    }

    @Test
    void shouldHandleAllNegativeNumbers() {
        assertEquals("-1 -4 -9", run("3\n-9 -1 -4\n"));
    }

    @Test
    void shouldHandleExtremeValues() {
        assertEquals("0 -2147483647 2147483647 -2147483648",
                run("4\n-2147483648 2147483647 0 -2147483647\n"));
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
