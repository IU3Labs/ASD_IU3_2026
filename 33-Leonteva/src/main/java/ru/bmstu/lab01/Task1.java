/*Задача 1. Максимум и минимум
Вход: массив из (n) целых чисел.
Выход: минимальный и максимальный элементы массива.
Ограничения: (1 < n < 10^5).
Сложность: базовая*/

package ru.bmstu.lab01;


public final class Task1 {

    //n > 1
    private static final int MIN_SIZE = 2;

    //n < 10^5
    private static final int MAX_SIZE = 100000;

    private Task1() {
        // утилитарный класс
    }

    public static int[] findMinMax(int[] array) {
        validate(array);

        int min = array[0];
        int max = array[0];

        for (int i = 1; i < array.length; i++) {
            int value = array[i];
            if (value < min) {
                min = value;
            }
            if (value > max) {
                max = value;
            }
        }

        return new int[] {min, max};
    }

    // Проверяет корректность
    private static void validate(int[] array) {
        if (array == null) {
            throw new IllegalArgumentException("array must not be null");
        }
        if (array.length < MIN_SIZE || array.length > MAX_SIZE) {
            throw new IllegalArgumentException(
                    "array length must be in range (" + MIN_SIZE + ", " + MAX_SIZE + ")");
        }
    }
}