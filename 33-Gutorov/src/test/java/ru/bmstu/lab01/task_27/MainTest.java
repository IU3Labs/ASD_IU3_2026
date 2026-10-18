package ru.bmstu.lab01.task_27;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** Проверяет вычисление суммы квадратов. */
class MainTest {

    @Test
    void shouldSumSquaresOfMixedNumbers() {
        assertEquals("29", run("3\n2 -3 4\n"));
    }

    @Test
    void shouldHandleNegativeNumbers() {
        assertEquals("110", run("3\n-5 -9 -2\n"));
    }

    @Test
    void shouldHandleZeros() {
        assertEquals("0", run("3\n0 0 0\n"));
    }

    @Test
    void shouldSquareBeyondIntegerRange() {
        assertEquals("5000000000", run("2\n50000 -50000\n"));
    }

    @Test
    void shouldSumBeyondLongRange() {
        assertEquals("13835058055282163712",
                run("3\n-2147483648 -2147483648 -2147483648\n"));
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