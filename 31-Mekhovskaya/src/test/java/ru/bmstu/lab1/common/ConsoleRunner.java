package ru.bmstu.lab1.common;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;
import java.util.function.BiConsumer;

/**
 * Вспомогательный класс для тестов: запускает метод {@code run(Scanner, PrintStream)} задачи
 * на заданной строке ввода и возвращает напечатанные строки.
 */
public final class ConsoleRunner {

    private ConsoleRunner() {
    }

    /**
     * Запускает программу на входных данных и возвращает строки вывода.
     *
     * @param program метод run задачи
     * @param input   входные данные, как если бы их ввели с клавиатуры
     * @return строки, напечатанные программой
     */
    public static List<String> execute(BiConsumer<Scanner, PrintStream> program, String input) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try (PrintStream out = new PrintStream(buffer, true, StandardCharsets.UTF_8)) {
            program.accept(new Scanner(input), out);
        }
        return buffer.toString(StandardCharsets.UTF_8).lines().toList();
    }
}
