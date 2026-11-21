package ru.bmstu.lab1.task_20;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/**
 * Тесты задачи 20: программа запускается с подставленным вводом, проверяется её вывод.
 */
class CountingSortTest {

    /**
     * Запускает CountingSort с заданным вводом и возвращает всё, что программа напечатала.
     */
    private String run(String input) {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            CountingSort.main(new String[0]);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
        return output.toString(StandardCharsets.UTF_8).strip();
    }

    @Test
    void shouldSortArray() {
        assertEquals("1 2 3 5 8", run("5\n5 3 8 1 2\n"));
    }

    @Test
    void shouldHandleDuplicates() {
        assertEquals("1 1 2 3 3 3", run("6\n3 1 3 2 3 1\n"));
    }

    @Test
    void shouldHandleNegativeNumbers() {
        assertEquals("-7 -2 0 4", run("4\n0 -2 4 -7\n"));
    }

    @Test
    void shouldHandleEqualElements() {
        assertEquals("5 5 5", run("3\n5 5 5\n"));
    }

    @Test
    void shouldHandleLargeValuesWithSmallRange() {
        assertEquals("2147483645 2147483646 2147483647", run("3\n2147483647 2147483645 2147483646\n"));
    }

    @Test
    void shouldAcceptMaximalRange() {
        assertEquals("0 999999", run("2\n999999 0\n"));
    }

    @Test
    void shouldReportErrorWhenRangeIsTooLarge() {
        assertTrue(run("2\n1000000 0\n").contains("слишком большой диапазон"));
    }

    @Test
    void shouldReportErrorWhenRangeIsWholeInt() {
        assertTrue(run("2\n-2147483648 2147483647\n").contains("слишком большой диапазон"));
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
