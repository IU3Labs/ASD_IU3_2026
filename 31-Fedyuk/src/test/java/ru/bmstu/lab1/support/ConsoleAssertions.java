package ru.bmstu.lab1.support;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

/** Проверяет консольные программы, восстанавливая стандартные потоки после каждого вызова. */
public final class ConsoleAssertions {
    private ConsoleAssertions() {}

    /** Сравнивает подсказки и результат программы без дополнительного параметра. */
    public static void assertResult(String input, String expected, Consumer<String[]> program) {
        assertResult(input, expected, "", program);
    }

    /** Сравнивает все подсказки и результат программы с дополнительным параметром. */
    public static void assertResult(
            String input, String expected, String parameterPrompt, Consumer<String[]> program) {
        String prompts = "Введите длину массива\nВведите массив\n";
        if (!parameterPrompt.isEmpty()) {
            prompts += parameterPrompt + "\n";
        }
        assertOutput(input, prompts + expected, program);
    }

    /** Запускает main с заданным вводом и сравнивает весь вывод, включая пустые строки. */
    public static void assertOutput(String input, String expected, Consumer<String[]> program) {
        InputStream originalInput = System.in;
        PrintStream originalOutput = System.out;
        ByteArrayOutputStream captured = new ByteArrayOutputStream();
        try (PrintStream output = new PrintStream(captured, true, StandardCharsets.UTF_8)) {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(output);
            program.accept(new String[0]);
        } finally {
            System.setIn(originalInput);
            System.setOut(originalOutput);
        }
        String actual = captured.toString(StandardCharsets.UTF_8).replace("\r\n", "\n");
        assertEquals(expected, actual);
    }
}
