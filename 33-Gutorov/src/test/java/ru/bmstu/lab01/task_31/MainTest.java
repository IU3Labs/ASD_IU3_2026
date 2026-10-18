package ru.bmstu.lab01.task_31;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** Проверяет скалярное произведение двух массивов. */
class MainTest {

    @Test
    void shouldMultiplyCorrespondingElements() {
        assertEquals("32", run("3\n1 2 3\n4 5 6\n"));
    }

    @Test
    void shouldHandleMixedSigns() {
        assertEquals("4", run("3\n-1 2 -3\n4 -5 -6\n"));
    }

    @Test
    void shouldHandleZeroArray() {
        assertEquals("0", run("3\n0 0 0\n4 -5 6\n"));
    }

    @Test
    void shouldMultiplyBeyondIntegerRange() {
        assertEquals("5000000000",
                run("2\n50000 50000\n50000 50000\n"));
    }

    @Test
    void shouldSumBeyondLongRange() {
        assertEquals("13835058055282163712",
                run("3\n-2147483648 -2147483648 -2147483648\n"
                        + "-2147483648 -2147483648 -2147483648\n"));
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