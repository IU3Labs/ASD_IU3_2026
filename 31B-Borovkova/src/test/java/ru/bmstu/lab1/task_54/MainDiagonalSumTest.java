package ru.bmstu.lab1.task_54;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/**
 * Тесты задачи 54: программа запускается с подставленным вводом, проверяется её вывод.
 */
class MainDiagonalSumTest {

    /**
     * Запускает MainDiagonalSum с заданным вводом и возвращает всё, что программа напечатала.
     */
    private String run(String input) {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            MainDiagonalSum.main(new String[0]);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
        return output.toString(StandardCharsets.UTF_8).strip();
    }

    @Test
    void shouldSumMainDiagonalOfOddSizeMatrix() {
        assertEquals("16", run("3\n1 2 3\n4 5 6\n7 8 10\n"));
    }

    @Test
    void shouldSumMainDiagonalOfEvenSizeMatrix() {
        assertEquals("6", run("2\n1 2\n3 5\n"));
    }

    @Test
    void shouldIgnoreElementsOutsideDiagonal() {
        assertEquals("0", run("2\n0 100\n100 0\n"));
    }

    @Test
    void shouldReturnElementForSingleCellMatrix() {
        assertEquals("7", run("1\n7\n"));
    }

    @Test
    void shouldHandleNegativeNumbers() {
        assertEquals("-11", run("2\n-5 1\n2 -6\n"));
    }

    @Test
    void shouldNotOverflowOnLargeValues() {
        assertEquals("4294967294", run("2\n2147483647 0\n0 2147483647\n"));
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
}
