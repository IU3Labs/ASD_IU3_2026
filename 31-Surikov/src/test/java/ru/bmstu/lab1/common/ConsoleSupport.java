package ru.bmstu.lab1.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/** перехват стандартного ввода-вывода для тестов */
public final class ConsoleSupport {
    private ConsoleSupport() {
    }

    /** запускает задачу и возвращает её вывод */
    public static String runMain(int task, String input) throws Exception {
        InputStream originalInput = System.in;
        PrintStream originalOutput = System.out;
        ByteArrayOutputStream captured = new ByteArrayOutputStream();
        try (PrintStream output = new PrintStream(captured, true, StandardCharsets.UTF_8)) {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(output);
            Class<?> entry = Class.forName("ru.bmstu.lab1.task_" + task + ".Main");
            entry.getMethod("main", String[].class).invoke(null, (Object) new String[0]);
        } finally {
            System.setIn(originalInput);
            System.setOut(originalOutput);
        }
        return captured.toString(StandardCharsets.UTF_8).replace("\r\n", "\n");
    }
}
