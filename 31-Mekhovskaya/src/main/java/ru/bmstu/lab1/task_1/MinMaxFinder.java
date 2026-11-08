package ru.bmstu.lab1.task_1;

import java.io.PrintStream;
import java.util.Scanner;
import ru.bmstu.lab1.common.InputReader;
import ru.bmstu.lab1.common.Validation;

/**
 * Задача 1. Максимум и минимум.
 *
 * <p>Вход: массив из {@code n} целых чисел, {@code 1 < n < 10^5}.
 * Выход: минимальный и максимальный элементы массива.
 *
 * <p>Идея: один линейный проход, на каждом элементе обновляются текущие минимум и максимум.
 * Дополнительная память не нужна, кроме двух переменных.
 */
public final class MinMaxFinder {

    private MinMaxFinder() {
    }

    /**
     * Находит минимальный и максимальный элементы массива.
     *
     * @param array исходный массив
     * @return пара (минимум, максимум)
     * @throws IllegalArgumentException если массив пустой или {@code null}
     */
    public static MinMax findMinMax(int[] array) {
        Validation.requireNonEmpty(array);
        // Начинаем с первого элемента, а не с Integer.MAX_VALUE/MIN_VALUE, —
        // так результат всегда является настоящим элементом массива.
        int min = array[0];
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            min = Math.min(min, array[i]);
            max = Math.max(max, array[i]);
        }
        return new MinMax(min, max);
    }

    /**
     * Читает массив из {@code in} и печатает в {@code out} строку «минимум максимум».
     *
     * @param in  источник данных
     * @param out поток вывода
     */
    public static void run(Scanner in, PrintStream out) {
        MinMax result = findMinMax(InputReader.readArray(in));
        out.println(result.min() + " " + result.max());
    }

    /**
     * Точка входа: ввод с клавиатуры, вывод в консоль.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        run(new Scanner(System.in), System.out);
    }
}
