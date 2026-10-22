package ru.bmstu.lab1.task_4;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/**
 * Тесты задачи 4: программа запускается с подставленным вводом, проверяется её вывод.
 */
class IndexElementsTest {

    /**
     * Запускает IndexElements с заданным вводом и возвращает всё, что программа напечатала.
     * Переводы строк Windows (\r\n) заменяются на \n, чтобы сравнение не зависело от системы.
     */
    private String run(String input) {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            IndexElements.main(new String[0]);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
        return output.toString(StandardCharsets.UTF_8).replace("\r\n", "\n").strip();
    }

    @Test
    void shouldPrintAllIndicesInAscendingOrder() {
        assertEquals("1\n2\n4", run("5\n1 2 2 5 2\n2\n"));
    }

    @Test
    void shouldPrintSingleIndex() {
        assertEquals("1", run("3\n7 8 9\n8\n"));
    }

    @Test
    void shouldPrintEveryIndexWhenAllElementsMatch() {
        assertEquals("0\n1\n2", run("3\n4 4 4\n4\n"));
    }

    @Test
    void shouldPrintMinusOneWhenElementIsAbsent() {
        assertEquals("-1", run("3\n1 2 3\n9\n"));
    }

    @Test
    void shouldReportErrorWhenLengthIsNotNumber() {
        assertTrue(run("abc\n").contains("должно быть целое число"));
    }

    @Test
    void shouldReportErrorWhenLengthIsNegative() {
        assertTrue(run("-3\n").contains("некорректная длина"));
    }

    @Test
    void shouldReportErrorWhenLengthIsTooLarge() {
        assertTrue(run("100000\n").contains("некорректная длина"));
    }

    @Test
    void shouldReportErrorWhenElementIsNotNumber() {
        assertTrue(run("3\n1 x\n").contains("должно быть целое число"));
    }

    @Test
    void shouldReportErrorWhenSearchValueIsNotNumber() {
        assertTrue(run("2\n1 2\ny\n").contains("должно быть целое число"));
    }
}