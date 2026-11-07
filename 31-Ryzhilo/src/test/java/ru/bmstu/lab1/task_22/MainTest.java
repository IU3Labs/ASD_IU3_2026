package ru.bmstu.lab1.task_22;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

/** Проверки задачи 22: основной результат и граничные случаи. */
class MainTest {

    @Test
    void shouldSortByDecreasingFrequency() {
        assertEquals("4 4 4 2 2 3 3 1\n",
                run("8\n4 2 4 3 2 4 1 3\n"));
    }

    @Test
    void shouldSortTiesByValue() {
        assertEquals("-1 -1 2 2 3 3\n",
                run("6\n3 -1 2 3 -1 2\n"));
    }

    @Test
    void shouldKeepEqualValues() {
        assertEquals("7 7 7 7\n",
                run("4\n7 7 7 7\n"));
    }

    @Test
    void shouldSortDistinctValues() {
        assertEquals("1 2 3 4 5\n",
                run("5\n5 1 4 2 3\n"));
    }

    @Test
    void shouldHandleZerosAndNegativeValues() {
        assertEquals("-2 -2 -2 0 0 0 5\n",
                run("7\n0 -2 0 -2 -2 5 0\n"));
    }

    @Test
    void shouldHandleIntegerLimits() {
        assertEquals("-2147483648 -2147483648 2147483647 2147483647\n",
                run("4\n2147483647 -2147483648 2147483647 -2147483648\n"));
    }

    @Test
    void shouldSortTwoValues() {
        assertEquals("2 9\n",
                run("2\n9 2\n"));
    }

    @Test
    void shouldReadAndWriteThroughMain() {
        String inputText = "8\n4 2 4 3 2 4 1 3\n";
        String expected = run(inputText);
        InputStream originalInput = System.in;
        PrintStream originalOutput = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        // Возвращаем стандартные потоки даже при ошибке, чтобы не повлиять на другие тесты.
        try (PrintStream output = new PrintStream(buffer, true, StandardCharsets.UTF_8)) {
            System.setIn(new ByteArrayInputStream(inputText.getBytes(StandardCharsets.UTF_8)));
            System.setOut(output);
            Main.main(new String[0]);
        } finally {
            System.setIn(originalInput);
            System.setOut(originalOutput);
        }

        assertEquals(expected, buffer.toString(StandardCharsets.UTF_8).replace("\r\n", "\n"));
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
