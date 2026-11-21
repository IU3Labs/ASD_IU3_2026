package ru.bmstu.lab1.task_23;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/**
 * Тесты задачи 23: программа запускается с подставленным вводом, проверяется её вывод.
 */
class ArrayAnagramTest {

    /**
     * Запускает ArrayAnagram с заданным вводом и возвращает всё, что программа напечатала.
     */
    private String run(String input) {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            ArrayAnagram.main(new String[0]);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
        return output.toString(StandardCharsets.UTF_8).strip();
    }

    @Test
    void shouldReturnYesForPermutation() {
        assertEquals("YES", run("4\n1 2 3 4\n4 3 1 2\n"));
    }

    @Test
    void shouldReturnYesForEqualArrays() {
        assertEquals("YES", run("3\n5 6 7\n5 6 7\n"));
    }

    @Test
    void shouldReturnNoForDifferentElements() {
        assertEquals("NO", run("3\n1 2 3\n1 2 4\n"));
    }

    @Test
    void shouldReturnNoWhenDuplicateCountsDiffer() {
        assertEquals("NO", run("4\n1 1 2 2\n1 2 2 2\n"));
    }

    @Test
    void shouldReturnYesWithDuplicates() {
        assertEquals("YES", run("5\n3 1 3 2 3\n3 3 2 3 1\n"));
    }

    @Test
    void shouldHandleNegativeNumbers() {
        assertEquals("YES", run("3\n-1 0 1\n1 -1 0\n"));
    }

    @Test
    void shouldReturnNoWhenSignsDiffer() {
        assertEquals("NO", run("2\n-1 2\n1 2\n"));
    }

    @Test
    void shouldReportErrorWhenLengthIsNotNumber() {
        assertTrue(run("abc\n").contains("должно быть целое число"));
    }

    @Test
    void shouldReportErrorWhenLengthIsTooSmall() {
        assertTrue(run("1\n5\n5\n").contains("некорректная длина"));
    }

    @Test
    void shouldReportErrorWhenLengthIsTooLarge() {
        assertTrue(run("100000\n").contains("некорректная длина"));
    }

    @Test
    void shouldReportErrorWhenFirstArrayElementIsNotNumber() {
        assertTrue(run("3\n1 x 2\n").contains("должно быть целое число"));
    }

    @Test
    void shouldReportErrorWhenSecondArrayElementIsNotNumber() {
        assertTrue(run("2\n1 2\n2 y\n").contains("должно быть целое число"));
    }

    @Test
    void shouldReportErrorWhenSecondArrayIsTooShort() {
        assertTrue(run("3\n1 2 3\n1 2\n").contains("должно быть целое число"));
    }
}
