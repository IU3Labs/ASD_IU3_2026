package ru.bmstu.lab1.task_26;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

/** Тесты произведения элементов массива по модулю. */
class MainTest {

    @Test
    void shouldMultiplyPositiveNumbers() {
        assertEquals("24\n", run("3\n2 3 4\n"));
    }

    @Test
    void shouldReturnZeroWhenArrayContainsZero() {
        assertEquals("0\n", run("4\n5 0 -3 7\n"));
    }

    @Test
    void shouldHandleOnes() {
        assertEquals("1\n", run("4\n1 1 1 1\n"));
    }

    @Test
    void shouldNormalizeNegativeProduct() {
        // -6 по модулю 1_000_000_007 равно 1_000_000_001.
        assertEquals("1000000001\n", run("2\n-2 3\n"));
    }

    @Test
    void shouldHandleTwoNegativeNumbers() {
        assertEquals("6\n", run("2\n-2 -3\n"));
    }

    @Test
    void shouldReturnZeroForMultipleOfModulus() {
        assertEquals("0\n", run("2\n1000000007 5\n"));
    }

    @Test
    void shouldHandleNegativeMultipleOfModulus() {
        assertEquals("0\n", run("2\n-1000000007 5\n"));
    }

    @Test
    void shouldReduceValuesGreaterThanModulus() {
        // Остатки равны 1 и 2.
        assertEquals("2\n", run("2\n1000000008 1000000009\n"));
    }

    @Test
    void shouldAvoidOverflowDuringRepeatedMultiplication() {
        // Каждый множитель сравним с -1; произведение четырёх равно 1.
        assertEquals(
                "1\n",
                run("4\n1000000006 1000000006 1000000006 1000000006\n")
        );
    }

    @Test
    void shouldHandleIntegerMinimum() {
        // -2_147_483_648 + 3 * 1_000_000_007 = 852_516_373.
        assertEquals("852516373\n", run("2\n-2147483648 1\n"));
    }

    @Test
    void shouldHandleIntegerMaximum() {
        assertEquals("147483633\n", run("2\n2147483647 1\n"));
    }

    @Test
    void shouldAcceptNumbersOnSeparateLines() {
        assertEquals("120\n", run("3\n4\n5\n6\n"));
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
        assertEquals("0\n", buffer.toString(StandardCharsets.UTF_8).replace("\r\n", "\n"));
    }
}
