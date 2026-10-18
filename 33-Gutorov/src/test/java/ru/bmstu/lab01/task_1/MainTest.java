package ru.bmstu.lab01.task_1;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** Проверяем задание 1 */
class MainTest {

    @Test
    void shouldFindMinAndMaxInMixedNumbers() {
        assertEquals("-2 10", run("5\n7 -2 10 0 4\n"));
    }

    @Test
    void shouldHandlePositiveNumbers() {
        assertEquals("3 9", run("3\n5 9 3\n"));
    }

    @Test
    void shouldHandleNegativeNumbers() {
        assertEquals("-9 -2", run("3\n-5 -9 -2\n"));
    }

    @Test
    void shouldHandleEqualNumbers() {
        assertEquals("4 4", run("3\n4 4 4\n"));
    }

    @Test
    void shouldHandleTwoNumbersAndIntegerLimits() {
        assertEquals("-2147483648 2147483647",
                run("2\n-2147483648 2147483647\n"));
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