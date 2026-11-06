package ru.bmstu.lab01.task_09;

public class SortedChecker {

    /**
     * Проверяет, является ли массив неубывающим.
     *
     * @param array входной массив целых чисел
     * @return {@code true}, если массив неубывающий, иначе {@code false}
     */
    public static boolean isNonDecreasing(int[] array) {

        for (int i = 1; i < array.length; i++) {
            if (array[i - 1] > array[i]) {
                return false;
            }
        }

        return true;
    }
}