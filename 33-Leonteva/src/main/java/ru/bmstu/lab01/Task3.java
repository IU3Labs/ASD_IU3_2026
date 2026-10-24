/*Задача 3. Подсчёт вхождений
Вход: массив из (n) целых чисел и число (x).
Выход: количество элементов, равных (x).
Сложность: базовая*/

package ru.bmstu.lab01;

//Подсчитывает количество вхождений заданного числа в целочисленный массив.
public final class Task3 {

    private Task3() {
        // утилитарный класс
    }

    public static int countOccurrences(int[] array, int target) {
        if (array == null) {
            throw new IllegalArgumentException("array must not be null");
        }

        int count = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == target) {
                count++;
            }
        }
        return count;
    }
}