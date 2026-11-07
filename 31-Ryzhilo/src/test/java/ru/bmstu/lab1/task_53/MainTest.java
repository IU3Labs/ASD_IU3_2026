package ru.bmstu.lab1.task_53;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

/** Граничные и функциональные тесты задачи 53. */
class MainTest {

    @Test
    void shouldFindPointInRectangularMatrix() {
        assertEquals("(1, 0, 4)\n", run("2 3\n1 2 3\n4 5 6\n"));
    }

    @Test
    void shouldFindPointInSingleCell() {
        assertEquals("(0, 0, 9)\n", run("1 1\n9\n"));
    }

    @Test
    void shouldFindNoPointInNegativeMatrix() {
        assertEquals("", run("2 2\n-3 -2\n-1 -4\n"));
    }

    @Test
    void shouldReturnEveryCellWhenValuesAreEqual() {
        assertEquals("(0, 0, 7)\n(0, 1, 7)\n(1, 0, 7)\n(1, 1, 7)\n", run("2 2\n7 7\n7 7\n"));
    }

    @Test
    void shouldHandleIntegerLimits() {
        assertEquals("(0, 1, -2147483648)\n(1, 1, -2147483648)\n",
                run("2 2\n2147483647 -2147483648\n2147483647 -2147483648\n"));
    }

    @Test
    void shouldFindMaximumInSingleColumn() {
        assertEquals("(2, 0, 3)\n", run("3 1\n1\n2\n3\n"));
    }

    @Test
    void shouldHandleNoSaddle() {
        assertEquals("", run("2 2\n1 3\n4 2\n"));
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
