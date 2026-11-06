package ru.bmstu.lab01.task_06;

public class AboveAverageCounter {

    /**
     * Возвращает количество элементов, строго больших среднего
     * арифметического.
     *
     * Сумма накапливается в {@code long}, чтобы избежать переполнения
     * {@code int} при больших значениях элементов.
     *
     * @param array входной массив целых чисел
     * @return количество элементов, строго больших среднего
     */
    public static int countGreaterThanAverage(int[] array) {

        // Первый проход: сумма всех элементов
        long sum = 0L;
        for (int value : array) {
            sum += value;
        }

        // Гарантируется, что среднее — целое число
        long average = sum / array.length;

        // Второй проход: подсчёт элементов строго больше среднего
        int count = 0;
        for (int value : array) {
            if (value > average) {
                count++;
            }
        }

        return count;
    }
}