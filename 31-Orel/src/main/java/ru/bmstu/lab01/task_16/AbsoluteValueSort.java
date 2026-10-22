/*
 * Задача 16. Сортировка по модулю.
 * Вход: массив из n целых чисел.
 * Выход: массив, отсортированный по неубыванию модуля элементов.
 * При равных модулях первым идёт отрицательный элемент.
 */
package ru.bmstu.lab01.task_16;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Comparator;

import ru.bmstu.lab01.common.ComparatorSort;
import ru.bmstu.lab01.common.InputReader;
import ru.bmstu.lab01.common.OutputFormatter;

/**
 * Сортировка массива по модулю элементов, при равенстве модулей — отрицательный элемент раньше.
 *
 * <p>Формат ввода: {@code n}, затем {@code n} чисел. Вывод: отсортированный массив в одну строку.
 * Время O(n log n), дополнительная память O(n).
 */
public final class AbsoluteValueSort {

    /*
     * Модуль считается в long: Math.abs(Integer.MIN_VALUE) в int переполняется и остаётся отрицательным.
     * Второй ключ — само значение: среди -x и x меньше -x, поэтому отрицательный элемент идёт первым.
     */
    private static final Comparator<Integer> BY_ABSOLUTE_THEN_VALUE =
            Comparator.<Integer>comparingLong(value -> Math.abs((long) value))
                    .thenComparingInt(Integer::intValue);

    private AbsoluteValueSort() {
    }

    /**
     * Точка входа: читает массив из стандартного ввода и печатает результат.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        run(System.in, System.out);
    }

    static void run(InputStream input, PrintStream output) {
        int[] array = new InputReader(input).nextArray();
        output.println(OutputFormatter.join(sort(array)));
    }

    /**
     * Возвращает новый массив, отсортированный по неубыванию модуля; исходный массив не меняется.
     *
     * @param array исходный массив
     * @return отсортированная копия
     */
    public static int[] sort(int[] array) {
        return ComparatorSort.sorted(array, BY_ABSOLUTE_THEN_VALUE);
    }
}
