package ru.bmstu.lab01.task_37;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

/** Проверяет разворот массива на месте и вывод результата. */
class MainTest {

    @Test
    void shouldReverseOddLengthArray() {
        assertEquals("5 4 3 2 1", run("5\n1 2 3 4 5\n"));
    }

    @Test
    void shouldReverseEvenLengthArray() {
        assertEquals("4 3 2 1", run("4\n1 2 3 4\n"));
    }

    @Test
    void shouldHandleNegativeNumbersAndZero() {
        assertEquals("0 3 -2", run("3\n-2 3 0\n"));
    }

    @Test
    void shouldHandleEqualNumbers() {
        assertEquals("7 7 7", run("3\n7 7 7\n"));
    }

    @Test
    void shouldReverseOriginalArray() {
        int[] numbers = {10, 20};

        Main.reverse(numbers);

        assertArrayEquals(new int[] {20, 10}, numbers);
    }

    private String run(String data) {
        InputStream originalInput = System.in;
        PrintStream originalOutput = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try (PrintStream testOutput =
                new PrintStream(output, true, StandardCharsets.UTF_8)) {
            System.setIn(new ByteArrayInputStream(
                    data.getBytes(StandardCharsets.UTF_8)));
            System.setOut(testOutput);

            Main.main();

            return output.toString(StandardCharsets.UTF_8).strip();
        } finally {
            System.setIn(originalInput);
            System.setOut(originalOutput);
        }
    }
}