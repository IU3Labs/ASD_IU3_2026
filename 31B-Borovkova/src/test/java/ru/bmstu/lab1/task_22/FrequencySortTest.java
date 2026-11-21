package ru.bmstu.lab1.task_22;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/**
 * Тесты задачи 22: программа запускается с подставленным вводом, проверяется её вывод.
 */
class FrequencySortTest {

    /**
     * Запускает FrequencySort с заданным вводом и возвращает всё, что программа напечатала.
     */
    private String run(String input) {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            FrequencySort.main(new String[0]);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
        return output.toString(StandardCharsets.UTF_8).strip();
    }

    @Test
    void shouldSortByFrequencyDescending() {
        assertEquals("3 3 3 1 1 2", run("6\n1 3 2 3 1 3\n"));
    }

    @Test
    void shouldSortByValueWhenFrequenciesAreEqual() {
        assertEquals("2 2 5 5 4 7", run("6\n5 7 2 4 5 2\n"));
    }

    @Test
    void shouldSortAscendingWhenAllElementsAreDistinct() {
        assertEquals("1 2 3 4", run("4\n4 2 1 3\n"));
    }

    @Test
    void shouldHandleEqualElements() {
        assertEquals("9 9 9", run("3\n9 9 9\n"));
    }

    @Test
    void shouldHandleNegativeNumbers() {
        assertEquals("-1 -1 -3 0", run("4\n0 -1 -3 -1\n"));
    }

    @Test
    void shouldHandleMinimalLength() {
        assertEquals("-5 5", run("2\n5 -5\n"));
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
