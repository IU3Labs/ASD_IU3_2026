package ru.bmstu.lab1.task_21;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

/** Тесты поиска k-го наименьшего элемента. */
class MainTest {

    @Test
    void shouldFindMiddleElement() {
        assertEquals("5\n", run("5\n9 1 5 3 7\n3\n"));
    }

    @Test
    void shouldFindMinimumWhenKIsOne() {
        assertEquals("-4\n", run("4\n8 -4 2 0\n1\n"));
    }

    @Test
    void shouldFindMaximumWhenKEqualsSize() {
        assertEquals("8\n", run("4\n8 -4 2 0\n4\n"));
    }

    @Test
    void shouldCountRepeatedValuesAsSeparateElements() {
        assertEquals("2\n", run("5\n4 2 2 1 9\n3\n"));
    }

    @Test
    void shouldHandleIntegerLimits() {
        assertEquals("-2147483648\n", run("2\n2147483647 -2147483648\n1\n"));
    }

    @Test
    void shouldHandleEqualElements() {
        assertEquals("7\n", run("4\n7 7 7 7\n2\n"));
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
