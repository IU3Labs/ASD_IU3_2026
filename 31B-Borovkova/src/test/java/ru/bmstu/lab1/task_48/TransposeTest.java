package ru.bmstu.lab1.task_48;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/**
 * Тесты задачи 48: программа запускается с подставленным вводом, проверяется её вывод.
 */
class TransposeTest {

    /**
     * Запускает Transpose с заданным вводом и возвращает всё, что программа напечатала.
     */
    private String run(String input) {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            Transpose.main(new String[0]);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
        return output.toString(StandardCharsets.UTF_8).replace("\r\n", "\n").strip();
    }

    @Test
    void shouldTransposeTwoByTwoMatrix() {
        assertEquals("1 3\n2 4", run("2\n1 2\n3 4\n"));
    }

    @Test
    void shouldTransposeThreeByThreeMatrix() {
        assertEquals("1 4 7\n2 5 8\n3 6 9", run("3\n1 2 3\n4 5 6\n7 8 9\n"));
    }

    @Test
    void shouldKeepSingleElement() {
        assertEquals("42", run("1\n42\n"));
    }

    @Test
    void shouldKeepSymmetricMatrix() {
        assertEquals("1 2 3\n2 5 6\n3 6 9", run("3\n1 2 3\n2 5 6\n3 6 9\n"));
    }

    @Test
    void shouldHandleNegativeNumbers() {
        assertEquals("-1 -3\n-2 -4", run("2\n-1 -2\n-3 -4\n"));
    }

    @Test
    void shouldReportErrorWhenSizeIsNotNumber() {
        assertTrue(run("abc\n").contains("должно быть целое число"));
    }

    @Test
    void shouldReportErrorWhenSizeIsTooSmall() {
        assertTrue(run("0\n").contains("некорректный размер"));
    }

    @Test
    void shouldReportErrorWhenSizeIsTooLarge() {
        assertTrue(run("1001\n").contains("некорректный размер"));
    }

    @Test
    void shouldReportErrorWhenElementIsNotNumber() {
        assertTrue(run("2\n1 x\n").contains("должно быть целое число"));
    }

    @Test
    void shouldReportErrorWhenNotEnoughElements() {
        assertTrue(run("2\n1 2 3\n").contains("должно быть целое число"));
    }
}
