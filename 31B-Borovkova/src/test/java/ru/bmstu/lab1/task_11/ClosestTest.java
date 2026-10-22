
package ru.bmstu.lab1.task_11;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/**
 * Тесты задачи 11: программа запускается с подставленным вводом, проверяется её вывод.
 */
class ClosestTest {

    /**
     * Запускает Closest с заданным вводом и возвращает всё, что программа напечатала.
     */
    private String run(String input) {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            Closest.main(new String[0]);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
        return output.toString(StandardCharsets.UTF_8).strip();
    }

    @Test
    void shouldFindElementClosest() {
        assertEquals("3", run("4\n1 2 3 10\n"));
    }

    @Test
    void shouldReturnFirstElementWhenDistancesAreEqual() {
        assertEquals("1", run("2\n1 3\n"));
    }

    @Test
    void shouldReturnFirstByIndexAmongSeveralCandidates() {
        assertEquals("2", run("4\n2 6 0 8\n"));
    }

    @Test
    void shouldUseFractionalAverage() {
        assertEquals("3", run("4\n2 3 3 3\n"));
    }

    @Test
    void shouldHandleNegativeNumbers() {
        assertEquals("-4", run("3\n-10 -4 1\n"));
    }

    @Test
    void shouldNotOverflowOnLargeValues() {
        assertEquals("2147483647", run("3\n2147483647 2147483647 0\n"));
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