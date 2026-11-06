package ru.bmstu.lab01.task_03;

public class OccurrenceCounter {

    /**
     * Возвращает количество элементов массива, равных {@code x}.
     *
     * @param array входной массив целых чисел
     * @param x     искомое значение
     * @return количество элементов, равных {@code x}
     */
    public static int countOccurrences(int[] array, int x) {

        int count = 0;
        for (int value : array) {
            if (value == x) {
                count++;
            }
        }

        return count;
    }
}