package ru.bmstu.lab1.common;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.function.BiConsumer;

/**
 * Общая обвязка ввода-вывода через стандартные потоки для всех задач.
 */
public final class ConsoleIo {

    private ConsoleIo() {
    }

    /**
     * Запускает обработчик на {@code System.in} / {@code System.out} и сбрасывает буфер вывода.
     *
     * @param handler логика чтения входа и печати ответа
     */
    public static void run(BiConsumer<InputReader, PrintWriter> handler) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out, false, StandardCharsets.UTF_8);
        handler.accept(in, out);
        out.flush();
    }

    /**
     * Форматирует массив как числа через пробел.
     *
     * @param values массив
     * @return строка вида {@code "1 2 3"}
     */
    public static String join(int[] values) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            if (i > 0) {
                builder.append(' ');
            }
            builder.append(values[i]);
        }
        return builder.toString();
    }
}
