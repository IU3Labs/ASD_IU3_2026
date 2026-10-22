package ru.bmstu.lab1.task_31;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/**
 * Тесты задачи 31: программа запускается с подставленным вводом, проверяется её вывод.
 */
class DotProductTest {

    /**
     * Запускает DotProduct с заданным вводом и возвращает всё, что программа напечатала.
     */
    private String run(String input) {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            DotProduct.main(new String[0]);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
        return output.toString(StandardCharsets.UTF_8).strip();
    }

    @Test
    void shouldComputeDotProduct() {
        assertEquals("32", run("3\n1 2 3\n4 5 6\n"));
    }

    @Test
    void shouldReturnZeroForOrthogonalVectors() {
        assertEquals("0", run("2\n1 0\n0 5\n"));
    }

    @Test
    void shouldHandleNegativeNumbers() {
        // (-1)·4 + 2·(-1) + (-3)·2 = -12
        assertEquals("-12", run("3\n-1 2 -3\n4 -1 2\n"));
    }

    @Test
    void shouldNotOverflowIntOnSingleProduct() {
        assertEquals("10000000000", run("2\n100000 0\n100000 7\n"));
    }

    @Test
    void shouldNotOverflowLong() {
        // 2 · (2^31)^2 = 2^63 — на единицу больше Long.MAX_VALUE
        assertEquals("9223372036854775808",
                run("2\n-2147483648 -2147483648\n-2147483648 -2147483648\n"));
    }

    @Test
    void shouldHandleLargeNegativeSum() {
        assertEquals("-9223372032559808512",
                run("2\n2147483647 2147483647\n-2147483648 -2147483648\n"));
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
    void shouldReportErrorWhenFirstArrayElementIsNotNumber() {
        assertTrue(run("3\n1 x 2\n").contains("должно быть целое число"));
    }

    @Test
    void shouldReportErrorWhenSecondArrayElementIsNotNumber() {
        assertTrue(run("2\n1 2\n3 y\n").contains("должно быть целое число"));
    }

    @Test
    void shouldReportErrorWhenSecondArrayIsTooShort() {
        assertTrue(run("3\n1 2 3\n4 5\n").contains("должно быть целое число"));
    }
}
