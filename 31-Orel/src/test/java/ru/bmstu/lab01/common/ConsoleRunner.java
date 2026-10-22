package ru.bmstu.lab01.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.function.BiConsumer;

/**
 * Запуск консольной части решения на заданном вводе с перехватом вывода.
 */
public final class ConsoleRunner {

    private ConsoleRunner() {
    }

    /**
     * Передаёт решению текст как стандартный ввод и возвращает напечатанное.
     *
     * @param solution метод {@code run(InputStream, PrintStream)} решения
     * @param input    содержимое стандартного ввода
     * @return вывод без завершающих пробелов, с переводами строк {@code \n}
     */
    public static String run(BiConsumer<InputStream, PrintStream> solution, String input) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try (PrintStream output = new PrintStream(buffer, true, StandardCharsets.UTF_8)) {
            solution.accept(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)), output);
        }
        return buffer.toString(StandardCharsets.UTF_8).replace(System.lineSeparator(), "\n").strip();
    }
}
