package ru.bmstu.lab1.task_20;

import ru.bmstu.lab1.common.ConsoleIo;

/**
 * Задача 20. Сортировка подсчётом.
 *
 * <p>Вход: {@code n}, затем {@code n} целых чисел. Выход: массив по неубыванию.
 * Считаем, сколько раз встречается каждое значение из диапазона {@code [min, max]}, затем выписываем
 * значения по возрастанию. Сдвиг на {@code min} позволяет сортировать и отрицательные числа.
 * O(n + k) по времени и O(k) доп. памяти, где {@code k = max - min + 1}.
 */
public final class CountingSort {

    /**
     * Наибольший допустимый диапазон значений. Массив счётчиков такого размера занимает ~40 МБ;
     * при большем диапазоне сортировка подсчётом теряет смысл и нужна сравнительная сортировка.
     */
    public static final long MAX_RANGE = 10_000_000L;

    private CountingSort() {
    }

    /**
     * Сортирует массив по неубыванию на месте.
     *
     * @param values сортируемый массив
     * @throws IllegalArgumentException если {@code max - min + 1 > MAX_RANGE}
     */
    public static void sort(int[] values) {
        if (values.length == 0) {
            return;
        }
        int min = values[0];
        int max = values[0];
        for (int value : values) {
            min = Math.min(min, value);
            max = Math.max(max, value);
        }
        long range = (long) max - min + 1;
        if (range > MAX_RANGE) {
            throw new IllegalArgumentException("Value range is too wide for counting sort: " + range);
        }
        int[] counts = new int[(int) range];
        for (int value : values) {
            counts[value - min]++;
        }
        int position = 0;
        for (int offset = 0; offset < counts.length; offset++) {
            for (int c = 0; c < counts[offset]; c++) {
                values[position++] = offset + min;
            }
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
