package ru.bmstu.lab1.task_55;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/**
 * Тесты задачи 55: программа запускается с подставленным вводом, проверяется её вывод.
 */
class AntiDiagonalSumTest {

    /**
     * Запускает AntiDiagonalSum с заданным вводом и возвращает всё, что программа напечатала.
     */
    private String run(String input) {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            AntiDiagonalSum.main(new String[0]);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
        return output.toString(StandardCharsets.UTF_8).strip();
    }

    @Test
    void shouldSumAntiDiagonalOfOddSizeMatrix() {
        assertEquals("15", run("3\n1 2 3\n4 5 6\n7 8 9\n"));
    }

    @Test
    void shouldSumAntiDiagonalOfEvenSizeMatrix() {
        assertEquals("5", run("2\n1 2\n3 4\n"));
    }

    @Test
    void shouldReturnElementForSingleCellMatrix() {
        assertEquals("7", run("1\n7\n"));
    }

    @Test
    void shouldHandleNegativeNumbers() {
        assertEquals("-11", run("2\n1 -5\n-6 2\n"));
    }

    @Test
    void shouldNotOverflowOnLargeValues() {
        assertEquals("4294967294", run("2\n0 2147483647\n2147483647 0\n"));
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
