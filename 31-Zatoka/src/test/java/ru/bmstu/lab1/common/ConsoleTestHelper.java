package ru.bmstu.lab1.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

/**
 * Помощник для тестов консольных программ: подставляет текст вместо клавиатуры
 * и перехватывает то, что программа напечатала.
 */
public class ConsoleTestHelper {

    private ConsoleTestHelper() {
    }

    /**
     * Запускает программу с заданным вводом и возвращает её вывод.
     *
     * @param input текст, который программа прочитает из System.in
     * @param program запуск программы, например {@code () -> Main.main(new String[0])}
     * @return напечатанный текст без пробелов и переводов строки по краям
     */
    public static String run(String input, Runnable program) {
        InputStream oldIn = System.in;
        PrintStream oldOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream(input.getBytes()));
            System.setOut(new PrintStream(output));
            program.run();
        } finally {
            // возвращаем стандартные потоки, чтобы не сломать другие тесты
            System.setIn(oldIn);
            System.setOut(oldOut);
        }
        return output.toString().trim();
    }
}
