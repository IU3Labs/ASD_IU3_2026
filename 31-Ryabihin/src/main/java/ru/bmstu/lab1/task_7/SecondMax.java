/*
 * Задача 7. Второй максимум.
 * Вход: массив из n целых чисел, все элементы различны.
 * Выход: второй по величине элемент массива.
 */
package ru.bmstu.lab1.task_7;

/** Поиск второго по величине элемента за один проход. */
public final class SecondMax {

    private SecondMax() {}

    /**
     * Находит второй максимум. Храним два самых больших элемента и обновляем их по ходу.
     *
     * @param array массив минимум из двух различных элементов
     * @return второй по величине элемент
     */
    public static int find(int[] array) {
        if (array.length < 2) {
            throw new IllegalArgumentException("Нужно хотя бы два элемента");
        }
        int first = Math.max(array[0], array[1]);
        int second = Math.min(array[0], array[1]);
        for (int i = 2; i < array.length; i++) {
            if (array[i] > first) {
                // старый максимум становится вторым
                second = first;
                first = array[i];
            } else if (array[i] > second) {
                second = array[i];
            }
        }
        return second;
    }
}
