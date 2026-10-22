package ru.bmstu.lab1.task_58;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/**
 * Тесты задачи 58: программа запускается с подставленным вводом, проверяется её вывод.
 */
class SnakeMatrixTest {

    /**
     * Запускает SnakeMatrix с заданным вводом и возвращает всё, что программа напечатала.
     * Переводы строк Windows (\r\n) заменяются на \n, чтобы сравнение не зависело от системы.
     */
    private String run(String input) {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            SnakeMatrix.main(new String[0]);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
        return output.toString(StandardCharsets.UTF_8).replace("\r\n", "\n").strip();
    }

    @Test
    void shouldFillSquareMatrixAsSnake() {
        assertEquals("1 2 3\n6 5 4\n7 8 9", run("3 3\n"));
    }

    @Test
    void shouldFillRectangularMatrixAsSnake() {
        assertEquals("1 2 3\n6 5 4", run("2 3\n"));
    }

    @Test
    void shouldFillSingleRowLeftToRight() {
        assertEquals("1 2 3 4", run("1 4\n"));
    }

    @Test
    void shouldFillSingleColumnTopToBottom() {
        assertEquals("1\n2\n3\n4", run("4 1\n"));
    }

    @Test
    void shouldFillSingleCellMatrix() {
        assertEquals("1", run("1 1\n"));
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
        assertTrue(run("5 1001\n").contains("некорректный размер"));
    }
}