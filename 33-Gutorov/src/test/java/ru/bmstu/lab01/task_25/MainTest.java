package ru.bmstu.lab01.task_25;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** Проверяет вычисление суммы элементов массива. */
class MainTest {

    @Test
    void shouldSumMixedNumbers() {
        assertEquals("19", run("5\n7 -2 10 0 4\n"));
    }

    @Test
    void shouldSumNegativeNumbers() {
        assertEquals("-16", run("3\n-5 -9 -2\n"));
    }

    @Test
    void shouldHandleZeros() {
        assertEquals("0", run("3\n0 0 0\n"));
    }

    @Test
    void shouldHandleCancellingNumbers() {
        assertEquals("0", run("4\n7 -7 3 -3\n"));
    }

    @Test
    void shouldSumBeyondIntegerRange() {
        assertEquals("4294967294",
                run("2\n2147483647 2147483647\n"));
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