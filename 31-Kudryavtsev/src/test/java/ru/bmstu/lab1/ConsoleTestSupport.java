package ru.bmstu.lab1;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.function.BiConsumer;

/** Запускает проверку ввода-вывода на данных в памяти, не подменяя системные потоки. */
public final class ConsoleTestSupport {

    private ConsoleTestSupport() {
    }

    /** Возвращает текст, напечатанный решением при заданном вводе. */
    public static String runProgram(BiConsumer<Scanner, PrintStream> solution, String inputText) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try (Scanner input = new Scanner(inputText);
                PrintStream output = new PrintStream(buffer, true, StandardCharsets.UTF_8)) {
            solution.accept(input, output);
        }
        return buffer.toString(StandardCharsets.UTF_8);
    }
}
