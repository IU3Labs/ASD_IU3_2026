package ru.bmstu.lab1.task_13;

import ru.bmstu.lab1.common.ConsoleIo;

/**
 * Задача 13. Сортировка выбором.
 *
 * <p>Вход: {@code n}, затем {@code n} целых чисел. Выход: массив по неубыванию.
 * На шаге {@code i} минимум суффикса {@code [i, n)} ставится на позицию {@code i}.
 * O(n^2) сравнений, не более {@code n - 1} обменов, O(1) доп. памяти.
 */
public final class SelectionSort {

    private SelectionSort() {
    }

    /**
     * Сортирует массив по неубыванию на месте.
     *
     * @param values сортируемый массив
     */
    public static void sort(int[] values) {
        for (int i = 0; i < values.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < values.length; j++) {
                if (values[j] < values[minIndex]) {
                    minIndex = j;
                }
            }
            int tmp = values[i];
            values[i] = values[minIndex];
            values[minIndex] = tmp;
        }
    }

    /**
     * Точка входа: читает массив, печатает его отсортированным.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        ConsoleIo.run((in, out) -> {
            int[] values = in.nextIntArray(in.nextInt());
            sort(values);
            out.println(ConsoleIo.join(values));
        });
    }
}
