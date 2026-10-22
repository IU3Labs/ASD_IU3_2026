/*
 * Задача 22. Сортировка по частоте.
 * Вход: массив из n целых чисел.
 * Выход: массив, отсортированный по убыванию частоты встречаемости элементов.
 * При равных частотах — по возрастанию значения.
 */
package ru.bmstu.lab01.task_22;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import ru.bmstu.lab01.common.ComparatorSort;
import ru.bmstu.lab01.common.InputReader;
import ru.bmstu.lab01.common.OutputFormatter;

/**
 * Сортировка массива по убыванию частоты элементов, при равной частоте — по возрастанию значения.
 *
 * <p>Формат ввода: {@code n}, затем {@code n} чисел. Вывод: отсортированный массив в одну строку
 * (одинаковые элементы идут подряд). Время O(n log n), дополнительная память O(n).
 */
public final class FrequencySort {

    private FrequencySort() {
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
     * Возвращает новый массив, отсортированный по частоте; исходный массив не меняется.
     *
     * @param array исходный массив
     * @return отсортированная копия
     */
    public static int[] sort(int[] array) {
        // HashMap даёт подсчёт частот за O(n) в среднем; значения могут быть любыми int,
        // поэтому массив-счётчик (как в сортировке подсчётом) здесь не подходит.
        Map<Integer, Integer> frequency = countFrequencies(array);
        Comparator<Integer> order = Comparator.<Integer>comparingInt(frequency::get).reversed()
                .thenComparingInt(Integer::intValue);
        return ComparatorSort.sorted(array, order);
    }

    private static Map<Integer, Integer> countFrequencies(int[] array) {
        Map<Integer, Integer> frequency = new HashMap<>();
        for (int value : array) {
            frequency.merge(value, 1, Integer::sum);
        }
        return frequency;
    }
}
