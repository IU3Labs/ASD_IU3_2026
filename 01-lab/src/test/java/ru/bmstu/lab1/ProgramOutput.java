package ru.bmstu.lab1;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

/**
 * Запускает main так, будто числа ввели с клавиатуры, и забирает напечатанный текст.
 */
public final class ProgramOutput {

    private ProgramOutput() {
    }

    /**
     * Возвращает вывод программы без последнего перевода строки.
     */
    public static String read(Consumer<String[]> mainMethod, String input) {
        InputStream oldInput = System.in;
        PrintStream oldOutput = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        System.setOut(new PrintStream(buffer, true, StandardCharsets.UTF_8));
        try {
            mainMethod.accept(new String[0]);
        } finally {
            System.setIn(oldInput);
            System.setOut(oldOutput);
        }

        String text = buffer.toString(StandardCharsets.UTF_8).replace("\r\n", "\n");
        if (text.endsWith("\n")) {
            text = text.substring(0, text.length() - 1);
        }
        return text;
    }
}
