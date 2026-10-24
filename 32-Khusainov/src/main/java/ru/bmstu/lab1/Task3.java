package ru.bmstu.lab1;

/**
 * Решение задачи 3: подсчёт количества вхождений элемента в массив.
 */
public class Task3 {

    /**
     * Подсчитывает количество элементов массива, равных заданному значению.
     *
     * @param array входной массив целых чисел
     * @param x     искомое значение
     * @return количество элементов, равных {@code x}
     */
    public static int countOccurrences(int[] array, int x) {
        int count = 0;

        for (int i = 0; i < array.length; i++) {
            if (array[i] == x) {
                count++;
            }
        }

        return count;
    }
}