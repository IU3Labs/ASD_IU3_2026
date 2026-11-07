package ru.bmstu.lab1.task_38;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

/** Граничные и функциональные тесты задачи 38. */
class MainTest {

    @Test
    void shouldHandleMixed() {
        assertEquals("3 -1 2 0 3\n", run("5\n3 -1 2 0 3\n0\n"));
    }

    @Test
    void shouldHandleNegative() {
        assertEquals("-4 -3 -2 -5\n", run("4\n-5 -4 -3 -2\n1\n"));
    }

    @Test
    void shouldHandleEqual() {
        assertEquals("7 7 7 7\n", run("4\n7 7 7 7\n4\n"));
    }

    @Test
    void shouldHandleZeros() {
        assertEquals("0 0 0\n", run("3\n0 0 0\n5\n"));
    }

    @Test
    void shouldHandleLimits() {
        assertEquals("-2147483648 -2147483648 2147483647 0\n",
                run("4\n-2147483648 2147483647 0 -2147483648\n9223372036854775807\n"));
    }

    @Test
    void shouldHandleTwoElements() {
        assertEquals("2 9\n", run("2\n9 2\n-1\n"));
    }

    private static String run(String inputText) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try (Scanner input = new Scanner(inputText);
                PrintStream output = new PrintStream(buffer, true, StandardCharsets.UTF_8)) {
            Main.solve(input, output);
        }
        return buffer.toString(StandardCharsets.UTF_8).replace("\r\n", "\n");
    }
}
