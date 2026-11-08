package ru.bmstu.lab1.task_46;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;
import ru.bmstu.lab1.common.InputReader;
import ru.bmstu.lab1.common.OutputWriter;
import ru.bmstu.lab1.common.Validation;

/**
 * Задача 46. Сжатие диапазона.
 *
 * <p>Вход: массив из {@code n} целых чисел.
 * Выход: массив той же длины, где каждый элемент заменён его рангом — номером в отсортированном
 * порядке без дубликатов (начиная с 0). Пример: {@code [100, 50, 100, 300] -> [1, 0, 1, 2]}.
 *
 * <p>Идея: отсортировать копию массива и удалить из неё повторы — получится упорядоченный список
 * различных значений. Ранг элемента равен его позиции в этом списке, которую находим бинарным
 * поиском. Отсортированный массив выбран вместо {@code TreeMap}: он компактнее по памяти
 * и даёт тот же O(log n) на поиск.
 */
public final class RangeCompressor {

    private RangeCompressor() {
    }

    /**
     * Заменяет каждый элемент его рангом среди различных значений массива.
     *
     * @param array исходный массив (не изменяется)
     * @return новый массив рангов той же длины
     * @throws IllegalArgumentException если массив пустой или {@code null}
     */
    public static int[] compress(int[] array) {
        Validation.requireNonEmpty(array);
        int[] sorted = array.clone();
        Arrays.sort(sorted);
        int uniqueCount = removeDuplicates(sorted);

        int[] ranks = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            // Элемент гарантированно есть в sorted, поэтому binarySearch вернёт его индекс (>= 0).
            ranks[i] = Arrays.binarySearch(sorted, 0, uniqueCount, array[i]);
        }
        return ranks;
    }

    /**
     * Сдвигает различные значения отсортированного массива в его начало.
     *
     * @return количество различных значений
     */
    private static int removeDuplicates(int[] sorted) {
        int uniqueCount = 1;
        for (int i = 1; i < sorted.length; i++) {
            if (sorted[i] != sorted[uniqueCount - 1]) {
                sorted[uniqueCount] = sorted[i];
                uniqueCount++;
            }
        }
        return uniqueCount;
    }

    /**
     * Читает массив и печатает массив рангов.
     *
     * @param in  источник данных
     * @param out поток вывода
     */
    public static void run(Scanner in, PrintStream out) {
        OutputWriter.printArray(out, compress(InputReader.readArray(in)));
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
