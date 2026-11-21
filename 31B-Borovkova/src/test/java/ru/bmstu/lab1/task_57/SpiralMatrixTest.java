package ru.bmstu.lab1.task_57;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/**
 * Тесты задачи 57: программа запускается с подставленным вводом, проверяется её вывод.
 */
class SpiralMatrixTest {

    /**
     * Запускает SpiralMatrix с заданным вводом и возвращает всё, что программа напечатала.
     */
    private String run(String input) {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            SpiralMatrix.main(new String[0]);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
        return output.toString(StandardCharsets.UTF_8).strip();
    }

    @Test
    void shouldTraverseSquareMatrixInSpiral() {
        assertEquals("1 2 3 6 9 8 7 4 5", run("3 3\n1 2 3\n4 5 6\n7 8 9\n"));
    }

    @Test
    void shouldTraverseWideMatrixInSpiral() {
        assertEquals("1 2 3 4 8 12 11 10 9 5 6 7", run("3 4\n1 2 3 4\n5 6 7 8\n9 10 11 12\n"));
    }

    @Test
    void shouldTraverseTallMatrixInSpiral() {
        assertEquals("1 2 3 6 9 12 11 10 7 4 5 8", run("4 3\n1 2 3\n4 5 6\n7 8 9\n10 11 12\n"));
    }

    @Test
    void shouldTraverseTwoByTwoMatrix() {
        assertEquals("1 2 4 3", run("2 2\n1 2\n3 4\n"));
    }

    @Test
    void shouldNotRepeatElementsOfSingleRow() {
        assertEquals("1 2 3 4", run("1 4\n1 2 3 4\n"));
    }

    @Test
    void shouldNotRepeatElementsOfSingleColumn() {
        assertEquals("1 2 3", run("3 1\n1\n2\n3\n"));
    }

    @Test
    void shouldHandleSingleCellMatrix() {
        assertEquals("7", run("1 1\n7\n"));
    }

    @Test
    void shouldReportErrorWhenRowsIsNotNumber() {
        assertTrue(run("abc\n").contains("должно быть целое число"));
    }

    @Test
    void shouldReportErrorWhenColumnsIsNotNumber() {
        assertTrue(run("3 x\n").contains("должно быть целое число"));
    }

    @Test
    void shouldReportErrorWhenSizeIsTooSmall() {
        assertTrue(run("0 5\n").contains("некорректный размер"));
    }

    @Test
    void shouldReportErrorWhenSizeIsTooLarge() {
        assertTrue(run("1001 1\n").contains("некорректный размер"));
    }

    @Test
    void shouldReportErrorWhenElementIsNotNumber() {
        assertTrue(run("2 2\n1 x\n").contains("должно быть целое число"));
    }
}