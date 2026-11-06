package ru.bmstu.lab01.task_11;

public class NearestToAverageFinder {

    /**
     * Возвращает элемент массива, ближайший к среднему арифметическому.
     *
     * <p>Если несколько элементов одинаково близки к среднему,
     * возвращается первый по индексу.
     *
     * @param array входной массив целых чисел
     * @return элемент, ближайший к среднему
     */
    public static int findClosestToAverage(int[] array) {

        long sum = 0L;
        for (int value : array) {
            sum += value;
        }

        long n = array.length;
        int best = array[0];
        long bestDiff = Math.abs((long) array[0] * n - sum);

        for (int i = 1; i < array.length; i++) {
            long diff = Math.abs((long) array[i] * n - sum);
            // Строгое < — при равенстве сохраняется первый по индексу
            if (diff < bestDiff) {
                bestDiff = diff;
                best = array[i];
            }
        }

        return best;
    }
}