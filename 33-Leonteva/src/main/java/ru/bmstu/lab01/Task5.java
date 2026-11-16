/*Задача 5. Сумма элементов на чётных позициях
Вход: массив из (n) целых чисел.
Выход: сумма элементов с чётными индексами (0, 2, 4, …).
Сложность: базовая*/

package ru.bmstu.lab01;

//Вычисляет сумму элементов массива, стоящих на чётных индексах
public final class Task5 {

    private Task5() {
        // утилитарный класс
    }

    public static long sumEvenIndices(int[] array) {
        if (array == null) {
            throw new IllegalArgumentException("array must not be null");
        }

        long sum = 0;
        for (int i = 0; i < array.length; i += 2) {
            sum += array[i];
        }
        return sum;
    }
}