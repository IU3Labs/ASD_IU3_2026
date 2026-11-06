package ru.bmstu.lab1;

/**
 * Решение задачи 2: поиск индекса первого вхождения элемента в массив.
 */
public class Task2 {

    /**
     * Находит индекс первого элемента массива, равного заданному значению.
     *
     * @param array входной массив целых чисел
     * @param x     искомое значение
     * @return индекс первого вхождения {@code x} в {@code array}, либо {@code -1}, 
     * если элемент не найден
     */
    public static int findFirstOccurrence(int[] array, int x) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == x) {
                return i;
            }
        }

        return -1;
    }
}