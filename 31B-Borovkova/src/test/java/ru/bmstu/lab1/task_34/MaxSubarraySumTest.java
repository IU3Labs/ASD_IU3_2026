package ru.bmstu.lab1.task_34;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/**
 * Тесты задачи 34: программа запускается с подставленным вводом, проверяется её вывод.
 */
class MaxSubarraySumTest {

    /**
     * Запускает MaxSubarraySum с заданным вводом и возвращает всё, что программа напечатала.
     */
    private String run(String input) {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            MaxSubarraySum.main(new String[0]);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
        return output.toString(StandardCharsets.UTF_8).strip();
    }

    @Test
    void shouldFindMaxSubarraySum() {
        // подмассив 4 -1 2 1
        assertEquals("6", run("9\n-2 1 -3 4 -1 2 1 -5 4\n"));
    }

    @Test
    void shouldReturnLargestElementWhenAllAreNegative() {
        assertEquals("-1", run("3\n-3 -1 -2\n"));
    }

    @Test
    void shouldReturnWholeSumWhenAllArePositive() {
        assertEquals("10", run("4\n1 2 3 4\n"));
    }

    @Test
    void shouldContinueThroughNegativeWhenProfitable() {
        assertEquals("9", run("3\n5 -1 5\n"));
    }

    @Test
    void shouldRestartAfterLargeNegative() {
        assertEquals("7", run("4\n3 -10 4 3\n"));
    }

    @Test
    void shouldHandleZeros() {
        assertEquals("0", run("3\n0 -1 0\n"));
    }

    @Test
    void shouldNotOverflowOnLargeValues() {
        assertEquals("6442450941", run("3\n2147483647 2147483647 2147483647\n"));
    }

    @Test
    void shouldHandleMinimalValues() {
        assertEquals("-2147483648", run("2\n-2147483648 -2147483648\n"));
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
