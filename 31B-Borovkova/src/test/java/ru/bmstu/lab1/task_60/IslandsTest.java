package ru.bmstu.lab1.task_60;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/**
 * Тесты задачи 60: программа запускается с подставленным вводом, проверяется её вывод.
 */
class IslandsTest {

    /**
     * Запускает Islands с заданным вводом и возвращает всё, что программа напечатала.
     */
    private String run(String input) {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            Islands.main(new String[0]);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
        return output.toString(StandardCharsets.UTF_8).strip();
    }

    @Test
    void shouldCountSeparateIslands() {
        assertEquals("2", run("3 3\n1 1 0\n0 0 0\n0 1 1\n"));
    }

    @Test
    void shouldNotConnectDiagonalCells() {
        assertEquals("2", run("2 2\n1 0\n0 1\n"));
    }

    @Test
    void shouldCountWholeMatrixAsOneIsland() {
        assertEquals("1", run("2 3\n1 1 1\n1 1 1\n"));
    }

    @Test
    void shouldReturnZeroWhenThereIsNoLand() {
        assertEquals("0", run("2 2\n0 0\n0 0\n"));
    }

    @Test
    void shouldCountIslandsOfComplexShape() {
        assertEquals("3", run("4 4\n1 1 0 1\n0 1 0 1\n1 1 0 0\n0 0 0 1\n"));
    }

    @Test
    void shouldHandleSingleCellMatrix() {
        assertEquals("1", run("1 1\n1\n"));
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
    void shouldReportErrorWhenCellIsNotNumber() {
        assertTrue(run("2 2\n1 x\n").contains("должно быть целое число"));
    }

    @Test
    void shouldReportErrorWhenCellIsNotBinary() {
        assertTrue(run("2 2\n1 2\n0 0\n").contains("должна состоять из 0 и 1"));
    }
}