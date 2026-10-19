package ru.bmstu.lab1.common;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.function.BiConsumer;

/** Запуск метода Main.run на заданном вводе, чтобы проверить вывод в тестах. */
public final class MainRunner {

    private MainRunner() {}

    /**
     * Подаёт строку на вход и возвращает то, что программа напечатала.
     *
     * @param main метод Main::run нужной задачи
     * @param input входные данные
     * @return вывод без пробелов по краям, переводы строк - \n
     */
    public static String run(BiConsumer<Scanner, PrintStream> main, String input) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        main.accept(new Scanner(input), new PrintStream(output));
        return output.toString().replace("\r\n", "\n").trim();
    }
}
