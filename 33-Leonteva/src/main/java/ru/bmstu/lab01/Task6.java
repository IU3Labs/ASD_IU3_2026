/*Задача 6. Элементы больше среднего
Вход: массив из (n) целых чисел.
Выход: количество элементов, строго больших среднего арифметического всех элементов.
Гарантируется, что среднее — целое число (сумма делится на (n)).
Сложность: базовая*/

package ru.bmstu.lab01;

//Считает количество элементов, строго больших среднего арифметического массива.
public final class Task6 {

    private Task6() {
        // утилитарный класс
    }

    public static int countGreaterThanAverage(int[] array) {
        if (array == null) {
            throw new IllegalArgumentException("array must not be null");
        }
        if (array.length == 0) {
            throw new IllegalArgumentException("array must not be empty");
        }

        long sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
        }
        long average = sum / array.length;

        int count = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] > average) {
                count++;
            }
        }
        return count;
    }
}