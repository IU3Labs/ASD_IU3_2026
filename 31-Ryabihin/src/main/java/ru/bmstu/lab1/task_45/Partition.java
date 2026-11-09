/*
 * Задача 45. Разбиение по опорному элементу.
 * Вход: массив из n целых чисел и опорное значение p.
 * Выход: сначала элементы < p, потом = p, потом > p (порядок внутри групп не важен).
 * Требование: O(n) времени, O(1) доп. памяти.
 */
package ru.bmstu.lab1.task_45;

/** Разбиение массива на три части относительно опорного значения. */
public final class Partition {

    private Partition() {}

    /**
     * Переставляет элементы: сначала меньше pivot, потом равные, потом больше.
     *
     * <p>Три указателя: всё левее less - меньше pivot, от less до i - равно pivot, правее greater -
     * больше pivot. Элемент i смотрим и отправляем в нужную часть.
     *
     * @param array массив (изменяется)
     * @param pivot опорное значение
     */
    public static void partition(int[] array, int pivot) {
        int less = 0;
        int i = 0;
        int greater = array.length - 1;
        while (i <= greater) {
            if (array[i] < pivot) {
                swap(array, less, i);
                less++;
                i++;
            } else if (array[i] > pivot) {
                // i не увеличиваем: элемент, пришедший справа, ещё не проверен
                swap(array, i, greater);
                greater--;
            } else {
                i++;
            }
        }
    }

    private static void swap(int[] array, int i, int j) {
        int tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }
}
