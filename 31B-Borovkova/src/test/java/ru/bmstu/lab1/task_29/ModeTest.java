package ru.bmstu.lab1.task_29;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/**
 * Тесты задачи 29: программа запускается с подставленным вводом, проверяется её вывод.
 */
class ModeTest {

    /**
     * Запускает Mode с заданным вводом и возвращает всё, что программа напечатала.
     */
    private String run(String input) {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            Mode.main(new String[0]);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
        return output.toString(StandardCharsets.UTF_8).strip();
    }

    @Test
    void shouldReturnMostFrequentElement() {
        assertEquals("3", run("6\n1 3 2 3 1 3\n"));
    }

    @Test
    void shouldReturnSmallestWhenFrequenciesAreEqual() {
        assertEquals("2", run("4\n5 2 5 2\n"));
    }

    @Test
    void shouldReturnMinimumWhenAllElementsAreDistinct() {
        assertEquals("7", run("3\n9 7 8\n"));
    }

    @Test
    void shouldHandleSingleElement() {
        assertEquals("42", run("1\n42\n"));
    }

    @Test
    void shouldHandleNegativeNumbers() {
        assertEquals("-2", run("5\n-1 -1 -2 -2 0\n"));
    }

    @Test
    void shouldPreferLongerSeriesOverSmallerValue() {
        assertEquals("9", run("5\n9 1 9 2 9\n"));
    }

    @Test
    void shouldHandleExtremeValues() {
        assertEquals("-2147483648", run("4\n2147483647 -2147483648 2147483647 -2147483648\n"));
    }

    @Test
    void shouldAcceptMaximalLength() {
        StringBuilder input = new StringBuilder("10000\n");
        for (int i = 0; i < 10_000; i++) {
            input.append(i % 3).append(' ');
        }
        assertEquals("0", run(input.toString()));
    }

    @Test
    void shouldReportErrorWhenLengthIsNotNumber() {
        assertTrue(run("abc\n").contains("должно быть целое число"));
    }

    @Test
    void shouldReportErrorWhenLengthIsZero() {
        assertTrue(run("0\n").contains("некорректная длина"));
    }

    @Test
    void shouldReportErrorWhenLengthIsTooLarge() {
        assertTrue(run("10001\n").contains("некорректная длина"));
    }

    @Test
    void shouldReportErrorWhenElementIsNotNumber() {
        assertTrue(run("3\n1 x 2\n").contains("должно быть целое число"));
    }
}
