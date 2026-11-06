package ru.bmstu.lab1.task_33;

import ru.bmstu.lab1.common.ConsoleIo;

/**
 * Задача 33. Количество инверсий.
 *
 * <p>Вход: {@code n}, затем {@code n} целых чисел. Выход: число пар {@code i < j} с {@code a[i] > a[j]}.
 *
 * <p>Модификация сортировки слиянием: при слиянии отсортированных половин, когда элемент правой половины
 * {@code right[j]} оказывается меньше {@code left[i]}, он меньше и всех оставшихся элементов левой половины,
 * то есть образует {@code mid - i} инверсий сразу. O(n log n) по времени, O(n) доп. памяти.
 * Ответ до {@code n(n-1)/2 ≈ 5·10^9}, поэтому хранится в {@code long}.
 */
public final class Inversions {

    private Inversions() {
    }

    /**
     * Считает количество инверсий. Исходный массив не изменяется.
     *
     * @param values массив
     * @return количество инверсий
     */
    public static long count(int[] values) {
        int[] work = values.clone();
        int[] buffer = new int[values.length];
        return sortAndCount(work, buffer, 0, values.length);
    }

    /** Сортирует {@code values[from, to)} и возвращает число инверсий внутри этого отрезка. */
    private static long sortAndCount(int[] values, int[] buffer, int from, int to) {
        if (to - from < 2) {
            return 0;
        }
        int mid = (from + to) >>> 1;
        long inversions = sortAndCount(values, buffer, from, mid) + sortAndCount(values, buffer, mid, to);
        return inversions + mergeAndCount(values, buffer, from, mid, to);
    }

    private static long mergeAndCount(int[] values, int[] buffer, int from, int mid, int to) {
        long inversions = 0;
        int i = from;
        int j = mid;
        int k = from;
        while (i < mid && j < to) {
            if (values[i] <= values[j]) {
                buffer[k++] = values[i++];
            } else {
                inversions += mid - i;
                buffer[k++] = values[j++];
            }
        }
        while (i < mid) {
            buffer[k++] = values[i++];
        }
        while (j < to) {
            buffer[k++] = values[j++];
        }
        System.arraycopy(buffer, from, values, from, to - from);
        return inversions;
    }

    /**
     * Точка входа: читает массив, печатает число инверсий.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        ConsoleIo.run((in, out) -> out.println(count(in.nextIntArray(in.nextInt()))));
    }
}
