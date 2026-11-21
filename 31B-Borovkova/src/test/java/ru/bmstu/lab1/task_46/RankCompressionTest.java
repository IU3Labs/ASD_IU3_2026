package ru.bmstu.lab1.task_46;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/**
 * Тесты задачи 46: программа запускается с подставленным вводом, проверяется её вывод.
 */
class RankCompressionTest {

    /**
     * Запускает RankCompression с заданным вводом и возвращает всё, что программа напечатала.
     */
    private String run(String input) {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            RankCompression.main(new String[0]);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
        return output.toString(StandardCharsets.UTF_8).strip();
    }

    @Test
    void shouldCompressExampleFromStatement() {
        assertEquals("1 0 1 2", run("4\n100 50 100 300\n"));
    }

    @Test
    void shouldReturnIndicesForSortedDistinctArray() {
        assertEquals("0 1 2 3", run("4\n10 20 30 40\n"));
    }

    @Test
    void shouldReturnZerosWhenAllElementsAreEqual() {
        assertEquals("0 0 0", run("3\n7 7 7\n"));
    }

    @Test
    void shouldHandleReversedArray() {
        assertEquals("2 1 0", run("3\n9 5 1\n"));
    }

    @Test
    void shouldHandleNegativeNumbers() {
        assertEquals("0 2 1 0", run("4\n-5 3 0 -5\n"));
    }

    @Test
    void shouldHandleExtremeValues() {
        assertEquals("2 0 1", run("3\n2147483647 -2147483648 0\n"));
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
