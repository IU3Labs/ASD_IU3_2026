package ru.bmstu.lab01.task_02;

public class FirstOccurrenceFinder {

    /**
     * Возвращает индекс первого элемента массива, равного {@code x}.
     *
     * <p>Если элемент не найден, возвращается
     * {@code -1}.
     *
     * @param array входной массив целых чисел
     * @param x     искомое значение
     * @return индекс первого вхождения {@code x} или {@code -1}, если
     *         элемент не найден
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