package ru.bmstu.lab1.task_6;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/**
 * Тесты задачи 6: программа запускается с подставленным вводом, проверяется её вывод.
 */
class NumElementsGreaterAverageTest {

    /**
     * Запускает NumElementsGreaterAverage с заданным вводом и возвращает всё, что программа напечатала.
     */
    private String run(String input) {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            NumElementsGreaterAverage.main(new String[0]);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
        return output.toString(StandardCharsets.UTF_8).strip();
    }

    @Test
    void shouldCountElementsAboveAverage() {
        assertEquals("1", run("4\n1 2 3 6\n"));
    }

    @Test
    void shouldReturnZeroWhenAllElementsAreEqual() {
        assertEquals("0", run("3\n5 5 5\n"));
    }

    @Test
    void shouldNotCountElementsEqualToAverage() {
        assertEquals("1", run("4\n0 2 2 4\n"));
    }

    @Test
    void shouldHandleNegativeNumbers() {
        assertEquals("2", run("4\n-4 -2 0 2\n"));
    }

    @Test
    void shouldNotOverflowOnLargeValues() {
        assertEquals("0", run("2\n2147483647 2147483647\n"));
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
