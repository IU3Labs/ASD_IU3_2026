package ru.bmstu.lab1.task_9;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/**
 * Тесты задачи 9: программа запускается с подставленным вводом, проверяется её вывод.
 */
class SortedCheckTest {

    /**
     * Запускает SortedCheck с заданным вводом и возвращает всё, что программа напечатала.
     */
    private String run(String input) {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            SortedCheck.main(new String[0]);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
        return output.toString(StandardCharsets.UTF_8).strip();
    }

    @Test
    void shouldReturnYesForStrictlyIncreasingArray() {
        assertEquals("YES", run("5\n1 2 3 4 5\n"));
    }

    @Test
    void shouldReturnYesWhenNeighborsAreEqual() {
        assertEquals("YES", run("4\n1 2 2 5\n"));
    }

    @Test
    void shouldReturnYesWhenAllElementsAreEqual() {
        assertEquals("YES", run("3\n7 7 7\n"));
    }

    @Test
    void shouldReturnYesForNegativeNumbers() {
        assertEquals("YES", run("3\n-5 -2 0\n"));
    }

    @Test
    void shouldReturnNoWhenOrderIsBrokenInMiddle() {
        assertEquals("NO", run("3\n1 3 2\n"));
    }

    @Test
    void shouldReturnNoWhenOrderIsBrokenAtEnd() {
        assertEquals("NO", run("4\n1 2 3 0\n"));
    }

    @Test
    void shouldReturnNoForDecreasingArray() {
        assertEquals("NO", run("3\n3 2 1\n"));
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