package ru.bmstu.lab1.task_15;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/**
 * Тесты задачи 15: программа запускается с подставленным вводом, проверяется её вывод.
 */
class BubbleSortTest {

    /**
     * Запускает BubbleSort с заданным вводом и возвращает всё, что программа напечатала.
     * Переводы строк приводятся к \n, чтобы тесты не зависели от ОС.
     */
    private String run(String input) {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            BubbleSort.main(new String[0]);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
        return output.toString(StandardCharsets.UTF_8).strip().replace("\r\n", "\n");
    }

    @Test
    void shouldSortArrayAndCountSwaps() {
        assertEquals("1 2 3 5 8\n7", run("5\n5 3 8 1 2\n"));
    }

    @Test
    void shouldMakeNoSwapsForSortedArray() {
        assertEquals("1 2 3 4\n0", run("4\n1 2 3 4\n"));
    }

    @Test
    void shouldMakeMaximumSwapsForReversedArray() {
        assertEquals("1 2 3 4 5\n10", run("5\n5 4 3 2 1\n"));
    }

    @Test
    void shouldNotSwapEqualElements() {
        assertEquals("2 2 2\n0", run("3\n2 2 2\n"));
    }

    @Test
    void shouldHandleDuplicates() {
        assertEquals("1 2 2 3 3\n6", run("5\n3 2 3 1 2\n"));
    }

    @Test
    void shouldHandleNegativeNumbers() {
        assertEquals("-7 -2 0 4\n4", run("4\n0 -2 4 -7\n"));
    }

    @Test
    void shouldHandleMinimalLength() {
        assertEquals("1 9\n1", run("2\n9 1\n"));
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
