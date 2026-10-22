package ru.bmstu.lab1.task_42;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/**
 * Тесты задачи 42: программа запускается с подставленным вводом, проверяется её вывод.
 */
class InsertSortedTest {

    /**
     * Запускает InsertSorted с заданным вводом и возвращает всё, что программа напечатала.
     */
    private String run(String input) {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            InsertSorted.main(new String[0]);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
        return output.toString(StandardCharsets.UTF_8).strip();
    }

    @Test
    void shouldInsertInMiddle() {
        assertEquals("1 3 4 5 7", run("4\n1 3 5 7\n4\n"));
    }

    @Test
    void shouldInsertAtBeginning() {
        assertEquals("0 1 2 3", run("3\n1 2 3\n0\n"));
    }

    @Test
    void shouldInsertAtEnd() {
        assertEquals("1 2 3 9", run("3\n1 2 3\n9\n"));
    }

    @Test
    void shouldInsertAmongEqualElements() {
        assertEquals("1 2 2 2 3", run("4\n1 2 2 3\n2\n"));
    }

    @Test
    void shouldInsertWhenAllElementsAreEqual() {
        assertEquals("5 5 5", run("2\n5 5\n5\n"));
    }

    @Test
    void shouldHandleNegativeNumbers() {
        assertEquals("-5 -3 -2 0", run("3\n-5 -2 0\n-3\n"));
    }

    @Test
    void shouldHandleExtremeValues() {
        assertEquals("-2147483648 0 2147483647 2147483647", run("3\n-2147483648 0 2147483647\n2147483647\n"));
    }

    @Test
    void shouldReportErrorWhenArrayIsNotSorted() {
        assertTrue(run("3\n3 1 2\n2\n").contains("отсортирован"));
    }

    @Test
    void shouldReportErrorWhenXIsNotNumber() {
        assertTrue(run("3\n1 2 3\nx\n").contains("должно быть целое число"));
    }

    @Test
    void shouldReportErrorWhenLengthIsNotNumber() {
        assertTrue(run("abc\n").contains("должно быть целое число"));
    }

    @Test
    void shouldReportErrorWhenLengthIsTooSmall() {
        assertTrue(run("1\n5\n5\n").contains("некорректная длина"));
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
