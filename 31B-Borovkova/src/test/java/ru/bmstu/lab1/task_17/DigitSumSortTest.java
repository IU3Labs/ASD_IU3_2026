package ru.bmstu.lab1.task_17;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/**
 * Тесты задачи 17: программа запускается с подставленным вводом, проверяется её вывод.
 */
class DigitSumSortTest {

    /**
     * Запускает DigitSumSort с заданным вводом и возвращает всё, что программа напечатала.
     */
    private String run(String input) {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            DigitSumSort.main(new String[0]);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
        return output.toString(StandardCharsets.UTF_8).strip();
    }

    @Test
    void shouldSortByDigitSum() {
        assertEquals("100 12 31 99", run("4\n99 12 100 31\n"));
    }

    @Test
    void shouldKeepOriginalOrderForEqualDigitSums() {
        assertEquals("51 15 42 24 6", run("5\n51 15 42 24 6\n"));
    }

    @Test
    void shouldKeepOrderStableAmongOtherElements() {
        assertEquals("10 1 20 11 2 9", run("6\n20 9 10 11 1 2\n"));
    }

    @Test
    void shouldPutZeroFirst() {
        assertEquals("0 5 7", run("3\n7 0 5\n"));
    }

    @Test
    void shouldHandleMaxIntValue() {
        assertEquals("1 2147483647", run("2\n2147483647 1\n"));
    }

    @Test
    void shouldHandleMinimalLength() {
        assertEquals("10 9", run("2\n9 10\n"));
    }

    @Test
    void shouldReportErrorWhenElementIsNegative() {
        assertTrue(run("3\n1 -2 3\n").contains("неотрицательными"));
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
