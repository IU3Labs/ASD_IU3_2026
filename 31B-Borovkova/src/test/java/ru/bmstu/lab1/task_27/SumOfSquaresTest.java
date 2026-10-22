package ru.bmstu.lab1.task_27;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/**
 * Тесты задачи 27: программа запускается с подставленным вводом, проверяется её вывод.
 */
class SumOfSquaresTest {

    /**
     * Запускает SumOfSquares с заданным вводом и возвращает всё, что программа напечатала.
     */
    private String run(String input) {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            SumOfSquares.main(new String[0]);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
        return output.toString(StandardCharsets.UTF_8).strip();
    }

    @Test
    void shouldSumSquares() {
        assertEquals("14", run("3\n1 2 3\n"));
    }

    @Test
    void shouldSquareNegativeNumbers() {
        assertEquals("25", run("2\n-3 4\n"));
    }

    @Test
    void shouldReturnZeroForZeros() {
        assertEquals("0", run("2\n0 0\n"));
    }

    @Test
    void shouldNotOverflowIntOnSingleSquare() {
        assertEquals("10000000000", run("2\n100000 0\n"));
    }

    @Test
    void shouldHandleSumCloseToLongLimit() {
        assertEquals("9223372028264841218", run("2\n2147483647 2147483647\n"));
    }

    @Test
    void shouldNotOverflowLong() {
        // 2 · (2^31)^2 = 2^63 — на единицу больше Long.MAX_VALUE
        assertEquals("9223372036854775808", run("2\n-2147483648 -2147483648\n"));
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
