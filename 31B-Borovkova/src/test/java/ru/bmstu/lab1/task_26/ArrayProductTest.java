package ru.bmstu.lab1.task_26;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/**
 * Тесты задачи 26: программа запускается с подставленным вводом, проверяется её вывод.
 */
class ArrayProductTest {

    /**
     * Запускает ArrayProduct с заданным вводом и возвращает всё, что программа напечатала.
     */
    private String run(String input) {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            ArrayProduct.main(new String[0]);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
        return output.toString(StandardCharsets.UTF_8).strip();
    }

    @Test
    void shouldMultiplyElements() {
        assertEquals("120", run("5\n1 2 3 4 5\n"));
    }

    @Test
    void shouldReturnZeroWhenThereIsZero() {
        assertEquals("0", run("3\n7 0 9\n"));
    }

    @Test
    void shouldReturnPositiveProductOfTwoNegatives() {
        assertEquals("12", run("2\n-3 -4\n"));
    }

    @Test
    void shouldReturnNonNegativeRemainderForNegativeProduct() {
        assertEquals("1000000002", run("2\n-1 5\n"));
    }

    @Test
    void shouldReturnZeroWhenElementEqualsModulus() {
        assertEquals("0", run("2\n1000000007 3\n"));
    }

    @Test
    void shouldTakeRemainderOfLargeProduct() {
        // 10^18 mod (10^9 + 7) = 49
        assertEquals("49", run("3\n1000000 1000000 1000000\n"));
    }

    @Test
    void shouldNotOverflowOnExtremeValues() {
        // (2^31 - 1)^2 mod (10^9 + 7) = 850618742
        assertEquals("850618742", run("2\n2147483647 2147483647\n"));
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
