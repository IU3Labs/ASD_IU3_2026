package ru.bmstu.lab1.task_56;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/**
 * Тесты задачи 56: программа запускается с подставленным вводом, проверяется её вывод.
 */
class MatrixMultiplicationTest {

    /**
     * Запускает MatrixMultiplication с заданным вводом и возвращает всё, что программа напечатала.
     * Переводы строк Windows (\r\n) заменяются на \n, чтобы сравнение не зависело от системы.
     */
    private String run(String input) {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            MatrixMultiplication.main(new String[0]);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
        return output.toString(StandardCharsets.UTF_8).replace("\r\n", "\n").strip();
    }

    @Test
    void shouldMultiplyRectangularMatrices() {
        assertEquals("58 64\n139 154", run("2 3\n1 2 3\n4 5 6\n3 2\n7 8\n9 10\n11 12\n"));
    }

    @Test
    void shouldNotChangeMatrixWhenMultipliedByIdentity() {
        assertEquals("1 2\n3 4", run("2 2\n1 2\n3 4\n2 2\n1 0\n0 1\n"));
    }

    @Test
    void shouldReturnSingleNumberForRowTimesColumn() {
        assertEquals("32", run("1 3\n1 2 3\n3 1\n4\n5\n6\n"));
    }

    @Test
    void shouldReturnMatrixForColumnTimesRow() {
        assertEquals("4 5\n8 10\n12 15", run("3 1\n1\n2\n3\n1 2\n4 5\n"));
    }

    @Test
    void shouldHandleNegativeNumbers() {
        assertEquals("-11", run("1 2\n-1 2\n2 1\n3\n-4\n"));
    }

    @Test
    void shouldNotOverflowOnLargeValues() {
        assertEquals("2000000000000", run("1 2\n1000000 1000000\n2 1\n1000000\n1000000\n"));
    }

    @Test
    void shouldReportErrorWhenSizesAreIncompatible() {
        assertTrue(run("2 3\n1 2 3\n4 5 6\n2 2\n1 0\n0 1\n").contains("число столбцов A"));
    }

    @Test
    void shouldReportErrorWhenSizeIsNotNumber() {
        assertTrue(run("abc\n").contains("должно быть целое число"));
    }

    @Test
    void shouldReportErrorWhenSizeIsTooSmall() {
        assertTrue(run("0 3\n").contains("некорректный размер"));
    }

    @Test
    void shouldReportErrorWhenSizeIsTooLarge() {
        assertTrue(run("501 1\n").contains("некорректный размер"));
    }

    @Test
    void shouldReportErrorWhenElementIsNotNumber() {
        assertTrue(run("2 2\n1 x\n").contains("должно быть целое число"));
    }

    @Test
    void shouldReportErrorWhenElementIsTooLarge() {
        assertTrue(run("1 1\n1000001\n").contains("слишком большой"));
    }

    @Test
    void shouldReportErrorWhenSecondMatrixSizeIsNotNumber() {
        assertTrue(run("1 1\n5\nx\n").contains("должно быть целое число"));
    }
}