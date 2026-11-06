package ru.bmstu.lab01.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/**
 * Помощник для тестов: запускает main с заданным вводом и возвращает то, что программа напечатала.
 */
public final class ConsoleHelper {

    private ConsoleHelper() {
    }

    /**
     * Подменяет System.in и System.out, запускает программу и возвращает потоки обратно.
     *
     * @param program вызов main
     * @param input   то, что «вводит пользователь»
     * @return вывод программы без пробелов по краям, переводы строк заменены на \n
     */
    public static String run(Runnable program, String input) {
        InputStream savedIn = System.in;
        PrintStream savedOut = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(buffer, true, StandardCharsets.UTF_8));
            program.run();
        } finally {
            System.setIn(savedIn);
            System.setOut(savedOut);
        }
        return buffer.toString(StandardCharsets.UTF_8).replace("\r\n", "\n").trim();
    }
}
