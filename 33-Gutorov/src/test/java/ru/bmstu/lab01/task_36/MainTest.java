package ru.bmstu.lab01.task_36;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** Проверяет вычисление бегущей суммы. */
class MainTest {

    @Test
    void shouldCalculateRunningSum() {
        assertEquals("2 5 4 8", run("4\n2 3 -1 4\n"));
    }

    @Test
    void shouldHandleNegativeNumbers() {
        assertEquals("-2 -5 -9", run("3\n-2 -3 -4\n"));
    }

    @Test
    void shouldHandleZeros() {
        assertEquals("0 0 0", run("3\n0 0 0\n"));
    }

    @Test
    void shouldHandleCancellingNumbers() {
        assertEquals("7 0 3 0", run("4\n7 -7 3 -3\n"));
    }

    @Test
    void shouldSumBeyondIntegerRange() {
        assertEquals("2147483647 4294967294",
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