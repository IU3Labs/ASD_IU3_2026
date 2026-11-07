package ru.bmstu.lab1.task_56;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

/** Граничные и функциональные тесты задачи 56. */
class MainTest {

    @Test
    void shouldMultiplyRectangularMatrices() {
        assertEquals("-4\n-13\n", run("2 3 1\n1 2 3\n4 5 6\n-2\n-1\n0\n"));
    }

    @Test
    void shouldMultiplyOneRowByTwoColumns() {
        assertEquals("-18 -9\n", run("1 1 2\n9\n-2 -1\n"));
    }

    @Test
    void shouldMultiplyNegativeValues() {
        assertEquals("8 3 -2\n6 1 -4\n", run("2 2 3\n-3 -2\n-1 -4\n-2 -1 0\n-1 0 1\n"));
    }

    @Test
    void shouldMultiplyRepeatedValues() {
        assertEquals("-21\n-21\n", run("2 2 1\n7 7\n7 7\n-2\n-1\n"));
    }

    @Test
    void shouldHandleIntegerLimits() {
        assertEquals("-2147483646 -2147483647\n-2147483646 -2147483647\n",
                run("2 2 2\n2147483647 -2147483648\n2147483647 -2147483648\n-2 -1\n-1 0\n"));
    }

    @Test
    void shouldMultiplyColumnByRow() {
        assertEquals("-2 -1 0\n-4 -2 0\n-6 -3 0\n", run("3 1 3\n1\n2\n3\n-2 -1 0\n"));
    }

    @Test
    void shouldHandleProductExceedsLong() {
        assertEquals("13835058055282163712\n",
                run("1 3 1\n-2147483648 -2147483648 -2147483648\n-2147483648\n-2147483648\n-2147483648\n"));
    }

    @Test
    void shouldHandleIdentityMatrix() {
        assertEquals("3 -2\n7 4\n", run("2 2 2\n3 -2\n7 4\n1 0\n0 1\n"));
    }

    @Test
    void shouldHandleZeroMatrix() {
        assertEquals("0 0\n0 0\n", run("2 2 2\n3 -2\n7 4\n0 0\n0 0\n"));
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
