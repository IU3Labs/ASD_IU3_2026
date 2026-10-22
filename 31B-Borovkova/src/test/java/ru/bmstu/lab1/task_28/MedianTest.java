package ru.bmstu.lab1.task_28;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/**
 * Тесты задачи 28: программа запускается с подставленным вводом, проверяется её вывод.
 */
class MedianTest {

    /**
     * Запускает Median с заданным вводом и возвращает всё, что программа напечатала.
     */
    private String run(String input) {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            Median.main(new String[0]);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
        return output.toString(StandardCharsets.UTF_8).strip();
    }

    @Test
    void shouldReturnMiddleElementForOddLength() {
        assertEquals("3", run("5\n5 3 8 1 2\n"));
    }

    @Test
    void shouldReturnFractionalMedianForEvenLength() {
        assertEquals("2.5", run("4\n4 1 3 2\n"));
    }

    @Test
    void shouldReturnWholeMedianAsFloatForEvenLength() {
        assertEquals("2.0", run("2\n3 1\n"));
    }

    @Test
    void shouldHandleNegativeFractionalMedian() {
        assertEquals("-0.5", run("2\n0 -1\n"));
    }

    @Test
    void shouldHandleDuplicates() {
        assertEquals("7", run("3\n7 7 1\n"));
    }

    @Test
    void shouldNotOverflowOnLargeValues() {
        assertEquals("2147483646.5", run("2\n2147483647 2147483646\n"));
    }

    @Test
    void shouldNotOverflowOnSmallValues() {
        assertEquals("-2147483648.0", run("2\n-2147483648 -2147483648\n"));
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
