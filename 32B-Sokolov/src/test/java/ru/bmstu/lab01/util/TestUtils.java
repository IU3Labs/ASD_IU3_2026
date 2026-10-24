package ru.bmstu.lab01.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.function.Consumer;

/**
 * Запуск main с подменённым вводом, чтобы проверить вывод программы.
 */
public final class TestUtils {

    private TestUtils() {
    }

    /**
     * Подаёт input на System.in, запускает main и возвращает то, что напечаталось.
     *
     * @param main  метод main задачи
     * @param input входные данные
     * @return вывод программы
     */
    public static String runMain(Consumer<String[]> main, String input) {
        InputStream oldIn = System.in;
        PrintStream oldOut = System.out;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream(input.getBytes()));
            System.setOut(new PrintStream(out));
            main.accept(new String[0]);
        } finally {
            System.setIn(oldIn);
            System.setOut(oldOut);
        }
        return out.toString().replace("\r", "").trim();
    }
}
