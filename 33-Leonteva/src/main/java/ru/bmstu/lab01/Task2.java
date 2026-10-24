/*Задача 2. Индекс первого вхождения
Вход: массив из (n) целых чисел и число (x).
Выход: индекс первого элемента, равного (x). Если такого нет — вывести -1.
Сложность: базовая*/

package ru.bmstu.lab01;


//Находит индекс первого вхождения заданного числа в целочисленный массив.
public final class Task2 {

    private static final int NOT_FOUND = -1;

    private Task2() {
        // утилитарный класс
    }

    public static int indexOfFirst(int[] array, int target) {
        if (array == null) {
            throw new IllegalArgumentException("array must not be null");
        }

        for (int i = 0; i < array.length; i++) {
            if (array[i] == target) {
                return i;
            }
        }
        return NOT_FOUND;
    }
}