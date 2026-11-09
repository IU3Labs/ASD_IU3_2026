/*
 * Задача 20. Сортировка подсчётом.
 * Вход: массив из n целых чисел.
 * Выход: массив, отсортированный по неубыванию, с помощью сортировки подсчётом.
 */
package ru.bmstu.lab1.task_20;

import ru.bmstu.lab1.task_1.MinMax;

/** Сортировка подсчётом. */
public final class CountingSort {

    /** Максимальная разница max - min, иначе массив счётчиков займёт слишком много памяти. */
    public static final int MAX_RANGE = 10_000_000;

    private CountingSort() {}

    /**
     * Сортирует массив подсчётом. Считаем, сколько раз встречается каждое число, потом выписываем
     * числа по возрастанию. Чтобы работали отрицательные числа, индекс счётчика = число - min.
     *
     * @param array исходный массив (не меняется)
     * @return новый отсортированный массив
     */
    public static int[] sort(int[] array) {
        if (array.length == 0) {
            return new int[0];
        }
        int[] minMax = MinMax.findMinMax(array);
        int min = minMax[0];
        long range = (long) minMax[1] - min + 1;
        if (range > MAX_RANGE) {
            throw new IllegalArgumentException("Слишком большой диапазон значений: " + range);
        }
        int[] count = new int[(int) range];
        for (int value : array) {
            count[value - min]++;
        }
        int[] result = new int[array.length];
        int pos = 0;
        for (int i = 0; i < count.length; i++) {
            for (int c = 0; c < count[i]; c++) {
                result[pos++] = i + min;
            }
        }
        return result;
    }
}
