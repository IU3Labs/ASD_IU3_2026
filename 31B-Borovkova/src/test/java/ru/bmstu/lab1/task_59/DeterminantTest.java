package ru.bmstu.lab1.task_59;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/**
 * Тесты задачи 59: программа запускается с подставленным вводом, проверяется её вывод.
 */
class DeterminantTest {

    /**
     * Запускает Determinant с заданным вводом и возвращает всё, что программа напечатала.
     */
    private String run(String input) {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            Determinant.main(new String[0]);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
        return output.toString(StandardCharsets.UTF_8).strip();
    }

    @Test
    void shouldReturnOneForIdentityMatrix() {
        assertEquals("1", run("1 0 0\n0 1 0\n0 0 1\n"));
    }

    @Test
    void shouldReturnProductOfDiagonalForDiagonalMatrix() {
        assertEquals("24", run("2 0 0\n0 3 0\n0 0 4\n"));
    }

    @Test
    void shouldCalculateDeterminantOfGeneralMatrix() {
        assertEquals("1", run("1 2 3\n0 1 4\n5 6 0\n"));
    }

    @Test
    void shouldReturnZeroForLinearlyDependentRows() {
        assertEquals("0", run("1 2 3\n4 5 6\n7 8 9\n"));
    }

    @Test
    void shouldReturnNegativeDeterminantWhenRowsAreSwapped() {
        assertEquals("-1", run("0 1 0\n1 0 0\n0 0 1\n"));
    }

    @Test
    void shouldNotOverflowOnLargeValues() {
        assertEquals("1000000000000000000", run("1000000 0 0\n0 1000000 0\n0 0 1000000\n"));
    }

    @Test
    void shouldReportErrorWhenElementIsNotNumber() {
        assertTrue(run("1 2 x\n").contains("должно быть целое число"));
    }

    @Test
    void shouldReportErrorWhenElementsAreMissing() {
        assertTrue(run("1 2 3\n4 5\n").contains("должно быть целое число"));
    }

    @Test
    void shouldReportErrorWhenElementIsTooLarge() {
        assertTrue(run("1000001 0 0\n0 1 0\n0 0 1\n").contains("слишком большой"));
    }

    @Test
    void shouldReportErrorWhenElementIsTooSmall() {
        assertTrue(run("-1000001 0 0\n0 1 0\n0 0 1\n").contains("слишком большой"));
    }
}