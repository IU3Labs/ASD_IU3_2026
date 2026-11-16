/*Задача 10. Поиск элемента с условием
Вход: массив из (n) целых чисел и число (k).
Выход: индекс последнего элемента, кратного (k). Если такого нет — вывести -1.
Ограничения: (k != 0).
Сложность: базовая*/

package ru.bmstu.lab01;


//Находит индекс последнего элемента массива, кратного заданному числу k.
public final class Task10 {

    private static final int NOT_FOUND = -1;

    private Task10() {
        // утилитарный класс
    }

    public static int lastMultipleIndex(int[] array, int k) {
        if (array == null) {
            throw new IllegalArgumentException("array must not be null");
        }
        if (k == 0) {
            throw new IllegalArgumentException("k must not be zero");
        }

        for (int i = array.length - 1; i >= 0; i--) {
            if (array[i] % k == 0) {
                return i;
            }
        }
        return NOT_FOUND;
    }
}