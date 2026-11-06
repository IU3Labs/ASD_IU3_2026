package ru.bmstu.lab01.task_07;

public class SecondMaxFinder {

    /**
     * Возвращает второй по величине элемент массива.
     *
     * @param array входной массив целых чисел (элементы различны)
     * @return второй по величине элемент
     */
    public static int findSecondMax(int[] array) {

        int max;
        int secondMax;

        if (array[0] > array[1]) {
            max = array[0];
            secondMax = array[1];
        } else {
            max = array[1];
            secondMax = array[0];
        }

        for (int i = 2; i < array.length; i++) {
            if (array[i] > max) {
                secondMax = max;
                max = array[i];
            } else if (array[i] > secondMax) {
                secondMax = array[i];
            }
        }

        return secondMax;
    }
}