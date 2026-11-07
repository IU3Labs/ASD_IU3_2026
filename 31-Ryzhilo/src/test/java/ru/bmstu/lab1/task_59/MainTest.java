package ru.bmstu.lab1.task_59;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

/** Граничные и функциональные тесты задачи 59. */
class MainTest {

    @Test
    void shouldReturnOneForIdentityMatrix() {
        assertEquals("1\n", run("1 0 0\n0 1 0\n0 0 1\n"));
    }

    @Test
    void shouldReturnZeroForEqualRows() {
        assertEquals("0\n", run("1 2 3\n1 2 3\n4 5 6\n"));
    }

    @Test
    void shouldComputeNondiagonalDeterminant() {
        assertEquals("1\n", run("1 2 3\n0 1 4\n5 6 0\n"));
    }

    @Test
    void shouldChangeSignAfterRowSwap() {
        assertEquals("-1\n", run("0 1 0\n1 0 0\n0 0 1\n"));
    }

    @Test
    void shouldHandleDeterminantAboveLongRange() {
        assertEquals("9903520300447984150353281023\n", run("2147483647 0 0\n0 2147483647 0\n0 0 2147483647\n"));
    }

    @Test
    void shouldHandleNegativeIntegerLimit() {
        assertEquals("6442450938\n", run("-2147483648 1 0\n2 -1 0\n0 0 3\n"));
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
