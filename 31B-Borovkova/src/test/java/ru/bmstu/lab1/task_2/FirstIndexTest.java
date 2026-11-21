package ru.bmstu.lab1.task_2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/**
 * Тесты задачи 2: программа запускается с подставленным вводом, проверяется её вывод.
 */
class FirstIndexTest {

    /**
     * Запускает FirstIndex с заданным вводом и возвращает всё, что программа напечатала.
     */
    private String run(String input) {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            FirstIndex.main(new String[0]);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
        return output.toString(StandardCharsets.UTF_8).strip();
    }

    @Test
    void shouldReturnIndexOfFirstOccurrence() {
        assertEquals("1", run("4\n5 3 7 3\n3\n"));
    }

    @Test
    void shouldReturnZeroWhenFirstElementMatches() {
        assertEquals("0", run("3\n8 1 2\n8\n"));
    }

    @Test
    void shouldReturnLastIndexWhenOnlyLastElementMatches() {
        assertEquals("2", run("3\n1 2 9\n9\n"));
    }

    @Test
    void shouldReturnMinusOneWhenElementIsAbsent() {
        assertEquals("-1", run("3\n1 2 3\n9\n"));
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

    @Test
    void shouldReportErrorWhenSearchValueIsNotNumber() {
        assertTrue(run("2\n1 2\ny\n").contains("должно быть целое число"));
    }
}