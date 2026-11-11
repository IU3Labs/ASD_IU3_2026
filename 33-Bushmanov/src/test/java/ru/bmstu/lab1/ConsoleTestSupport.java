package ru.bmstu.lab1;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Проверка консольных программ с подстановкой ввода.
 */
public final class ConsoleTestSupport {

    private ConsoleTestSupport() {
    }

    /**
     * Запускает программу и проверяет её вывод.
     *
     * @param input входные данные
     * @param expected ожидаемый вывод
     * @param program метод main проверяемой программы
     */
    public static void assertConsoleOutput(
            String input,
            String expected,
            Consumer<String[]> program) {

        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try (PrintStream capturedOut =
                     new PrintStream(output, true, StandardCharsets.UTF_8)) {

            System.setIn(new ByteArrayInputStream(
                    input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(capturedOut);

            program.accept(new String[0]);
            capturedOut.flush();
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }

        String actual = output.toString(StandardCharsets.UTF_8);
        assertEquals(normalize(expected), normalize(actual));
    }

    private static String normalize(String text) {
        return text.replace("\r\n", "\n")
                .replace('\r', '\n')
                .replaceAll("(?m)[\\t ]+$", "")
                .strip();
    }
}