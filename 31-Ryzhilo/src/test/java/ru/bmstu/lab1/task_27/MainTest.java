package ru.bmstu.lab1.task_27;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

/** Тесты вычисления суммы квадратов элементов массива. */
class MainTest {

    @Test
    void shouldSumSquaresOfPositiveNumbers() {
        // 1 + 4 + 9 + 16 = 30.
        assertEquals("30\n", run("4\n1 2 3 4\n"));
    }

    @Test
    void shouldSumSquaresOfNegativeNumbers() {
        assertEquals("29\n", run("3\n-2 -3 -4\n"));
    }

    @Test
    void shouldHandleMixedSignsAndZero() {
        // 9 + 0 + 16 + 1 = 26.
        assertEquals("26\n", run("4\n-3 0 4 -1\n"));
    }

    @Test
    void shouldHandleOnlyZeros() {
        assertEquals("0\n", run("3\n0 0 0\n"));
    }

    @Test
    void shouldHandleRepeatedValues() {
        assertEquals("100\n", run("4\n5 5 5 5\n"));
    }

    @Test
    void shouldAvoidIntegerOverflowWhenSquaring() {
        // Квадрат 50000 уже превышает максимальное значение int.
        assertEquals("5000000000\n", run("2\n50000 -50000\n"));
    }

    @Test
    void shouldHandleIntegerMaximum() {
        assertEquals(
                "4611686014132420609\n",
                run("2\n2147483647 0\n")
        );
    }

    @Test
    void shouldHandleIntegerMinimum() {
        assertEquals(
                "4611686018427387904\n",
                run("2\n-2147483648 0\n")
        );
    }

    @Test
    void shouldHandleSumExceedingLongMaximum() {
        // Сумма равна 2^63 и уже не помещается в long.
        assertEquals(
                "9223372036854775808\n",
                run("2\n-2147483648 -2147483648\n")
        );
    }

    @Test
    void shouldAcceptNumbersOnSeparateLines() {
        assertEquals("25\n", run("2\n3\n4\n"));
    }

    @Test
    void shouldHandleMaximumAllowedSize() {
        int size = 99_999;
        StringBuilder input = new StringBuilder();
        input.append(size).append('\n');

        for (int i = 0; i < size; i++) {
            input.append("1 ");
        }

        assertEquals("99999\n", run(input.toString()));
    }

    private static String run(String inputText) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        try (Scanner input = new Scanner(inputText);
                PrintStream output = new PrintStream(buffer, true, StandardCharsets.UTF_8)) {
            Main.solve(input, output);
        }

        return buffer.toString(StandardCharsets.UTF_8).replace("\r\n", "\n");
    }

    @Test
    void shouldReadAndWriteThroughMain() {
        InputStream savedInput = System.in;
        PrintStream savedOutput = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        try (PrintStream output = new PrintStream(buffer, true, StandardCharsets.UTF_8)) {
            System.setIn(new ByteArrayInputStream("5\n3 -1 2 0 3\n".getBytes(StandardCharsets.UTF_8)));
            System.setOut(output);
            Main.main(new String[0]);
        } finally {
            // Потоки возвращаются даже при ошибке, чтобы не влиять на другие тесты.
            System.setIn(savedInput);
            System.setOut(savedOutput);
        }
        assertEquals("23\n", buffer.toString(StandardCharsets.UTF_8).replace("\r\n", "\n"));
    }
}
