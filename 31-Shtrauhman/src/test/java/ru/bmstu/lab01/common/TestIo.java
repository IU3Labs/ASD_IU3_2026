package ru.bmstu.lab01.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.function.BiConsumer;

/**
 * Помощник для тестов: подаёт строку на вход решения и возвращает всё, что оно напечатало.
 */
public final class TestIo {

    private TestIo() {
    }

    /**
     * Запускает решение (например, Task25::run) на заданном вводе.
     * Переводы строк в результате приводятся к \n, чтобы тесты не зависели от ОС.
     */
    public static String run(BiConsumer<InputStream, PrintStream> task, String input) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(buffer, true, StandardCharsets.UTF_8);
        task.accept(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)), out);
        out.flush();
        return buffer.toString(StandardCharsets.UTF_8).replace("\r\n", "\n");
    }
}
