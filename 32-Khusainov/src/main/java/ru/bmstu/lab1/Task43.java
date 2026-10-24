package ru.bmstu.lab1;

/**
 * Решение задачи 43: перемещение нулей в конец массива.
 */
public class Task43 {

    /**
     * Перемещает все нули в конец массива, сохраняя порядок остальных элементов.
     * Модификация происходит на месте.
     *
     * @param array входной массив целых чисел
     */
    public static void moveZeroes(int[] array) {
        int insertPos = 0;

        for (int i = 0; i < array.length; i++) {
            if (array[i] != 0) {
                array[insertPos] = array[i];
                insertPos++;
            }
        }

        for (int i = insertPos; i < array.length; i++) {
            array[i] = 0;
        }
    }
}