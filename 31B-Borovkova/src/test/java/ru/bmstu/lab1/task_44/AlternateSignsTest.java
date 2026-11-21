package ru.bmstu.lab1.task_44;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/**
 * Тесты задачи 44: программа запускается с подставленным вводом, проверяется её вывод.
 */
class AlternateSignsTest {

    /**
     * Запускает AlternateSigns с заданным вводом и возвращает всё, что программа напечатала.
     */
    private String run(String input) {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            AlternateSigns.main(new String[0]);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
        return output.toString(StandardCharsets.UTF_8).strip();
    }

    @Test
    void shouldAlternateSigns() {
        assertEquals("1 -1 2 -2 3 -3", run("6\n1 2 -1 -2 3 -3\n"));
    }

    @Test
    void shouldStartWithPositiveWhenArrayStartsWithNegative() {
        assertEquals("5 -4 6 -7", run("4\n-4 -7 5 6\n"));
    }

    @Test
    void shouldKeepAlreadyAlternatingArray() {
        assertEquals("1 -2 3 -4", run("4\n1 -2 3 -4\n"));
    }

    @Test
    void shouldKeepRelativeOrderInsideGroups() {
        assertEquals("9 -5 1 -8 4 -2", run("6\n-5 9 -8 1 -2 4\n"));
    }

    @Test
    void shouldHandleMinimalLength() {
        assertEquals("3 -3", run("2\n-3 3\n"));
    }

    @Test
    void shouldHandleExtremeValues() {
        assertEquals("2147483647 -2147483648", run("2\n-2147483648 2147483647\n"));
    }

    @Test
    void shouldReportErrorWhenThereIsZero() {
        assertTrue(run("4\n1 0 -1 -2\n").contains("не должно быть нулей"));
    }

    @Test
    void shouldReportErrorWhenCountsDiffer() {
        assertTrue(run("4\n1 2 3 -1\n").contains("поровну"));
    }

    @Test
    void shouldReportErrorWhenLengthIsOdd() {
        assertTrue(run("3\n1 -1 2\n").contains("поровну"));
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
