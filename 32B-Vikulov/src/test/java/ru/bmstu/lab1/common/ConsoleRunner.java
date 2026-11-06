package ru.bmstu.lab1.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

/**
 * Тестовый помощник: подменяет стандартные потоки, запускает {@code main} и возвращает вывод.
 */
public final class ConsoleRunner {

    private ConsoleRunner() {
    }

    /**
     * Запускает {@code main} с заданным вводом.
     *
     * @param main  ссылка на метод {@code main}
     * @param input текст стандартного ввода
     * @return стандартный вывод без завершающих пробелов
     */
    public static String run(Consumer<String[]> main, String input) {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(buffer, true, StandardCharsets.UTF_8));
            main.accept(new String[0]);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
        return buffer.toString(StandardCharsets.UTF_8).strip().replace("\r\n", "\n");
    }
}
