package ru.bmstu.lab1.task_30;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/**
 * Тесты задачи 30: программа запускается с подставленным вводом, проверяется её вывод.
 */
class VarianceTest {

    /**
     * Запускает Variance с заданным вводом и возвращает всё, что программа напечатала.
     */
    private String run(String input) {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            Variance.main(new String[0]);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
        return output.toString(StandardCharsets.UTF_8).strip();
    }

    @Test
    void shouldComputeVariance() {
        assertEquals("1.250000", run("4\n1 2 3 4\n"));
    }

    @Test
    void shouldReturnZeroForEqualElements() {
        assertEquals("0.000000", run("3\n5 5 5\n"));
    }

    @Test
    void shouldHandleNegativeNumbers() {
        assertEquals("1.000000", run("2\n-1 1\n"));
    }

    @Test
    void shouldHandleFractionalMean() {
        // среднее 4/3, D = 2/9
        assertEquals("0.222222", run("3\n1 1 2\n"));
    }

    @Test
    void shouldNotOverflowOnLargeValues() {
        assertEquals("4000000000000000000.000000", run("2\n2000000000 -2000000000\n"));
    }

    @Test
    void shouldStayAccurateForLargeCloseValues() {
        assertEquals("0.250000", run("2\n2147483646 2147483647\n"));
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
