package ru.bmstu.lab1.task_31;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

/** Граничные и функциональные тесты задачи 31. */
class MainTest {

    @Test
    void shouldHandleMixed() {
        assertEquals("-2\n", run("5\n3 -1 2 0 3\n2147483647 -1 0 3 -2147483648\n"));
    }

    @Test
    void shouldHandleNegative() {
        assertEquals("-10737418237\n", run("4\n-5 -4 -3 -2\n2147483647 -1 0 3\n"));
    }

    @Test
    void shouldHandleEqual() {
        assertEquals("15032385543\n", run("4\n7 7 7 7\n2147483647 -1 0 3\n"));
    }

    @Test
    void shouldHandleZeros() {
        assertEquals("0\n", run("3\n0 0 0\n2147483647 -1 0\n"));
    }

    @Test
    void shouldHandleLimits() {
        assertEquals("-4611686024869838847\n", run("4\n-2147483648 2147483647 0 -2147483648\n2147483647 -1 0 3\n"));
    }

    @Test
    void shouldHandleTwoElements() {
        assertEquals("19327352821\n", run("2\n9 2\n2147483647 -1\n"));
    }

    @Test
    void shouldHandleDotExceedsLong() {
        assertEquals("13835058055282163712\n",
                run("3\n-2147483648 -2147483648 -2147483648\n-2147483648 -2147483648 -2147483648\n"));
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
