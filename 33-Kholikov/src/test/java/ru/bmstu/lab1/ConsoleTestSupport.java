package ru.bmstu.lab1;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/** Запускает консольную программу и всегда восстанавливает стандартные потоки. */
public final class ConsoleTestSupport {
    private ConsoleTestSupport() {
    }

    /** Возвращает вывод программы для заданного текстового ввода. */
    public static String run(String input, Runnable program) {
        InputStream previousInput = System.in;
        PrintStream previousOutput = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try (PrintStream output = new PrintStream(buffer, true, StandardCharsets.UTF_8)) {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(output);
            program.run();
        } finally {
            System.setIn(previousInput);
            System.setOut(previousOutput);
        }
        return buffer.toString(StandardCharsets.UTF_8).replace("\r\n", "\n");
    }
}
