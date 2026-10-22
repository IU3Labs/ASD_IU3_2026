/*
 * Задача 33. Количество инверсий.
 * Вход: массив из n целых чисел.
 * Выход: количество пар (i, j) таких, что i < j и a[i] > a[j].
 * Требование: O(n log n) (через модификацию сортировки слиянием).
 */
package ru.bmstu.lab01.task_33;

import java.io.InputStream;
import java.io.PrintStream;

import ru.bmstu.lab01.common.InputReader;

/**
 * Подсчёт инверсий модифицированной сортировкой слиянием.
 *
 * <p>Формат ввода: {@code n}, затем {@code n} чисел. Вывод: одно число — количество инверсий.
 * Время O(n log n), дополнительная память O(n).
 */
public final class InversionCounter {

    private InversionCounter() {
    }

    /**
     * Точка входа: читает массив из стандартного ввода и печатает количество инверсий.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        run(System.in, System.out);
    }

    static void run(InputStream input, PrintStream output) {
        int[] array = new InputReader(input).nextArray();
        output.println(count(array));
    }

    /**
     * Считает количество инверсий; исходный массив не меняется.
     *
     * @param array исходный массив
     * @return количество инверсий (для n = 10^5 может достигать ~5 * 10^9, поэтому {@code long})
     */
    public static long count(int[] array) {
        int[] work = array.clone();
        int[] buffer = new int[array.length];
        return sortAndCount(work, buffer, 0, array.length);
    }

    /** Сортирует work[from, to) и возвращает число инверсий внутри этого отрезка. */
    private static long sortAndCount(int[] work, int[] buffer, int from, int to) {
        if (to - from < 2) {
            return 0;
        }
        int middle = (from + to) >>> 1;
        long inversions = sortAndCount(work, buffer, from, middle)
                + sortAndCount(work, buffer, middle, to);
        return inversions + mergeAndCount(work, buffer, from, middle, to);
    }

    /**
     * Сливает отсортированные половины [from, middle) и [middle, to).
     * Когда элемент правой половины строго меньше текущего элемента левой, он образует инверсию
     * со всеми оставшимися элементами левой половины — их ровно middle - left.
     */
    private static long mergeAndCount(int[] work, int[] buffer, int from, int middle, int to) {
        long inversions = 0;
        int left = from;
        int right = middle;
        int index = from;
        while (left < middle && right < to) {
            // "<=" — равные элементы инверсию не образуют, левый берём первым
            if (work[left] <= work[right]) {
                buffer[index++] = work[left++];
            } else {
                inversions += middle - left;
                buffer[index++] = work[right++];
            }
        }
        while (left < middle) {
            buffer[index++] = work[left++];
        }
        while (right < to) {
            buffer[index++] = work[right++];
        }
        System.arraycopy(buffer, from, work, from, to - from);
        return inversions;
    }
}
