/*
 * Задача 38. Циклический сдвиг влево.
 * Вход: массив из n целых чисел и число k.
 * Выход: массив, сдвинутый влево на k позиций.
 * Требование: O(n) времени, O(1) доп. памяти (через тройной разворот).
 */
package ru.bmstu.lab1.task_38;

/** Циклический сдвиг массива влево через три разворота. */
public final class RotateLeft {

    private RotateLeft() {}

    /**
     * Сдвигает массив влево на k позиций на месте.
     *
     * <p>Если массив = A B, где A - первые k элементов, то нужно получить B A. Разворачиваем A,
     * разворачиваем B, потом разворачиваем весь массив - получается B A.
     *
     * @param array массив (изменяется)
     * @param k на сколько сдвинуть
     */
    public static void rotate(int[] array, int k) {
        int n = array.length;
        if (n == 0) {
            return;
        }
        // сдвиг на n ничего не меняет, поэтому берём остаток
        k = k % n;
        if (k < 0) {
            k += n;
        }
        reverse(array, 0, k - 1);
        reverse(array, k, n - 1);
        reverse(array, 0, n - 1);
    }

    private static void reverse(int[] array, int left, int right) {
        while (left < right) {
            int tmp = array[left];
            array[left] = array[right];
            array[right] = tmp;
            left++;
            right--;
        }
    }
}
