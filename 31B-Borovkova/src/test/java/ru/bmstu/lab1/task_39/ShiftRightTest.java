package ru.bmstu.lab1.task_39;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/**
 * Тесты задачи 39: программа запускается с подставленным вводом, проверяется её вывод.
 */
class ShiftRightTest {

    /**
     * Запускает ShiftRight с заданным вводом и возвращает всё, что программа напечатала.
     */
    private String run(String input) {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            ShiftRight.main(new String[0]);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
        return output.toString(StandardCharsets.UTF_8).strip();
    }

    @Test
    void shouldShiftRight() {
        assertEquals("4 5 1 2 3", run("5\n1 2 3 4 5\n2\n"));
    }

    @Test
    void shouldShiftRightByOne() {
        assertEquals("4 1 2 3", run("4\n1 2 3 4\n1\n"));
    }

    @Test
    void shouldKeepArrayWhenKIsZero() {
        assertEquals("1 2 3", run("3\n1 2 3\n0\n"));
    }

    @Test
    void shouldKeepArrayWhenKEqualsN() {
        assertEquals("1 2 3", run("3\n1 2 3\n3\n"));
    }

    @Test
    void shouldHandleKGreaterThanN() {
        // 7 mod 3 = 1
        assertEquals("3 1 2", run("3\n1 2 3\n7\n"));
    }

    @Test
    void shouldHandleKMultipleOfN() {
        assertEquals("1 2 3 4", run("4\n1 2 3 4\n12\n"));
    }

    @Test
    void shouldHandleMaximalK() {
        // 2147483647 mod 4 = 3
        assertEquals("2 3 4 1", run("4\n1 2 3 4\n2147483647\n"));
    }

    @Test
    void shouldHandleNegativeElements() {
        assertEquals("-3 -1 -2", run("3\n-1 -2 -3\n1\n"));
    }

    @Test
    void shouldReportErrorWhenKIsNegative() {
        assertTrue(run("3\n1 2 3\n-1\n").contains("не должно быть отрицательным"));
    }

    @Test
    void shouldReportErrorWhenKIsNotNumber() {
        assertTrue(run("3\n1 2 3\nk\n").contains("должно быть целое число"));
    }

    @Test
    void shouldReportErrorWhenLengthIsNotNumber() {
        assertTrue(run("abc\n").contains("должно быть целое число"));
    }

    @Test
    void shouldReportErrorWhenLengthIsTooSmall() {
        assertTrue(run("1\n5\n1\n").contains("некорректная длина"));
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
