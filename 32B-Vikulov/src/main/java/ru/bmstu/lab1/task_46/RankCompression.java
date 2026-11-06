package ru.bmstu.lab1.task_46;

import java.util.Arrays;
import ru.bmstu.lab1.common.ConsoleIo;

/**
 * Задача 46. Сжатие диапазона.
 *
 * <p>Вход: {@code n}, затем {@code n} чисел. Выход: массив рангов — номер каждого элемента среди
 * отсортированных различных значений (с нуля). Пример: {@code [100, 50, 100, 300] → [1, 0, 1, 2]}.
 *
 * <p>Шаги: 1) копия массива сортируется — O(n log n); 2) из неё удаляются повторы — O(n);
 * 3) ранг каждого элемента находится бинарным поиском в массиве уникальных значений — O(n log n).
 * Итог O(n log n) по времени, O(n) доп. памяти. Хеш-таблицу не используем: отсортированный массив
 * компактнее и даёт гарантированную, а не среднюю оценку.
 */
public final class RankCompression {

    private RankCompression() {
    }

    /**
     * Заменяет каждый элемент его рангом.
     *
     * @param values исходный массив (не изменяется)
     * @return новый массив рангов
     */
    public static int[] compress(int[] values) {
        int[] unique = sortedUnique(values);
        int[] ranks = new int[values.length];
        for (int i = 0; i < values.length; i++) {
            // элемент гарантированно есть в unique, поэтому индекс неотрицателен
            ranks[i] = Arrays.binarySearch(unique, values[i]);
        }
        return ranks;
    }

    private static int[] sortedUnique(int[] values) {
        int[] sorted = values.clone();
        Arrays.sort(sorted);
        int size = 0;
        for (int i = 0; i < sorted.length; i++) {
            if (i == 0 || sorted[i] != sorted[i - 1]) {
                sorted[size++] = sorted[i];
            }
        }
        return Arrays.copyOf(sorted, size);
    }

    /**
     * Точка входа: читает массив, печатает ранги.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        ConsoleIo.run((in, out) -> out.println(ConsoleIo.join(compress(in.nextIntArray(in.nextInt())))));
    }
}
