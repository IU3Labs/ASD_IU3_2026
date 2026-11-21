package ru.bmstu.lab1.task_5;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/**
 * Тесты задачи 5: программа запускается с подставленным вводом, проверяется её вывод.
 */
class SumElementsTest {

    /**
     * Запускает SumElements с заданным вводом и возвращает всё, что программа напечатала.
     */
    private String run(String input) {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            SumElements.main(new String[0]);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
        return output.toString(StandardCharsets.UTF_8).strip();
    }

    @Test
    void shouldSumElementsAtEvenIndicesForOddLength() {
        assertEquals("9", run("5\n1 2 3 4 5\n"));
    }

    @Test
    void shouldSumElementsAtEvenIndicesForEvenLength() {
        assertEquals("4", run("4\n1 2 3 4\n"));
    }

    @Test
    void shouldHandleNegativeNumbers() {
        assertEquals("-3", run("4\n-1 5 -2 7\n"));
    }

    @Test
    void shouldNotOverflowOnLargeValues() {
        assertEquals("4294967294", run("3\n2147483647 0 2147483647\n"));
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
}